package com.example.cecy_.arbolito;

import android.content.BroadcastReceiver;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class login extends AppCompatActivity {
    private EditText user, pass;
    private Button ingresar;
    public static String usuario, usuarioPass;
    public static int idUsuario, idRuta;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
    Date fecha = new Date();
    String date = dateFormat.format(fecha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //readFromServer();
        //readFromServerProductoAsig();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cleanDB();
        getSupportActionBar().hide();

        user = (EditText) findViewById(R.id.etUser);
        pass = (EditText) findViewById(R.id.etPass);
        ingresar = (Button) findViewById(R.id.btnIngresar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                usuario = user.getText().toString();
                usuarioPass = pass.getText().toString();
                String MD5_Pass = md5(usuarioPass);
                String pass_db;

                DbHelper dbHelper = new DbHelper(login.this);
                SQLiteDatabase bd = dbHelper.getWritableDatabase();
                Cursor fila = bd.rawQuery("SELECT Usuario.idUsuario, usuario, md5, idRuta FROM Usuario LEFT JOIN ProductoAsig ON Usuario.idUsuario = ProductoAsig.idUsuario WHERE usuario LIKE '" + usuario + "'", null);

                if (fila.moveToNext()) {
                    pass_db = fila.getString(2);

                    //if ((fila.getString(2)).equals(usuarioPass)) {
                    if ((fila.getString(2)).equals(MD5_Pass)) {
                        idUsuario = fila.getInt(0);
                        idRuta = fila.getInt(3);
                        Intent activ = new Intent(login.this, menu.class);
                        startActivity(activ);
                    } else {
                        Toast.makeText(login.this, "Contraseña incorrecta.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(login.this, "No existe el usuario.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String md5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    private void readFromServer() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(login.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncLogin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int x = 0; x < array.length(); x++){
                                JSONObject jsonObject = array.getJSONObject(x);
                                /*Toast.makeText(login.this, "insertado: " + response,
                                        Toast.LENGTH_LONG).show();*/
                                dbHelper.saveToLocalDatabaseUsuario(jsonObject.getInt("idUsuario"), jsonObject.getString("usuario"), jsonObject.getString("nombreUsuario"), jsonObject.getString("contrasena"), jsonObject.getString("md5"), jsonObject.getInt("tipoUsuario"), jsonObject.getInt("sucursal"), database);
                            }
                            if(array.length() > 0) {
                                Toast.makeText(login.this, "Usuarios sincronizados.",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(login.this, "Error al sincronizar.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "error: " + e,
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(login.this).addToRequestQue(stringRequest);

        }
    }

    private void readFromServerProductoAsig() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(login.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncProductoAsig.php",
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
                            Toast.makeText(login.this, "error: " + e,
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(login.this).addToRequestQue(stringRequest);
        }
    }

    private void readFromServerProducto() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(login.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncProducto.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                /*Toast.makeText(login.this, "insertado: " + response,
                                        Toast.LENGTH_LONG).show();*/
                                    dbHelper.saveToLocalDatabaseProducto(jsonObject.getInt("idProducto"), jsonObject.getDouble("precio"), database);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(login.this, "error: " + e,
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(login.this).addToRequestQue(stringRequest);
        }
    }

    private void readFromServerVentasConsultar() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(login.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncVentas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int x = 0; x < array.length(); x++){
                                JSONObject jsonObject = array.getJSONObject(x);
                            /*Toast.makeText(login.this, "insertado: " + response,
                                    Toast.LENGTH_LONG).show();*/
                                dbHelper.saveToLocalDatabaseVentasConsultar(jsonObject.getInt("idCliente"), jsonObject.getInt("idProducto"), jsonObject.getInt("ventas"), jsonObject.getInt("cambios"), jsonObject.getInt("cortesia"), jsonObject.getInt("danado"), (float) jsonObject.getDouble("precio"),  jsonObject.getInt("ventaNo"), jsonObject.getString("fecha"), database);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "error: " + e,
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(login.this).addToRequestQue(stringRequest);

        }
    }

    public void cleanDB() {
        if (checkNetworkConnection()) {
            final DbHelper dbHelper = new DbHelper(login.this);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.execSQL("DELETE FROM usuario");
            database.execSQL("DELETE FROM productoAsig");
            database.execSQL("DELETE FROM VentasClientesConsultar");
            database.execSQL("DELETE FROM producto");

            readFromServer();
            readFromServerProductoAsig();
            readFromServerProducto();
            readFromServerVentasConsultar();
        }
    }


    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
