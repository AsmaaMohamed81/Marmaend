package com.Alatheer.marmy.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Alatheer.marmy.Fragments.FragmentADD;
import com.Alatheer.marmy.Fragments.FragmentDelegatesOrders;
import com.Alatheer.marmy.Fragments.FragmentListOrders;
import com.Alatheer.marmy.Fragments.FragmentListPlayground;
import com.Alatheer.marmy.Fragments.FragmentProfile;
import com.Alatheer.marmy.R;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import eu.long1.spacetablayout.SpaceTabLayout;


public class Home extends AppCompatActivity {
    SpaceTabLayout tabLayout;
  public String id;
   String delegate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i=getIntent();
        id=    i.getStringExtra("user_id");
        delegate=i.getStringExtra("isdelegate");
      // Toast.makeText(this, ""+delegate, Toast.LENGTH_SHORT).show();





        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        //add the fragments you want to display in a List
        List<Fragment> fragmentList;
        if (delegate.equals("1")){
           fragmentList = new ArrayList<>();
            fragmentList.add(new FragmentDelegatesOrders());
            fragmentList.add(new FragmentListPlayground());
            fragmentList.add(new FragmentProfile());
            fragmentList.add(new FragmentADD());
        }else {
            fragmentList = new ArrayList<>();
            fragmentList.add(new FragmentListOrders());
            fragmentList.add(new FragmentListPlayground());
            fragmentList.add(new FragmentProfile());
            fragmentList.add(new FragmentADD());

        }

        final CoordinatorLayout coordinatorLayout =  findViewById(R.id.activity_main);

        ViewPager viewPager =  findViewById(R.id.viewPager);
        tabLayout =  (SpaceTabLayout) findViewById(R.id.spaceTabLayout);


        this.tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList,null);



        this.tabLayout.setTabOneOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to SpaceTabLayout", Snackbar.LENGTH_SHORT);

                snackbar.show();


            }
        });

        this.tabLayout.setTabTwoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to SpaceTabLayout", Snackbar.LENGTH_SHORT);

                snackbar.show();

            }
        });

        this.tabLayout.setTabThreeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to SpaceTabLayout", Snackbar.LENGTH_SHORT);

                snackbar.show();

            }
        });

        this.tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getApplication(), "" + tabLayout.getCurrentPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        super.onBackPressed();
    }
}
