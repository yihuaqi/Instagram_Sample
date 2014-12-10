package com.example.yihuaqi.instagram_sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ActionMenuView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by yihuaqi on 2014/12/10.
 */
public class ImageAdapter extends BaseAdapter {
    private ArrayList<ImageURL> imageURLs;
    private LayoutInflater inflater;
    public ImageAdapter(Context context, ArrayList<ImageURL> urls ){

        imageURLs = urls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ImageURL getItem(int position) {
        return imageURLs.get(position);
    }

    @Override
    public int getCount() {
        return imageURLs.size();
    }

    public static class ViewHolder {
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_layout,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageURL imageURL = imageURLs.get(position);
        boolean scaleDown = (position%3!=0);
        new ImageDownloaderTask(holder.imageView, scaleDown).execute(imageURL.getLow_imageurl());


        return convertView;


    }


}
