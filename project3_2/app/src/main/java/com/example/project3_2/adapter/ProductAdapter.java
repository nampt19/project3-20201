package com.example.project3_2.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project3_2.R;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;
    @NonNull
    List<Product> objects;

    public ProductAdapter(@NonNull Activity context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtNameProduct = row.findViewById(R.id.txtNameProduct);
        TextView txtPriceProduct = row.findViewById(R.id.txtPriceProduct);
        txtNameProduct.setText(this.objects.get(position).getName());
        txtPriceProduct.setText(formatCurrency(this.objects.get(position).getPrice()));
        return row;
    }

    private String formatCurrency(int number) {
        // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
        // đơn vị tiền tệ của Việt Nam là đồng
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(number);
        return str1;
    }
}
