package com.brookleaf.quote.service;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.converter.StringQuoteConverter;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
import static java.util.Collections.*;

@Service
public class SparkQuoteService implements QuoteService {
    private String dataPath;
    private String regex;
    
    private JavaSparkContext sparkContext;
    private StringQuoteConverter stringQuoteConverter;
    
    public SparkQuoteService(@Autowired JavaSparkContext sparkContext,
                             @Autowired StringQuoteConverter stringQuoteConverter,
                             @Value("${data.path}") String dataPath,
                             @Value("${quote.regex}") String regex) {
        this.sparkContext = sparkContext;
        this.stringQuoteConverter = stringQuoteConverter;
        this.dataPath = dataPath;
        this.regex = regex;
    }
    
    @Override
    public List<Quote> findMatchingQuotes(String text) {
        String quoteRegex = String.format(regex, text);
        
        List<String> matchingLines = sparkContext.textFile(dataPath)
                .filter(line -> line.matches(quoteRegex))
                .collect();
        
        List<Quote> quotes = matchingLines.stream()
                .map(line -> stringQuoteConverter.convert(line))
                .filter(quoteOptional -> quoteOptional.isPresent())
                .map(quoteOptional -> quoteOptional.get())
                .collect(Collectors.toList());
        
        return quotes;
    }
    
    @Override
    public List<Quote> findQuotesByAuthor(String author) {
        return null;
    }
    
    @Override
    public List<Quote> findQuotesByCategories(List<String> categories) {
        return null;
    }
}
