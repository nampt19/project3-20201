package com.example.project3_2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.adapter.CustomerAdapter;
import com.example.project3_2.controller.CustomerController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.CustomerModel;
import com.example.project3_2.model.Login;
import com.example.project3_2.model.response.CustomerResponse.CustomerListResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    List<CustomerModel> customerModelss;
    ArrayAdapter<CustomerModel> customerAdapter;
    ListView lvCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentReply = new Intent();
                intentReply.putExtra("id_customer",customerModelss.get(position).getId());
                intentReply.putExtra("name_customer",customerModelss.get(position).getName());
                intentReply.putExtra("phone_customer",customerModelss.get(position).getPhone());
                intentReply.putExtra("email_customer",customerModelss.get(position).getEmail());

                setResult(35, intentReply);
                finish();
            }
        });
    }

    private void addControl() {
        lvCustomer = findViewById(R.id.lvCustomer);
        new CustomerListTask().execute();
    }

    class CustomerListTask extends AsyncTask<Void, Void, List<CustomerModel>> {
        @Override
        protected List<CustomerModel> doInBackground(Void... voids) {
            List<CustomerModel> customerModelList = new ArrayList<>();
            try {
                String s = new CachingFile().readCache(CustomerActivity.this);
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
                customerAdapter = new CustomerAdapter(CustomerActivity.this, R.layout.item_customer, customerModels);
                lvCustomer.setAdapter(customerAdapter);
                if (customerModels.size()==0){
                    Toast.makeText(CustomerActivity.this, "Lỗi kết nối server, vui lòng thử lại", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Khách hàng");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.add);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.nav_add) {
//                    startActivity(new Intent(getApplicationContext(), FormUser.class));
                }
                return false;
            }
        });
    }


}