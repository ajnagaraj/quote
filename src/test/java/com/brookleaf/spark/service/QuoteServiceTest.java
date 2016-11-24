package com.brookleaf.spark.service;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.configuration.SparkConfiguration;
import com.brookleaf.quote.service.QuoteService;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SparkConfiguration.class)
public class QuoteServiceTest {
    
    @Autowired
    JavaSparkContext sparkContext;
    @Autowired
    QuoteService quoteService;
    
    @Test
    public void shouldFindQuotesThatMatchAText() {
        Quote expectedQuote = Quote.builder()
                .withText("Difficulties strengthen the mind, as labor does the body.")
                .withAuthor("Seneca")
                .withCategories(asList("Mind"))
                .build();
        
        
        List<Quote> quotes = quoteService.findMatchingQuotes("Difficulties");
        
        assertThat(quotes).containsOnly(expectedQuote);
    }
}
