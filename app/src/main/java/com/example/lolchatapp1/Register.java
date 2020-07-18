package com.example.lolchatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText remail, runame, rpass, rcpass;
    private Button rbutton;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        remail = findViewById(R.id.regemail_in);
        runame = findViewById(R.id.reguname_in);
        rpass = findViewById(R.id.regpass_in);
        rcpass = findViewById(R.id.regcpass_in);
        rbutton = findViewById(R.id.regbutton);

        rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = remail.getText().toString();
                String uname = runame.getText().toString();
                String password = rpass.getText().toString();
                String cpassword = rcpass.getText().toString();
                Log.e("EEEEEEEEEEEEEE", "Calling Validate");
                validateuser(email, uname, password, cpassword);




            }
        });
    }

    private void validateuser(String email, String uname, String password, String cpassword) {
        boolean f = true;
        if (email.isEmpty()) {
            f = false;
            remail.setError("You left this one empty");
        } else if (uname.isEmpty()) {
            f = false;
            runame.setError("You left this one empty");
        } else if (password.isEmpty()) {
            f = false;
            rpass.setError("You left this one empty");
        } else if (cpassword.isEmpty()) {
            f = false;
            rcpass.setError("You left this one empty");
        } else if(password.length()<8){
            f = false;
            rcpass.setError("Password too short!!!");
        } else if (!(password.equals(cpassword))) {
            f = false;
            Toast.makeText(Register.this, "Password does not match", Toast.LENGTH_SHORT).show();
            rpass.setError("!");
        }
        if (f) {
            Log.e("EEEEEEEEEEEEEE", "Calling Reguser");
            Reguser(email, uname, password, cpassword);
        }

    }

    private void Reguser(final String email, final String uname, String password, String cpassword) {
        final boolean[] f = {false};

      mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseUser cu = mAuth.getCurrentUser();
                            String s = cu.getUid();

                            db = FirebaseDatabase.getInstance().getReference().child("users").child(s);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("email",email);
                            map.put("uname",uname);
                            map.put("status", getResources().getString(R.string.status));
                            map.put("image","default");
                            map.put("thumb_image","default");

                            db.setValue(map);



                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("EEEEEEEEEEEEEE", "Failed");
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            Log.e("EEEEEEEEEEEEEE", String.valueOf(f[0]));


        }
}

