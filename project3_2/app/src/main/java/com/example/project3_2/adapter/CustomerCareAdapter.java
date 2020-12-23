package com.example.project3_2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.project3_2.R;
import com.example.project3_2.activity.CustomerCareDetailActivity;
import com.example.project3_2.activity.ProductActivity;
import com.example.project3_2.activity.TransactionDetailActivity;
import com.example.project3_2.controller.HistoryCareController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.util.List;

public class CustomerCareAdapter extends ArrayAdapter<CustomerModel> {

    Activity context;
    int resource;
    @NonNull
    List<CustomerModel> objects;
    int idCustomer=0;
    String  txtNameCustomer="";
    public CustomerCareAdapter(@NonNull Activity context, int resource, @NonNull List<CustomerModel> objects) {
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
       final TextView txtNameCustomerCare = row.findViewById(R.id.txtNameCustomerCare);
        TextView txtPhoneCustomerCare = row.findViewById(R.id.txtPhoneCustomerCare);
        TextView txtEmailCustomerCare = row.findViewById(R.id.txtEmailCustomerCare);
        ImageView infoCustomerCareIcon = row.findViewById(R.id.infoCustomerCareIcon);
        txtNameCustomerCare.setText(this.objects.get(position).getName());
        txtPhoneCustomerCare.setText(this.objects.get(position).getPhone());
        txtEmailCustomerCare.setText(this.objects.get(position).getEmail());
        final String status = this.objects.get(position).getStatus();
        addControl(row, this.objects.get(position).getStatus());

        final int idCutomer =this.objects.get(position).getId();
        infoCustomerCareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNameCustomer =txtNameCustomerCare.getText().toString();
               new UpdateStatusUserTask().execute(idCutomer);
            }
        });
        return row;

    }


    private void addControl(View row, String statusCustomerCare) {
        final ConstraintLayout expandableView = row.findViewById(R.id.expandableView);
        final CardView cardView = row.findViewById(R.id.cardView);
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

        //loader
        LazyLoader lazyLoader = row.findViewById(R.id.lazyLoader);
        if (statusCustomerCare.equals("inaccess")) {
            lazyLoader.setVisibility(View.VISIBLE);
        } else {
            lazyLoader.setVisibility(View.INVISIBLE);
        }
    }


    class UpdateStatusUserTask extends AsyncTask<Integer,Void, BaseResponse>{
        @Override
        protected BaseResponse doInBackground(Integer... integers) {
            BaseResponse res = new BaseResponse();
            try{
                idCustomer= integers[0];
                String s = new CachingFile().readCache(context);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                res = new HistoryCareController().updateStatusUser(loginResponse.getToken(),integers[0]);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);
            if (baseResponse.getCode().equals("100")){
                Intent intent = new Intent(context,CustomerCareDetailActivity.class);
                intent.putExtra("idCustomer",idCustomer);
                intent.putExtra("nameCustomerCare",txtNameCustomer);
                Log.d("TAG", txtNameCustomer);
                context.startActivity(intent);
            }else if(baseResponse.getCode().equals("101")){
                Toast.makeText(context,"Khách hàng đang được chăm sóc,hãy quay lại sau",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(context,"Không có token !",Toast.LENGTH_LONG).show();
            }
        }
    }
}
