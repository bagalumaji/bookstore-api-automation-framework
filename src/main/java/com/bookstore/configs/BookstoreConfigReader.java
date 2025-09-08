package com.bookstore.configs;

import org.aeonbits.owner.ConfigCache;

public class BookstoreConfigReader {
    private BookstoreConfigReader(){}
    public static BookstoreConfig config(){
        return ConfigCache.getOrCreate(BookstoreConfig.class);
    }
}