package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.Alatheer.marmy.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        logo=(ImageView)findViewById(R.id.imgLogo);

        final Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash);
        logo.startAnimation(animation);

        animation.setDuration(3000);

        Intent i = new Intent(MainActivity.this,Loogin.class);
        startActivity(i);

    }
}
