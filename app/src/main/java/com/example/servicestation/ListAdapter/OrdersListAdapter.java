package com.example.servicestation.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.servicestation.Models.Order;
import com.example.servicestation.R;

import java.util.List;

public class OrdersListAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;

    public OrdersListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_order, parent, false);
        }

        Order order = orderList.get(position);

        TextView orderNumberTextView = convertView.findViewById(R.id.orderNubmer);
        orderNumberTextView.setText("Order number: " + order.getOrderId());

        return convertView;
    }
}
