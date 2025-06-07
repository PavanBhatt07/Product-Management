package com.example.fetchapi.model;

import java.util.List;
public class ProductResponse {
    public ProductResult results;

    public class ProductResult {
        public int status;
        public String msg;
        public List<Product> data;
    }
}
