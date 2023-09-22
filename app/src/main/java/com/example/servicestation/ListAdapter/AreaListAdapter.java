package com.example.servicestation.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.servicestation.Models.Area;
import com.example.servicestation.R;

import java.util.List;

public class AreaListAdapter extends BaseAdapter {
    private Context context;
    private List<Area> areaList;

    public AreaListAdapter(Context context, List<Area> areaList) {
        this.context = context;
        this.areaList = areaList;
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_area, parent, false);
        }

        Area area = areaList.get(position);

        TextView userNameTextView = convertView.findViewById(R.id.ownerName);
        TextView NameTextView = convertView.findViewById(R.id.Name);
        TextView ModelTextView = convertView.findViewById(R.id.Model);
        TextView YearTextView = convertView.findViewById(R.id.Year);
        TextView NumberTextView = convertView.findViewById(R.id.Number);
        TextView VINTextView = convertView.findViewById(R.id.VIN);
        TextView CapacityTextView = convertView.findViewById(R.id.Capacity);

        userNameTextView.setText("Owner name: " + area.getOwnerName());
        NameTextView.setText("Name car: " + area.getName());
        ModelTextView.setText("Model car: " + area.getModel());
        YearTextView.setText("Year car: " + area.getYear());
        NumberTextView.setText("Number car: " + area.getNumber());
        VINTextView.setText("VIN car: " + area.getVIN());
        CapacityTextView.setText("Capacity car: " + area.getCapacity());

        return convertView;
    }

}
