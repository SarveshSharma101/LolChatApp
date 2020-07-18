package com.example.lolchatapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText loge, logpass;
    private Button logb;
    private TextView caa;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        loge = findViewById(R.id.logemail_in);
        logpass = findViewById(R.id.logpass_in);
        caa = findViewById(R.id.caa);
        logb = findViewById(R.id.logbutton);

        final Intent i = new Intent(this,Chat_alluser.class);
        logb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loge.getText().toString();
                String password = logpass.getText().toString();
                if (validate(email, password)) {
                    login(email, password);

                }
            }


        });


        caa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
    }

    private boolean validate(String email, String password) {
        boolean f = true;
        if(email.isEmpty()){
            loge.setError("You left Email empty");
            f=false;
        }else if(password.isEmpty()){
            loge.setError("You left Password empty");
            f=false;
        }
        return f;
    }

    private void login(String email, String password) {
        final boolean[] f = {false};
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            Intent i = new Intent(MainActivity.this, Chat_alluser.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });





    }

}
