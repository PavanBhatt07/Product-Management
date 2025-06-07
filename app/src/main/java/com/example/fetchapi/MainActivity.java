package com.example.fetchapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fetchapi.activity.AddProductActivity;
import com.example.fetchapi.activity.GoToCartActivity;
import com.example.fetchapi.activity.OrderHistoryActivity;
import com.example.fetchapi.activity.ProductDetailActivity;
import com.example.fetchapi.adapter.ProductAdapter;
import com.example.fetchapi.database.DatabaseHelper;
import com.example.fetchapi.database.DatabaseHelperCart;
import com.example.fetchapi.model.Cart;
import com.example.fetchapi.model.Product;
import com.example.fetchapi.repository.Repository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private Button addButton;
    private Repository repository;
    private Button cartButton;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.mainAddBtn);
        cartButton = findViewById(R.id.mainGoToCart);
        historyButton = findViewById(R.id.mainOrderHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Button addToCartBtn = findViewById(R.id.mainAddToCart);

        DatabaseHelperCart cartDB = new DatabaseHelperCart(this);

        dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();



        List<Product> dbProducts = dbHelper.getAllProducts();

        if (dbProducts.isEmpty()) {
            loadFromAPI();
        } else {
            loadFromAPI();
        }

        addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });

        cartButton.setOnClickListener(v -> {
            startActivity(new Intent(this, GoToCartActivity.class));
        });

        historyButton.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        });

//        addToCartBtn.setOnClickListener(v -> {
//            List<Product> allProducts = adapter.getProductList();
//            int addedCount = 0;
//
//            for (Product p : allProducts) {
//                int qty = Integer.parseInt(p.getQuantity());
//                if (qty > 0) {
//                    Cart cart = new Cart();
//                    cart.setName(p.getName());
//                    cart.setBrand(p.getBrand());
//                    cart.setCategory(p.getCategory());
//                    cart.setQuantity(String.valueOf(qty));
//                    cartDB.InsertIntoCart(cart);
//                    addedCount++;
//                }
//            }
//            if (addedCount > 0) {
//                Toast.makeText(this, addedCount + " product added to cart", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No products selected", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }




    private void loadFromDB() {
        List<Product> products = dbHelper.getAllProducts();
        DatabaseHelperCart cartDB = new DatabaseHelperCart(this);
        adapter = new ProductAdapter(products, this::onProductClick, cartDB);
        recyclerView.setAdapter(adapter);
    }

    private void loadFromAPI() {
        repository = new Repository(getIntent().getStringExtra("token"));
        repository.fetchProducts(this, products -> {
            loadFromDB(); // ✅ only reload UI with updated DB
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadFromDB();
    }
}
