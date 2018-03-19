package com.Alatheer.marmy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.AddPlayGroundActivity;
import com.Alatheer.marmy.UI.Home;
import com.Alatheer.marmy.UI.mandopRequest;

/**
 * Created by m on 3/15/2018.
 */



public class FragmentADD extends Fragment {

    String id;
    Button addmandop,addmal3ab;

    Home home;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentadd, container, false);
        home = (Home) getActivity();
        id = home.id;

        addmandop=view.findViewById(R.id.addmandop);
        addmal3ab=view.findViewById(R.id.addmal3aab);

        addmandop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),mandopRequest.class);
                i.putExtra("id",id);
                getActivity().startActivity(i);

            }
        });

        addmal3ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),AddPlayGroundActivity.class);
                i.putExtra("id",id);
                getActivity().startActivity(i);

            }
        });

        return view;

    }
}
