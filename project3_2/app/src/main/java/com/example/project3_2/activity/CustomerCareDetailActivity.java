package com.example.project3_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.adapter.CustomerCareAdapter;
import com.example.project3_2.adapter.HistoryCareAdapter;
import com.example.project3_2.controller.HistoryCareController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.HistoryCareModel;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.HistoryCareResponse.HistoryCareRes;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomerCareDetailActivity extends AppCompatActivity {
    List<HistoryCareModel> historyCareList;
    ArrayAdapter<HistoryCareModel> historyCareAdapter;
    ListView lvHistoryCare;
    int idCustomer;
    String nameCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care_detail);

        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {

    }

    private void addControl() {
        lvHistoryCare = findViewById(R.id.lvHistoryCare);
        historyCareList = new ArrayList<>();
       TextView txtCustomerNameCustomerCareDetail = findViewById(R.id.txtCustomerNameCustomerCareDetail);


        Intent intent = getIntent();
        idCustomer = intent.getIntExtra("idCustomer", 0);
        nameCustomer = intent.getStringExtra("nameCustomerCare");
        Log.v("sdfsadf", idCustomer + "");
        txtCustomerNameCustomerCareDetail.setText(nameCustomer+"");
        new GetHistoryCaresByIdCustomerTask().execute(idCustomer);

    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Lịch sử chăm sóc chi tiết");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RefreshStatusUserTask().execute(idCustomer);
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.add);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.nav_add){

                    Intent intent = new Intent(CustomerCareDetailActivity.this,AddHistoryCareActivity.class);
                    intent.putExtra("idCustomerAddHC",idCustomer);
                    intent.putExtra("nameCustomerAddHC",nameCustomer);
                    startActivity(intent);

                   // startActivity(new Intent(getApplicationContext(), AddHistoryCareActivity.class));

                }
                return false;
            }
        });
    }

    class GetHistoryCaresByIdCustomerTask extends AsyncTask<Integer, Void, List<HistoryCareRes>> {
        @Override
        protected List<HistoryCareRes> doInBackground(Integer... integers) {
            List<HistoryCareRes> res = new ArrayList<>();
            try {
                String s = new CachingFile().readCache(CustomerCareDetailActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                res = new HistoryCareController().getHistoryCaresByIdCustomer(loginResponse.getToken(), integers[0]).getHistoryCareList();
            } catch (Exception ex) {
                ex.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<HistoryCareRes> historyCareRes) {
            super.onPostExecute(historyCareRes);
            try {
                historyCareList.clear();
                for (HistoryCareRes res : historyCareRes) {
                    historyCareList.add(new HistoryCareModel(res.getTime(), res.getNote(), res.getUserName()));
                }
                historyCareAdapter = new HistoryCareAdapter(CustomerCareDetailActivity.this, R.layout.item_customer_care_detail, historyCareList);
                lvHistoryCare.setAdapter(historyCareAdapter);
            }catch (Exception ex){
                Toast.makeText(CustomerCareDetailActivity.this,"Không có phiên nào !",Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }


        }
    }

    class RefreshStatusUserTask extends AsyncTask<Integer, Void, BaseResponse> {
        @Override
        protected BaseResponse doInBackground(Integer... integers) {
            BaseResponse res = new BaseResponse();
            try {
                String s = new CachingFile().readCache(CustomerCareDetailActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                res = new HistoryCareController().refreshStatusUser(loginResponse.getToken(), integers[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);
            if (baseResponse.getCode().equals("100")) {
                Log.v("reFreshStatusUser", "ok !");
            } else {
                Toast.makeText(CustomerCareDetailActivity.this, "Không có token !", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        new GetHistoryCaresByIdCustomerTask().execute(idCustomer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        new RefreshStatusUserTask().execute(idCustomer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new RefreshStatusUserTask().execute(idCustomer);
    }
}