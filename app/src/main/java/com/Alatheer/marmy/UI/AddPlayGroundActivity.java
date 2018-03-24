package com.Alatheer.marmy.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.ActivitySelectLocation;
import com.Alatheer.marmy.Adapter.CustomGalleryAdapter;
import com.Alatheer.marmy.Model.MessageResponse;
import com.Alatheer.marmy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPlayGroundActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener ,GoogleApiClient.ConnectionCallbacks,LocationListener{
    Gallery simpleGallery;
    ImageView multiImg;
    EditText name, cost, adress, phone;
    Button add;
    int IMG_REQ = 200;
    List<Uri> uriList;
    CustomGalleryAdapter customGalleryAdapter;
    List<Uri> selectedImage;
    List<String> enCodedImageList;
    String id;
    ProgressDialog dialog;
    private final int PER_REQ = 1000;
    private boolean isGranted = false;
    LocationManager manager;
    private AlertDialog.Builder builder;
    private String lat, lng;
    private final int LOC_REQ = 3000;
    Spinner spinner;
    String msupplier, item;
    private Location location;
    private FusedLocationProviderClient locationProviderClient;
    GoogleApiClient apiClient;
    LocationRequest locationRequest;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_play_ground);
        uriList = new ArrayList<>();
        selectedImage = new ArrayList<>();
        initView();
        getDataFromIntent();
        CreateDialog();
        //CheckPermission();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.capcity_array, R.layout.sipnneritem2);
        adapter.setDropDownViewResource(R.layout.sppinnersheck);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                msupplier = spinner.getSelectedItem().toString();

                if (msupplier.equals("6*6")) {
                    item = "1";
                } else if (msupplier.equals("7*7")) {
                    item = "2";
                } else if (msupplier.equals("8*8")) {
                    item = "3";
                } else if (msupplier.equals("9*9")) {
                    item = "4";
                } else if (msupplier.equals("10*10")) {
                    item = "5";
                } else {
                    item = "6";
                }


                Log.e("Selected item : ", item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                ) {
            isGranted = true;
        } else {
            isGranted = false;
            String[] Permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(AddPlayGroundActivity.this, Permissions, PER_REQ);

            //location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id");
            }
        }
    }

    private void initView() {

        enCodedImageList = new ArrayList<>();
        simpleGallery = findViewById(R.id.simpleGallery); // get the reference of Gallery
        multiImg = findViewById(R.id.images);
        name = findViewById(R.id.name_ground);
        cost = findViewById(R.id.cost);
        adress = findViewById(R.id.adress);
        phone = findViewById(R.id.phone);
        add = findViewById(R.id.addmal3aab);
        spinner = findViewById(R.id.spinner);


        multiImg.setOnClickListener(this);
        add.setOnClickListener(this);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        builder = new AlertDialog.Builder(this);
        builder.setMessage("هل موقعك الحالي هو موقع الملعب الذي تريد اضافتة");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    getDeviceLocation();
                   // settingsRequest();
                //
            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(AddPlayGroundActivity.this, ActivitySelectLocation.class);
                startActivityForResult(intent, LOC_REQ);

            }
        });
        apiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addOnConnectionFailedListener(this).addConnectionCallbacks(this).build();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                ) {
            isGranted = true;
            //getDeviceLocation();
        } else {
            //isGranted = false;
            String[] Permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(AddPlayGroundActivity.this, Permissions, PER_REQ);

            //location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }
        //locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void CreateDialog() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("جاري اضافة ملعب...");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addmal3aab:
                AddPlayGround();
                break;

            case R.id.images:

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, IMG_REQ);


                break;


        }

    }

    private void AddPlayGround() {

        /*getDeviceLocation();*/
        String p_name = name.getText().toString();
        String p_cost = cost.getText().toString();
        String p_capacity = item;
        String p_address = adress.getText().toString();
        String p_phone = phone.getText().toString();


        if (enCodedImageList.size() > 0 && !TextUtils.isEmpty(p_name) && !TextUtils.isEmpty(p_cost) && !TextUtils.isEmpty(p_address) && !TextUtils.isEmpty(p_phone)) {

            builder.show();

           /* dialog.show();
           */
        }
        if (enCodedImageList.size() == 0) {
            Toast.makeText(this, "اختر مجموعة صور للملعب حد ادنى 2 حد اقصى 5", Toast.LENGTH_LONG).show();
        }
        if (enCodedImageList.size() == 0 && TextUtils.isEmpty(p_name) && TextUtils.isEmpty(p_cost) && TextUtils.isEmpty(p_capacity) && TextUtils.isEmpty(p_address) && TextUtils.isEmpty(p_phone)) {
            name.setError("ادخل اسم الملعب");
            cost.setError("ادخل سعر الملعب");
            adress.setError("ادخل العنوان");
            phone.setError("ادخل الهاتف");
        }
        if (TextUtils.isEmpty(p_name)) {
            name.setError("ادخل اسم الملعب");

        } else if (!TextUtils.isEmpty(p_name)) {
            name.setError(null);

        }
        if (TextUtils.isEmpty(p_cost)) {
            cost.setError("ادخل سعر الملعب");

        } else if (!TextUtils.isEmpty(p_cost)) {
            cost.setError(null);

        }

        if (TextUtils.isEmpty(p_phone)) {
            cost.setError("ادخل رقم الهاتف");

        } else if (!TextUtils.isEmpty(p_phone)) {
            cost.setError(null);

        }


        if (TextUtils.isEmpty(p_address)) {
            adress.setError("ادخل العنوان");

        } else if (!TextUtils.isEmpty(p_address)) {
            adress.setError(null);

        }


    }

    private void getDeviceLocation() {

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
        location =LocationServices.FusedLocationApi.getLastLocation(apiClient);
        if (location!=null)
        {
            String lat = String.valueOf(location.getLatitude());
            String lng = String.valueOf(location.getLongitude());
            UploadData(lat, lng);
        }else
            {
                if (!apiClient.isConnected())
                {
                    apiClient.connect();
                    //LocationServices.FusedLocationApi.requestLocationUpdates(apiClient,locationRequest, this);

                }

            }
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (isGranted) {
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Toast.makeText(this, "cccccccccccc", Toast.LENGTH_SHORT).show();
            if (manager != null) {

                Toast.makeText(this, "bbbb", Toast.LENGTH_SHORT).show();

                if (location != null) {
                    Log.e("www", "wwwwwwwwwwwwwwwwwwwww");

                    String lat = String.valueOf(location.getLatitude());
                    String lng = String.valueOf(location.getLongitude());
                    UploadData(lat, lng);
                } else {
                    Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();

                }
            }


        } else {
            Toast.makeText(this, "عفوا لا يمكن تحديد الموقع الخاص بك", Toast.LENGTH_SHORT).show();
        }*/
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            uriList.clear();

            //   Log.e("eddddd",""+clipData.getItemCount()+"   "+clipData.getItemAt(0));
            if (clipData != null) {


                for (int index = 0; index < clipData.getItemCount(); index++) {
                    ClipData.Item item = clipData.getItemAt(index);
                    Uri uri = item.getUri();
                    uriList.add(uri);

                }
                if (uriList.size() > 5) {
                    for (int i = 0; i <= 4; i++) {
                        selectedImage.add(uriList.get(i));
                    }
                    Toast.makeText(this, "size1" + selectedImage.size(), Toast.LENGTH_SHORT).show();

                    UpdateUI(selectedImage);
                } else {

                    selectedImage = uriList;
                    UpdateUI(selectedImage);
                    Toast.makeText(this, "size2" + selectedImage.size(), Toast.LENGTH_SHORT).show();


                }


            } else {
                   /* selectedImage.clear();
                    Uri uri = data.getData();
                    selectedImage.add(uri);

                    UpdateUI(selectedImage);*/
                Toast.makeText(this, "اختر مجموعة صور للملعب حد ادنى 2 حد اقصى 5", Toast.LENGTH_LONG).show();
            }


//
//            Intent intent = new Intent(AddPlayGroundActivity.this,DetailsAlbumaty.class);
//
//            intent.putExtra("details", (Serializable) uriList);
//
//            startActivity(intent);
//
//            Toast.makeText(this, ""+uriList.get(0), Toast.LENGTH_SHORT).show();
        } else if (requestCode == LOC_REQ && resultCode == RESULT_OK && data != null) {
            String lat = data.getStringExtra("lat");
            String lng = data.getStringExtra("lng");
            UploadData(lat, lng);
        }
        else if (requestCode==1000&&resultCode == RESULT_OK && data != null)
        {
            getDeviceLocation();
        }
    }

    private void UpdateUI(List<Uri> uriList) {

        customGalleryAdapter = new CustomGalleryAdapter(this, uriList); // initialize the adapter
        simpleGallery.setAdapter(customGalleryAdapter);

        enCodeImage(uriList);
    }

    private List<String> enCodeImage(List<Uri> imagesUri) {

        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < imagesUri.size(); i++) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagesUri.get(i)));
                bitmapList.add(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (bitmapList.size() > 0) {
            for (int i = 0; i < bitmapList.size(); i++) {
                Bitmap bitmap = bitmapList.get(i);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

                byte[] bytes = outputStream.toByteArray();

                enCodedImageList.add(Base64.encodeToString(bytes, Base64.DEFAULT));

                Log.e("imaaaaaaaaaaaaaaaaage", enCodedImageList.get(i));

            }
        }
        return enCodedImageList;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false;
                } else {
                    isGranted = true;
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
                    location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
            }
        }
    }

    private void UploadData(String lat,String lng)
    {
        Log.e("dd","sssssssssssssssssssssssssssssssss");
        String p_name = name.getText().toString();
        String p_cost = cost.getText().toString();
        String p_capacity = item;
        String p_address = adress.getText().toString();
        String p_phone = phone.getText().toString();

        Log.e("name", p_name);
        Log.e("name", p_cost);
        Log.e("name", p_capacity);
        Log.e("name", p_address);
        Log.e("name", id);
      //  Log.e("name", enCodedImageList.get(0) + "\n" + enCodedImageList.get(1) + "\n" + enCodedImageList.get(0));
        dialog.show();
        Retrofit retrofit = APIClient.getClient();
        Services services = retrofit.create(Services.class);
        Call<MessageResponse> call = services.addplayground(p_name, p_cost, p_capacity, p_address, lng, lat, id, enCodedImageList,p_phone);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() == 1) {
                        Toast.makeText(AddPlayGroundActivity.this, "add playground successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(AddPlayGroundActivity.this, "add playground failed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }





            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
                dialog.dismiss();
                Toast.makeText(AddPlayGroundActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Log.e("lat",""+lat);
        Log.e("lat",""+lng);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isConnected = false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        isConnected = true;
        //settingsRequest();

    }

    private void settingsRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(apiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates state = locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                      //getDeviceLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(AddPlayGroundActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;

                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (apiClient!=null && apiClient.isConnected())
        {
            apiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location!=null)
        {
          this.location = location;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (apiClient!=null)
        {
            apiClient.connect();
        }
    }
}
