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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.LoginController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MoreInforActivity extends AppCompatActivity {
    LinearLayout lineUser, lineCustomer, lineProduct, lineLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_infor);
        setBottomBav();
        setHeader();
        addControl();
        addEvent();

    }

    private void addEvent() {
        lineUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkReadPage("user","read");
            }
        });
        lineCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkReadPage("customer","read");
             //   startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
            }
        });
        lineProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkReadPage("product","read");
//                startActivity(new Intent(getApplicationContext(), ProductActivity.class));
            }
        });
        lineLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handLogout();
            }
        });
    }

    private void checkReadPage(String pageName,String roleName) {
        String strCache = new CachingFile().readCache(MoreInforActivity.this);
        if (new CommonController().checkReadPage(strCache, pageName, roleName)) {
            if(pageName.equals("user")){
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }else if(pageName.equals("customer")) {
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
            }else if(pageName.equals("product")){
                startActivity(new Intent(getApplicationContext(), ProductActivity.class));
            }else {
                Toast.makeText(MoreInforActivity.this, "Bạn không có quền truy cập", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MoreInforActivity.this, "Bạn không có quền truy cập", Toast.LENGTH_LONG).show();

        }
    }

    private void handLogout() {
        String s = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
        LogoutTask logouttask = new LogoutTask();
        logouttask.execute(loginResponse.getToken());
        Log.v("token", loginResponse.getToken());
    }

    private void addControl() {
        lineUser = findViewById(R.id.line_user);
        lineProduct = findViewById(R.id.line_course);
        lineCustomer = findViewById(R.id.line_customer);
        lineLogout = findViewById(R.id.line_logout);
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Thông tin");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_more_horiz_24);
    }

    private void setBottomBav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_more);
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
                        startActivity(new Intent(getApplicationContext(), CustomerCareActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.nav_transaction:
                        startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.nav_more:
                        return true;
                }
                return false;
            }
        });

    }

    class LogoutTask extends AsyncTask<String, Void, BaseResponse> {

        @Override
        protected BaseResponse doInBackground(String... strings) {
            return new LoginController().checkLogout(strings[0]);
        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            new CachingFile().loadAllCache(MoreInforActivity.this);
            Toast.makeText(MoreInforActivity.this, "Đã đăng xuất khỏi hệ thống !", Toast.LENGTH_LONG).show();
            finish();


        }


    }


}