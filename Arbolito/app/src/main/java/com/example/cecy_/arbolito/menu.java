package com.example.cecy_.arbolito;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        cleanDB();

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

    public void cleanDB() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(menu.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.execSQL("DELETE FROM notacobrar");
            database.execSQL("DELETE FROM cliente");

            readFromServerClientesPorVisitar();
            readFromServerNotaCobrar();

        }
    }


    public void readFromServerClientesPorVisitar() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(menu.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncClientesPorVisitar.php?Ruta="+login.idRuta+"&idUsuario=" + login.idUsuario,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("insertadoClientes", response);
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                        /*Toast.makeText(clientesPorVisitar.this, "insertado: " + jsonObject,
                                Toast.LENGTH_LONG).show();*/
                                    dbHelper.saveToLocalDatabaseClientes(jsonObject.getInt("idCliente"), jsonObject.getInt("idTipoNegocio"), jsonObject.getInt("idRuta"), jsonObject.getString("nombrePropietario"), jsonObject.getString("nombreNegocio"), jsonObject.getString("domicilio"), jsonObject.getString("colonia"), jsonObject.getString("ciudad"), jsonObject.getString("telefono"), jsonObject.getInt("notaCobrar"), jsonObject.getDouble("bono"), jsonObject.getString("latitud"), jsonObject.getString("longitud"), jsonObject.getInt("estado"), database);
                                }
                                //readFromLocalStorage();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(menu.this, "error: " + e,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //readFromLocalStorage();

                }
            });

            MySingleton.getInstance(menu.this).addToRequestQue(stringRequest);

        } else {
            //readFromLocalStorage();
        }
    }

    public void readFromServerNotaCobrar() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(menu.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncNotaCobrar.php?Ruta="+login.idRuta,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                    /*Toast.makeText(clientesPorVisitar.this, "insertado: " + response,
                                            Toast.LENGTH_LONG).show();*/
                                    dbHelper.saveToLocalDatabaseProductoAsig(jsonObject.getInt("idProductoAsig"), jsonObject.getInt("idUsuario"), jsonObject.getInt("idRuta"), jsonObject.getString("fecha"), database);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(menu.this, "error: " + e,
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(menu.this).addToRequestQue(stringRequest);

        }
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelado");
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Escaneado");
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
