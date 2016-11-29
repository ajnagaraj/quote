package com.brookleaf.quote.service;

import com.brookleaf.quote.Quote;
import com.brookleaf.quote.configuration.SparkConfiguration;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SparkConfiguration.class)
public class SparkQuoteServiceTest {
    
    @Autowired
    JavaSparkContext sparkContext;
    @Autowired
    QuoteService sparkQuoteService;
    
    @Test
    public void shouldFindQuotesThatMatchAText() {
        Quote expectedQuote = Quote.builder()
                .withText("Difficulties strengthen the mind, as labor does the body.")
                .withAuthor("Seneca")
                .withCategories(asList("Mind"))
                .build();
        
        
        List<Quote> quotes = sparkQuoteService.findMatchingQuotes("Difficulties");
        
        assertThat(quotes).containsOnly(expectedQuote);
    }
}
