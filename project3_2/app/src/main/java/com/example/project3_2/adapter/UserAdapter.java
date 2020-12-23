package com.example.project3_2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project3_2.R;
import com.example.project3_2.helper.SaveFileToStorage;
import com.example.project3_2.model.UserModel;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserModel> {

    TextView txtRoleUser;

    Activity context;
    int resource;
    @NonNull
    List<UserModel> objects;

    public UserAdapter(@NonNull Activity context, int resource, @NonNull List<UserModel> objects) {
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
        ImageView imgUser = row.findViewById(R.id.imageViewUserList);
        TextView txtNameUser = row.findViewById(R.id.txtNameUser);
        TextView txtPhoneUser = row.findViewById(R.id.txtPhoneUser);
        TextView txtEmailUser = row.findViewById(R.id.txtEmailUser);
//        txtRoleUser = row.findViewById(R.id.txtRoleUser);
        txtNameUser.setText(this.objects.get(position).getName());
        txtPhoneUser.setText(this.objects.get(position).getPhone());
        txtEmailUser.setText(this.objects.get(position).getEmail());
        imgUser.setImageBitmap(new SaveFileToStorage().
                loadImageFromStorage(this.objects.get(position).getUrlImage()));
        // txtRoleUser.setText(this.objects.get(position).getRoleId()+"");
        // checkColorRoleUser(txtRoleUser.getText().toString());
        return row;
    }

//    private void checkColorRoleUser(String role) {
//        if (role.equals("nhân viên CSKH")) {
//            txtRoleUser.setTextColor(this.context.getResources().getColor(R.color.colorGreen));
//            txtRoleUser.setBackground(this.context.getDrawable(R.drawable.bg_green_border));
//        }
//        if (role.equals("nhân viên chốt đơn")) {
//            txtRoleUser.setTextColor(this.context.getResources().getColor(R.color.colorBlue));
//            txtRoleUser.setBackground(this.context.getDrawable(R.drawable.bg_blue_border));
//        }
//        if (role.equals("quản lý nhân viên")) {
//            txtRoleUser.setTextColor(this.context.getResources().getColor(R.color.colorRed));
//            txtRoleUser.setBackground(this.context.getDrawable(R.drawable.bg_red_border));
//        }
//    }
}
