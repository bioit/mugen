package com.arexis.mugen.search;

public class Keyword {
    
    private String word;
    
    public Keyword(String word) {
        this.word = word.toLowerCase();
    }
    
    public String getKeyword() {
        return word;
    }
    
}
