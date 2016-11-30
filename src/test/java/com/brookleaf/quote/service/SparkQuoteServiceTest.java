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
    
    @Test
    public void shouldReturnEmptyQuotesIfATextDoesNotMatch() {
        List<Quote> quotes = sparkQuoteService.findMatchingQuotes("Effective");
        
        assertThat(quotes).isEmpty();
    }
    
    @Test
    public void shouldReturnEmptyQuotesIfATextIsNull() {
        List<Quote> quotes = sparkQuoteService.findMatchingQuotes(null);
        
        assertThat(quotes).isEmpty();
    }
    
    @Test
    public void shouldReturnEmptyQuotesIfATextIsEmpty() {
        List<Quote> quotes = sparkQuoteService.findMatchingQuotes("");
        
        assertThat(quotes).isEmpty();
    }
    
    @Test
    public void shouldFindQuotesThatMatchAnAuthor() {
        String text = "Be pleasant until ten o'clock in the morning and the rest of the day will take care of itself.";
        Quote expectedQuote = Quote.builder()
                .withText(text)
                .withAuthor("Elbert Hubbard")
                .withCategories(asList("Meditation"))
                .build();
    
    
        List<Quote> quotes = sparkQuoteService.findQuotesByAuthor("Elbert");
    
        assertThat(quotes).containsOnly(expectedQuote);
    }
    
    @Test
    public void shouldFindQuotesThatMatchACategory() {
        String text = "Be pleasant until ten o'clock in the morning and the rest of the day will take care of itself.";
        Quote expectedQuote = Quote.builder()
                .withText(text)
                .withAuthor("Elbert Hubbard")
                .withCategories(asList("Meditation"))
                .build();
        
        
        List<Quote> quotes = sparkQuoteService.findQuotesByCategories(asList("Meditation", "Moral"));
        
        assertThat(quotes).containsOnly(expectedQuote);
    }
}
