package com.Alatheer.marmy.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.Alatheer.marmy.Model.User;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Model.MSG;
import com.Alatheer.marmy.Preferense;
import com.Alatheer.marmy.R;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loogin extends AppCompatActivity {
    public boolean isFirstStart;

    static String[] items;
     String id;
    EditText username,password;
    Button login;
    TextView signup;
    ArrayList<User> users;
    private ProgressDialog pDialog;

    public static String convertPassMd5(String pass) {

        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loogin);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(Loogin.this, MyIntro.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        String id=sharedPreferences.getString("user_id","");
        String del=sharedPreferences.getString("isdelegate","");

        if (!TextUtils.isEmpty(id)&& !TextUtils.isEmpty(del)){

            if (del.equals("1")){
                Preferense pref = new Preferense(Loogin.this);
                pref.CreateSharedPref(id,"1");
                Intent intent = new Intent(Loogin.this, Home.class);
                intent.putExtra("user_id", id);
                intent.putExtra("isdelegate","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
            }else {
            Preferense pref = new Preferense(Loogin.this);
            pref.CreateSharedPref(id,"0");
            Intent intent = new Intent(Loogin.this, Home.class);
            intent.putExtra("user_id", id);
            intent.putExtra("isdelegate","0");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();}
        }
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        users = new ArrayList<>();

        username=findViewById(R.id.edt_user_name);
        password=findViewById(R.id.edt_user_pass);
        login=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.txtsignup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Loogin.this,Register.class);
                startActivity(i);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginByServer();

            }
        });

    }

    private void loginByServer() {
        pDialog = new ProgressDialog(Loogin.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Loogin...");
        pDialog.setCancelable(false);

        showpDialog();
        final String user = username.getText().toString();
        String pass = password.getText().toString();

        Services service = APIClient.getClient().create(Services.class);

        Call<MSG> userCall = service.userLogIn(user,pass);


        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

                if (response.isSuccessful()) {


                    items = new String[users.size()];
                    id = response.body().getId();

                    if (response.body().getIs_delegate() == 1) {
                      //  Toast.makeText(Loogin.this, ""+FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
//                        Log.e("mm",FirebaseInstanceId.getInstance().getToken());
                        Preferense pref = new Preferense(Loogin.this);
                        pref.CreateSharedPref(id,"1");
                        Intent intent = new Intent(Loogin.this, Home.class);
                        intent.putExtra("user_id", id);
                        intent.putExtra("isdelegate","1");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    //    Toast.makeText(Loogin.this, "" + response.body().getId(), Toast.LENGTH_SHORT).show();

                    }else {
                       // Toast.makeText(Loogin.this, ""+FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
                        Preferense pref = new Preferense(Loogin.this);
                        pref.CreateSharedPref(id,"0");
                        Intent intent = new Intent(Loogin.this, Home.class);
                        intent.putExtra("user_id", id);
                        intent.putExtra("isdelegate","0");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                       // Toast.makeText(Loogin.this, "" + response.body().getId(), Toast.LENGTH_SHORT).show();

                }
                    // finish();
                } else {
                Toast.makeText(Loogin.this, "failed" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
               // Log.d("onFailure", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
    }

    public void onLoginSuccess() {
        login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
     //   Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);

        super.onBackPressed();
    }

}
