package com.example.project3_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.adapter.CustomerAdapter;
import com.example.project3_2.adapter.CustomerCareAdapter;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.CustomerController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomerCareActivity extends AppCompatActivity {
    List<CustomerModel> customerModelss;
    ArrayAdapter<CustomerModel> customerCareAdapter;
    ListView lvCustomerCare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);
        setBottomBav();
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {

    }

    private void addControl() {
        String strCache = new CachingFile().readCache(this);
        if (new CommonController().checkReadPage(strCache, "history_care", "read") == false) {
            Toast.makeText(this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        lvCustomerCare = findViewById(R.id.lvCustomerCare);
        new CustomerListTask().execute();

    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Chăm sóc khách hàng");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_care_24);
    }

    private void setBottomBav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_care_customer);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.nav_care_customer:
                        return true;
                    case R.id.nav_transaction:
                        startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.nav_more:
                        startActivity(new Intent(getApplicationContext(), MoreInforActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    class CustomerListTask extends AsyncTask<Void, Void, List<CustomerModel>> {
        @Override
        protected List<CustomerModel> doInBackground(Void... voids) {
            List<CustomerModel> customerModelList = new ArrayList<>();
            try {
                String s = new CachingFile().readCache(CustomerCareActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                customerModelList = new CustomerController().getAll(loginResponse.getToken());
                return customerModelList;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return customerModelList;
        }

        @Override
        protected void onPostExecute(List<CustomerModel> customerModels) {
            super.onPostExecute(customerModels);
            try {
                customerModelss = customerModels;
                customerCareAdapter = new CustomerCareAdapter(CustomerCareActivity.this, R.layout.item_customer_care, customerModels);
                lvCustomerCare.setAdapter(customerCareAdapter);
                if (customerModels.size() == 0) {
                    Toast.makeText(CustomerCareActivity.this,
                            "Lỗi kết nối server, vui lòng thử lại",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new CustomerListTask().execute();
    }
}