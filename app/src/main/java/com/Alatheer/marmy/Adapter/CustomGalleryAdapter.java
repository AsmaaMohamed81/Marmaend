package com.Alatheer.marmy.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.Alatheer.marmy.R;

import java.util.List;

public class CustomGalleryAdapter extends BaseAdapter {

    private Context context;
    private List<Uri> images;

    public CustomGalleryAdapter(Context c, List<Uri> images) {
        context = c;
        this.images = images;
    }

    // returns the number of images
    public int getCount() {
        return images.size();
    }

    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }

    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        Uri uri = images.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_gallary,parent,false);
        ImageView imageView = view.findViewById(R.id.itemimage);
        imageView.setImageURI(uri);



        return view;
    }
}