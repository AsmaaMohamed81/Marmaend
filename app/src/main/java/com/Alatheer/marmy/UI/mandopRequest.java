package com.Alatheer.marmy.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Model.MessageResponse;
import com.Alatheer.marmy.R;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mandopRequest extends Activity implements View.OnClickListener{
ImageView profileimge,cardimge;
Button endm;
Uri img1,img2;

EditText reason;
ProgressDialog dialog;


String id;
    byte[] photo;
    private Bitmap bp1,bp2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandop_request);
        initView();
        getDataFromIntent();
        CreateDialog();

    }

    private void CreateDialog() {
       dialog = new ProgressDialog(this);
       dialog.setIndeterminate(true);
       dialog.setMessage("Wait for sending data...");
       dialog.setCancelable(true);
       dialog.setCanceledOnTouchOutside(false);
    }

    private void getDataFromIntent() {
        Intent intent= getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("id"))
            {
                id=intent.getStringExtra("id");

            }


        }
    }

    private void initView() {
        profileimge=findViewById(R.id.profileimg);
        cardimge=findViewById(R.id.cardimg);
        endm=findViewById(R.id.endam);
        reason=findViewById(R.id.reson);
        profileimge.setOnClickListener(this);
        cardimge.setOnClickListener(this);
        endm.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 2:
                if(resultCode == RESULT_OK){
                    img1 = data.getData();

                    if(img1 !=null){

                        bp1=decodeUri(img1, 200);
                        profileimge.setImageBitmap(bp1);
                    }
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    img2= data.getData();

                    if(img2 !=null){

                        bp2=decodeUri(img2, 200);
                        cardimge.setImageBitmap(bp2);
                    }
                }
                break;
        }
    }
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage),
                    null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected  String encode(Bitmap bitmap)
    {


        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
        byte[] bytes=outputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);


    }
    private void saveToServerDB() {
//        pDialog = new ProgressDialog(Register.this);
//        pDialog.setIndeterminate(true);
//        pDialog.setMessage("Creating Account...");
//        pDialog.setCancelable(false);

      //  showpDialog();




        if (bp1==null||bp2==null){

            final AlertDialog.Builder alertadd = new AlertDialog.Builder(mandopRequest.this);
            LayoutInflater factory = LayoutInflater.from(mandopRequest.this);
            final View viewu = factory.inflate(R.layout.sampl4, null);
            alertadd.setView(viewu);
            alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dlg, int sumthin) {

                }
            });
            AlertDialog dialog = alertadd.create();

            dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

            dialog.show();

        }else if (TextUtils.isEmpty(reason.getText().toString())){

            reason.setError("Enter Reason");
        }

        else {
            dialog.show();

            String Reson = reason.getText().toString();
            String profile = encode(bp1);
            String card = encode(bp2);


        Log.e("dataaaaaaaaaaaaaaaaa",Reson+"\n"+profile+"\n \n \n"+card+"\n"+id);

        Log.e("image2","\n \n \n"+card+"\n");

        Log.e("idddddddddddddddd",id);


        Services service = APIClient.getClient().create(Services.class);


        Call<MessageResponse> userCall = service.mandopRequest(id, profile,card, Reson);
        // startActivity(new Intent(Register.this, Home.class));

        userCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                //hidepDialog();
                //onSignupSuccess();
                //   Log.d("onResponse", "" + response.body().getMessage());


                if (response.isSuccessful()) {
                    Intent i=new Intent(mandopRequest.this, MainActivity.class);
                    dialog.dismiss();
                    startActivity(i);
                    finish();
                } else {
                    dialog.dismiss();
                     Toast.makeText(mandopRequest.this, "response error" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
             //   hidepDialog();
                dialog.dismiss();
                Log.d("onFailure", t.toString());
            }
        });
    }}

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.profileimg:
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
                break;
             case R.id.cardimg:
                 Intent photoPickerIntent2 = new Intent(Intent.ACTION_GET_CONTENT);
                 photoPickerIntent2.setType("image/*");
                 startActivityForResult(photoPickerIntent2, 3);
                 break;
              case R.id.endam:
                  saveToServerDB();
                  break;
        }
    }
}
