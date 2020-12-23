package com.example.project3_2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.LoginController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.Login;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        addEvent();
    }


    private void addControl() {
        txtUsername = findViewById(R.id.txtUserNameLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang tải ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handPostLogin();
            }
        });
    }

    private void handPostLogin() {
        LoginTask loginTask = new LoginTask();
        loginTask.execute(new Login(txtUsername.getText().toString(),
                txtPassword.getText().toString()));
    }

    class LoginTask extends AsyncTask<Login, Void, String> {

        @Override
        protected String doInBackground(Login... logins) {
            return  new LoginController().checkLogin(logins[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
            try {
                if (loginResponse.getCode().equals("100")) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    new CachingFile().createCache(LoginActivity.this, s);
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản - mật khẩu !", Toast.LENGTH_LONG).show();
                    Log.v("sdasd",loginResponse.getCode());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(LoginActivity.this, "Lỗi kết nốt máy chủ - vui lòng thử lại !", Toast.LENGTH_LONG).show();
            }
        }
    }


}
