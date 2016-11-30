package com.brookleaf.quote.configuration;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.converter.StringQuoteConverter;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.*;


@Configuration
@ComponentScan("com.brookleaf.quote")
@PropertySource("classpath:/quote.properties")
public class SparkConfiguration {
    
    @Bean
    JavaSparkContext sparkContext(@Value("${spark.application.name}") String appName,
                                  @Value("${spark.application.master}") String master,
                                  @Value("${data.path}") String dataPath,
                                  @Value("${quote.regex}") String regex) {
        SparkConf sparkConf = new SparkConf().setAppName(appName).setMaster(master);
        return new JavaSparkContext(sparkConf);
    }
    
    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public ConversionServiceFactoryBean conversionService(StringQuoteConverter stringQuoteConverter) {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
    
        List<Converter> converters = asList(stringQuoteConverter);
        conversionServiceFactoryBean.setConverters(new HashSet<>(converters));
        return conversionServiceFactoryBean;
    }
}
