package com.example.lolchatapp1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class Alluser_f extends Fragment {
    FirebaseUser firebaseUser;
    DatabaseReference db;
    ArrayList<users> a;
    Context c;
    public Alluser_f(Context c) {
        this.c =c;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_alluser_f, container, false);
        Toast.makeText(getActivity(),"We are here!!!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show();
        Log.e("jjjjjjjjjjjjjjjjj", "Again inside it");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String cu = firebaseUser.getUid();
        boolean Flag=false;
        db = FirebaseDatabase.getInstance().getReference().child("users");
        db.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a= new ArrayList<>();

                for(DataSnapshot i: snapshot.getChildren()){

                    //Toast.makeText(getActivity(),i.getKey().toString(), Toast.LENGTH_SHORT).show();
                    if(i.getKey().toString().equals(cu)){
                        continue;
                    }else{
                        users u = new users();
                        u.setUid(i.getKey().toString());
                        for(DataSnapshot j: i.getChildren()){
                            //Toast.makeText(getActivity(),j.getKey().toString(), Toast.LENGTH_SHORT).show();
                            if((j.getKey().toString()).equals("uname")){

                                u.setUname(j.getValue().toString());


                            }else if((j.getKey().toString()).equals("status")){
                                u.setStatus(j.getValue().toString());
                            }
                            /*;*/
                        }
                        /*users u = new users();
                        u.setUname(i.child("uname").toString());
                        u.setStatus(i.child("status").toString());
                        a.add(u);*/
                        a.add(u);
                    }
                    //updatea(u);
                }
                //Toast.makeText(getActivity(),s, Toast.LENGTH_SHORT).show();
                RecyclerView rc = v.findViewById(R.id.rc);
                rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                rc.setHasFixedSize(true);
                rcadapter rcadapter = new rcadapter(a, c);
                rc.setAdapter(rcadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }


   /* public void updatea(users u){
        a.add(u);
    }*/
}
