package com.example.project3_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.graphics.vector.SolidFill;
import com.example.project3_2.R;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.CustomerController;
import com.example.project3_2.controller.ReportController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.ReportResponse.ReportResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Spinner spFilterTime;
    List<String> timeList;
    ArrayAdapter<String> timeAdapter;

    AnyChartView chartView;
    Pie pie;

    TextView txtNumberCustomer, txtNumberTransaction, txtNumberHistoryCare;
    int numberStatusPaid, numberStatusPaying, numberStatusDestroy;
    TextView txtMoneyStatusPaid, txtMoneyStatusPaying, txtMoneyStatusDestroy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBottomNav();
        setHeader();
        addControl();
        addEvent();

    }

    private void addEvent() {
        spFilterTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (timeList.get(position).equals("Tất cả")) {
                    new AllReportTask().execute();
                } else if (timeList.get(position).equals("Hôm nay")) {
                    new ReportByTimeTask().execute(0);
                } else if (timeList.get(position).equals("Hôm qua")) {
                    new ReportByTimeTask().execute(86400);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addControl() {

        new CommonController().checkToken(this);

        txtNumberCustomer = findViewById(R.id.txtNumberCustomer);
        txtNumberTransaction = findViewById(R.id.txtNumberTransaction);
        txtNumberHistoryCare = findViewById(R.id.txtNumberHistoryCare);
        numberStatusPaid = 0;
        numberStatusPaying = 0;
        numberStatusDestroy = 0;
        txtMoneyStatusPaid = findViewById(R.id.txtMoneyStatusPaid);
        txtMoneyStatusPaying = findViewById(R.id.txtMoneyStatusPaying);
        txtMoneyStatusDestroy = findViewById(R.id.txtMoneyStatusDestroy);


        chartView = findViewById(R.id.chartView);
        pie = AnyChart.pie();
        pie.palette().itemAt(0, new SolidFill("#669900", 1d));
        pie.palette().itemAt(1, new SolidFill("#0099ff", 1d));
        pie.palette().itemAt(2, new SolidFill("#CC0000", 1d));
        pie.title("Biểu đồ thống kê tình trạng đơn hàng");
        chartView.setChart(pie);
        spFilterTime = findViewById(R.id.spFilterTimeDashboard);
        timeList = new ArrayList<String>();
        timeList.add("Tất cả");
        timeList.add("Hôm nay");
        timeList.add("Hôm qua");
        timeList.add("Tuần này");
        timeList.add("Tháng này");
        timeAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spFilterTime.setAdapter(timeAdapter);


    }

    private void setChartView() {
        String status[] = {"Đã thanh toán", "Đang nợ", "Đã hủy"};
        int[] quantity = {numberStatusPaid, numberStatusPaying, numberStatusDestroy};
        // pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < status.length; i++) {
            dataEntries.add(new ValueDataEntry(status[i], quantity[i]));
        }
        //  pie.palette().itemAt(0, new SolidFill("#669900", 1d));
        //  pie.palette().itemAt(1, new SolidFill("#0099ff", 1d));
        //  pie.palette().itemAt(2, new SolidFill("#CC0000", 1d));
        pie.data(dataEntries);
        //   pie.title("Biểu đồ thống kê tình trạng đơn hàng");
        // chartView.setChart(pie);

    }

    class AllReportTask extends AsyncTask<Void, Void, ReportResponse> {
        @Override
        protected ReportResponse doInBackground(Void... voids) {
            try {
                String s = new CachingFile().readCache(MainActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                ReportResponse response = new ReportController().getAll(loginResponse.getToken());
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return new ReportResponse();
        }

        @Override
        protected void onPostExecute(ReportResponse reportResponse) {
            super.onPostExecute(reportResponse);
            try {
                if (reportResponse.getCode().equals("100")) {
                    txtNumberCustomer.setText(reportResponse.getReport().getNumberCustomer() + "");
                    txtNumberTransaction.setText(reportResponse.getReport().getNumberTransaction() + "");
                    txtNumberHistoryCare.setText(reportResponse.getReport().getNumberHistoryCare() + "");
                    numberStatusPaid = reportResponse.getReport().getNumberStatusPaid();
                    numberStatusPaying = reportResponse.getReport().getNumberStatusPaying();
                    numberStatusDestroy = reportResponse.getReport().getNumberStatusDestroy();
                    txtMoneyStatusPaid.setText(formatCurrency(reportResponse.getReport().getMoneyStatusPaid()));
                    txtMoneyStatusPaying.setText(formatCurrency(reportResponse.getReport().getMoneyStatusPaying()));
                    txtMoneyStatusDestroy.setText(formatCurrency(reportResponse.getReport().getMoneyStatusDestroy()));
                } else {
                    Toast.makeText(MainActivity.this, "no token", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.toString();
            }
            setChartView();

        }
    }

    class ReportByTimeTask extends AsyncTask<Integer, Void, ReportResponse> {

        @Override
        protected ReportResponse doInBackground(Integer... integers) {
            try {
                String s = new CachingFile().readCache(MainActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                ReportResponse response = new ReportController().getByTime(loginResponse.getToken(), integers[0]);
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return new ReportResponse();
        }

        @Override
        protected void onPostExecute(ReportResponse reportResponse) {
            super.onPostExecute(reportResponse);
            try {
                if (reportResponse.getCode().equals("100")) {
                    txtNumberCustomer.setText(reportResponse.getReport().getNumberCustomer() + "");
                    txtNumberTransaction.setText(reportResponse.getReport().getNumberTransaction() + "");
                    txtNumberHistoryCare.setText(reportResponse.getReport().getNumberHistoryCare() + "");
                    numberStatusPaid = reportResponse.getReport().getNumberStatusPaid();
                    numberStatusPaying = reportResponse.getReport().getNumberStatusPaying();
                    numberStatusDestroy = reportResponse.getReport().getNumberStatusDestroy();
                    txtMoneyStatusPaid.setText(formatCurrency(reportResponse.getReport().getMoneyStatusPaid()));
                    txtMoneyStatusPaying.setText(formatCurrency(reportResponse.getReport().getMoneyStatusPaying()));
                    txtMoneyStatusDestroy.setText(formatCurrency(reportResponse.getReport().getMoneyStatusDestroy()));
                } else {
                    Toast.makeText(MainActivity.this, "no token", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "lỗi mạng !", Toast.LENGTH_LONG).show();
                ex.toString();
            }
            setChartView();

        }
    }

    private String formatCurrency(int number) {
        // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
        // đơn vị tiền tệ của Việt Nam là đồng
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(number);
        return str1;
    }

    private void setBottomNav() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
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
                        startActivity(new Intent(getApplicationContext(), MoreInforActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Tổng quan");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackground(Drawable.createFromPath("#0099ff"));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_more_horiz_24);
    }

}