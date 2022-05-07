package com.example.kotprog_ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder>{
    private ArrayList<CartItem> mCartItem;
    private Context mContext;

    public CartAdapter(ArrayList<CartItem> mCartItem, Context mContext) {
        this.mCartItem = mCartItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartAdapter.CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
        return new CartAdapter.CartItemHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartItemHolder holder, int position) {
        String eventName = mCartItem.get(position).getEventName();
        String date = mCartItem.get(position).getEventDate();
        String location = mCartItem.get(position).getEventLocation();
        String price = String.valueOf(mCartItem.get(position).getTicketPrice()) + " Ft";

        holder.eventName.setText(eventName);
        holder.eventDate.setText(date);
        holder.eventLocation.setText(location);
        holder.ticketPrice.setText(price);

        holder.remove.setOnClickListener(view -> ((CartActivity)mContext).removeFromCart(mCartItem.get(position)));
    }

    @Override
    public int getItemCount() {
        return mCartItem.size();
    }

    public class CartItemHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView eventDate;
        private TextView eventLocation;
        private TextView ticketPrice;
        private Button remove;

        public CartItemHolder(final View view){
            super(view);
            eventName = view.findViewById(R.id.cartEventName);
            eventDate = view.findViewById(R.id.cartEventDate);
            eventLocation = view.findViewById(R.id.cartEventLocation);
            ticketPrice = view.findViewById(R.id.cartTicketPrice);
            remove = view.findViewById(R.id.removeButton);
        }
    }
}
