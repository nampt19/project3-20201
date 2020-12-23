package com.example.project3_2.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.TransactionController;
import com.example.project3_2.fragment.SingleChoiceDialogFragment;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Locale;

public class TransactionAddActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {

    ImageButton btnChoiceCustomerTA;
    LinearLayout cardViewCustomer;
    TextView txtNameUserTA, txtPhoneUserTA, txtEmailUserTA;

    ImageView imgChoiceProductTA;
    CardView cardViewProduct;
    TextView txtProductNameTA, txtProductPriceTA, txtStatusTA;

    ImageButton btnChoiceStatusTA;

    int REQUEST_CODE_PRODUCT = 90;
    int REQUEST_CODE_CUSTOMER = 91;

    int idCustomer = 0;
    int idUser = 0;
    int idProduct = 0;

    EditText edtNoteTA;
    Button btnAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnChoiceStatusTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });
        btnChoiceCustomerTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lúc trả về thì ẩn cái btnChoiceCustomerTA nhé !
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }
        });
        cardViewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lúc trả về thì ẩn cái btnChoiceCustomerTA nhé !
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }
        });
        imgChoiceProductTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lúc trả về thì ẩn cái imgChoiceProductTA + hiện layout sản phẩm nhé
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PRODUCT);

            }
        });
        cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lúc trả về thì ẩn cái imgChoiceProductTA + hiện layout sản phẩm nhé
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PRODUCT);

            }
        });
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTransactionTask().execute();
            }
        });

    }

    private void addControl() {
        txtStatusTA = findViewById(R.id.tvStatusTA);
        btnChoiceStatusTA = findViewById(R.id.btnChoiceStatusTA);

        imgChoiceProductTA = findViewById(R.id.imgChoiceProductTA);
        cardViewProduct = findViewById(R.id.cardViewProduct);
        txtProductNameTA = findViewById(R.id.txtProductNameTA);
        txtProductPriceTA = findViewById(R.id.txtProductPriceTA);

        btnChoiceCustomerTA = findViewById(R.id.btnChoiceCustomerTA);
        cardViewCustomer = findViewById(R.id.cardViewCustomer);
        txtNameUserTA = findViewById(R.id.txtNameUserTA);
        txtPhoneUserTA = findViewById(R.id.txtPhoneUserTA);
        txtEmailUserTA = findViewById(R.id.txtEmailUserTA);
        edtNoteTA = findViewById(R.id.edtNoteTA);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Thêm hóa đơn");
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
        txtStatusTA.setText(list[position]);
        checkColorStatus(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    private void checkColorStatus(String colorStatus) {
        if (colorStatus.equals("Đã thanh toán")) {
            txtStatusTA.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        if (colorStatus.equals("Đang nợ")) {
            txtStatusTA.setTextColor(getResources().getColor(R.color.colorBlue));
        }
        if (colorStatus.equals("Đã hủy")) {
            txtStatusTA.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PRODUCT && resultCode == 34) {
            idProduct = data.getIntExtra("id_product", 0);
            Log.v("id_product", data.getIntExtra("id_product", 0) + "");
            Log.v("name_product", data.getStringExtra("name_product"));
            Log.v("price_product", data.getIntExtra("price_product", 0) + "");
            txtProductNameTA.setText(data.getStringExtra("name_product").toString());
            txtProductPriceTA.setText(formatCurrency(data.getIntExtra("price_product", 0)));

            imgChoiceProductTA.setVisibility(View.GONE);
            cardViewProduct.setVisibility(View.VISIBLE);
        }
        if (requestCode == REQUEST_CODE_CUSTOMER && resultCode == 35) {
            idCustomer = data.getIntExtra("id_customer", 0);
            Log.v("id_customer", data.getIntExtra("id_customer", 0) + "");
            Log.v("name_customer", data.getStringExtra("name_customer"));
            txtNameUserTA.setText(data.getStringExtra("name_customer"));
            txtPhoneUserTA.setText(data.getStringExtra("phone_customer"));
            txtEmailUserTA.setText(data.getStringExtra("email_customer"));

            btnChoiceCustomerTA.setVisibility(View.GONE);
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

    class CreateTransactionTask extends AsyncTask<Void, Void, BaseResponse> {
        @Override
        protected BaseResponse doInBackground(Void... voids) {
            BaseResponse baseResponse = new BaseResponse();
            try {
                String s = new CachingFile().readCache(TransactionAddActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                Log.v("idCustomewwr", idCustomer + "");
                Log.v("idUserwwwr", loginResponse.getIdUser() + "");
                Log.v("idProductwwwr", idProduct + "");

                if (idCustomer != 0 && idProduct != 0) {
                    baseResponse = new TransactionController().createTransaction(loginResponse.getToken(),
                            idCustomer, loginResponse.getIdUser(), idProduct, txtStatusTA.getText().toString(),
                            edtNoteTA.getText().toString());
                }
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
                    Toast.makeText(TransactionAddActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TransactionAddActivity.this, "Không có quyền", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(TransactionAddActivity.this, "Mời nhập đủ thông tin", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    }


}