package com.example.kotprog_ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {
    private static final String LOG_TAG = NewEventActivity.class.getName();

    private RecyclerView recyclerView;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;

    EditText eventName;
    EditText eventLocation;
    EditText ticketPrice;
    EditText ticketAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        eventName = findViewById(R.id.createEventName);
        eventLocation = findViewById(R.id.createEventLocation);
        ticketPrice = findViewById(R.id.createEventPrice);
        ticketAmount = findViewById(R.id.createEventTicketAmount);
    }

    public void Close(View view) {
        finish();
    }

    public void CreateNewEvent(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

        String name = this.eventName.getText().toString();
        String location = this.eventLocation.getText().toString();
        String price = this.ticketPrice.getText().toString();
        String amount = this.ticketAmount.getText().toString();

        if(name.isEmpty() || location.isEmpty() || price.isEmpty() || amount.isEmpty()){
            Toast.makeText(NewEventActivity.this, "Ne hagyj üresen egy mezőt sem!", Toast.LENGTH_LONG).show();
        }else{
            EventItem newEvent = new EventItem(name,date,location,Integer.parseInt(price),Integer.parseInt(amount));

            mFirestore.collection("Events").add(newEvent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(NewEventActivity.this, "Új esemény létrehozva", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewEventActivity.this, "Új esemény létrehozása sikertelen:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void CreateEvent(View view) {
        CreateNewEvent();
    }
}