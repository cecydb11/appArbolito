package com.example.cecy_.arbolito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cecy_ on 02/08/2018.
 */
public class NetworkMonitor extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (checkNetworkConnection(context)){
            final DbHelper dbHelper = new DbHelper(context);
            final SQLiteDatabase database = dbHelper.getWritableDatabase();
            Cursor cursor = dbHelper.readFromLocalDatabaseVentas(database);

            while(cursor.moveToNext()){
                int edit = cursor.getInt(cursor.getColumnIndex("edit"));
                int sync_status = cursor.getInt(cursor.getColumnIndex(DbHelper.SYNC_STATUS));
                if(sync_status == 0 &&  edit == 0){
                    Log.d("paraInsertar", "tengoInserts");
                    final String idCliente = cursor.getString(cursor.getColumnIndex("idCliente"));
                    final String idProducto = cursor.getString(cursor.getColumnIndex("idProducto"));
                    final String ventas = cursor.getString(cursor.getColumnIndex("ventas"));
                    final String cambios = cursor.getString(cursor.getColumnIndex("cambios"));
                    final String cortesia = cursor.getString(cursor.getColumnIndex("cortesia"));
                    final String devolucion = cursor.getString(cursor.getColumnIndex("devolucion"));
                    final String danado = cursor.getString(cursor.getColumnIndex("danado"));
                    final String precio = cursor.getString(cursor.getColumnIndex("precio"));
                    final String ventaNo = cursor.getString(cursor.getColumnIndex("ventaNo"));
                    final String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                    final String createdOn = cursor.getString(cursor.getColumnIndex("createdOn"));
                    final String updatedOn = cursor.getString(cursor.getColumnIndex("updatedOn"));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DbHelper.SERVER_URL + "insertVenta.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("insertadoVenta", response);
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Response = jsonObject.getString("response");
                                    if(Response.equals("OK")){
                                        dbHelper.updateLocalDatabase(Integer.parseInt(idCliente), 1, database);
                                        context.sendBroadcast(new Intent(DbHelper.UI_UPDATE_BROADCAST));
                                        Toast.makeText(context, "Datos sincronizados.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("errorSincronizaVenta", e.getMessage());
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
                            params.put("idCliente", String.valueOf(idCliente));
                            params.put("idProducto", String.valueOf(idProducto));
                            params.put("ventas", String.valueOf(ventas));
                            params.put("cambios", String.valueOf(cambios));
                            params.put("cortesia", String.valueOf(cortesia));
                            params.put("devolucion", String.valueOf(devolucion));
                            params.put("danado", String.valueOf(danado));
                            params.put("precio", String.valueOf(precio));
                            params.put("ventaNo", String.valueOf(ventaNo));
                            params.put("fecha", fecha);
                            params.put("createdOn", createdOn);
                            params.put("updatedOn", updatedOn);
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
