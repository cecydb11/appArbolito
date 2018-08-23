package com.example.cecy_.arbolito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class menu extends AppCompatActivity {
    public ListView lvOpciones;
    String[] web = {"Clientes por visitar", "Clientes visitados"} ;

    Integer[] imageId = {
            R.drawable.delivery,
            R.drawable.deliverytruck
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        lvOpciones = (ListView) findViewById(R.id.lvOpciones);

        list_single adapter = new
                list_single(menu.this, web, imageId);
        lvOpciones.setAdapter(adapter);
        lvOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position==0) {
                    //Intent intent = new Intent(menu.this, clientesPorVisitar.class);
                    Intent intent = new Intent(menu.this, clientesPorVisitar.class);
                    startActivity(intent);
                }else if(position==1){
                    Intent intent = new Intent(menu.this, clientesVisitados.class);
                    startActivity(intent);
                }
            }
        });

    }
}
