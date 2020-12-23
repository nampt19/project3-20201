package com.example.project3_2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project3_2.R;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.UserModel;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<CustomerModel> {
    Activity context;
    int resource;
    @NonNull
    List<CustomerModel> objects;
    public CustomerAdapter(@NonNull Activity context, int resource, @NonNull List<CustomerModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtNameCustomer = row.findViewById(R.id.txtNameCustomer);
        TextView txtPhoneCustomer = row.findViewById(R.id.txtPhoneCustomer);
        TextView txtEmailCustomer = row.findViewById(R.id.txtEmailCustomer);
        txtNameCustomer.setText(this.objects.get(position).getName());
        txtPhoneCustomer.setText(this.objects.get(position).getPhone());
        txtEmailCustomer.setText(this.objects.get(position).getEmail());
        return row;
    }
}
