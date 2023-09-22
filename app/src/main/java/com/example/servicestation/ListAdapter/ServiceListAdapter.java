package com.example.servicestation.ListAdapter;// ServiceListAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.servicestation.R;
import com.example.servicestation.Models.Service;

import java.util.List;

public class ServiceListAdapter extends BaseAdapter {
    private Context context;
    private List<Service> serviceList;

    public ServiceListAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false);
        }

        Service service = serviceList.get(position);

        TextView serviceNameTextView = convertView.findViewById(R.id.serviceNameTextView);
        TextView serviceDescriptionTextView = convertView.findViewById(R.id.serviceDescriptionTextView);
        TextView servicePriceTextView = convertView.findViewById(R.id.servicePriceTextView);

        serviceNameTextView.setText(service.getName());
        serviceDescriptionTextView.setText(service.getDescription());
        servicePriceTextView.setText("Price: $" + service.getPrice());

        return convertView;
    }
}
