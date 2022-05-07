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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private ArrayList<EventItem> mEventList;
    private Context mContext;


    public EventAdapter(Context context, ArrayList<EventItem> eventList){
        this.mEventList = eventList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public EventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_items,parent,false);
        return new EventHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventHolder holder, int position) {
        String eventName = mEventList.get(position).getEventName();
        String date = mEventList.get(position).getEventDate();
        String location = mEventList.get(position).getEventLocation();
        String remaining = String.valueOf(mEventList.get(position).getUnsoldTickets());
        String price = String.valueOf(mEventList.get(position).getTicketPrice()) + " Ft";

        holder.eventName.setText(eventName);
        holder.eventDate.setText(date);
        holder.eventLocation.setText(location);
        holder.unsoldTickets.setText(remaining);
        holder.ticketPrice.setText(price);

        holder.addToCart.setOnClickListener(view -> ((EventsActivity)mContext).addToCart(mEventList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView eventDate;
        private TextView eventLocation;
        private TextView unsoldTickets;
        private TextView ticketPrice;
        private Button addToCart;

        public EventHolder(final View view){
            super(view);
            eventName = view.findViewById(R.id.eventName);
            eventDate = view.findViewById(R.id.eventDate);
            eventLocation = view.findViewById(R.id.eventLocation);
            unsoldTickets = view.findViewById(R.id.unsoldTickets);
            ticketPrice = view.findViewById(R.id.ticketPrice);
            addToCart = view.findViewById(R.id.addButton);
        }
    }
}
