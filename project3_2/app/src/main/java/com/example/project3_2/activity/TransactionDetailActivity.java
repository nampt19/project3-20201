package com.example.project3_2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.TransactionController;
import com.example.project3_2.controller.UserController;
import com.example.project3_2.fragment.SingleChoiceDialogFragment;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.TransactionResponse.TransactionResponse;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Locale;

public class TransactionDetailActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {

    TextView txtIdTransactionDetail, txtTimeTransactionDetail, txtCustomerNameTransactionDetail,
            txtCustomerPhoneTransactionDetail, txtUserNameTransactionDetail, txtStatusTransactionDetail;

    TextView txtProductNameTransactionDetail, txtProductPriceTransactionDetail;

    TextView edtNoteTransactionDetail;

    Button btnUpdateTransaction, btnDeleteTransaction;

    int idTransaction = 0;

    ImageButton btnChoiceStatusTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnChoiceStatusTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });
        new GetTransactionByIdTask().execute();
        btnUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCache = new CachingFile().readCache(TransactionDetailActivity.this);
                if (new CommonController().checkReadPage(strCache, "transaction", "update") == false) {
                    Toast.makeText(TransactionDetailActivity.this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
                } else {
                    new UpdateTransactionTask().execute();
                }
            }
        });
    }

    private void addControl() {
        txtStatusTransactionDetail = findViewById(R.id.tvStatusTransactionDetail);
        btnChoiceStatusTransaction = findViewById(R.id.btnChoiceStatusTransaction);
        checkColorStatus(txtStatusTransactionDetail.getText().toString());

        Intent intent = getIntent();
        idTransaction = intent.getIntExtra("idTransaction", 0);
        Log.v("idTransacion", idTransaction + "");

        txtIdTransactionDetail = findViewById(R.id.txtIdTransactionDetail);
        txtTimeTransactionDetail = findViewById(R.id.txtTimeTransactionDetail);
        txtCustomerNameTransactionDetail = findViewById(R.id.txtCustomerNameTransactionDetail);
        txtCustomerPhoneTransactionDetail = findViewById(R.id.txtCustomerPhoneTransactionDetail);
        txtUserNameTransactionDetail = findViewById(R.id.txtUserNameTransactionDetail);
        txtStatusTransactionDetail = findViewById(R.id.tvStatusTransactionDetail);
        txtProductNameTransactionDetail = findViewById(R.id.txtProductNameTransactionDetail);
        txtProductPriceTransactionDetail = findViewById(R.id.txtProductPriceTransactionDetail);
        edtNoteTransactionDetail = findViewById(R.id.edtNoteTransactionDetail);
        btnUpdateTransaction = findViewById(R.id.btnUpdateTransaction);
        btnDeleteTransaction = findViewById(R.id.btnDeleteTransaction);

    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Chi tiết hóa đơn");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        txtStatusTransactionDetail.setText(list[position]);
        checkColorStatus(list[position]);

    }

    @Override
    public void onNegativeButtonClicked() {
        // tvStatusTransaction.setText(list[position]);
    }


    private void checkColorStatus(String colorStatus) {
        if (colorStatus.equals("Đã thanh toán")) {
            txtStatusTransactionDetail.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        if (colorStatus.equals("Đang nợ")) {
            txtStatusTransactionDetail.setTextColor(getResources().getColor(R.color.colorBlue));
        }
        if (colorStatus.equals("Đã hủy")) {
            txtStatusTransactionDetail.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    class GetTransactionByIdTask extends AsyncTask<Void, Void, TransactionResponse> {

        @Override
        protected TransactionResponse doInBackground(Void... voids) {
            TransactionResponse response = new TransactionResponse();
            try {
                String s = new CachingFile().readCache(TransactionDetailActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                response = new TransactionController().getTransactionById(loginResponse.getToken(), idTransaction);
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(TransactionResponse transactionResponse) {
            super.onPostExecute(transactionResponse);

            txtIdTransactionDetail.setText("DH." + transactionResponse.getId());
            txtTimeTransactionDetail.setText(transactionResponse.getTime().toString() + "");
            txtCustomerNameTransactionDetail.setText(transactionResponse.getCustomerName());
            txtCustomerPhoneTransactionDetail.setText(transactionResponse.getCustomerPhone());
            txtUserNameTransactionDetail.setText(transactionResponse.getUserName());
            txtStatusTransactionDetail.setText(transactionResponse.getStatus());
            txtProductNameTransactionDetail.setText(transactionResponse.getProduct().getName());
            txtProductPriceTransactionDetail.setText(formatCurrency(transactionResponse.getProduct().getPrice()));
            edtNoteTransactionDetail.setText(transactionResponse.getNote());
            checkColorStatus(txtStatusTransactionDetail.getText().toString());

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

    class UpdateTransactionTask extends AsyncTask<Void, Void, BaseResponse> {
        @Override
        protected BaseResponse doInBackground(Void... voids) {
            BaseResponse response = new BaseResponse();
            try {
                String s = new CachingFile().readCache(TransactionDetailActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                response = new TransactionController().updateTransaction(loginResponse.getToken(),
                        idTransaction,
                        txtStatusTransactionDetail.getText().toString(),
                        edtNoteTransactionDetail.getText().toString());
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);
            if (baseResponse.getCode().equals("100")) {
                Toast.makeText(TransactionDetailActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TransactionDetailActivity.this, "Không có quyền", Toast.LENGTH_LONG).show();
            }
        }
    }


}

