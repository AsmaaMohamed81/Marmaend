package com.Alatheer.marmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Alatheer.marmy.Model.gallry;
import com.Alatheer.marmy.UI.gallrydetails;
import com.Alatheer.marmy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleiman19 on 10/22/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static Context context;
    gallry gallryyyy;
    List<gallry> data = new ArrayList<>();

    public GalleryAdapter(Context context, List<gallry> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item, parent, false);
            viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Glide.with(context).load(data.get(position).getImg())
                    .thumbnail(0.5f)
                    .override(200,200)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((MyItemHolder) holder).mImg);

        ((MyItemHolder) holder).mImg.setTag(position);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImg;


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
            mImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();
            gallryyyy = data.get(position);

            Intent i = new Intent(context,gallrydetails.class);

           // Toast.makeText(context, gallryyyy.getImg()+"", Toast.LENGTH_SHORT).show();
            i.putExtra("img",gallryyyy.getImg());
            context.startActivity(i);





        }
    }


}
