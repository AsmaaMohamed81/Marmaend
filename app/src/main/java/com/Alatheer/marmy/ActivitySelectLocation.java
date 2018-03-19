package com.Alatheer.marmy;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Model.MessageResponse;
import com.Alatheer.marmy.UI.AddPlayGroundActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivitySelectLocation extends AppCompatActivity implements OnMapReadyCallback{

    private Button searchBtn;
    private EditText search_txt;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private boolean isGranted =false;
    private final int PER_REQ = 2500;
    private LocationManager manager;
    private TextView done;
    private String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initView();
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                )
        {
            isGranted = false;
            String[] Permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(ActivitySelectLocation.this, Permissions, PER_REQ);
        } else {
            isGranted = true;
            initMap();
        }
    }

    private void initView() {

        done = findViewById(R.id.done);
        searchBtn = findViewById(R.id.searchBtn);
        search_txt = findViewById(R.id.text_search);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc = search_txt.getText().toString();
                if (TextUtils.isEmpty(loc))
                {
                    Toast.makeText(ActivitySelectLocation.this, "من فضلك ادخل العنوان بالتفصيل", Toast.LENGTH_LONG).show();
                }else
                    {
                        getLocation(loc);
                    }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = getIntent();
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void getLocation(String loc) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(loc,5);
            Address address = addressList.get(0);
            if (addressList.size()>0)
            {


                if (address!=null)
                {
                    lat = String.valueOf(address.getLatitude());
                    lng = String.valueOf(address.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(),address.getLongitude()),11f));
                    done.setVisibility(View.VISIBLE);

                    if (address.getLocality()!=null||!address.getLocality().isEmpty())
                    {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())).title(address.getLocality()));
                    }else
                    {
                        mMap.clear();

                        mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));

                    }
                }
            }else
            {
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMap()
    {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap!=null)
        {
            mMap = googleMap;
            getDeviceLocation();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0)
        {
            for (int i=0;i<grantResults.length;i++)
            {
                if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                {
                    isGranted=false;
                }else
                    {
                        initMap();
                    }
            }
        }
    }
    private void getDeviceLocation()
    {
        if (isGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            if (manager!=null)
            {

                Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.e("loooo","  "+location.getLongitude());
                if (location!=null)
                {


                    double  lat = location.getLatitude();
                    double  lng =location.getLongitude();

                    Log.e("location",""+lat+","+lng);
                    Geocoder geocoder = new Geocoder(ActivitySelectLocation.this);
                    try {
                        List<Address> addressList = geocoder.getFromLocation(lat,lng,1);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),11f));
                        if (addressList.size()>0)
                        {
                            Address address = addressList.get(0);

                            if (address!=null)
                            {


                                if (address.getLocality()!=null||!address.getLocality().isEmpty())
                                {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(address.getLocality()));
                                }else
                                    {
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));

                                    }
                            }
                        }else
                            {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));

                            }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



            }


        }else
        {
            Toast.makeText(this, "عفوا لا يمكن تحديد الموقع الخاص بك", Toast.LENGTH_SHORT).show();
        }
    }

}
}
