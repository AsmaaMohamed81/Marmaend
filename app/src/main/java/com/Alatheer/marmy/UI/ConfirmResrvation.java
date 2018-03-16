package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Alatheer.marmy.Model.User;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfirmResrvation extends AppCompatActivity {
    EditText name,phone;
    ImageView img;
    ImageView upload;
    TextView textim;
    Button send;
    String client_id,client_name,client_phone, picturePath,Reservation_id;
    private String selectedImagePath;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_resrvation);

        name=findViewById(R.id.edtnme);
        phone=findViewById(R.id.edtphone);
        upload=findViewById(R.id.btnupload);
        send=findViewById(R.id.btnsend);
        textim=findViewById(R.id.textimg);
        get_intent();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();


            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            upload.setImageURI(selectedImage);
            textim.setText(picturePath);


            //Toast.makeText(this, ""+picturePath, Toast.LENGTH_SHORT).show();



        }

    }


    private void sendData(){
        Services services=APIClient.getClient().create(Services.class);
        Call<User> call=services.ReservationConfirmation(client_id,name.getText().toString(),phone.getText().toString(),picturePath,Reservation_id);
        call.enqueue(new  Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()){

          Toast.makeText(ConfirmResrvation.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    textim.setText("");


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void get_intent(){

        Intent intent=getIntent();
        client_id=intent.getStringExtra("client_id");
        client_name=intent.getStringExtra("client_name");
        client_phone=intent.getStringExtra("client_phone");
        Reservation_id=intent.getStringExtra("Reservation_id");
        name.setText(client_name);
        phone.setText(client_phone);

    }
}
