package com.example.lolchatapp1;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chats_f extends Fragment {
    FirebaseUser firebaseUser;
    DatabaseReference db;
    DatabaseReference db1;
    ArrayList<users1> a;
    RecyclerView rc;
    ArrayList<String> uid;

    public Chats_f() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_chats_f, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String cu = firebaseUser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("chats");
        db1 = FirebaseDatabase.getInstance().getReference().child("users");
        rc = v.findViewById(R.id.chatrc);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uid = new ArrayList<>();

                Log.e("chatting", String.valueOf(uid));
                for (DataSnapshot i: snapshot.getChildren()){
                    /*Log.e("chatting", i.getKey().toString());*/
                    if(i.getKey().equals(cu)){
                        for(DataSnapshot j : i.getChildren()){
                            //Log.e("chatting", j.getKey().toString());

                            uid.add(j.getKey().toString());

                        }
                    }
                }
                Log.e("chatting", String.valueOf(uid));
                setRC(uid);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    private void setRC(final ArrayList<String> uid) {
        int i = uid.size();


            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    a = new ArrayList<>();
                   // Log.e("chatting", String.valueOf(snapshot));
                    for( DataSnapshot d: snapshot.getChildren()){
                        //Log.e("chatting", String.valueOf(d));
                        if(uid.indexOf(d.getKey())!=-1){

                            users1 u1 = new users1();
                            u1.setUname1(d.child("uname").getValue().toString());
                            u1.setUid1(d.getKey());
                            a.add(u1);

                        }
                    }
                    Log.e("chatting", String.valueOf(a));
                    rc.setHasFixedSize(true);
                    rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rc.setAdapter(new rcadapter1(a, getActivity()));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





    }
}
