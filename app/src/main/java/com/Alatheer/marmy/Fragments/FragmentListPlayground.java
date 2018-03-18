package com.Alatheer.marmy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Alatheer.marmy.Model.Model;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Adapter.graundAdapter;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.Home;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListPlayground extends Fragment {
    ArrayList<Model> Model;
    graundAdapter adapter;
    RecyclerView recyclerView;
    private SearchView search_view;
    private TextView ts;
    //String id;
Home home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_list_playground, container, false);

        search_view = view.findViewById(R.id.search_view);
        ts = view.findViewById(R.id.ts);

        search_view.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts.setVisibility(View.GONE);

            }
        });
        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_view.setIconified(false);
                ts.setVisibility(View.GONE);
            }
        });

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_view.setIconified(false);
                ts.setVisibility(View.GONE);

            }
        });
        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ts.setVisibility(View.VISIBLE);

                return false;
            }
        });

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search_playGround(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                {
                    DisplayAllStadium();
                }
                return false;
            }
        });
        home= (Home) getActivity();

       // Toast.makeText(home, ""+home.id, Toast.LENGTH_SHORT).show();

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "JannaLT-Regular.ttf", true);



        recyclerView = view.findViewById(R.id.recyc_ground);

        Model=new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setHasFixedSize(true);

        adapter=new graundAdapter(getContext(),Model);
        recyclerView.setAdapter(adapter);
       /* if (getArguments() != null) {
            id = getArguments().getString("userName");
            Toast.makeText(getActivity(), "" +id, Toast.LENGTH_SHORT).show();
            Log.e("sssss",id);

        }*/
       DisplayAllStadium();
        return view;
    }

    private void DisplayAllStadium()
    {
        Services service = APIClient.getClient().create(Services.class);
        Call<List<Model>> call = service.getgroundData();

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {

                Model.clear();

                Model.addAll(response.body());
                adapter.notifyDataSetChanged();
                //  Toast.makeText(getContext(), ""+home.id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

                //   Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void Search_playGround(String query) {
        Services service = APIClient.getClient().create(Services.class);
        Call<List<Model>> call = service.searchplayground(query);

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {

                Model.clear();

                Model.addAll(response.body());
                adapter.notifyDataSetChanged();
                //  Toast.makeText(getContext(), ""+home.id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

                //   Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    /*private void getOrders() {

        Intent i = new Intent(getContext(), Client_orders.class);
        i.putExtra("userId", id);
        startActivity(i);
        Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();
        Log.d("mmm", id);
    }
*/


}

