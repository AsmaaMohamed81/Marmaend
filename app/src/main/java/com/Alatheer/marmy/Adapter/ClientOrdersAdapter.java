package com.Alatheer.marmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Alatheer.marmy.Model.ClientOrderModel;
import com.Alatheer.marmy.Model.User;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.ConfirmResrvation;
import com.Alatheer.marmy.UI.Home;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ClientOrdersAdapter extends RecyclerView.Adapter<ClientOrdersAdapter.Holder> {
    Context context;
    ClientOrderModel mmodel;
    List<ClientOrderModel> Array;
    Home home;
    String Reservation_id,name,phone;

    public ClientOrdersAdapter(Context context, List<ClientOrderModel> Array) {
        this.context = context;
        this.Array = Array;
        this.home= (Home) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_order_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);

        holder.lingrand.setTag(position);

        holder.delegatename.setText(mmodel.getDelegates_name());
        holder.groundname.setText(mmodel.getPlayground_name());
        holder.cost.setText(mmodel.getPlayground_cost());


    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groundname, delegatename, cost;
        LinearLayout lingrand;

        public Holder(View itemView) {
            super(itemView);

            delegatename = itemView.findViewById(R.id.delegate_name);
            groundname = itemView.findViewById(R.id.name_ground);
            cost = itemView.findViewById(R.id.cost);
            lingrand = itemView.findViewById(R.id.linearresponse);
            lingrand.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();

            mmodel = Array.get(position);
          //  Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            insure_reservation();

            Services services=APIClient.getClient().create(Services.class);
            Call<User> call=services.getData(home.id);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Intent i = new Intent(context, ConfirmResrvation.class);
                    i.putExtra("client_id",home.id);
                    i.putExtra("client_name",response.body().getUser_name());
                    i.putExtra("client_phone",response.body().getMobile());
                    i.putExtra("Reservation_id",mmodel.getReservation_id_fk());
                    context.startActivity(i);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }

    }

    private void insure_reservation() {



        Services service = APIClient.getClient().create(Services.class);


        Call<ClientOrderModel> userCall = service.SelectDelegates(mmodel.getOrder_id(),mmodel.getDelegates_id_fk(),mmodel.getReservation_id_fk());
        // startActivity(new Intent(Register.this, Home.class));

        userCall.enqueue(new Callback<ClientOrderModel>() {
            @Override
            public void onResponse(Call<ClientOrderModel> call, Response<ClientOrderModel> response) {
                //onSignupSuccess();


                if (response.body().getSuccess() == 1) {

                    Reservation_id= response.body().getReservation_id_fk();
                //    Toast.makeText(context, ""+Reservation_id, Toast.LENGTH_SHORT).show();
                  //  name=response.body().
                 //   Intent i = new Intent(context, ConfirmResrvation.class);

                  //  context.startActivity(i);

                  //  Toast.makeText(context, "تم تاكيد الحجز", Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(context, ""+, Toast.LENGTH_LONG).show();
                } else {
                  //  Toast.makeText(context, "" + response.body().getOrder_id(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClientOrderModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void send_data(){


}

}
