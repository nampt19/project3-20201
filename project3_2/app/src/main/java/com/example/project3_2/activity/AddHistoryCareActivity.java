package com.example.project3_2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.HistoryCareController;
import com.example.project3_2.controller.TransactionController;
import com.example.project3_2.fragment.SingleChoiceDialogFragment;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHistoryCareActivity extends AppCompatActivity {

    TextView timeHistoryCareAdd, nameCustomerHistoryCareAdd, nameUserHistoryCareAdd, noteUserHistoryCareAdd;
    Button btnAddHistoryCare;
    int idCustomer, idUser;
    String nameCustomer, token, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history_care);
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnAddHistoryCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCache = new CachingFile().readCache(AddHistoryCareActivity.this);
                if (new CommonController().checkReadPage(strCache, "history_care", "create") == false) {
                    Toast.makeText(AddHistoryCareActivity.this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
                } else {
                    new CreateTransactionTask().execute();
                }
            }
        });
    }

    private void addControl() {
        timeHistoryCareAdd = findViewById(R.id.timeHistoryCareAdd);
        nameCustomerHistoryCareAdd = findViewById(R.id.nameCustomerHistoryCareAdd);
        // nameUserHistoryCareAdd = findViewById(R.id.nameUserHistoryCareAdd);
        noteUserHistoryCareAdd = findViewById(R.id.noteUserHistoryCareAdd);
        btnAddHistoryCare = findViewById(R.id.btnAddHistoryCare);

        Intent intent = getIntent();
        idCustomer = intent.getIntExtra("idCustomerAddHC", 0);
        nameCustomer = intent.getStringExtra("nameCustomerAddHC");

        String s = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
        idUser = loginResponse.getIdUser();
        token = loginResponse.getToken();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
        DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        time = sdf.format(new Date(System.currentTimeMillis() - 3600000 * 7));
        String stringDate2 = sdf2.format(new Date());
        timeHistoryCareAdd.setText(stringDate2);
        nameCustomerHistoryCareAdd.setText(nameCustomer);

        Log.v("idCustomer", idCustomer + "");
        Log.v("nameCustomer", nameCustomer + "");
        Log.v("idUser", idUser + "");
        Log.v("timeHistoryCareAdd", time);
        Log.v("note", noteUserHistoryCareAdd.getText().toString());
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Thêm phiên chăm sóc");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class CreateTransactionTask extends AsyncTask<Void, Void, BaseResponse> {
        @Override
        protected BaseResponse doInBackground(Void... voids) {
            BaseResponse baseResponse = new BaseResponse();
            try {

                baseResponse = new HistoryCareController().createHistoryCare(
                        token,
                        idCustomer,
                        idUser,
                        time,
                        noteUserHistoryCareAdd.getText().toString());
                return baseResponse;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return baseResponse;

        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);
            try {
                if (baseResponse != null && baseResponse.getCode().equals("100")) {
                    Toast.makeText(AddHistoryCareActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddHistoryCareActivity.this, "Không có quyền", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(AddHistoryCareActivity.this, "Lỗi mạng !", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    }


}