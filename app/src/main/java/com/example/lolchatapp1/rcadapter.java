package com.example.lolchatapp1;

import android.content.ContentValues;
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

public class rcadapter extends RecyclerView.Adapter<rcadapter.rc_viewHolder> {
    static ArrayList<users> a;
    Context c;
    public rcadapter(ArrayList<users> a, Context c) {
        this.a= a;
        this.c = c;
    }

    @NonNull
    @Override
    public rc_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        rc_viewHolder rc_viewHolder = new rc_viewHolder(view, this.c);
        return rc_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull rc_viewHolder holder, int position) {
        users u = a.get(position);
            holder.uname.setText(u.getUname());
            holder.status.setText(u.getStatus());

    }



    @Override
    public int getItemCount() {
        return a.size();
    }

    public static String getUID(String uname){
        for(users u: a ){
            if((u.getUname()).equals(uname)){
                return u.getUid();
            }
        }
        return null;
    }

    public class rc_viewHolder extends RecyclerView.ViewHolder{
        TextView uname, status;
        RelativeLayout relativeLayout;
        Context c;
        public rc_viewHolder(@NonNull View itemView, final Context c) {
            super(itemView);
            this.c = c;
            uname = itemView.findViewById(R.id.Uname);
            status = itemView.findViewById(R.id.Status);
            relativeLayout = itemView.findViewById(R.id.vv);

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Intent i = new Intent(c,View_Acc.class);
                    i.putExtra("uname", uname.getText().toString());

                    c.startActivity(i);

                    return true;
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(c,Chatting_activity.class);
                    String uid = rcadapter.getUID(uname.getText().toString());
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
