package com.brookleaf.quote.service;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.converter.StringQuoteConverter;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.*;
import static org.apache.commons.lang3.StringUtils.*;

@Service
public class SparkQuoteService implements QuoteService {
    private static final String QUOTE_REGEX = ".*(%s).*(;).*";
    private static final String AUTHOR_REGEX = ".*(;).*(%s).*(;).*";
    private static final String CATEGORIES_REGEX = ".*(;).*(;).*";
    
    private String dataPath;
    
    private JavaSparkContext sparkContext;
    private StringQuoteConverter stringQuoteConverter;
    
    private Function<String, List<Quote>> findText = regex -> {
        List<String> matchingLines = sparkContext.textFile(dataPath)
                .filter(line -> line.matches(regex))
                .collect();
    
        List<Quote> quotes = matchingLines.stream()
                .map(line -> stringQuoteConverter.convert(line))
                .filter(quoteOptional -> quoteOptional.isPresent())
                .map(quoteOptional -> quoteOptional.get())
                .collect(Collectors.toList());
    
        return quotes;
    };
    
    public SparkQuoteService(@Autowired JavaSparkContext sparkContext,
                             @Autowired StringQuoteConverter stringQuoteConverter,
                             @Value("${data.path}") String dataPath) {
        this.sparkContext = sparkContext;
        this.stringQuoteConverter = stringQuoteConverter;
        this.dataPath = dataPath;
    }
    
    @Override
    public List<Quote> findMatchingQuotes(String text) {
        if(isEmpty(text)) {
            return emptyList();
        }
    
        String regex = String.format(QUOTE_REGEX, text);
        return findText.apply(regex);
    }
    
    @Override
    public List<Quote> findQuotesByAuthor(String author) {
        if(isEmpty(author)) {
            return emptyList();
        }
    
        String regex = String.format(AUTHOR_REGEX, author);
        return findText.apply(regex);
    }
    
    @Override
    public List<Quote> findQuotesByCategories(List<String> categories) {
        if(isListEmpty(categories)) {
            return emptyList();
        }
        
        String regexSuffix = getRegexSuffix(categories);
        String categoriesRegex = CATEGORIES_REGEX + regexSuffix;
        String regex = String.format(categoriesRegex, categories.toArray());
        
        return findText.apply(regex);
    }
    
    private boolean isListEmpty(List<?> elements) {
        if(elements == null || elements.size() == 0) {
            return true;
        }
        
        return false;
    }
    
    private String getRegexSuffix(List<String> categories) {
        StringJoiner regex = new StringJoiner("|", "(", ")");
        categories.forEach(category -> regex.add("%s"));
        return regex.toString();
    }
}
