package com.example.fetchapi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fetchapi.MainActivity;
import com.example.fetchapi.R;
import com.example.fetchapi.api.ApiService;
import com.example.fetchapi.api.RetrofitClient;
import com.example.fetchapi.model.TokenResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText instanceEdit, usernameEdit, passwordEdit;
    private Button loginBtn;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        instanceEdit = findViewById(R.id.instance);
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        loginBtn = findViewById(R.id.credintialsSubmit);

        instanceEdit.setText("idfuatpwa");
        usernameEdit.setText("ajm-01");
        passwordEdit.setText("ajm-01");

        Retrofit retrofit = RetrofitClient.getClient("https://idfuat.salesdiary.in:4003/");
        apiService = retrofit.create(ApiService.class);

        loginBtn.setOnClickListener(v -> {
            String instance = instanceEdit.getText().toString().trim();
            String username = usernameEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            if(instance.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> credentials = new HashMap<>();
            credentials.put("instance", instance);
            credentials.put("username", username);
            credentials.put("password", password);

            apiService.authenticate(credentials).enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().result != null) {

                        String token = response.body().result.access_token;

                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "No Internet or failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}