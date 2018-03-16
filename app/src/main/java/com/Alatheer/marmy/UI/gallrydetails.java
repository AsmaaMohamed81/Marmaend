package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.Alatheer.marmy.R;
import com.squareup.picasso.Picasso;

public class gallrydetails extends AppCompatActivity {
String Img;
ImageView imgdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallrydetails);

        imgdetail=(ImageView)findViewById(R.id.detailImg);

        Intent i = getIntent();
        Img = i.getStringExtra("img");

       // Toast.makeText(this, Img+"", Toast.LENGTH_SHORT).show();
        Picasso.with(gallrydetails.this).load(Img)
                .into(imgdetail);
    }
}
