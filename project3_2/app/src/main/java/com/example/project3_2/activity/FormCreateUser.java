package com.example.project3_2.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.controller.RoleController;
import com.example.project3_2.controller.UserController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.helper.SaveFileToStorage;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.RoleResponse.Role;
import com.example.project3_2.model.response.UserResponse.UserRequest;
import com.example.project3_2.model.response.UserResponse.UserResponse;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FormCreateUser extends AppCompatActivity {
    Spinner spRole;
    ArrayAdapter<String> roleAdapter;
    List<String> roleNames;
    List<Role> roles;
    int spRoleSelectedPosition;


    ImageView imgUserCreate;
    TextView txtNameUserCreate, txtPhoneUserCreate, txtEmailUserCreate, txtAddressUserCreate, txtPasswordUserCreate;
    Bitmap bmImgUserCreate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_create_user);

        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
        imgUserCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spRoleSelectedPosition = position;
                Log.v("spRoleSelectedPosition", spRoleSelectedPosition + "");
                Log.v("roleNames", roleNames.get(spRoleSelectedPosition) + "");
                Log.v("roleId", roles.get(spRoleSelectedPosition + 1).getId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //nút add user ở phía dưới !
    }

    private void addControl() {
        spRole = findViewById(R.id.spRole);
        RoleTask roleTask = new RoleTask();
        String strCache = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(strCache, LoginResponse.class);
        roleTask.execute(loginResponse.getToken());


        imgUserCreate = findViewById(R.id.imgUserCreate);
        txtNameUserCreate = findViewById(R.id.txtNameUserCreate);
        txtEmailUserCreate = findViewById(R.id.txtEmailUserCreate);
        txtPhoneUserCreate = findViewById(R.id.txtPhoneUserCreate);
        txtAddressUserCreate = findViewById(R.id.txtAddressUserCreate);
        txtPasswordUserCreate = findViewById(R.id.txtPasswordUserCreate);
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Thêm Nhân Viên");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addUser(View view) {

        CreateUserTask createUserTask = new CreateUserTask();
        UserRequest userRequest = new UserRequest();

        String s = new CachingFile().readCache(FormCreateUser.this);
        LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
        userRequest.setToken(loginResponse.getToken());
        userRequest.setName(txtNameUserCreate.getText().toString());
        userRequest.setPhone(txtPhoneUserCreate.getText().toString());
        userRequest.setPassword(txtPasswordUserCreate.getText().toString());
        userRequest.setEmail(txtEmailUserCreate.getText().toString());
        userRequest.setAddress(txtAddressUserCreate.getText().toString());
        userRequest.setIdRole(roles.get(spRoleSelectedPosition + 1).getId());

        createUserTask.execute(userRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        bmImgUserCreate = BitmapFactory.decodeStream(is);
                        imgUserCreate.setImageBitmap(bmImgUserCreate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class RoleTask extends AsyncTask<String, Void, List<Role>> {
        @Override
        protected void onPostExecute(List<Role> roles) {
            super.onPostExecute(roles);
            try {
                roleNames = new ArrayList<>();
                for (Role role : roles) {
                    if (!role.getName().equals("admin"))
                        roleNames.add(role.getName());
                }
                roleAdapter = new ArrayAdapter<String>(FormCreateUser.this, android.R.layout.simple_list_item_1, roleNames);
                spRole.setAdapter(roleAdapter);
                spRole.setSelection(0);
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(FormCreateUser.this, "Không lấy đc quyền - token sai", Toast.LENGTH_LONG);
                finish();
            }

        }

        @Override
        protected List<Role> doInBackground(String... strings) {
            roles = new RoleController().getAllRole(strings[0]);
            return roles;
        }
    }

    class CreateUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

        @Override
        protected UserResponse doInBackground(UserRequest... userRequests) {
            UserResponse userResponse = new UserController().createUser(userRequests[0]);
            if (userResponse.getCode().equals("100")) {
                imgUserCreate.setDrawingCacheEnabled(true);
                bmImgUserCreate = imgUserCreate.getDrawingCache();
                new SaveFileToStorage().saveToInternalStorage(bmImgUserCreate,
                        FormCreateUser.this, userResponse.getUrl_image());
            }
            return userResponse;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            super.onPostExecute(userResponse);
            if (userResponse.getCode().equals("100")) {
                Toast.makeText(FormCreateUser.this, "Thêm NV thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(FormCreateUser.this, "Thêm NV thất bại," +
                        "kiểm tra email/password !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}