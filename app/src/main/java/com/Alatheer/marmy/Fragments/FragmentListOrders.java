package com.Alatheer.marmy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Alatheer.marmy.Adapter.DelegatesAdapter;
import com.Alatheer.marmy.Model.ClientOrderModel;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Adapter.ClientOrdersAdapter;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.Home;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentListOrders extends Fragment {
    ArrayList<ClientOrderModel> model;
    ClientOrdersAdapter adapter;
    RecyclerView recyclerView;
    //String id;
    private LinearLayoutManager mLayoutManager;

    Home home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_list_client_orders, container, false);

        home= (Home) getActivity();
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "JannaLT-Regular.ttf", true);
       // Intent intent = getIntent();
      //  id = intent.getStringExtra("userId");
//        if (getArguments() != null) {
//            id = getArguments().getString("userName");        }
//
//        Toast.makeText(getContext(), id + "", Toast.LENGTH_LONG).show();

    /*    mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ClientOrdersAdapter(getContext(), model);
        recyclerView.setAdapter(adapter);
*/

        recyclerView = view.findViewById(R.id.recyc_orders);

        model = new ArrayList<>();

        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ClientOrdersAdapter(getContext(), model);
        recyclerView.setAdapter(adapter);

        Services service = APIClient.getClient().create(Services.class);
        Call<List<ClientOrderModel>> call = service.getresponse(home.id);
        call.enqueue(new Callback<List<ClientOrderModel>>() {
            @Override
            public void onResponse(Call<List<ClientOrderModel>> call, Response<List<ClientOrderModel> >response) {

              //  Toast.makeText(getContext(), home.id + "", Toast.LENGTH_LONG).show();

                model.addAll( response.body());
                adapter.notifyDataSetChanged();
                Log.e("mmm", response.body().toString());


            }

            @Override
            public void onFailure(Call<List<ClientOrderModel>> call, Throwable t) {

               // Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}

