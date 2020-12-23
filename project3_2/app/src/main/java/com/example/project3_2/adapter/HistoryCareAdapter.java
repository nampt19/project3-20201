package com.example.project3_2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.project3_2.R;
import com.example.project3_2.model.HistoryCareModel;

import java.util.List;

public class HistoryCareAdapter extends ArrayAdapter<HistoryCareModel> {
     Activity context;
    int resource;
  List<HistoryCareModel> objects;
    public HistoryCareAdapter(@NonNull Activity context, int resource, @NonNull List<HistoryCareModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtUserCare = row.findViewById(R.id.txtUserCare);
        TextView txtTimeCare = row.findViewById(R.id.txtTimeCare);
        TextView txtNoteCare = row.findViewById(R.id.txtNoteCare);
        txtUserCare.setText(this.objects.get(position).getUserName());
        txtTimeCare.setText(this.objects.get(position).getTime());
        txtNoteCare.setText(this.objects.get(position).getNote());
        addControl(row);
        return row;

    }
    private void addControl(View row) {
        final ConstraintLayout expandableView = row.findViewById(R.id.expandableView2);
        final CardView cardView = row.findViewById(R.id.cardView4);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility() == View.GONE) {
                    expandableView.setVisibility(View.VISIBLE);
                } else {
                    expandableView.setVisibility(View.GONE);
                }
            }
        });

    }
}
