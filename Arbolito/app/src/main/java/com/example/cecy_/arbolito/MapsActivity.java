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
        Long lat = null, lon = null;

        //LatLng adress = new LatLng(20.70379, -102.34855);
        Toast.makeText(getApplicationContext(),
                "Lon " + clientesPorVisitar.lon + " lat " + clientesPorVisitar.lat,
                Toast.LENGTH_LONG).show();
        LatLng adress = new LatLng(clientesPorVisitar.lat,clientesPorVisitar.lon);
        mMap.addMarker(new MarkerOptions().position(adress).title("Domicilio"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(adress, 15));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(adress));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));


    }
}
