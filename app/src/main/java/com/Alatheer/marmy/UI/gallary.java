package com.Alatheer.marmy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Alatheer.marmy.Model.gallry;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Adapter.GalleryAdapter;
import com.Alatheer.marmy.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class gallary extends AppCompatActivity {
    String ID;
    String s = "sdss";
    ArrayList<gallry> Model;
    GalleryAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);
        recyclerView = findViewById(R.id.list);


        Intent gall = getIntent();

        ID = gall.getStringExtra("IDgroundgllry");
      //  Toast.makeText(this, ID + "", Toast.LENGTH_SHORT).show();

        Model = new ArrayList<>();


        recyclerView.setLayoutManager(new GridLayoutManager(gallary.this, 3));
        recyclerView.setHasFixedSize(true);

        adapter = new GalleryAdapter(gallary.this, Model);
        recyclerView.setAdapter(adapter);

        Services service = APIClient.getClient().create(Services.class);
        Call<List<gallry>> call = service.gallry(ID);


        call.enqueue(new Callback<List<gallry>>() {
            @Override
            public void onResponse(Call<List<gallry>> call, Response<List<gallry>> response) {
             //   Toast.makeText(gallary.this, "sucsess" + response.body(), Toast.LENGTH_SHORT).show();
               // Log.d(s, String.valueOf(response.body()));
                Model.addAll(response.body());
                //Log.d(s, String.valueOf(Model));
                adapter.notifyDataSetChanged();

    

            }

            @Override
            public void onFailure(Call<List<gallry>> call, Throwable t) {
               // Toast.makeText(gallary.this, "fail" + t, Toast.LENGTH_LONG).show();
               // Log.d(s, String.valueOf(t));


            }
        });


    }
}
