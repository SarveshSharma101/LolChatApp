package com.example.lolchatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class View_Acc extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference db;
    private TextView email, uname, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__acc);

        Intent i = getIntent();
        final String u = i.getStringExtra("uname");

        email = findViewById(R.id.vemail);
        uname = findViewById(R.id.vuname);
        status = findViewById(R.id.vstatus);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("u",u);
        String cu = firebaseUser.getUid();
        final boolean[] f = {false};
        db = FirebaseDatabase.getInstance().getReference().child("users");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot i: snapshot.getChildren()){

                    for(DataSnapshot j : i.getChildren()){
                        Log.e("pppppppppp",j.toString());
                        if((j.getKey().toString()).equals("uname")){
                            Log.e("u",j.getValue().toString());
                                if(j.getValue().toString().equals(u)){
                                    Log.e("u",j.getValue().toString());

                                    String tuname = i.child("uname").getValue().toString();
                                    String temail = i.child("email").getValue().toString();
                                    String tstatus = i.child("status").getValue().toString();

                                f[0] =true;
                                email.setText(temail);
                                uname.setText(tuname);
                                status.setText(tstatus);
                                break;
                            }

                        }

                    }
                    if(f[0]==true){
                        break;
                    }

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
