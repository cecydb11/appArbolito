package com.example.cecy_.arbolito;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class verVentasMes extends AppCompatActivity {
    EditText etVentas_335lts, etVentas_5lts, etVentas1_5lts, etVentas5lts, etCambios_335lts, etCambios_5lts, etCambios1_5lts, etCambios5lts, etCortesias_335lts, etCortesias_5lts, etCortesias1_5lts, etCortesias5lts, etDevolucion_335lts, etDevolucion_5lts, etDevolucion1_5lts, etDevolucion5lts, etDanado_335lts, etDanado_5lts, etDanado1_5lts, etDanado5lts;
    TextView tvTotalVentas, tvTotalCambios, tvTotalCortesias, tvTotalDanado, tvTotalBono, tvTotalTotal;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    TextView cliente;
    int totalVentas, totalCambios, totalCortesias, totalDanado, totalTotal = 0, idCliente;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_venta);

        cliente = (TextView) findViewById(R.id.tvNombreCliente);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        if(clientesPorVisitar.cliente != null) {
            cliente.setText(clientesPorVisitar.cliente);
        }else if(clientesVisitados.cliente != null){
            cliente.setText(clientesVisitados.cliente);
        }

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

    View.OnFocusChangeListener calcular = new View.OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            int ventas_335lts = 0, ventas_5lts = 0, ventas1_5lts = 0, ventas5lts = 0;
            int cambios_335lts = 0, cambios_5lts = 0, cambios1_5lts = 0, cambios5lts = 0;
            int cortesias_335lts = 0, cortesias_5lts = 0, cortesias1_5lts = 0, cortesias5lts = 0;
            int danado_335lts = 0, danado_5lts = 0, danado1_5lts = 0, danado5lts = 0;

            double totalBono, totalTotal;

            if (etVentas_335lts.getText().toString().isEmpty()) {
                ventas_335lts = 0;
            } else {
                ventas_335lts = Integer.parseInt(etVentas_335lts.getText().toString());
            }
            if (etVentas_5lts.getText().toString().isEmpty()) {
                ventas_5lts = 0;
            } else {
                ventas_5lts = Integer.parseInt(etVentas_5lts.getText().toString());
            }
            if (etVentas1_5lts.getText().toString().isEmpty()) {
                ventas1_5lts = 0;
            } else {
                ventas1_5lts = Integer.parseInt(etVentas1_5lts.getText().toString());
            }
            if (etVentas5lts.getText().toString().isEmpty()) {
                ventas5lts = 0;
            } else {
                ventas5lts = Integer.parseInt(etVentas5lts.getText().toString());
            }
            totalVentas = ((ventas_335lts * 8) + (ventas_5lts * 8) + (ventas1_5lts * 16) + (ventas5lts * 50));
            tvTotalVentas.setText("$" + String.valueOf(totalVentas));

            if (etCambios_335lts.getText().toString().isEmpty()) {
                cambios_335lts = 0;
            } else {
                cambios_335lts = Integer.parseInt(etCambios_335lts.getText().toString());
            }
            if (etCambios_5lts.getText().toString().isEmpty()) {
                cambios_5lts = 0;
            } else {
                cambios_5lts = Integer.parseInt(etCambios_5lts.getText().toString());
            }
            if (etCambios1_5lts.getText().toString().isEmpty()) {
                cambios1_5lts = 0;
            } else {
                cambios1_5lts = Integer.parseInt(etCambios1_5lts.getText().toString());
            }
            if (etCambios5lts.getText().toString().isEmpty()) {
                cambios5lts = 0;
            } else {
                cambios5lts = Integer.parseInt(etCambios5lts.getText().toString());
            }
            totalCambios = ((cambios_335lts * 8) + (cambios_5lts * 8) + (cambios1_5lts * 16) + (cambios5lts * 50));
            tvTotalCambios.setText("$" + String.valueOf(totalCambios));

            if (etCortesias_335lts.getText().toString().isEmpty()) {
                cortesias_335lts = 0;
            } else {
                cortesias_335lts = Integer.parseInt(etCortesias_335lts.getText().toString());
            }
            if (etCortesias_5lts.getText().toString().isEmpty()) {
                cortesias_5lts = 0;
            } else {
                cortesias_5lts = Integer.parseInt(etCortesias_5lts.getText().toString());
            }
            if (etCortesias1_5lts.getText().toString().isEmpty()) {
                cortesias1_5lts = 0;
            } else {
                cortesias1_5lts = Integer.parseInt(etCortesias1_5lts.getText().toString());
            }
            if (etCortesias5lts.getText().toString().isEmpty()) {
                cortesias5lts = 0;
            } else {
                cortesias5lts = Integer.parseInt(etCortesias5lts.getText().toString());
            }
            totalCortesias = ((cortesias_335lts * 8) + (cortesias_5lts * 8) + (cortesias1_5lts * 16) + (cortesias5lts * 50));
            tvTotalCortesias.setText("$" + String.valueOf(totalCortesias));

            if (etDanado_335lts.getText().toString().isEmpty()) {
                danado_335lts = 0;
            } else {
                danado_335lts = Integer.parseInt(etDanado_335lts.getText().toString());
            }
            if (etDanado_5lts.getText().toString().isEmpty()) {
                danado_5lts = 0;
            } else {
                danado_5lts = Integer.parseInt(etDanado_5lts.getText().toString());
            }
            if (etDanado1_5lts.getText().toString().isEmpty()) {
                danado1_5lts = 0;
            } else {
                danado1_5lts = Integer.parseInt(etDanado1_5lts.getText().toString());
            }
            if (etDanado5lts.getText().toString().isEmpty()) {
                danado5lts = 0;
            } else {
                danado5lts = Integer.parseInt(etDanado5lts.getText().toString());
            }
            totalDanado = ((danado_335lts * 8) + (danado_5lts * 8) + (danado1_5lts * 16) + (danado5lts * 50));
            tvTotalDanado.setText("$" + String.valueOf(totalDanado));

            totalBono = (clientesPorVisitar.bonoPorcentaje / 100) * (totalVentas - totalCambios - totalCortesias - totalDanado);
            tvTotalBono.setText("$" + String.valueOf(totalBono));

            totalTotal = totalVentas - totalCambios - totalCortesias - totalDanado - totalBono;
            tvTotalTotal.setText("$" + String.valueOf(totalTotal));
        }
    };

    private void readUltimaventa() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        int ventas_335lts, ventas_5lts, ventas1_5lts, ventas5lts;
        int cambios_335lts, cambios_5lts, cambios1_5lts, cambios5lts;
        int cortesias_335lts, cortesias_5lts, cortesias1_5lts, cortesias5lts;
        int danado_335lts, danado_5lts, danado1_5lts, danado5lts;

        if(clientesPorVisitar.idCliente != 0) {
            idCliente = clientesPorVisitar.idCliente;
        }else if(clientesVisitados.idCliente != 0){
            idCliente = clientesVisitados.idCliente;
        }else{
            idCliente = 0;
        }

        String fecha = dateFormat.format(date);
        DbHelper dbHelper = new DbHelper(verVentasMes.this);
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        Cursor fila1 = bd.rawQuery("SELECT SUM(ventas), SUM(cambios), SUM(cortesias), SUM(danado) FROM VentasClientesConsultar WHERE (idCliente = " + idCliente + ") AND (idProducto = 1)", null);

        if (fila1.moveToFirst()) {
            ventas5lts = fila1.getInt(0);
            cambios5lts = fila1.getInt(1);
            cortesias5lts = fila1.getInt(2);
            danado5lts = fila1.getInt(3);

            if (ventas5lts != 0) {
                etVentas5lts.setText(String.valueOf(ventas5lts));
            }
            if (cambios5lts != 0) {
                etCambios5lts.setText(String.valueOf(cambios5lts));
            }
            if (cortesias5lts != 0) {
                etCortesias5lts.setText(String.valueOf(cortesias5lts));
            }
            if (danado5lts != 0) {
                etDanado5lts.setText(String.valueOf(danado5lts));
            }
        }

        Cursor fila2 = bd.rawQuery("SELECT SUM(ventas), SUM(cambios), SUM(cortesias), SUM(danado) FROM VentasClientesConsultar WHERE (idCliente = " + idCliente + ") AND (idProducto = 2)", null);
        if (fila2.moveToFirst()) {
            ventas_5lts = fila2.getInt(0);
            cambios_5lts = fila2.getInt(1);
            cortesias_5lts = fila2.getInt(2);
            danado_5lts = fila2.getInt(3);

            if (ventas_5lts != 0) {
                etVentas_5lts.setText(String.valueOf(ventas_5lts));
            }
            if (cambios_5lts != 0) {
                etCambios_5lts.setText(String.valueOf(cambios_5lts));
            }
            if (cortesias_5lts != 0) {
                etCortesias_5lts.setText(String.valueOf(cortesias_5lts));
            }
            if (danado_5lts != 0) {
                etDanado_5lts.setText(String.valueOf(danado_5lts));
            }
        }

        Cursor fila3 = bd.rawQuery("SELECT SUM(ventas), SUM(cambios), SUM(cortesias), SUM(danado) FROM VentasClientesConsultar WHERE (idCliente = " + idCliente + ") AND (idProducto = 3)", null);
        if (fila3.moveToFirst()) {
            ventas1_5lts = fila3.getInt(0);
            cambios1_5lts = fila3.getInt(1);
            cortesias1_5lts = fila3.getInt(2);
            danado1_5lts = fila3.getInt(3);

            if (ventas1_5lts != 0) {
                etVentas1_5lts.setText(String.valueOf(ventas1_5lts));
            }
            if (cambios1_5lts != 0) {
                etCambios1_5lts.setText(String.valueOf(cambios1_5lts));
            }
            if (cortesias1_5lts != 0) {
                etCortesias1_5lts.setText(String.valueOf(cortesias1_5lts));
            }
            if (danado1_5lts != 0) {
                etDanado1_5lts.setText(String.valueOf(danado1_5lts));
            }
        }

        Cursor fila4 = bd.rawQuery("SELECT SUM(ventas), SUM(cambios), SUM(cortesias), SUM(danado) FROM VentasClientesConsultar WHERE (idCliente = " + idCliente + ") AND (idProducto = 4)", null);
        Log.d("consulta", "SELECT * FROM VentasClientes WHERE idCliente = " + clientesVisitados.idCliente + " AND fecha LIKE '" + fecha + "' AND idProducto = 4");
        //Log.d("hi", String.valueOf(fila4.getInt(3)));
        if (fila4.moveToFirst()) {
            ventas_335lts = fila4.getInt(0);
            cambios_335lts = fila4.getInt(1);
            cortesias_335lts = fila4.getInt(2);
            danado_335lts = fila4.getInt(3);

            if (ventas_335lts != 0) {
                etVentas_335lts.setText(String.valueOf(ventas_335lts));
            }
            if (cambios_335lts != 0) {
                etCambios_335lts.setText(String.valueOf(cambios_335lts));
            }
            if (cortesias_335lts != 0) {
                etCortesias_335lts.setText(String.valueOf(cortesias_335lts));
            }
            if (danado_335lts != 0) {
                etDanado_335lts.setText(String.valueOf(danado_335lts));
            }
        }
    }
}
