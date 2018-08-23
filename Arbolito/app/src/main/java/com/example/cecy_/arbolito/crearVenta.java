package com.example.cecy_.arbolito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class crearVenta extends AppCompatActivity {
    EditText etVentas_335lts, etVentas_5lts, etVentas1_5lts, etVentas5lts, etCambios_335lts, etCambios_5lts, etCambios1_5lts, etCambios5lts, etCortesias_335lts, etCortesias_5lts, etCortesias1_5lts, etCortesias5lts, etDevolucion_335lts, etDevolucion_5lts, etDevolucion1_5lts, etDevolucion5lts, etDanado_335lts, etDanado_5lts, etDanado1_5lts, etDanado5lts;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    TextView cliente;
    ArrayList<DetallePedido> arrayList = new ArrayList<>();
    ArrayList<String> arList = new ArrayList<>();
    BluetoothPrint bluetoothPrint = new BluetoothPrint(crearVenta.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_venta);




        cliente = (TextView) findViewById(R.id.tvNombreCliente);

        cliente.setText(clientesPorVisitar.cliente);

        etVentas_335lts = (EditText) findViewById(R.id.etVentas_335lts);
        etVentas_5lts = (EditText) findViewById(R.id.etVentas_5lts);
        etVentas1_5lts = (EditText) findViewById(R.id.etVentas1_5lts);
        etVentas5lts = (EditText) findViewById(R.id.etVentas5lts);

        etCambios_335lts = (EditText) findViewById(R.id.etCambios_335lts);
        etCambios_5lts = (EditText) findViewById(R.id.etCambios_5lts);
        etCambios1_5lts = (EditText) findViewById(R.id.etCambios1_5lts);
        etCambios5lts = (EditText) findViewById(R.id.etCambios5lts);

        etCortesias_335lts = (EditText) findViewById(R.id.etCortesias_335lts);
        etCortesias_5lts = (EditText) findViewById(R.id.etCortesias_5lts);
        etCortesias1_5lts = (EditText) findViewById(R.id.etCortesias1_5lts);
        etCortesias5lts = (EditText) findViewById(R.id.etCortesias5lts);

        etDanado_335lts = (EditText) findViewById(R.id.etDanado_335lts);
        etDanado_5lts = (EditText) findViewById(R.id.etDanado_5lts);
        etDanado1_5lts = (EditText) findViewById(R.id.etDanado1_5lts);
        etDanado5lts = (EditText) findViewById(R.id.etDanado5lts);


    }

    public void submitVenta(View view){
        int idCliente = login.idUsuario;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        String fecha =  dateFormat.format(date);

        //Valores .355 lts
        int ventas_355lts = 0, cambios_355lts = 0, cortesias_355lts = 0, danado_355lts = 0;
        if(!etVentas_335lts.getText().toString().equals("")) {
            ventas_355lts = Integer.parseInt(etVentas_335lts.getText().toString());
        }
        if(!etCambios_335lts.getText().toString().equals("")) {
            cambios_355lts = Integer.parseInt(etCambios_335lts.getText().toString());
        }
        if(!etCortesias_335lts.getText().toString().equals("")) {
            cortesias_355lts = Integer.parseInt(etCortesias_335lts.getText().toString());
        }
        if(!etDanado_335lts.getText().toString().equals("")) {
            danado_355lts = Integer.parseInt(etDanado_335lts.getText().toString());
        }

        //Valores .5 lts
        int ventas_5lts = 0, cambios_5lts = 0, cortesias_5lts = 0, danado_5lts = 0;

        if(!etVentas_5lts.getText().toString().equals("")) {
            ventas_5lts = Integer.parseInt(etVentas_5lts.getText().toString());
        }
        if(!etCambios_5lts.getText().toString().equals("")) {
            cambios_5lts = Integer.parseInt(etCambios_5lts.getText().toString());
        }
        if(!etCortesias_5lts.getText().toString().equals("")) {
            cortesias_5lts = Integer.parseInt(etCortesias_5lts.getText().toString());
        }
        if(!etDanado_5lts.getText().toString().equals("")) {
            danado_5lts = Integer.parseInt(etDanado_5lts.getText().toString());
        }

        //Valores 1.5 lts
        int ventas1_5lts = 0, cambios1_5lts = 0, cortesias1_5lts = 0,danado1_5lts = 0;

        if(!etVentas1_5lts.getText().toString().equals("")) {
            ventas1_5lts = Integer.parseInt(etVentas1_5lts.getText().toString());
        }
        if(!etCambios1_5lts.getText().toString().equals("")) {
            cambios1_5lts = Integer.parseInt(etCambios1_5lts.getText().toString());
        }
        if(!etCortesias1_5lts.getText().toString().equals("")) {
            cortesias1_5lts = Integer.parseInt(etCortesias1_5lts.getText().toString());
        }
        if(!etDanado1_5lts.getText().toString().equals("")) {
            danado1_5lts = Integer.parseInt(etDanado1_5lts.getText().toString());
        }

        //Valores 5 lts
        int ventas5lts = 0, cambios5lts = 0, cortesias5lts = 0, danado5lts = 0;

        if(!etVentas5lts.getText().toString().equals("")) {
            ventas5lts = Integer.parseInt(etVentas5lts.getText().toString());
        }
        if(!etCambios5lts.getText().toString().equals("")) {
            cambios5lts = Integer.parseInt(etCambios5lts.getText().toString());
        }
        if(!etCortesias5lts.getText().toString().equals("")) {
            cortesias5lts = Integer.parseInt(etCortesias5lts.getText().toString());
        }
        if(!etDanado5lts.getText().toString().equals("")) {
            danado5lts = Integer.parseInt(etDanado5lts.getText().toString());
        }

        //Insertar para producto de .355 lts con id 4
        saveToAppServer(idCliente, 4, ventas_355lts, cambios_355lts, cortesias_355lts, danado_355lts, (float) 8.0, 1, fecha);
        etVentas_335lts.setText("");
        etCambios_335lts.setText("");
        etCortesias_335lts.setText("");
        etDanado_335lts.setText("");

        //Insertar para producto de .5 lts con id 2
        saveToAppServer(idCliente, 2, ventas_5lts, cambios_5lts, cortesias_5lts, danado_5lts, (float) 8.0, 1, fecha);
        etVentas_5lts.setText("");
        etCambios_5lts.setText("");
        etCortesias_5lts.setText("");
        etDanado_5lts.setText("");

        //Insertar para producto de 1.5 lts con id 3
        saveToAppServer(idCliente, 3, ventas1_5lts, cambios1_5lts, cortesias1_5lts, danado1_5lts, (float) 16.0, 1, fecha);
        etVentas1_5lts.setText("");
        etCambios1_5lts.setText("");
        etCortesias1_5lts.setText("");
        etDanado1_5lts.setText("");

        //Insertar para producto de 5 lts con id 1
        saveToAppServer(idCliente, 1, ventas5lts, cambios5lts, cortesias5lts, danado5lts, (float) 50.0, 1, fecha);
        etVentas5lts.setText("");
        etCambios5lts.setText("");
        etCortesias5lts.setText("");
        etDanado5lts.setText("");

        bluetoothPrint.FindBluetoothDevice();
        try {
            bluetoothPrint.openBluetoothPrinter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bluetoothPrint.printData(arrayList, clientesPorVisitar.cliente, "100");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void saveToLocalStorage(int idCliente, int idProducto, int ventas, int cambios, int cortesia, int danado, float precio, int ventaNo, String fecha, int sync){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        dbHelper.saveToLocalDatabaseVentas(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, sync, database);
        dbHelper.close();
    }

    private void saveToAppServer(final int idCliente, final int idProducto, final int ventas, final int cambios, final int cortesia, final int danado, final float precio, final int ventaNo, final String fecha) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbHelper.SERVER_URL + "insertVenta.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(Response.equals("OK")){
                                    saveToLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 1);
                                    Toast.makeText(getApplicationContext(),
                                            "Datos guardados en el servidor.",
                                            Toast.LENGTH_LONG).show();
                                }else{
                                    saveToLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);


                                    Toast.makeText(getApplicationContext(),
                                            "Datos guardados localmente1.",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);
                    Toast.makeText(getApplicationContext(),
                            "Datos guardados localmente2.",
                            Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idCliente", String.valueOf(idCliente));
                    params.put("idProducto", String.valueOf(idProducto));
                    params.put("ventas", String.valueOf(ventas));
                    params.put("cambios", String.valueOf(cambios));
                    params.put("cortesia", String.valueOf(cortesia));
                    params.put("danado", String.valueOf(danado));
                    params.put("precio", String.valueOf(precio));
                    params.put("ventaNo", String.valueOf(ventaNo));
                    params.put("fecha", fecha);

                    return params;
                }
            };

            MySingleton.getInstance(crearVenta.this).addToRequestQue(stringRequest);

        } else {
            saveToLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);
            Toast.makeText(getApplicationContext(),
                    "Datos guardados localmente3.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
