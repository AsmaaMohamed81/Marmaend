package com.Alatheer.marmy.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Alatheer.marmy.Model.AllDelegateModel;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.Model.MSG;
import com.Alatheer.marmy.Fragments.FragmentListOrders;
import com.Alatheer.marmy.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Detail extends AppCompatActivity {

    final ArrayList seletedItems = new ArrayList();
    Spinner spinner;
    TextView name, address, capicty, cost,dateall, map,total,back;
    ImageView img, imgs;
    TextView book;
    ArrayList<AllDelegateModel> delegate;
    Double latitude, longitude;
    String Sname, Scost, Scapacity, SImg, Saddress, IDuser, IDground, formattedDate, date;
    ArrayList<String> delegate_ids = new ArrayList<>();
    ArrayList<String> unique;
    String date_time, ClientID, Date, GroundID;
    private ImageView calender;
    private ProgressDialog pDialog;
    String msupplier,hours;
    Date c;
    String client_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);

        getdelegetes();
        getintent();
        intiatview();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Detail.this,Home.class);
                startActivity(i);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendintent();

            }


        });

        address.setText(Saddress);
        name.setText(Sname);
        capicty.setText(Scapacity);
        cost.setText(Scost);
        total.setText(Scost);
        Picasso.with(Detail.this).load(SImg).into(img);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });
        c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(client_id==null){

                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(Detail.this);
                    LayoutInflater factory = LayoutInflater.from(Detail.this);
                    final View viewu = factory.inflate(R.layout.sample2, null);
                    alertadd.setView(viewu);
                    alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {

                            Intent i = new Intent(Detail.this,Loogin.class);
                            startActivity(i);

                        }
                    });

                    AlertDialog dialog = alertadd.create();

                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

                    dialog.show();

                }else if(date==null){


                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(Detail.this);
                    LayoutInflater factory = LayoutInflater.from(Detail.this);
                    final View viewu = factory.inflate(R.layout.sample3, null);
                    alertadd.setView(viewu);
                    alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                            DateDialog();


                        }
                    });
                    AlertDialog dialog = alertadd.create();

                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

                    dialog.show();

                }

                else{
                    getdelegetes();

                //    Toast.makeText(Detail.this,date+ "", Toast.LENGTH_SHORT).show();
                    final String[] items = new String[delegate.size()];
                    final String[] ids = new String[delegate.size()];

                for (int i = 0; i < delegate.size(); i++) {
                    items[i] = delegate.get(i).getUser_name();
                    ids[i] = delegate.get(i).getId();

                }
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String formattedDate = df.format(c);
                    Date=date;

                if (Date.equals(formattedDate)){
                    sendtoadmin();
                }else {
                    AlertDialog dialog = new AlertDialog.Builder(Detail.this)
                            .setTitle("اختار مندوب")
                            .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {


                                    for (int i = 0; i < delegate.size(); i++) {


                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            seletedItems.add(indexSelected);

                                            delegate_ids.add(ids[indexSelected]);
                                            //     Toast.makeText(Detail.this, ids[indexSelected]+"", Toast.LENGTH_SHORT).show();


                                        } else if (seletedItems.contains(indexSelected)) {
                                            // Else, if the item is already in the array, remove it
                                            seletedItems.remove(Integer.valueOf(indexSelected));
                                            delegate_ids.remove(ids[Integer.valueOf(indexSelected)]);
                                        }
                                    }

                                }
                            }).setPositiveButton("OK",


                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    unique = removeDuplicates(delegate_ids);
                                    savetodelegates();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Your code when user clicked on Cancel
                                }
                            }).create();
                    dialog.show();
                }



            }
        }});



        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gllary = new Intent(Detail.this, gallary.class);
                gllary.putExtra("IDgroundgllry", IDground);
                startActivity(gllary);

            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.sipnneritem);
        adapter.setDropDownViewResource(R.layout.sppinnersheck);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                msupplier = spinner.getSelectedItem().toString();

                if (msupplier.equals("6-8")){
                    hours="1";

                }else if(msupplier.equals("8-10")){
                    hours="2";
                }else {
                    hours="3";
                }
               // Toast.makeText(Detail.this, msuppl
                // ier + "", Toast.LENGTH_SHORT).show();
            ///    Toast.makeText(Detail.this, Scost + "", Toast.LENGTH_SHORT).show();


              //  Log.e("Selected item : ", msupplier);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getdelegetes() {
     //   Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Services service = APIClient.getClient().create(Services.class);

        Call<List<AllDelegateModel>> call = service.getDelegate();

        call.enqueue(new Callback<List<AllDelegateModel>>() {
            @Override
            public void onResponse(Call<List<AllDelegateModel>> call, Response<List<AllDelegateModel>> response) {
                //Toast.makeText(Detail.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                delegate.addAll(response.body());

            }
            @Override
            public void onFailure(Call<List<AllDelegateModel>> call, Throwable t) {

                Toast.makeText(Detail.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getintent() {
        Intent i = getIntent();
        Sname = i.getStringExtra("namegraund");
        Scost = i.getStringExtra("cost");
        Scapacity = i.getStringExtra("capacity");
        SImg = i.getStringExtra("Img");
        Saddress = i.getStringExtra("address");
        latitude = i.getDoubleExtra("latitude", 1.1);
        longitude = i.getDoubleExtra("longitude", 1.1);
        IDground = i.getStringExtra("IdGround");
        client_id=i.getStringExtra("IdUser");
    }

    private void intiatview() {
        calender = findViewById(R.id.calender);
        dateall = findViewById(R.id.datetext);
        total = findViewById(R.id.total);
        map = findViewById(R.id.map);
        address = findViewById(R.id.adress);
        capicty = findViewById(R.id.capicity);
        cost = findViewById(R.id.cost);
        name = findViewById(R.id.namegraund);
        img = findViewById(R.id.Img_graund);
        book = findViewById(R.id.book);
        delegate = new ArrayList<>();
        imgs = findViewById(R.id.imgs);
        spinner = findViewById(R.id.spinner);
        back=findViewById(R.id.back);
    }

    public void DateDialog() {

        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Detail.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("ResourceAsColor")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date = dateFormatter.format(newDate.getTime());

              Date dateSpecified = newDate.getTime();

                if (dateSpecified.before(c)) {
                    final AlertDialog.Builder alertadd = new AlertDialog.Builder(Detail.this);
                    LayoutInflater factory = LayoutInflater.from(Detail.this);
                    final View viewu = factory.inflate(R.layout.sample, null);
                    alertadd.setView(viewu);

                    alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                            DateDialog();


                        }
                    });
                    AlertDialog dialog = alertadd.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

                    dialog.show();

                    dateall.setText("Choose date");

                } else {
                    dateall.setText(date);

                }

            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void sendtoadmin(){
        pDialog = new ProgressDialog(Detail.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Register...");
        pDialog.setCancelable(false);

        showpDialog();

        IDuser = client_id;
        ClientID = IDuser;
        Date = date;
        GroundID = IDground;
        Services service = APIClient.getClient().create(Services.class);
        Call<MSG> userCall = service.BookGroundAdmin(hours, ClientID, Date, GroundID);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

            ///    Toast.makeText(Detail.this, "mm"+hours + Date, Toast.LENGTH_SHORT).show();


                if (response.body().getSuccess() == 1) {
                    // startActivity(new Intent(Detail.this, Home.class));

                      //  Toast.makeText(Detail.this, "success", Toast.LENGTH_SHORT).show();
                        finish();

                    //Toast.makeText(Detail.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
    private void savetodelegates() {
        pDialog = new ProgressDialog(Detail.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Register...");
        pDialog.setCancelable(false);

        showpDialog();

        IDuser = client_id;
        ClientID = IDuser;
        Date = date;
        GroundID = IDground;

            Services service = APIClient.getClient().create(Services.class);
            Call<MSG> userCall = service.BookGround(hours, ClientID, Date, GroundID, unique);
            // startActivity(new Intent(Register.this, Home.class));

            userCall.enqueue(new Callback<MSG>() {
                @Override
                public void onResponse(Call<MSG> call, Response<MSG> response) {
                    hidepDialog();

                 //   Toast.makeText(Detail.this, hours + Date, Toast.LENGTH_SHORT).show();

                    if (response.body().getSuccess() == 1) {
                        // startActivity(new Intent(Detail.this, Home.class));

                        if (response.body().getTime()==1){

                            final AlertDialog.Builder alertadd = new AlertDialog.Builder(Detail.this);
                            LayoutInflater factory = LayoutInflater.from(Detail.this);
                            final View viewu = factory.inflate(R.layout.timebuilder, null);
                            alertadd.setView(viewu);
                            alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {

                                        getSupportFragmentManager().beginTransaction()
                                                .add(android.R.id.content, new FragmentListOrders()).commit();



                                }
                            });
                            AlertDialog dialog = alertadd.create();

                            dialog.getWindow().getAttributes().windowAnimations = R.style.CustomAnimations_slide; //style id

                            dialog.show();
                        }else {
                          //  Toast.makeText(Detail.this, "success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                       // Toast.makeText(Detail.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MSG> call, Throwable t) {
                    hidepDialog();
                    Log.d("onFailure", t.toString());
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

    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        ArrayList<String> result = new ArrayList<>();
        HashSet<String> set = new HashSet<>();

        for (String item : list) {

            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
    private void sendintent() {
        Intent i = new Intent(Detail.this, MapsActivity.class);
        i.putExtra("latitude", latitude);
        i.putExtra("longitude", longitude);
        i.putExtra("namegraund", Sname);
        i.putExtra("address", Saddress);

        startActivity(i);
    }
}