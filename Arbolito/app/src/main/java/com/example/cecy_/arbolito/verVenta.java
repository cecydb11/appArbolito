package com.example.cecy_.arbolito;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class verVenta extends AppCompatActivity {
    EditText etVentas_335lts, etVentas_5lts, etVentas1_5lts, etVentas5lts, etCambios_335lts, etCambios_5lts, etCambios1_5lts, etCambios5lts, etCortesias_335lts, etCortesias_5lts, etCortesias1_5lts, etCortesias5lts, etDevolucion_335lts, etDevolucion_5lts, etDevolucion1_5lts, etDevolucion5lts, etDanado_335lts, etDanado_5lts, etDanado1_5lts, etDanado5lts;
    TextView tvTotalVentas, tvTotalCambios, tvTotalCortesias, tvTotalDanado, tvTotalBono, tvTotalTotal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    TextView cliente;
    //ArrayList<DetallePedido> arrayList = new ArrayList<>();
    int totalVentas, totalCambios, totalCortesias, totalDanado, totalTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_venta);

        cliente = (TextView) findViewById(R.id.tvNombreCliente);

        cliente.setText(clientesVisitados.cliente);


        tvTotalVentas = (TextView) findViewById(R.id.tvTotalVentas);
        tvTotalCambios = (TextView) findViewById(R.id.tvTotalCambios);
        tvTotalCortesias = (TextView) findViewById(R.id.tvTotalCortesias);
        tvTotalDanado = (TextView) findViewById(R.id.tvTotalDanado);
        tvTotalBono = (TextView) findViewById(R.id.tvTotalBono);
        tvTotalTotal = (TextView) findViewById(R.id.tvTotalTotal);

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

        readUltimaventa();

        //On lost focus listeners
        etVentas_335lts.setOnFocusChangeListener(calcular);
        etVentas_5lts.setOnFocusChangeListener(calcular);
        etVentas1_5lts.setOnFocusChangeListener(calcular);
        etVentas5lts.setOnFocusChangeListener(calcular);

        etCambios_335lts.setOnFocusChangeListener(calcular);
        etCambios_5lts.setOnFocusChangeListener(calcular);
        etCambios1_5lts.setOnFocusChangeListener(calcular);
        etCambios5lts.setOnFocusChangeListener(calcular);

        etCortesias_335lts.setOnFocusChangeListener(calcular);
        etCortesias_5lts.setOnFocusChangeListener(calcular);
        etCortesias1_5lts.setOnFocusChangeListener(calcular);
        etCortesias5lts.setOnFocusChangeListener(calcular);

        etDanado_335lts.setOnFocusChangeListener(calcular);
        etDanado_5lts.setOnFocusChangeListener(calcular);
        etDanado1_5lts.setOnFocusChangeListener(calcular);
        etDanado5lts.setOnFocusChangeListener(calcular);

    }

    public void submitVenta(View view){

        int idCliente;
        idCliente = clientesVisitados.idCliente;

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
        if((ventas_355lts != 0) || (cambios_355lts != 0) || (cortesias_355lts != 0) || (danado_355lts != 0)){
            UpdateAppServer(idCliente, 4, ventas_355lts, cambios_355lts, cortesias_355lts, danado_355lts, (float) 8.0, 1, fecha);
        }
        //arrayList.add(new DetallePedido(ventas_355lts, "Ventas .355 lts", (ventas_355lts * 8)));
        //arrayList.add(new DetallePedido(cambios_355lts, "Cambios .355 lts", (cambios_355lts * 8)));
        //arrayList.add(new DetallePedido(cortesias_355lts, "Cortesias .355 lts", (cortesias_355lts * 8)));
        //arrayList.add(new DetallePedido(danado_355lts, "Danado .355 lts", (danado_355lts * 8)));
        totalTotal += ((ventas_355lts - cambios_355lts - cortesias_355lts - danado_355lts) * 8);

        etVentas_335lts.setText("");
        etCambios_335lts.setText("");
        etCortesias_335lts.setText("");
        etDanado_335lts.setText("");

        tvTotalVentas.setText("$0");
        tvTotalCambios.setText("$0");
        tvTotalCortesias.setText("$0");
        tvTotalDanado.setText("$0");
        tvTotalBono.setText("$0");
        tvTotalTotal.setText("$0");

        //Insertar para producto de .5 lts con id 2
        if((ventas_5lts != 0) || (cambios_5lts != 0) || (cortesias_5lts != 0) || (danado_5lts != 0)) {
            UpdateAppServer(idCliente, 2, ventas_5lts, cambios_5lts, cortesias_5lts, danado_5lts, (float) 8.0, 1, fecha);
        }
        //arrayList.add(new DetallePedido(ventas_5lts, "Ventas .500 lts", (ventas_5lts * 8)));
        //arrayList.add(new DetallePedido(cambios_5lts, "Cambios .500 lts", (cambios_5lts * 8)));
        //arrayList.add(new DetallePedido(cortesias_5lts, "Cortesias .500 lts", (cortesias_5lts * 8)));
        //arrayList.add(new DetallePedido(danado_5lts, "Danado .500 lts", (danado_5lts * 8)));
        totalTotal += ((ventas_5lts - cambios_5lts - cortesias_5lts - danado_5lts) * 8);

        etVentas_5lts.setText("");
        etCambios_5lts.setText("");
        etCortesias_5lts.setText("");
        etDanado_5lts.setText("");

        //Insertar para producto de 1.5 lts con id 3
        if((ventas1_5lts != 0) || (cambios1_5lts != 0) || (cortesias1_5lts != 0) || (danado1_5lts != 0)) {
            UpdateAppServer(idCliente, 3, ventas1_5lts, cambios1_5lts, cortesias1_5lts, danado1_5lts, (float) 16.0, 1, fecha);
        }
        //arrayList.add(new DetallePedido(ventas1_5lts, "Ventas .500 lts", (ventas1_5lts * 16)));
        //arrayList.add(new DetallePedido(cambios1_5lts, "Cambios .500 lts", (cambios1_5lts * 16)));
        //arrayList.add(new DetallePedido(cortesias1_5lts, "Cortesias .500 lts", (cortesias1_5lts * 16)));
        //arrayList.add(new DetallePedido(danado1_5lts, "Danado .500 lts", (danado1_5lts * 16)));
        totalTotal += ((ventas1_5lts - cambios1_5lts - cortesias1_5lts - danado1_5lts) * 16);

        etVentas1_5lts.setText("");
        etCambios1_5lts.setText("");
        etCortesias1_5lts.setText("");
        etDanado1_5lts.setText("");

        //Insertar para producto de 5 lts con id 1
        if((ventas5lts != 0) || (cambios5lts != 0) || (cortesias5lts != 0) || (danado5lts != 0)) {
            UpdateAppServer(idCliente, 1, ventas5lts, cambios5lts, cortesias5lts, danado5lts, (float) 50.0, 1, fecha);
        }
        //arrayList.add(new DetallePedido(ventas5lts, "Ventas .500 lts", (ventas5lts * 50)));
        //arrayList.add(new DetallePedido(cambios5lts, "Cambios .500 lts", (cambios5lts * 50)));
        //arrayList.add(new DetallePedido(cortesias5lts, "Cortesias .500 lts", (cortesias5lts * 50)));
        //arrayList.add(new DetallePedido(danado5lts, "Danado .500 lts", (danado5lts * 50)));
        totalTotal += ((ventas5lts - cambios1_5lts - cortesias5lts - danado5lts) * 50);

        etVentas5lts.setText("");
        etCambios5lts.setText("");
        etCortesias5lts.setText("");
        etDanado5lts.setText("");
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void UpdateLocalStorage(int idCliente, int idProducto, int ventas, int cambios, int cortesia, int danado, float precio, int ventaNo, String fecha, int edit){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        dbHelper.UpdateLocalDatabaseVentas(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, edit, database);
        dbHelper.close();
    }

    private void UpdateAppServer(final int idCliente, final int idProducto, final int ventas, final int cambios, final int cortesia, final int danado, final float precio, final int ventaNo, final String fecha) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbHelper.SERVER_URL + "insertVenta.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            if(Response.equals("OK")){
                                UpdateLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 1);
                                Toast.makeText(getApplicationContext(),
                                        "Datos guardados en el servidor.",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                UpdateLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);
                                Toast.makeText(getApplicationContext(),
                                        "Datos guardados localmente1." + Response,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    UpdateLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);
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

            MySingleton.getInstance(verVenta.this).addToRequestQue(stringRequest);

        } else {
            UpdateLocalStorage(idCliente, idProducto, ventas, cambios, cortesia, danado, precio, ventaNo, fecha, 0);
            Toast.makeText(getApplicationContext(),
                    "Datos guardados localmente3.",
                    Toast.LENGTH_LONG).show();
        }
    }

    View.OnFocusChangeListener calcular = new View.OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            int ventas_335lts = 0, ventas_5lts = 0, ventas1_5lts = 0, ventas5lts = 0;
            int cambios_335lts = 0, cambios_5lts = 0, cambios1_5lts = 0, cambios5lts = 0;
            int cortesias_335lts = 0, cortesias_5lts = 0,cortesias1_5lts = 0, cortesias5lts = 0;
            int danado_335lts = 0, danado_5lts = 0, danado1_5lts = 0, danado5lts = 0;

            double totalBono, totalTotal;

            if(etVentas_335lts.getText().toString().isEmpty()){
                ventas_335lts = 0;
            }else{
                ventas_335lts = Integer.parseInt(etVentas_335lts.getText().toString());
            }
            if(etVentas_5lts.getText().toString().isEmpty()){
                ventas_5lts = 0;
            }else{
                ventas_5lts = Integer.parseInt(etVentas_5lts.getText().toString());
            }
            if(etVentas1_5lts.getText().toString().isEmpty()){
                ventas1_5lts = 0;
            }else{
                ventas1_5lts = Integer.parseInt(etVentas1_5lts.getText().toString());
            }
            if(etVentas5lts.getText().toString().isEmpty()){
                ventas5lts = 0;
            }else{
                ventas5lts = Integer.parseInt(etVentas5lts.getText().toString());
            }
            totalVentas = ((ventas_335lts * 8)  + (ventas_5lts * 8) + (ventas1_5lts * 16) + (ventas5lts * 50));
            tvTotalVentas.setText("$" + String.valueOf(totalVentas));

            if(etCambios_335lts.getText().toString().isEmpty()){
                cambios_335lts = 0;
            }else{
                cambios_335lts = Integer.parseInt(etCambios_335lts.getText().toString());
            }
            if(etCambios_5lts.getText().toString().isEmpty()){
                cambios_5lts = 0;
            }else{
                cambios_5lts = Integer.parseInt(etCambios_5lts.getText().toString());
            }
            if(etCambios1_5lts.getText().toString().isEmpty()){
                cambios1_5lts = 0;
            }else{
                cambios1_5lts = Integer.parseInt(etCambios1_5lts.getText().toString());
            }
            if(etCambios5lts.getText().toString().isEmpty()){
                cambios5lts = 0;
            }else{
                cambios5lts = Integer.parseInt(etCambios5lts.getText().toString());
            }
            totalCambios = ((cambios_335lts * 8)  + (cambios_5lts * 8) + (cambios1_5lts * 16) + (cambios5lts * 50));
            tvTotalCambios.setText("$" + String.valueOf(totalCambios));

            if(etCortesias_335lts.getText().toString().isEmpty()){
                cortesias_335lts = 0;
            }else{
                cortesias_335lts = Integer.parseInt(etCortesias_335lts.getText().toString());
            }
            if(etCortesias_5lts.getText().toString().isEmpty()){
                cortesias_5lts = 0;
            }else{
                cortesias_5lts = Integer.parseInt(etCortesias_5lts.getText().toString());
            }
            if(etCortesias1_5lts.getText().toString().isEmpty()){
                cortesias1_5lts = 0;
            }else{
                cortesias1_5lts = Integer.parseInt(etCortesias1_5lts.getText().toString());
            }
            if(etCortesias5lts.getText().toString().isEmpty()){
                cortesias5lts = 0;
            }else{
                cortesias5lts = Integer.parseInt(etCortesias5lts.getText().toString());
            }
            totalCortesias = ((cortesias_335lts * 8)  + (cortesias_5lts * 8) + (cortesias1_5lts * 16) + (cortesias5lts* 50));
            tvTotalCortesias.setText("$" + String.valueOf(totalCortesias));

            if(etDanado_335lts.getText().toString().isEmpty()){
                danado_335lts = 0;
            }else{
                danado_335lts = Integer.parseInt(etDanado_335lts.getText().toString());
            }
            if(etDanado_5lts.getText().toString().isEmpty()){
                danado_5lts = 0;
            }else{
                danado_5lts = Integer.parseInt(etDanado_5lts.getText().toString());
            }
            if(etDanado1_5lts.getText().toString().isEmpty()){
                danado1_5lts = 0;
            }else{
                danado1_5lts = Integer.parseInt(etDanado1_5lts.getText().toString());
            }
            if(etDanado5lts.getText().toString().isEmpty()){
                danado5lts = 0;
            }else{
                danado5lts = Integer.parseInt(etDanado5lts.getText().toString());
            }
            totalDanado = ((danado_335lts * 8)  + (danado_5lts * 8) + (danado1_5lts * 16) + (danado5lts * 50));
            tvTotalDanado.setText("$" + String.valueOf(totalDanado));

            totalBono = (clientesPorVisitar.bonoPorcentaje/100) *  (totalVentas - totalCambios - totalCortesias - totalDanado);
            tvTotalBono.setText("$" + String.valueOf(totalBono));

            totalTotal = totalVentas - totalCambios - totalCortesias - totalDanado - totalBono;
            tvTotalTotal.setText("$" + String.valueOf(totalTotal));
        }
    };

    private void readUltimaventa(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        int ventas_335lts, ventas_5lts, ventas1_5lts, ventas5lts;
        int cambios_335lts, cambios_5lts, cambios1_5lts, cambios5lts;
        int cortesias_335lts, cortesias_5lts,cortesias1_5lts, cortesias5lts;
        int danado_335lts, danado_5lts, danado1_5lts, danado5lts;

        String fecha =  dateFormat.format(date);
        DbHelper dbHelper = new DbHelper(verVenta.this);
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        Cursor fila1 = bd.rawQuery("SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 1", null);
        Log.d("consulta" , "SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 1");

        if (fila1.moveToFirst()) {
            ventas5lts = fila1.getInt(3);
            cambios5lts = fila1.getInt(4);
            cortesias5lts = fila1.getInt(5);
            danado5lts = fila1.getInt(7);

            etVentas5lts.setText(ventas5lts);
            etCambios5lts.setText(cambios5lts);
            etCortesias5lts.setText(cortesias5lts);
            etDanado5lts.setText(danado5lts);
        }

        Cursor fila2 = bd.rawQuery("SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 2", null);
        Log.d("consulta" , "SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 2");
        if (fila2.moveToFirst()) {
            ventas_5lts = fila2.getInt(3);
            cambios_5lts = fila2.getInt(4);
            cortesias_5lts = fila2.getInt(5);
            danado_5lts = fila2.getInt(7);

            etVentas_5lts.setText(ventas_5lts);
            etCambios_5lts.setText(cambios_5lts);
            etCortesias_5lts.setText(cortesias_5lts);
            etDanado_5lts.setText(danado_5lts);
        }

        Cursor fila3 = bd.rawQuery("SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 3", null);
        Log.d("consulta" , "SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 3");

        if (fila3.moveToFirst()) {
            ventas1_5lts = fila3.getInt(3);
            cambios1_5lts = fila3.getInt(4);
            cortesias1_5lts = fila3.getInt(5);
            danado1_5lts = fila3.getInt(7);

            etVentas1_5lts.setText(ventas1_5lts);
            etCambios1_5lts.setText(cambios1_5lts);
            etCortesias1_5lts.setText(cortesias1_5lts);
            etDanado1_5lts.setText(danado1_5lts);
        }

        Cursor fila4 = bd.rawQuery("SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 4", null);
        Log.d("consulta" , "SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 4");
        Log.d("hi", String.valueOf(fila4.getInt(3)));
        if (fila4.moveToFirst()) {

            ventas_335lts = fila4.getInt(3);
            cambios_335lts = fila4.getInt(4);
            cortesias_335lts = fila4.getInt(5);
            danado_335lts = fila4.getInt(7);

            etVentas_335lts.setText(ventas_335lts);
            etCambios_335lts.setText(cambios_335lts);
            etCortesias_335lts.setText(cortesias_335lts);
            etDanado_335lts.setText(danado_335lts);

        }
    }
}
