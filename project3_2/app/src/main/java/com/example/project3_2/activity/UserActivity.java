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
import com.example.project3_2.adapter.UserAdapter;
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.UserController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    List<UserModel> userModels;
    ArrayAdapter<UserModel> userAdapter;
    ListView lvUser;
    List<UserModel> userModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   startActivity(new Intent(getApplicationContext(), EditUserActivity.class));
                Intent intent = new Intent(UserActivity.this,EditUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEdit",userModelList.get(position));
                intent.putExtra("bundle_container",bundle);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        lvUser = findViewById(R.id.lvUser);
        ListUserTask listUserTask = new ListUserTask();
        String s = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
        listUserTask.execute(loginResponse.getToken());

    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Nhân Viên");
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
                    String strCache = new CachingFile().readCache(UserActivity.this);
                    if (new CommonController().checkReadPage(strCache, "user", "create")) {
                        startActivity(new Intent(getApplicationContext(), FormCreateUser.class));
                    }else{
                        Toast.makeText(UserActivity.this,"Bạn không có quyền",Toast.LENGTH_LONG);
                        Log.v("createUser","Bạn không có quyền");
                    }
                }
                return false;
            }
        });
    }

    class ListUserTask extends AsyncTask<String,Void,List<UserModel>>{
        @Override
        protected List<UserModel> doInBackground(String... strings) {
             userModelList = new ArrayList<>();
            try {
               userModelList = new UserController().getAllUserExceptAdmin(strings[0]);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return userModelList;
        }

        @Override
        protected void onPostExecute(List<UserModel> userModelList) {
            super.onPostExecute(userModelList);
                try{
                    userAdapter = new UserAdapter(UserActivity.this, R.layout.item_user, userModelList);
                    lvUser.setAdapter(userAdapter);
                    if (userModelList.size()==0){
                        Toast.makeText(UserActivity.this,"Lỗi mạng, vui lòng thử lại",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ListUserTask listUserTask = new ListUserTask();
        String s = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
        listUserTask.execute(loginResponse.getToken());
    }
}