package com.brookleaf.quote.converter;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.configuration.SparkConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SparkConfiguration.class)
public class StringQuoteConverterTest {
    
    @Autowired
    StringQuoteConverter stringQuoteConverter;
    
    @Test
    public void validQuoteLineShouldBeConvertedToQuote() {
        String validLine = "Difficulties strengthen the mind, as labor does the body.;Seneca;Mind";
        
        Optional<Quote> quoteOptional = stringQuoteConverter.convert(validLine);
    
        assertThat(quoteOptional).isPresent();
        
        Quote quote = quoteOptional.get();
        assertThat(quote.getAuthor()).isEqualTo("Seneca");
        assertThat(quote.getText()).isEqualTo("Difficulties strengthen the mind, as labor does the body.");
        assertThat(quote.getCategories()).containsOnly("Mind");
    }
    
    @Test
    public void emptyQuoteLineShouldBeConvertedToEmptyOptional() {
        String emptyLine = "";
        
        Optional<Quote> quoteOptional = stringQuoteConverter.convert(emptyLine);
        
        assertThat(quoteOptional).isEmpty();
    }
    
    @Test
    public void nullQuoteLineShouldBeConvertedToEmptyOptional() {
        String nullLine = null;
        
        Optional<Quote> quoteOptional = stringQuoteConverter.convert(nullLine);
        
        assertThat(quoteOptional).isEmpty();
    }
    
    @Test
    public void invalidQuoteLineShouldBeConvertedToEmptyOptional() {
        String invalidLine = "Difficulties strengthen the mind, as labor does the body.$SenecaMind";
        
        Optional<Quote> quoteOptional = stringQuoteConverter.convert(invalidLine);
        
        assertThat(quoteOptional).isEmpty();
    }
}
