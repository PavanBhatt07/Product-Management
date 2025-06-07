package com.example.fetchapi.adapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fetchapi.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchapi.database.DatabaseHelper;
import com.example.fetchapi.database.DatabaseHelperCart;
import com.example.fetchapi.model.Cart;
import com.example.fetchapi.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnItemClickListener listener;
    private DatabaseHelperCart dbHelperCart;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public List<Product> getProductList() {
        return productList;
    }



    public ProductAdapter(List<Product> products, OnItemClickListener listener, DatabaseHelperCart dbHelperCart) {
        this.productList = products;
        this.listener = listener;
        this.dbHelperCart = dbHelperCart;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.name.setText(p.getName());
        holder.brand.setText(p.getBrand());
        holder.category.setText(p.getCategory());
        holder.price.setText(String.valueOf("₹ " + p.getPriceToRetailer()));
        holder.imageView.setImageResource(p.getImage());

        holder.bind(p, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand, category, price;
        EditText quantity;
        Button incrementBtn, decrementBtn;
        DatabaseHelperCart dbHelperCart;
        ImageView imageView;


        public ProductViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.lProductName);
            brand = view.findViewById(R.id.lBrand);
            category = view.findViewById(R.id.lCategory);
            price = view.findViewById(R.id.lPrice);
            quantity = view.findViewById(R.id.lQuantity);
            incrementBtn = view.findViewById(R.id.lIncrementBtn);
            decrementBtn = view.findViewById(R.id.lDecrementBtn);
            dbHelperCart = new DatabaseHelperCart(view.getContext());
            imageView = view.findViewById(R.id.lImage);
        }

        public void bind(Product product, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(product));



            String qtyFromCart = dbHelperCart.getQuantity(product.getName());
            quantity.setText(qtyFromCart);
            product.setQuantity(qtyFromCart);


            String currQty = quantity.getText().toString().trim();
            String dbQty = dbHelperCart.getQuantity(product.getName());

            if (!currQty.equals(dbQty)) {
                int qty = Integer.parseInt(currQty);
                dbHelperCart.updateQuantity(product.getName(), String.valueOf(qty));


                if (qty == 0) {
                    dbHelperCart.deleteFromCart(product.getName());
                } else if (dbHelperCart.isProductInCart(product.getName())) {
                    dbHelperCart.updateQuantity(product.getName(), currQty);
                } else {
                    Cart cart = new Cart(product.getName(), product.getBrand(), product.getCategory(), currQty);
                    dbHelperCart.InsertIntoCart(cart);
                }
            }


            incrementBtn.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                currentQuantity++;
                quantity.setText(String.valueOf(currentQuantity));
                product.setQuantity(String.valueOf(currentQuantity));



                if (dbHelperCart.isProductInCart(product.getName())) {
                    dbHelperCart.updateQuantity(product.getName(), String.valueOf(currentQuantity));
                } else {
                    Cart cart = new Cart(product.getName(), product.getBrand(), product.getCategory(), String.valueOf(currentQuantity));
                    dbHelperCart.InsertIntoCart(cart);
                }

            });

            decrementBtn.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());


                if (currentQuantity > 0) {
                    currentQuantity--;
                    quantity.setText(String.valueOf(currentQuantity));
                    product.setQuantity(String.valueOf(currentQuantity));


                    if (currentQuantity == 0) {
                        dbHelperCart.deleteFromCart(product.getName());
                    } else {
                        dbHelperCart.updateQuantity(product.getName(), String.valueOf(currentQuantity));
                    }
                }
            });


        }
    }

}