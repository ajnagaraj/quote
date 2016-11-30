package com.brookleaf.quote.converter;

import com.brookleaf.quote.Quote;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.*;
import static java.util.Collections.*;

@Component("stringQuoteConverter")
public class StringQuoteConverter implements Converter<String, Optional<Quote>> {
    
    private final String quoteDelimiter;
    private final String categoryDelimiter;
    private final Integer length;
    
    public StringQuoteConverter(@Value("${quote.delimiter}") String quoteDelimiter,
                                @Value("${quote.categories.delimiter}") String categoryDelimiter,
                                @Value("${quote.length}") Integer length) {
        this.quoteDelimiter = quoteDelimiter;
        this.categoryDelimiter = categoryDelimiter;
        this.length = length;
    }
    
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
