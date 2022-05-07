package com.example.kotprog_ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private ArrayList<EventItem> mEvents;
    private RecyclerView recyclerView;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.events_recycle_view);

        mEvents = new ArrayList<>();
        setAdapter();
        QueryData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mEvents.clear();
        QueryData();
    }

    private void QueryData() {
        mFirestore.collection("Events")
                .whereGreaterThan("unsoldTickets",0)
                .orderBy("unsoldTickets", Query.Direction.ASCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        EventItem item = document.toObject(EventItem.class);
                        item.setId(document.getId());
                        mEvents.add(item);
                    }
                    mAdapter.notifyDataSetChanged();
                });
    }

    private void setAdapter(){
        mAdapter = new EventAdapter(this,mEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_stripe,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart){
            Intent cart = new Intent(this,CartActivity.class);
            startActivity(cart);
        }else if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(this,MainActivity.class);
            startActivity(login);
        }else if(item.getItemId() == R.id.addEvent){
            Intent newEvent = new Intent(this,NewEventActivity.class);
            startActivity(newEvent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void addToCart(EventItem eventItem) {
        CartItem cartItem = new CartItem(mUser.getEmail(),eventItem.getEventName(),eventItem.getEventDate(),eventItem.getEventLocation(),eventItem.getTicketPrice(), eventItem._getId());
        mFirestore.collection("Cart").add(cartItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                updateUnsoldNumber(eventItem);
                Toast.makeText(EventsActivity.this, "Kosárba helyezve", Toast.LENGTH_SHORT).show();
                mEvents.clear();
                QueryData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventsActivity.this, "Kosárba rakás sikertelen" + e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUnsoldNumber(EventItem eventItem) {
        int unsoldTickets = eventItem.getUnsoldTickets()-1;
        DocumentReference ref = mFirestore.collection("Events").document(eventItem._getId());
        ref.update("unsoldTickets",unsoldTickets);
    }
}