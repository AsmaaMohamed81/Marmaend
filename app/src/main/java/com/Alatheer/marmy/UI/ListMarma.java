package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Adapter.graundAdapter;
import com.Alatheer.marmy.NetworkConnection;
import com.Alatheer.marmy.Preferense;
import com.Alatheer.marmy.R;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMarma extends AppCompatActivity {
    ArrayList<com.Alatheer.marmy.Model.Model> Model;
    graundAdapter adapter;
    RecyclerView recyclerView;
    String id;
    TextView logout, notfy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_marma);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        Intent i=getIntent();
      id=  i.getStringExtra("userName");



        recyclerView = findViewById(R.id.recyc_ground);

        Model=new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(ListMarma.this,3));
        recyclerView.setHasFixedSize(true);

        adapter=new graundAdapter(ListMarma.this,Model);
        recyclerView.setAdapter(adapter);


        Services service = APIClient.getClient().create(Services.class);
        Call<List<com.Alatheer.marmy.Model.Model>> call = service.getgroundData();

        call.enqueue(new Callback<List<com.Alatheer.marmy.Model.Model>>() {
            @Override
            public void onResponse(Call<List<com.Alatheer.marmy.Model.Model>> call, Response<List<com.Alatheer.marmy.Model.Model>>response) {

                Model.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<com.Alatheer.marmy.Model.Model>> call, Throwable t) {

             //   Toast.makeText(ListMarma.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }



    @Override
    public void onBackPressed() {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
        super.onBackPressed();

    }

    private void checkOfflineData() {
        SharedPreferences pref = getSharedPreferences("id", MODE_PRIVATE);
        String id = pref.getString("user_id", "");
        if (pref == null) {
            SignOut();
        } else {
            if (TextUtils.isEmpty(id) || id == null) {
                SignOut();
            } else {
                Intent i = getIntent();
                id = i.getStringExtra("userName");
            }
        }

    }

    private void SignOut() {
        NetworkConnection connection = new NetworkConnection(this);
        if (connection.getConnection() == true) {

            Preferense pref = new Preferense(this);
            pref.clear();
            SharedPreferences preferences = getSharedPreferences("id", MODE_PRIVATE);
            startActivity(new Intent(this, Loogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            Log.e("pref", preferences.getString("user_id", "loggedout"));
        } else {
           // Toast.makeText(this, "check network connection.", Toast.LENGTH_SHORT).show();
        }

    }
}

