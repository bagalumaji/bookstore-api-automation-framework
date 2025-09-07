package com.bookstore.configs;

import org.aeonbits.owner.ConfigCache;

public class BookstoreConfigReader {
    private BookstoreConfigReader(){}
    public static BookstoreConfig getConfigs(){
        return ConfigCache.getOrCreate(BookstoreConfig.class);
    }
}