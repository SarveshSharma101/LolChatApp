package com.example.lolchatapp1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rcadapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static HashMap<String, String> a;
    static ArrayList<String> sr;
    Context c;
    public rcadapter2(HashMap<String, String> a, ArrayList<String> sr,Context c) {
        this.sr=sr;
        this.a= a;
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case 1:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieve_message_item, parent, false);
                Src_viewHolder rc_viewHolder = new Src_viewHolder(view, this.c);
                return rc_viewHolder;
            case 0:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message_item, parent, false);
                Rrc_viewHolder rrc_viewHolder = new Rrc_viewHolder(view1, this.c);
                return rrc_viewHolder;

        }

        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            /*users1 u1 =a.get(position);
            String u =u1.getUname1();
            holder.uname.setText(u);*/
            switch (holder.getItemViewType()){
                case 1:
                    Src_viewHolder rc_viewHolder = (Src_viewHolder) holder;
                    rc_viewHolder.uname.setText(a.get(sr.get(position)).split("->")[1]);
                    break;

                case 0:
                    Rrc_viewHolder rrc_viewHolder = (Rrc_viewHolder) holder;
                    rrc_viewHolder.uname.setText(a.get(sr.get(position)).split("->")[1]);
                    break;
            }


    }



    @Override
    public int getItemCount() {
        return sr.size();
    }

    @Override
    public int getItemViewType(int position) {

        String key = sr.get(position);
        String s = a.get(key);
        String x = s.split("->")[0];
        /*if(key.contains("s")){
            return 1;
        }else{
            return 0;
        }*/
        if(x.equals("s")){
            return 1;
        }else{
            return 0;
        }

        //return super.getItemViewType(position);
    }

    public class Src_viewHolder extends RecyclerView.ViewHolder{
        TextView uname;

        public Src_viewHolder(@NonNull View itemView, final Context c) {
            super(itemView);
            uname = itemView.findViewById(R.id.sendm);

        }
    }

    public class Rrc_viewHolder extends RecyclerView.ViewHolder{
        TextView uname;

        public Rrc_viewHolder(@NonNull View itemView, final Context c) {
            super(itemView);

            uname = itemView.findViewById(R.id.recievem);

        }
    }
}
