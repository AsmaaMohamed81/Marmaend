package com.Alatheer.marmy.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Alatheer.marmy.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

    }
}
