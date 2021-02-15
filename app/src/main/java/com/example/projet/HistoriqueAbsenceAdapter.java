package com.example.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoriqueAbsenceAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<AbsenceData> recordList;

    public HistoriqueAbsenceAdapter(Context context, int layout, ArrayList<AbsenceData> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
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
        TextView TxtDatee,TxtHeuree,Justifabs;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.TxtDatee=row.findViewById(R.id.TxtDatee);
            holder.TxtHeuree=row.findViewById(R.id.TxtHeuree);
            holder.Justifabs=row.findViewById(R.id.TxtJustif);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }
       AbsenceData absencedata = recordList.get(i);

        holder.TxtDatee.setText(absencedata.getDateabs());
        holder.TxtHeuree.setText(absencedata.getHeureabs());
        holder.Justifabs.setText(absencedata.getJustifabs());
        return row;

    }
}
