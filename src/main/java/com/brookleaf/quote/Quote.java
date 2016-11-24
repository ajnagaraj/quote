package com.brookleaf.quote;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Quote {
    private final String text;
    private final String author;
    private final List<String> categories;
    
    private Quote(Builder builder) {
        this.text = builder.text;
        this.author = builder.author;
        this.categories = builder.categories;
    }
    
    public static class Builder {
        private String text;
        private String author;
        private List<String> categories;
        
        private Builder() {
            categories = new ArrayList<>();
        }
        
        public Builder withText(String text) {
            this.text = text;
            return this;
        }
        
        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }
        
        public Builder withCategories(List<String> categories) {
            this.categories.addAll(categories);
            return this;
        }
        
        public Quote build() {
            return new Quote(this);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public String getText() {
        return text;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public List<String> getCategories() {
        return Collections.unmodifiableList(categories);
    }
    
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
