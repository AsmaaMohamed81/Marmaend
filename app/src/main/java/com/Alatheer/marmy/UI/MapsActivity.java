package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Alatheer.marmy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Double latitude, longitude;
    String name, address;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        latitude = i.getDoubleExtra("latitude", 1.1);
        longitude = i.getDoubleExtra("longitude", 1.1);
        name = i.getStringExtra("namegraund");
        address = i.getStringExtra("address");

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //   Toast.makeText(this, ""+latitude +"ss"+longitude, Toast.LENGTH_SHORT).show();

//         Log.e("eeeee",latitude+"");
//        Log.e("ffff",latitude+"");

        // Add a marker in Sydney and move the camera
     /*   LatLng playground = new LatLng(latitude, longitude);
        MarkerOptions marker=new MarkerOptions().position(playground).title("الملعب");
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(playground,16.0f));
*/


        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.football);
        LatLng playground = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(playground)
                .title(name)
                .snippet(address)
                .icon(icon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(playground, 16.0f));

        mMap.addMarker(markerOptions);
    }
}
