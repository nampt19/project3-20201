package com.example.project3_2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project3_2.R;
import com.example.project3_2.activity.ProductActivity;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter implements Filterable {
    Activity context;
    List<Product> objects;
    List<Product> itemsModelListFiltered;

    public ProductAdapter(List<Product> itemsModelsl, Activity context) {
        this.objects = itemsModelsl;
        this.itemsModelListFiltered = itemsModelsl;
        this.context = context;
    }
    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_product, null);
        TextView txtNameProduct = row.findViewById(R.id.txtNameProduct);
        TextView txtPriceProduct = row.findViewById(R.id.txtPriceProduct);
        try {
            txtNameProduct.setText(this.itemsModelListFiltered.get(position).getName());
            txtPriceProduct.setText(formatCurrency(this.itemsModelListFiltered.get(position).getPrice()));
        } catch (Exception ex) {
            // Log.v("Error", ex.getMessage());
            ex.printStackTrace();
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("main activity", "item clicked");
                Intent intentReply = new Intent();
                intentReply.putExtra("id_product", itemsModelListFiltered.get(position).getId());
                intentReply.putExtra("name_product", itemsModelListFiltered.get(position).getName());
                intentReply.putExtra("price_product", itemsModelListFiltered.get(position).getPrice());
                context.setResult(34, intentReply);
                context.finish();
            }
        });
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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = objects.size();
                    filterResults.values = objects;
                } else {
                    List<Product> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (Product itemsModel : objects) {
                        if (itemsModel.getName().toLowerCase().contains(searchStr) || (itemsModel.getPrice() + "").toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemsModelListFiltered = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }



}
