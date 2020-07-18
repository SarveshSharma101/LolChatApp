package com.example.lolchatapp1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Chatting_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    DatabaseReference db;
    EditText message;
    ImageView send;
    String uname;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_activity);

        Toolbar toolbar = findViewById(R.id.ct);
        final Intent i = getIntent();
        toolbar.setTitle(i.getStringExtra("uname"));
        uname = i.getStringExtra("uname");

        //Log.e("UID", i.getStringExtra("Uid"));
        //Log.e("Millis", String.valueOf(System.currentTimeMillis()));


        recyclerView = findViewById(R.id.crc);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String cu = firebaseUser.getUid();

        db = FirebaseDatabase.getInstance().getReference().child("chats");

        setrc(recyclerView, db, cu,i.getStringExtra("Uid"));

        message= findViewById(R.id.message);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Log.e("ccccc", "Came here");

                    sendmessage(message.getText().toString(), cu, i.getStringExtra("Uid"));
                    recievemessage(message.getText().toString(),cu, i.getStringExtra("Uid"));
                    message.setText("");

                }
            }
        });
    }

    private void setrc(RecyclerView recyclerView, DatabaseReference db, final String cu, final String ru) {


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final HashMap<String, String> map = new HashMap<String, String>();
                final ArrayList<String> sr = new ArrayList<>();
                Log.e("FFFFFFFFFF", String.valueOf(snapshot));
                for(DataSnapshot i:snapshot.getChildren()){
                    Log.e("FFFFFFFFFF", String.valueOf(i));
                    if(i.getKey().toString().equals(cu)){
                        Log.e("FFFFFFFFFF", String.valueOf(i.getValue().toString()));
                        for( DataSnapshot j: i.getChildren()){
                            Log.e("FFFFFFFFFF", String.valueOf(j));
                            if(j.getKey().toString().equals(ru)){

                                for(DataSnapshot k: j.getChildren()){
                                    Log.e("FFFFFFFFFF", String.valueOf(k));
                                    String key = k.getKey();
                                    sr.add(key);
                                    for(DataSnapshot l : k.getChildren()){

                                        String message = l.getValue().toString();
                                        map.put(key, message);
                                    }

                                }
                            }

                        }
                    }
                }
                Log.e("FFFFFFFFFF", String.valueOf(map));
                Log.e("FFFFFFFFFF", String.valueOf(sr));
                RecyclerView rc = findViewById(R.id.crc);
                rc.setLayoutManager(new LinearLayoutManager(Chatting_activity.this));
                rc.setHasFixedSize(true);
                rc.setAdapter(new rcadapter2(map,sr, Chatting_activity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void sendmessage(String s, String cu, String ru) {
        Log.e("ccccc", "Came here");

        DatabaseReference dbs;
        dbs = db.child(cu);
        dbs= dbs.child(ru);
        //dbs.child("s->"+System.currentTimeMillis()).push().setValue(s);
        dbs.child(String.valueOf(System.currentTimeMillis())).push().setValue("s->"+s);



    }



    private void recievemessage(String s,  String cu, String ru) {
        DatabaseReference dbs;
        dbs = db.child(ru);
        dbs= dbs.child(cu);
        //dbs.child("r->"+System.currentTimeMillis()).push().setValue(s);
        dbs.child(String.valueOf(System.currentTimeMillis())).push().setValue("r->"+s);
    }



    private boolean validate() {
        String m =message.getText().toString();
        if(m.length()<0){
            return false;
        }
        return true;
    }
}
