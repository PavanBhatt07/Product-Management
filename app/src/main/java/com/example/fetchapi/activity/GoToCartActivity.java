package com.example.fetchapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchapi.MainActivity;
import com.example.fetchapi.R;
import com.example.fetchapi.adapter.CartAdapter;
import com.example.fetchapi.database.DatabaseHelperCart;
import com.example.fetchapi.database.DatabaseHelperHistory;
import com.example.fetchapi.model.Cart;
import com.example.fetchapi.model.History;

import java.util.List;
import java.util.Map;

public class GoToCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    DatabaseHelperCart databaseHelperCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_go_to_cart);

        databaseHelperCart = new DatabaseHelperCart(this);
        databaseHelperCart.getWritableDatabase();
        recyclerView = findViewById(R.id.cartRecyclerView);

        List<Cart> cartProducts = databaseHelperCart.getAllProductsFromCart();

        cartAdapter = new CartAdapter(cartProducts);

        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button checkoutBtn = findViewById(R.id.checkout);
        checkoutBtn.setOnClickListener(v -> {

            List<Cart> items = databaseHelperCart.getAllProductsFromCart();
            DatabaseHelperHistory databaseHistory = new DatabaseHelperHistory(this);


            Map<Integer, List<History>> grouped = databaseHistory.getHistory();
            int newOrderId = grouped.size() + 1;


            for (Cart cart : items) {
                History history = new History();
                history.setOrderId(newOrderId);
                history.setName(cart.getName());
                history.setBrand(cart.getBrand());
                history.setCategory(cart.getCategory());
                history.setQuantity(cart.getQuantity());
                databaseHistory.insertHistory(history);
            }


            databaseHelperCart.clearCart();


            Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
