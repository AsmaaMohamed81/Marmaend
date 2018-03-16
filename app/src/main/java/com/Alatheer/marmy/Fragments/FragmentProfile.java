package com.Alatheer.marmy.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Alatheer.marmy.Model.User;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Preferense;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.Home;
import com.Alatheer.marmy.UI.Loogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentProfile extends Fragment {

    TextView name,email,phone;
    Button update,update_pass,logout;

    Home home;
    String n,e,p,pass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
       // Toast.makeText(getActivity(), "" +id, Toast.LENGTH_SHORT).show();

        home= (Home) getActivity();
        name=view.findViewById(R.id.txt_user_name);
        phone=view.findViewById(R.id.txt_user_phone);
        email=view.findViewById(R.id.txt_email);
        update=view.findViewById(R.id.btnupdate);
        update_pass=view.findViewById(R.id.btnpass);

        logout=view.findViewById(R.id.btnlogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferense preferense=new Preferense(getContext());
                preferense.clear();
                Intent i=new Intent(getActivity(), Loogin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(i);
                getActivity().finish();

            }
        });

        Services services=APIClient.getClient().create(Services.class);
         Call<User> call=services.getuserdata(home.id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                name.setText(response.body().getUser_name());
                phone.setText(response.body().getMobile());
                email.setText(response.body().getEmail());

               // Toast.makeText(getContext(), ""+name+phone+email, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              update_profile();
            }


        });

        update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(" تعديل كلمة المرور");

// Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.setHint("ادخل كلمة المرور الجديده");
                builder.setView(input);


// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pass = input.getText().toString();
                        Services service = APIClient.getClient().create(Services.class);


                        Call<User> userCall = service.update_pass(home.id, pass);
                        // startActivity(new Intent(Register.this, ListMarma.class));

                        userCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {


                                if (response.isSuccessful()) {

                                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.d("onFailure", t.toString());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    return view;
    }

    public void update_profile(){





        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("تعديل الصفحه الشخصيه");


        Context context = builder.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText cname = new EditText(getContext());
        final EditText cemail = new EditText(getContext());
        final EditText cphone = new EditText(getContext());

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        cname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        cname.setText(name.getText().toString());
        layout.addView(cname);

        cemail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        cemail.setText(email.getText().toString());
        layout.addView(cemail);

        cphone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
        cphone.setText(phone.getText().toString());
        layout.addView(cphone);
        builder.setView(layout);
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   cost = input.getText().toString();
                name.setText(cname.getText().toString());
                email.setText(cemail.getText().toString());
                phone.setText(cphone.getText().toString());
                 n=cname.getText().toString();
                 e=cemail.getText().toString();
                 p=cphone.getText().toString();

                Services service=APIClient.getClient().create(Services.class);
                Call<User> call1=service.update(home.id,n,e,p);

                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), ""+n+e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
