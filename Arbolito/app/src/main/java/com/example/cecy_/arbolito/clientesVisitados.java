package com.example.cecy_.arbolito;

import android.app.Dialog;
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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class clientesVisitados extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Clientes> arrayList = new ArrayList<>();
    BroadcastReceiver broadcastReceiver;
    public static String cliente;
    private int notaID;
    public double cantidadAbono;
    public static String lat, lon;
    public static double bonoPorcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_por_visitar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewVisitar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        readFromServerNotaCobrar();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromLocalStorage();
            }
        };

        final GestureDetector mGestureDetector = new GestureDetector(clientesVisitados.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        final int position = recyclerView.getChildAdapterPosition(child);

                        lat = arrayList.get(position).getLatitud();
                        lon = arrayList.get(position).getLongitud();
                        bonoPorcentaje = arrayList.get(position).getBono();


                        final Dialog dialog = new Dialog(clientesVisitados.this);
                        dialog.setContentView(R.layout.detalles_cliente);
                        dialog.setTitle("Detalles de cliente");

                        //Elementos del dialog
                        TextView nombreNegocio = (TextView) dialog.findViewById(R.id.tvNombreNegocio);
                        nombreNegocio.setText(arrayList.get(position).getNombreNegocio());

                        TextView nombrePropietario = (TextView) dialog.findViewById(R.id.tvNombrePropietario);
                        nombrePropietario.setText(arrayList.get(position).getNombrePropietario());

                        TextView tipoNegocio = (TextView) dialog.findViewById(R.id.tvTipoNegocio);
                        tipoNegocio.setText(arrayList.get(position).getTipoNegocio());

                        TextView domicilio = (TextView) dialog.findViewById(R.id.tvDomicilio);
                        domicilio.setText(arrayList.get(position).getDomicilio());

                        TextView telefono = (TextView) dialog.findViewById(R.id.tvTelefono);
                        telefono.setText(arrayList.get(position).getTelefono());

                        Button capturar = (Button) dialog.findViewById(R.id.btnCapturar);
                        capturar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent activ = new Intent(clientesVisitados.this, crearVenta.class);
                                startActivity(activ);
                            }
                        });

                        Button ubicacion = (Button) dialog.findViewById(R.id.btnUbicacion);
                        ubicacion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent activ = new Intent(clientesVisitados.this, MapsActivity.class);
                                startActivity(activ);
                            }
                        });

                        Button nota = (Button) dialog.findViewById(R.id.btnNota);
                        if(arrayList.get(position).getNotaCobrar() == 1) {
                            nota.setVisibility(View.VISIBLE);
                            nota.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog dialog2 = new Dialog(clientesVisitados.this);
                                    dialog2.setContentView(R.layout.nota_cobrar);
                                    dialog2.setTitle("Nota Cobrar");

                                    dialog.dismiss();

                                    //Elementos del dialog
                                    TextView nombreCliente = (TextView) dialog2.findViewById(R.id.tvNombreClienteNota);
                                    nombreCliente.setText(arrayList.get(position).getNombreNegocio());

                                    final Spinner notas = (Spinner) dialog2.findViewById(R.id.spNotas);

                                    final DbHelper dbHelper = new DbHelper(clientesVisitados.this);
                                    SQLiteDatabase bd = dbHelper.getWritableDatabase();

                                    Cursor fila = bd.rawQuery("SELECT idNota as _id, cantidad, folio FROM notacobrar WHERE idCliente = " + arrayList.get(position).getIdCliente(), null);

                                    final SimpleCursorAdapter adapter = new SimpleCursorAdapter(clientesVisitados.this,android.R.layout.simple_spinner_item,fila,new String[]{"folio"},new int[] {android.R.id.text1});

                                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                                    notas.setAdapter(null);
                                    notas.setAdapter(adapter);

                                    notas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                            notaID = ((Cursor) notas.getSelectedItem()).getInt(0);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                    EditText abono = (EditText) dialog2.findViewById(R.id.etAbonoNota);
                                    abono.setText("");

                                    Button guardar = (Button) dialog2.findViewById(R.id.btnGuardar);
                                    guardar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DbHelper dbHelper = new DbHelper(clientesVisitados.this);
                                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                                            dbHelper.saveToLocalDatabasePagoNota(notaID, login.idUsuario, cantidadAbono, 0, database);
                                            dbHelper.close();
                                            dialog.dismiss();
                                        }
                                    });

                                    Button cancelar = (Button) dialog2.findViewById(R.id.btnCancelar);
                                    cancelar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog2.dismiss();
                                        }
                                    });
                                    dialog2.show();
                                }
                            });
                        }else{
                            nota.setVisibility(View.INVISIBLE);
                        }

                        dialog.show();
                        Toast.makeText(clientesVisitados.this,"Cliente: "+ arrayList.get(position).getNombreNegocio() ,Toast.LENGTH_SHORT).show();
                        cliente = arrayList.get(position).getNombreNegocio();
                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });
        readFromServer();
    }

    private void readFromServer() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(clientesVisitados.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncClientes.php?Ruta="+login.idRuta+"&idUsuario=" + login.idUsuario,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                    /*Toast.makeText(clientesPorVisitar.this, "insertado: " + jsonObject,
                                            Toast.LENGTH_LONG).show();*/
                                    dbHelper.saveToLocalDatabaseClientes(jsonObject.getInt("idCliente"), jsonObject.getInt("idTipoNegocio"), jsonObject.getInt("idRuta"), jsonObject.getString("nombrePropietario"), jsonObject.getString("nombreNegocio"), jsonObject.getString("domicilio"), jsonObject.getString("colonia"), jsonObject.getString("ciudad"), jsonObject.getString("telefono"), jsonObject.getInt("notaCobrar"), jsonObject.getDouble("bono"), jsonObject.getString("latitud"), jsonObject.getString("longitud"), jsonObject.getInt("estado"), database);
                                }
                                readFromLocalStorage();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(clientesVisitados.this, "error: " + e,
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    readFromLocalStorage();

                }
            });

            MySingleton.getInstance(clientesVisitados.this).addToRequestQue(stringRequest);

        } else {
            readFromLocalStorage();
        }
    }

    private void readFromLocalStorage() {
        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readFromLocalDatabaseClientes(database);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("nombreNegocio"));
            String nombrePropietario = cursor.getString(cursor.getColumnIndex("nombrePropietario"));
            String tipoNegocio = "Abarrotes"; //cursor.getString(cursor.getColumnIndex("tipoNegocio"));
            String Domicilio = cursor.getString(cursor.getColumnIndex("domicilio"));
            String Telefono = cursor.getString(cursor.getColumnIndex("telefono"));
            int Estado = cursor.getInt(cursor.getColumnIndex("estado"));
            int NotaCobrar = cursor.getInt(cursor.getColumnIndex("notaCobrar"));
            int idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"));
            double bono = cursor.getDouble(cursor.getColumnIndex("bono"));
            String latitud = cursor.getString(cursor.getColumnIndex("latitud"));
            String longitud = cursor.getString(cursor.getColumnIndex("longitud"));
            arrayList.add(new Clientes(name, nombrePropietario, tipoNegocio, Domicilio,Telefono, Estado, NotaCobrar, idCliente, bono, latitud, longitud));
        }

        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }

    public void submitVenta(View view){
        saveToAppServer(notaID, login.idUsuario, cantidadAbono);
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void saveToLocalStorage(int idNota, int idUsuario, double cantidadPago, int sync){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        dbHelper.saveToLocalDatabasePagoNota(idNota, idUsuario, cantidadPago, sync, database);
        dbHelper.close();
    }

    private void saveToAppServer(final int idNota, final int idUsuario, final double cantidadPago) {
        if (checkNetworkConnection()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbHelper.SERVER_URL + "insertAbono.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(Response.equals("OK")){
                                    saveToLocalStorage(idNota, idUsuario, cantidadPago,1);
                                    Toast.makeText(getApplicationContext(),
                                            "Datos guardados en el servidor.",
                                            Toast.LENGTH_LONG).show();
                                }else{
                                    saveToLocalStorage(idNota, idUsuario, cantidadPago, 0);


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
                    saveToLocalStorage(idNota, idUsuario, cantidadPago, 0);
                    Toast.makeText(getApplicationContext(),
                            "Datos guardados localmente2.",
                            Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idNota", String.valueOf(idNota));
                    params.put("idUsuario", String.valueOf(idUsuario));
                    params.put("cantidadPago", String.valueOf(cantidadPago));

                    return params;
                }
            };

            MySingleton.getInstance(clientesVisitados.this).addToRequestQue(stringRequest);

        } else {
            saveToLocalStorage(idNota, idUsuario, cantidadPago, 0);
            Toast.makeText(getApplicationContext(),
                    "Datos guardados localmente3.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void readFromServerNotaCobrar() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(clientesVisitados.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncNotaCobrar.php?Ruta="+login.idRuta,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                    /*Toast.makeText(login.this, "insertado: " + response,
                                            Toast.LENGTH_LONG).show();*/
                                    dbHelper.saveToLocalDatabaseProductoAsig(jsonObject.getInt("idProductoAsig"), jsonObject.getInt("idUsuario"), jsonObject.getInt("idRuta"), jsonObject.getString("fecha"), database);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(clientesVisitados.this, "error: " + e,
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(clientesVisitados.this).addToRequestQue(stringRequest);

        }
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
