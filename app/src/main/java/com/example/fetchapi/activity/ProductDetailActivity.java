package com.example.fetchapi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchapi.MainActivity;
import com.example.fetchapi.R;
import com.example.fetchapi.database.DatabaseHelper;
import com.example.fetchapi.database.DatabaseHelperCart;
import com.example.fetchapi.model.Cart;
import com.example.fetchapi.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    Product product;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        TextView name, sku, brand, category, description, price, gst;

        EditText quantity;

        Button delete, update, addToCart, goToCart, incrementBtn, decrementBtn;
        dbHelper = new DatabaseHelper(this);

        name = findViewById(R.id.tvProductName);
        sku = findViewById(R.id.tvSKU);
        brand = findViewById(R.id.tvBrand);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);
        price = findViewById(R.id.tvPrice);
        gst = findViewById(R.id.tvGst);
//        quantity = findViewById(R.id.tvQuantity);
        delete = findViewById(R.id.tvDelete);
        update = findViewById(R.id.tvUpdate);
//        addToCart = findViewById(R.id.btnAddToCart);
//        goToCart = findViewById(R.id.btnGoToCart);
//        incrementBtn = findViewById(R.id.tvIncrementBtn);
//        decrementBtn = findViewById(R.id.tvDecrementBtn);


        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            name.setText("Product Name: " + product.getName());
            sku.setText("SKU: " + product.getSku());
            brand.setText("Brand: " + product.getBrand());
            category.setText("Category: " + product.getCategory());
            description.setText(product.getDescription());
            price.setText("Price to Retailer: ₹" + product.getPriceToRetailer());
            gst.setText("GST: " + product.getGst());
        }

        update.setOnClickListener(v -> {
            Intent updateIntent = new Intent(ProductDetailActivity.this, EditProductActivity.class);
            updateIntent.putExtra("product", product);
            startActivity(updateIntent);
        });

        delete.setOnClickListener(v -> {
            new AlertDialog.Builder(ProductDetailActivity.this)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean deleted = dbHelper.deleteProduct(product.getId());
                        if (deleted) {
                            Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Deletion failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

//        incrementBtn.setOnClickListener(v -> {
//            int currentQuantity = Integer.parseInt(quantity.getText().toString());
//            currentQuantity++;
//            quantity.setText(String.valueOf(currentQuantity));
//        });
//
//        decrementBtn.setOnClickListener(v -> {
//            int currentQuantity = Integer.parseInt(quantity.getText().toString());
//            if (currentQuantity > 0) {
//                currentQuantity--;
//                quantity.setText(String.valueOf(currentQuantity));
//            }
//        });
//
//        addToCart.setOnClickListener(v -> {
//            int qnty = Integer.parseInt(quantity.getText().toString());
//
//            if (product != null && qnty > 0) {
//                Cart cartItem = new Cart();
//                cartItem.setName(product.getName());
//                cartItem.setBrand(product.getBrand());
//                cartItem.setCategory(product.getCategory());
//                cartItem.setQuantity(String.valueOf(qnty));
//
//                DatabaseHelperCart cartDbHelper = new DatabaseHelperCart(ProductDetailActivity.this);
//                cartDbHelper.InsertIntoCart(cartItem);
//
//                Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ProductDetailActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        goToCart.setOnClickListener(v -> {
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//            startActivity(new Intent(ProductDetailActivity.this, GoToCartActivity.class));
//        });

    }
}

