package com.example.lolchatapp1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rcadapter1 extends RecyclerView.Adapter<rcadapter1.rc_viewHolder> {
    static ArrayList<users1> a;
    Context c;
    public rcadapter1(ArrayList<users1> a, Context c) {
        this.a= a;
        this.c = c;
    }

    @NonNull
    @Override
    public rc_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item1, parent, false);
        rc_viewHolder rc_viewHolder = new rc_viewHolder(view, this.c);
        return rc_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull rc_viewHolder holder, int position) {
            users1 u1 =a.get(position);
            String u =u1.getUname1();
            holder.uname.setText(u);


    }



    @Override
    public int getItemCount() {
        return a.size();
    }
    public static String getUID(String uname){
        for(users1 u: a ){
            if((u.getUname1()).equals(uname)){
                return u.getUid1();
            }
        }
        return null;
    }


    public class rc_viewHolder extends RecyclerView.ViewHolder{
        TextView uname;
        Context c;
        RelativeLayout vvc;
        public rc_viewHolder(@NonNull View itemView, final Context c) {
            super(itemView);
            this.c = c;
            vvc = itemView.findViewById(R.id.vvc);
            uname = itemView.findViewById(R.id.cUname);

            vvc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c,Chatting_activity.class);
                    String uid = rcadapter1.getUID(uname.getText().toString());
                    if(uid!=null){
                        i.putExtra("Uid", uid);
                        i.putExtra("uname", uname.getText().toString());
                        c.startActivity(i);
                    }
                }
            });




        }
    }
}
