package com.example.cecy_.arbolito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkMonitorNotaCobrar extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (checkNetworkConnection(context)){
            final DbHelper dbHelper = new DbHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncNotaCobrar.php?Ruta="+login.idRuta,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                    dbHelper.saveToLocalDatabaseNotaCobrar(jsonObject.getInt("idNota"), jsonObject.getInt("idCliente"), jsonObject.getDouble("cantidad"), jsonObject.getString("folio"), jsonObject.getDouble("cantidadPago"), jsonObject.getInt("isPagada"), jsonObject.getString("fechaNota"), jsonObject.getString("fechaCobrar"), database);
                                    Toast.makeText(context, "Datos sincronizados.",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            MySingleton.getInstance(context).addToRequestQue(stringRequest);
        }
    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

}
