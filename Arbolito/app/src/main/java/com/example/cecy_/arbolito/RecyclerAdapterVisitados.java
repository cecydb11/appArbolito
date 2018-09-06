package com.example.cecy_.arbolito;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cecy_ on 04/08/2018.
 */
public class RecyclerAdapterVisitados  extends RecyclerView.Adapter<RecyclerAdapterVisitados.MyViewHolder>  {
    private ArrayList<Clientes> arrayList = new ArrayList<>();

    public RecyclerAdapterVisitados(ArrayList<Clientes> arrayList){
        this.arrayList = arrayList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Name.setText(arrayList.get(position).getNombreNegocio() + " - " +arrayList.get(position).getNombrePropietario());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.nombre);
        }
    }
}
