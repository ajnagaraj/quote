package com.brookleaf.quote.converter;

import com.brookleaf.quote.Quote;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.*;
import static java.util.Collections.*;

@Component("stringQuoteConverter")
public class StringQuoteConverter implements Converter<String, Optional<Quote>> {
    
    @Value("${quote.delimiter}")
    private String quoteDelimiter;
    
    @Value("${quote.categories.delimiter}")
    private String categoryDelimiter;
    
    @Value("${quote.length}")
    private Integer length;
    
    @Override
    public Optional<Quote> convert(String quoteLine) {
        if(StringUtils.isEmpty(quoteLine)) {
            return Optional.empty();
        }
        
        String[] quoteSplit = quoteLine.split(quoteDelimiter);
        if(quoteSplit == null || quoteSplit.length != length) {
            return Optional.empty();
        }
        
        String text = quoteSplit[0];
        String author = quoteSplit[1];
        
        String[] categoriesSplit = quoteSplit[2].split(categoryDelimiter);
        List<String> categories = (categoriesSplit == null || categoriesSplit.length == 0)
                ? emptyList()
                : asList(categoriesSplit);
        
        Quote quote = Quote.builder()
                .withText(text)
                .withAuthor(author)
                .withCategories(categories)
                .build();
        
        return Optional.of(quote);
    }
}
