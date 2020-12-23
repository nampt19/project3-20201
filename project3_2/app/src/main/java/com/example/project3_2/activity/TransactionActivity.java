package com.example.project3_2.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.adapter.TransactionAdapter;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.ProductController;
import com.example.project3_2.controller.TransactionController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.TransactionModel;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionRes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    ListView lvTransaction;
    ArrayAdapter<TransactionModel> transactionAdapter;
    List<TransactionModel> transactionList;

    Spinner spFilterTransaction;
    List<String> statusTransactionList;
    ArrayAdapter<String> statusTransactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        setBottomBav();
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        lvTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //truyền dữ liệu qua màn kia !
                Intent intent = new Intent(TransactionActivity.this, TransactionDetailActivity.class);
                intent.putExtra("idTransaction", transactionList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        String strCache = new CachingFile().readCache(TransactionActivity.this);
        if (new CommonController().checkReadPage(strCache, "transaction", "read") == false) {
            Toast.makeText(this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        lvTransaction = findViewById(R.id.lvTransaction);
        spFilterTransaction = findViewById(R.id.spFilterTransaction);

        statusTransactionList = new ArrayList<String>();
        statusTransactionList.add("Đã thanh toán");
        statusTransactionList.add("Đang nợ");
        statusTransactionList.add("Đã hủy");
        statusTransactionAdapter = new ArrayAdapter<String>(TransactionActivity.this, android.R.layout.simple_list_item_1, statusTransactionList);
        spFilterTransaction.setAdapter(statusTransactionAdapter);
        transactionList = new ArrayList<TransactionModel>();
        new TransactionListTask().execute();
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Giao dịch");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_attach_money_24);

        toolbar.inflateMenu(R.menu.add);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.nav_add) {
                    String strCache = new CachingFile().readCache(TransactionActivity.this);
                    if (new CommonController().checkReadPage(strCache, "transaction", "create") == false) {
                        Toast.makeText(TransactionActivity.this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(getApplicationContext(), TransactionAddActivity.class));
                    }
                }

                return false;
            }
        });
    }

    private void setBottomBav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_transaction);
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

    class TransactionListTask extends AsyncTask<Void, Void, List<TransactionRes>> {

        @Override
        protected List<TransactionRes> doInBackground(Void... voids) {
            List<TransactionRes> res = new ArrayList<>();
            try {
                String s = new CachingFile().readCache(TransactionActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                res = new TransactionController().getAll(loginResponse.getToken());
                return res;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<TransactionRes> transactionRes) {
            super.onPostExecute(transactionRes);
            try {
                for (TransactionRes res : transactionRes)
                    transactionList.add(new TransactionModel(res.getId(), res.getCustomerName(), res.getTime(), res.getPrice(), res.getStatus()));

                transactionAdapter = new TransactionAdapter(TransactionActivity.this, R.layout.item_transaction, transactionList);
                lvTransaction.setAdapter(transactionAdapter);
                if (transactionRes.size() == 0)
                    Toast.makeText(TransactionActivity.this, "Lỗi mạng !", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(TransactionActivity.this, "Lỗi mạng !", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        transactionList.clear();
        new TransactionListTask().execute();
    }
}