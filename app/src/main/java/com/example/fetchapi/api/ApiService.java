package com.example.fetchapi.api;

import com.example.fetchapi.model.ImageResponse;
import com.example.fetchapi.model.ProductResponse;
import com.example.fetchapi.model.TokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/res_users/authenticateSystemUser")
    Call<TokenResponse> authenticate(@Body Map<String, String> credentials);

    @POST("api/product_products/getProductsListPWA")
    Call<ProductResponse> getProducts(@Query("access_token") String token, @Body Map<String, Object> empty);

    @POST("api/product_products/fetch_file")
    Call<ImageResponse> getImage(@Query("access_token") String token, @Body Map<String, Object> empty);
}