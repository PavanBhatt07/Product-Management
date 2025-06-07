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

public class EditProductActivity extends AppCompatActivity {

    private EditText eName, eSKU, eBrand, eCategory, eDescription, ePrice, eGST;
    private Button eUpdateBtn;
    private DatabaseHelper dbHelper;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        dbHelper = new DatabaseHelper(this);

        eName = findViewById(R.id.eAddProduct);
        eSKU = findViewById(R.id.eSKU);
        eBrand = findViewById(R.id.eBrand);
        eCategory = findViewById(R.id.eCategory);
        eDescription = findViewById(R.id.eDescription);
        ePrice = findViewById(R.id.ePOR);
        eGST = findViewById(R.id.eGST);
        eUpdateBtn = findViewById(R.id.eAddProductBtn);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");

            if (product != null) {
                productId = product.getId();
                eName.setText(product.getName());
                eSKU.setText(product.getSku());
                eBrand.setText(product.getBrand());
                eCategory.setText(product.getCategory());
                eDescription.setText(product.getDescription());
                ePrice.setText(String.valueOf(product.getPriceToRetailer()));
                eGST.setText(product.getGst());
            }
        }

        eUpdateBtn.setOnClickListener(v -> {
            String name = eName.getText().toString().trim();
            String sku = eSKU.getText().toString().trim();
            String brand = eBrand.getText().toString().trim();
            String category = eCategory.getText().toString().trim();
            String description = eDescription.getText().toString().trim();
            String gst = eGST.getText().toString().trim();
            double price;

            try {
                price = Double.parseDouble(ePrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty() || sku.isEmpty()) {
                Toast.makeText(this, "Product Name and SKU are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Product updatedProduct = new Product(brand, category, description, gst, productId, name, price, sku);
//            byte[] dummyImageBytes = new byte[0];
            dbHelper.updateProduct(updatedProduct);

            Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}
