package com.example.fetchapi.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.fetchapi.api.ApiService;
import com.example.fetchapi.api.RetrofitClient;
import com.example.fetchapi.database.DatabaseHelper;
import com.example.fetchapi.model.ImageResponse;
import com.example.fetchapi.model.Product;
import com.example.fetchapi.model.ProductResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    private ApiService apiService;
    private String token;

    public Repository(String token) {
        this.token = token;
        Retrofit retrofit = RetrofitClient.getClient("https://idfuat.salesdiary.in:4003/");
        apiService = retrofit.create(ApiService.class);
    }

    public void fetchProducts(Context context, Consumer<List<Product>> callback) {
        apiService.getProducts(token, new HashMap<>()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().results != null) {
                    List<Product> products = response.body().results.data;

                    // Step 1: Fetch image separately
                    apiService.getImage(token, new HashMap<>()).enqueue(new Callback<ImageResponse>() {
                        @Override
                        public void onResponse(Call<ImageResponse> call, Response<ImageResponse> imgResponse) {
                            if (imgResponse.isSuccessful() && imgResponse.body() != null && imgResponse.body().results != null) {
                                String base64Image = imgResponse.body().results.fileData;
                                byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);

                                Log.d("IMAGE_RESPONSE", "Image length: " + decodedBytes.length);


                                Log.d("DB_IMAGE", "Image byte length = " + decodedBytes.length);


                                DatabaseHelper dbHelper = new DatabaseHelper(context);
                                for (Product p : products) {
                                    dbHelper.insertProduct(p, decodedBytes);  // ✅ insert product with image
                                }

                                callback.accept(products);
                                Toast.makeText(context, "Products + Image saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to fetch image", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ImageResponse> call, Throwable t) {
                            Toast.makeText(context, "Image fetch error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(context, "Product fetch error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}