package com.example.projet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class HistoriqueAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<EvenementData> recordList;

    public HistoriqueAdapter(Context context, int layout, ArrayList<EvenementData> evenementdata) {
        this.context = context;
        this.layout = layout;
        this.recordList = evenementdata;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView TextEv,TextDes,TextDate;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.TextEv=row.findViewById(R.id.TxtEv);
            holder.TextDes=row.findViewById(R.id.TxtDes);
            holder.TextDate=row.findViewById(R.id.TxtDaate);
            holder.imageView=row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        EvenementData evenementdata = recordList.get(i);

        holder.TextEv.setText(evenementdata.getEvenement());
        holder.TextDes.setText(evenementdata.getDescription());
        holder.TextDate.setText(evenementdata.getDate_());
        byte[] recordImage =evenementdata.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;

    }
}