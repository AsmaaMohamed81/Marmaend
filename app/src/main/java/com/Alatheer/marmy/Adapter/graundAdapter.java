package com.Alatheer.marmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Alatheer.marmy.Model.Model;
import com.Alatheer.marmy.R;
import com.Alatheer.marmy.UI.Detail;
import com.Alatheer.marmy.UI.Home;
import com.squareup.picasso.Picasso;

import java.util.List;


public class graundAdapter extends RecyclerView.Adapter<graundAdapter.Holder>{
Context context;
Model mmodel;
    List<Model> Array;

    Home home;


    public graundAdapter(Context context, List<Model> Array) {
        this.context = context;
        this.Array = Array;
        this.home= (Home) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemground,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel=Array.get(position);

        holder.detail.setTag(position);



        holder.detail.setText(mmodel.getPlaygroundName());


        Picasso.with(context).load(mmodel.getImageName()).into(holder.name);


    }

    @Override
    public int getItemCount() {
        return  Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView name;
        TextView detail , nameg;
        LinearLayout lingrand;

        public Holder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.imggraund);
            lingrand =  itemView.findViewById(R.id.linegrand);
             detail=itemView.findViewById( R.id.detailsgraund);



            detail.setOnClickListener(this);




        }

        @Override
        public void onClick(final View view) {

                    int position = (int) view.getTag();

                    mmodel = Array.get(position);
                    Intent i = new Intent(context, Detail.class);

                    i.putExtra("namegraund",mmodel.getPlaygroundName());
                    i.putExtra("cost",mmodel.getPlaygroundCost());
                    i.putExtra("capacity", mmodel.getPlaygroundCapacity());
                    i.putExtra("Img", mmodel.getImageName());
                    i.putExtra("address", mmodel.getPlaygroundAddress());
                    i.putExtra("latitude", mmodel.getPlaygroundGoogleLat());
                    i.putExtra("longitude", mmodel.getPlaygroundGoogleLng());
                    i.putExtra("IdGround", mmodel.getPlaygroundIdPk());
                    i.putExtra("IdUser",home.id);


                    context.startActivity(i);



        }
    }
}
