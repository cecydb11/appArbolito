package com.example.cecy_.pruebasync;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cecy_ on 30/07/2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Contact> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Contact> arrayList){
        this.arrayList = arrayList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Name.setText(arrayList.get(position).getName());
        int sync_status = arrayList.get(position).getSync_status();
        if(sync_status == DbContact.SYNC_STATUS_OK){
            holder.Sync_status.setImageResource(R.drawable.success);
        }else{
            holder.Sync_status.setImageResource(R.drawable.sync);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView Sync_status;
        TextView Name;

        public MyViewHolder(View itemView) {
            super(itemView);
            Sync_status = (ImageView)itemView.findViewById(R.id.imageView);
            Name = (TextView)itemView.findViewById(R.id.textView);
        }
    }
}
