package com.example.kotprog_ticket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartItem> mCartItems;
    private ArrayList<EventItem> mEventItems;
    private RecyclerView recyclerView;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private CartAdapter mAdapter;
    private NotificationService mNotificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mNotificationService = new NotificationService(this);

        recyclerView = findViewById(R.id.cart_recycle_view);

        mCartItems = new ArrayList<>();
        mEventItems = new ArrayList<>();
        setAdapter();
        QueryData();
    }

    @Override
    protected void onStop() {
        //Emulátoron (Pixel 3a API 30) működött, APK-ból telepítve valós telefonon futási hibát okozott (Samsung Galaxy S10)
        //mNotificationService.SendNotification("Ne felejtsd el megvenni a jegyeket!",getIntent());
        super.onStop();
    }

    private void QueryData() {
        mFirestore.collection("Cart")
                .whereEqualTo("userEmail",mUser.getEmail())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        CartItem item = document.toObject(CartItem.class);
                        item.setId(document.getId());
                        mCartItems.add(item);
                    }
                    mAdapter.notifyDataSetChanged();
                });
    }

    private void setAdapter(){
        mAdapter = new CartAdapter(mCartItems,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void removeFromCart(CartItem cartItem) {
        DocumentReference eventRef = mFirestore.collection("Events").document(cartItem.getEventId());
        eventRef.update("unsoldTickets", FieldValue.increment(1));

        DocumentReference cartRef = mFirestore.collection("Cart").document(cartItem._getId());
        cartRef.delete();

        mCartItems.clear();
        QueryData();
    }
}