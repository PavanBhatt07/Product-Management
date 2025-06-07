package com.example.fetchapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchapi.R;
import com.example.fetchapi.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartProducts;

    public CartAdapter(List<Cart> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_view_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartProducts.get(position);

        holder.name.setText(cart.getName());
        holder.brand.setText(cart.getBrand());
        holder.category.setText(cart.getCategory());
        holder.quantity.setText("Qty : "+cart.getQuantity());

    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand, category, quantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cProductName);
            brand = itemView.findViewById(R.id.cBrand);
            category = itemView.findViewById(R.id.cCategory);
            quantity = itemView.findViewById(R.id.cQuantity);
        }
    }
}
