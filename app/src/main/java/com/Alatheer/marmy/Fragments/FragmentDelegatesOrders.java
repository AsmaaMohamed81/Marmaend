package com.Alatheer.marmy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Alatheer.marmy.Model.DelegateOrder;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Adapter.DelegatesAdapter;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.Home;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentDelegatesOrders extends Fragment {
    ArrayList<DelegateOrder> model;
    DelegatesAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    //String id;
Home home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_list_delegete_orders, container, false);
        home= (Home) getActivity();
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "JannaLT-Regular.ttf", true);




        recyclerView = view.findViewById(R.id.recyc_delegate);
        model = new ArrayList<>();

        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new DelegatesAdapter(getContext(), model);
        recyclerView.setAdapter(adapter);


        Services service = APIClient.getClient().create(Services.class);
        Call<List<DelegateOrder>> call = service.getOrders(home.id);
        call.enqueue(new Callback<List<DelegateOrder>>() {
            @Override
            public void onResponse(Call<List<DelegateOrder>> call, Response<List<DelegateOrder>> response) {
                model.addAll(response.body());
                adapter.notifyDataSetChanged();
            //    Log.e("mmm", response.body().toString());


            }

            @Override
            public void onFailure(Call<List<DelegateOrder>> call, Throwable t) {

              //  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





        return view;
    }


}

