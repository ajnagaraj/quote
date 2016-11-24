package com.brookleaf.quote.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@PropertySource("classpath:/sparklab.properties")
public class SparkConfiguration {
    
    @Bean
    JavaSparkContext sparkContext(@Value("${spark.application.name}") String appName,
                                  @Value("${spark.application.master}") String master) {
        SparkConf sparkConf = new SparkConf().setAppName(appName).setMaster(master);
        return new JavaSparkContext(sparkConf);
    }
    
    
    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
