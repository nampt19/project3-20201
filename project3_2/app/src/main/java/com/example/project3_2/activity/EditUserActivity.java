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
import com.example.project3_2.controller.CommonController;
import com.example.project3_2.controller.RoleController;
import com.example.project3_2.controller.UserController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.helper.SaveFileToStorage;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.BaseResponse;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.example.project3_2.model.response.RoleResponse.Role;
import com.example.project3_2.model.response.UserResponse.UserRequest;
import com.example.project3_2.model.response.UserResponse.UserResponse;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditUserActivity extends AppCompatActivity {
    Spinner spRole;
    ArrayAdapter<String> roleAdapter;
    List<String> roleNames;
    List<Role> roless = new ArrayList<>();
    int spRoleSelectedPosition;


    ImageView imgUserEdit;
    TextView txtNameUserEdit, txtPhoneUserEdit, txtAddressUserEdit;
    Bitmap bmImgUserEdit = null;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        setHeader();
        addControl();
        addEvent();

    }

    private void addEvent() {
        imgUserEdit.setOnClickListener(new View.OnClickListener() {
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
                Log.v("roleId", roless.get(spRoleSelectedPosition + 1).getId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addControl() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle_container");
        userModel = (UserModel) bundle.getSerializable("userEdit");
        Log.v("fgfgfg", userModel.toString());

        imgUserEdit = findViewById(R.id.imgUserEdit);
        txtNameUserEdit = findViewById(R.id.txtNameUserEdit);
        txtPhoneUserEdit = findViewById(R.id.txtPhoneUserEdit);
        txtAddressUserEdit = findViewById(R.id.txtAddressUserEdit);
        imgUserEdit.setImageBitmap(new SaveFileToStorage().
                loadImageFromStorage(userModel.getUrlImage()));
        txtNameUserEdit.setText(userModel.getName());
        txtPhoneUserEdit.setText(userModel.getPhone());
        txtAddressUserEdit.setText(userModel.getAddress());

        spRole = findViewById(R.id.spRoleEdit);
        RoleTask roleTask = new RoleTask();
        String strCache = new CachingFile().readCache(this);
        LoginResponse loginResponse = new Gson().fromJson(strCache, LoginResponse.class);
        roleTask.execute(loginResponse.getToken());


    }

    public void editUser(View view) {
        String strCache = new CachingFile().readCache(this);
        if (new CommonController().checkReadPage(strCache, "customer", "update") == false) {
            Toast.makeText(this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
        } else {
            UpdateUserTask updateUserTask = new UpdateUserTask();
            UserRequest userRequest = new UserRequest();
            String s = new CachingFile().readCache(this);
            LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
            userRequest.setIdUser(userModel.getId());
            userRequest.setToken(loginResponse.getToken());
            userRequest.setName(txtNameUserEdit.getText().toString());
            userRequest.setPhone(txtPhoneUserEdit.getText().toString());
            userRequest.setAddress(txtAddressUserEdit.getText().toString());
            userRequest.setIdRole(roless.get(spRoleSelectedPosition + 1).getId());
            userRequest.setUrl_image(userModel.getUrlImage());
            updateUserTask.execute(userRequest);
        }
    }

    public void deleteUser(View view) {
        String strCache = new CachingFile().readCache(this);
        if (new CommonController().checkReadPage(strCache, "customer", "delete") == false) {
            Toast.makeText(this, "Bạn không có quyền !", Toast.LENGTH_LONG).show();
        } else {
            DeleteUserTask deleteUserTask = new DeleteUserTask();
            deleteUserTask.execute(userModel.getId());
        }

    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Sửa Nhân Viên");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                        bmImgUserEdit = BitmapFactory.decodeStream(is);
                        imgUserEdit.setImageBitmap(bmImgUserEdit);
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
                roless = roles;
                roleNames = new ArrayList<>();
                for (Role role : roles) {
                    if (!role.getName().equals("admin"))
                        roleNames.add(role.getName());
                }

                String roleName = "";
                int f = 0;
                for (int i = 0; i < roles.size(); i++) {
                    if (roles.get(i).getId() == userModel.getRoleId()) {
                        roleName = roles.get(i).getName();
                    }
                }

                for (int i = 0; i < roleNames.size(); i++) {
                    if (roleNames.get(i).equals(roleName))
                        f = i;
                }

                roleAdapter = new ArrayAdapter<String>(EditUserActivity.this, android.R.layout.simple_list_item_1, roleNames);
                spRole.setAdapter(roleAdapter);
                spRole.setSelection(f);
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(EditUserActivity.this, "Không lấy đc quyền - token sai", Toast.LENGTH_LONG);
                finish();
            }

        }

        @Override
        protected List<Role> doInBackground(String... strings) {
            List<Role> roles = new RoleController().getAllRole(strings[0]);
            return roles;
        }
    }

    class UpdateUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

        @Override
        protected UserResponse doInBackground(UserRequest... userRequests) {
            UserResponse userResponse = new UserController().updateUser(userRequests[0]);
            if (userResponse.getCode().equals("100")) {
                imgUserEdit.setDrawingCacheEnabled(true);
                bmImgUserEdit = imgUserEdit.getDrawingCache();
                new SaveFileToStorage().saveToInternalStorage(bmImgUserEdit,
                        EditUserActivity.this, userResponse.getUrl_image());
            }
            return userResponse;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            super.onPostExecute(userResponse);
            if (userResponse.getCode().equals("100")) {
                Toast.makeText(EditUserActivity.this, "Sửa NV thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditUserActivity.this, "Sửa NV thất bại," +
                        "kiểm tra định dạng sđt/connection !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DeleteUserTask extends AsyncTask<Integer, Void, BaseResponse> {

        @Override
        protected BaseResponse doInBackground(Integer... integers) {
            String s = new CachingFile().readCache(EditUserActivity.this);
            LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
            BaseResponse baseResponse = new UserController().deleteUser(loginResponse.getToken(), integers[0]);
            return baseResponse;

        }

        @Override
        protected void onPostExecute(BaseResponse baseResponse) {
            super.onPostExecute(baseResponse);
            if (baseResponse.getCode().equals("100")) {
                Toast.makeText(EditUserActivity.this, "Xóa User thành công !", Toast.LENGTH_LONG).show();
                finish();
                new SaveFileToStorage().deleteFileFromStorage(userModel.getUrlImage());
            } else {
                Toast.makeText(EditUserActivity.this, "User không tồn tại !", Toast.LENGTH_LONG).show();
            }
        }
    }
}