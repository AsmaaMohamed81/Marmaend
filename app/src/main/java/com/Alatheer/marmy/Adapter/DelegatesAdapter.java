package com.Alatheer.marmy.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Alatheer.marmy.Model.DelegateOrder;
import com.Alatheer.marmy.Model.ResponseModel;
import com.Alatheer.marmy.API.Service.APIClient;
import com.Alatheer.marmy.API.Service.Services;
import com.Alatheer.marmy.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DelegatesAdapter extends RecyclerView.Adapter<DelegatesAdapter.Holder> {
    Context context;
    DelegateOrder mmodel;
    List<DelegateOrder> Array;

    String cost;
    public DelegatesAdapter(Context context, List<DelegateOrder> Array) {
        this.context = context;
        this.Array = Array;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delegate, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);

        holder.lingrand.setTag(position);

        holder.username.setText(mmodel.getUserName());
        holder.groundname.setText(mmodel.getPlaygroundName());
        holder.time.setText(mmodel.getTime());
        holder.date.setText(mmodel.getDateOrder());


    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username, groundname, time, date;
        LinearLayout lingrand;

        public Holder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.client_name);
            groundname = itemView.findViewById(R.id.ground_name);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            lingrand = itemView.findViewById(R.id.linear);
            lingrand.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();

            mmodel = Array.get(position);


            add_cost();
           // Toast.makeText(context, mmodel.getOrderId() + cost, Toast.LENGTH_SHORT).show();


        }


        private void add_cost() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("ادخل السعر");

// Set up the input
            final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cost = input.getText().toString();
                    Services service = APIClient.getClient().create(Services.class);


                    Call<ResponseModel> userCall = service.response(mmodel.getOrderId(),mmodel.getClientIdFk(), cost);
                    // startActivity(new Intent(Register.this, ListMarma.class));

                    userCall.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {


                            if (response.isSuccessful()) {

                                Toast.makeText(context, ""+mmodel.getOrderId()+"  "+mmodel.getClientIdFk(), Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            } else {
                             Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
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

    }
}
