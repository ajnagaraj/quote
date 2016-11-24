package com.brookleaf.quote.service;

import com.brookleaf.quote.Quote;

import java.util.List;

import static com.brookleaf.quote.Quote.*;

public interface QuoteService {
    List<Quote> findMatchingQuotes(String text);
    List<Quote> findQuotesByAuthor(String author);
    List<Quote> findQuotesByType(QuoteType quoteType);
}
