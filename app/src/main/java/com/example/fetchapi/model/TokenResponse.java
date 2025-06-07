package com.example.fetchapi.model;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    public Result result;

    public class Result {
        public int status;
        public String msg;
        public String access_token;
    }
}