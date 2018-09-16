package com.example.cecy_.arbolito;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class menu extends AppCompatActivity {
    public ListView lvOpciones;
    public static int idCliente;
    String[] web = {"Clientes por visitar", "Clientes visitados", "Leer QR"} ;

    Integer[] imageId = {
            R.drawable.delivery,
            R.drawable.deliverytruck,
            R.drawable.qr_code
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        lvOpciones = (ListView) findViewById(R.id.lvOpciones);

        list_single adapter = new
                list_single(menu.this, web, imageId);
        final Activity activity = this;
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
                }else if(position==2){
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Escanea el código");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.setCaptureActivity(CaptureActivityPortrait.class);
                    integrator.initiateScan();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelado");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                String[] res = result.getContents().split("-");
                idCliente = Integer.parseInt(res[1]);
                Intent intent = new Intent(menu.this, crearVenta.class);
                startActivity(intent);

                Toast.makeText(this, "Resultado: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
