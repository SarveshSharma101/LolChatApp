package com.example.lolchatapp1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Acc_setting extends AppCompatActivity {
    private ImageView image;
    private TextView email;
    private EditText username, status;
    FirebaseUser firebaseUser;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_setting);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String cu = firebaseUser.getUid();

        db = FirebaseDatabase.getInstance().getReference().child("users").child(cu);

        image = findViewById(R.id.image);
        email = findViewById(R.id.emailid);
        username = findViewById(R.id.username);
        status = findViewById(R.id.status);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tuname = snapshot.child("uname").getValue().toString();
                String temail = snapshot.child("email").getValue().toString();
                String tstatus = snapshot.child("status").getValue().toString();
                String timage = snapshot.child("image").getValue().toString();

                email.setText(temail);
                username.setText(tuname);
                status.setText(tstatus);

                if(timage.equals("defualt")){
                    image.setImageDrawable(getResources().getDrawable(R.drawable.path));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button sc = findViewById(R.id.sc);

        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Savechanges();

            }
        });
    }

    private void Savechanges() {

        String tuname = username.getText().toString();
        String temail = email.getText().toString();
        String tstatus = status.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("email",temail);
        map.put("uname",tuname);
        map.put("status",tstatus);
        map.put("image","default");
        map.put("thumb_image","default");

        db.setValue(map);

    }
}
