package com.example.cecy_.arbolito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class clientesVisitados extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Clientes> arrayList = new ArrayList<>();
    BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_visitados);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewVisitar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        readFromLocalStorage();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromLocalStorage();
            }
        };
    }

    private void readFromLocalStorage() {
        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabaseClientes(database);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("nombreNegocio"));
            String nombrePropietario = cursor.getString(cursor.getColumnIndex("nombrePropietario"));
            String tipoNegocio = cursor.getString(cursor.getColumnIndex("tipoNegocio"));
            String Domicilio = cursor.getString(cursor.getColumnIndex("domicilio"));
            String Telefono = cursor.getString(cursor.getColumnIndex("telefono"));
            int Estado = cursor.getInt(cursor.getColumnIndex("estado"));
            if(Estado == 1) {
                //arrayList.add(new Clientes(name, nombrePropietario, tipoNegocio, Domicilio,Telefono, Estado));
            }
        }

        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(DbHelper.UI_UPDATE_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
