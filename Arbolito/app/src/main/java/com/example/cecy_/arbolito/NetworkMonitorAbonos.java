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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkMonitorAbonos extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (checkNetworkConnection(context)){
            final DbHelper dbHelper = new DbHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            Cursor cursor = dbHelper.readFromLocalDatabaseAbonos(database);

            while(cursor.moveToNext()){
                int sync_status = cursor.getInt(cursor.getColumnIndex(DbHelper.SYNC_STATUS));
                if(sync_status == 0){
                    final String idPago = cursor.getString(cursor.getColumnIndex("idPago"));
                    final String idNota = cursor.getString(cursor.getColumnIndex("idNota"));
                    final String idUsuario = cursor.getString(cursor.getColumnIndex("idUsuario"));
                    final String cantidadPago = cursor.getString(cursor.getColumnIndex("cantidadPago"));
                    final String createdOn = cursor.getString(cursor.getColumnIndex("createdOn"));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DbHelper.SERVER_URL + "insertAbono.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String Response = jsonObject.getString("response");
                                        if(Response.equals("OK")){
                                            dbHelper.updateLocalDatabaseAbonos(Integer.parseInt(idPago), 1, database);
                                            context.sendBroadcast(new Intent(DbHelper.UI_UPDATE_BROADCAST));
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
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("idNota", String.valueOf(idNota));
                            params.put("idUsuario", String.valueOf(idUsuario));
                            params.put("cantidadPago", String.valueOf(cantidadPago));
                            params.put("createdOn", createdOn);
                            return params;
                        }
                    };
                    MySingleton.getInstance(context).addToRequestQue(stringRequest);
                }
            }
        }
    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }
}

