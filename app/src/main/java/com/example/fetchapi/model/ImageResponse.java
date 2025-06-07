package com.example.fetchapi.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {
    public ImageResult results;

    public static class ImageResult {
        public int status;
        public String msg;

        @SerializedName("file_data")
        public String fileData;
    }
}
