package com.example.cecy_.arbolito;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DbHelper dbHelper = new DbHelper(MapsActivity.this);
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        double lat, lon;

        //LatLng address = new LatLng(20.70379, -102.34855);
        try{
            lat = Double.parseDouble(clientesPorVisitar.lat);
            lon = Double.parseDouble(clientesPorVisitar.lon);
        }catch(Exception e){
            lat = 0;
            lon = 0;
        }

        LatLng address = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(address).title("Domicilio"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(adress));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }
}
