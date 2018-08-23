package com.example.cecy_.arbolito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cecy_ on 07/08/2018.
 */
public class NetworkMonitorClientes extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (checkNetworkConnection(context)){
            final DbHelper dbHelper = new DbHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbHelper.SERVER_URL + "syncClientes.php?Ruta="+login.idRuta+"&idUsuario=" + login.idUsuario,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int x = 0; x < array.length(); x++){
                                    JSONObject jsonObject = array.getJSONObject(x);
                                    dbHelper.saveToLocalDatabaseClientes(jsonObject.getInt("idCliente"), jsonObject.getInt("idTipoNegocio"), jsonObject.getInt("idRuta"), jsonObject.getString("nombrePropietario"), jsonObject.getString("nombreNegocio"), jsonObject.getString("domicilio"), jsonObject.getString("colonia"), jsonObject.getString("ciudad"), jsonObject.getString("telefono"), jsonObject.getInt("notaCobrar"), jsonObject.getDouble("bono"), jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"), jsonObject.getInt("estado"), database);
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
