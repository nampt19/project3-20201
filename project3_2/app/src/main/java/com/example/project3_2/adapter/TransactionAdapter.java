package com.example.project3_2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project3_2.R;
import com.example.project3_2.model.TransactionModel;
import com.example.project3_2.model.UserModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends ArrayAdapter<TransactionModel> {
    TextView txtIdTransaction, txtNameCustomerTransaction,
            txtTransactionTime, txtPriceCourseTransaction, txtStatusTransaction;

    Activity context;
    int resource;
    @NonNull
    List<TransactionModel> objects;

    public TransactionAdapter(@NonNull Activity context, int resource, @NonNull List<TransactionModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        txtIdTransaction = row.findViewById(R.id.txtIdTransaction);
        txtNameCustomerTransaction = row.findViewById(R.id.txtNameCustomerTransaction);
        txtTransactionTime = row.findViewById(R.id.txtTransactionTime);
        txtPriceCourseTransaction = row.findViewById(R.id.txtPriceCourseTransaction);
        txtStatusTransaction = row.findViewById(R.id.txtStatusTransaction);

        txtIdTransaction.setText("Mã: DH."+this.objects.get(position).getId());

        txtNameCustomerTransaction.setText("KH: "+this.objects.get(position).getCustomerName());
        txtTransactionTime.setText(this.objects.get(position).getTransactionTime());
        txtPriceCourseTransaction.setText(formatCurrency(this.objects.get(position).getPriceCourse()));
        txtStatusTransaction.setText(this.objects.get(position).getStatus());
        checkColorStatus(this.objects.get(position).getStatus());
        return row;
    }
    private void checkColorStatus(String colorStatus) {
        if (colorStatus.equals("Đã thanh toán")) {
            txtStatusTransaction.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
        if (colorStatus.equals("Đang nợ")) {
            txtStatusTransaction.setTextColor(context.getResources().getColor(R.color.colorBlue));
        }
        if (colorStatus.equals("Đã hủy")) {
            txtStatusTransaction.setTextColor(context.getResources().getColor(R.color.colorRed));
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
}
