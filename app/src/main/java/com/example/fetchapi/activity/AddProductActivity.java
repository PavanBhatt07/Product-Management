package com.example.fetchapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchapi.MainActivity;
import com.example.fetchapi.R;
import com.example.fetchapi.database.DatabaseHelper;
import com.example.fetchapi.model.Product;

public class AddProductActivity extends AppCompatActivity {

    private EditText mAddProduct, mSKU, mBrand, mCategory, mDescription, mPTR, mGST;
    private Button mAddProductBtn;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mAddProduct = findViewById(R.id.mAddProduct);
        mSKU = findViewById(R.id.mSKU);
        mBrand = findViewById(R.id.mBrand);
        mCategory = findViewById(R.id.mCategory);
        mDescription = findViewById(R.id.mDescription);
        mPTR = findViewById(R.id.mPTR);
        mGST = findViewById(R.id.mGST);
        mAddProductBtn = findViewById(R.id.mAddProductBtn);

        dbHelper = new DatabaseHelper(this);

        mAddProductBtn.setOnClickListener(v -> {
            String name = mAddProduct.getText().toString().trim();
            String sku = mSKU.getText().toString().trim();
            String brand = mBrand.getText().toString().trim();
            String category = mCategory.getText().toString().trim();
            String description = mDescription.getText().toString().trim();
            String gst = mGST.getText().toString().trim();
            double price;

            try {
                price = Double.parseDouble(mPTR.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price entered", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty() || sku.isEmpty()) {
                Toast.makeText(this, "Product name and SKU are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = new Product(brand, category, description, gst, 0, name, price, sku);
            dbHelper.insertProduct(product, new byte[0]);


            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
