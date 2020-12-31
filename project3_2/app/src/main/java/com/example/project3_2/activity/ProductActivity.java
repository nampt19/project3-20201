package com.example.project3_2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project3_2.R;
import com.example.project3_2.adapter.CustomerAdapter;
import com.example.project3_2.adapter.ProductAdapter;
import com.example.project3_2.controller.CustomerController;
import com.example.project3_2.controller.ProductController;
import com.example.project3_2.helper.CachingFile;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.Product;
import com.example.project3_2.model.UserModel;
import com.example.project3_2.model.response.LoginResponse.LoginResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    List<Product> productModels;
    ProductAdapter productAdapter;
    ListView lvProduct;
    EditText searchProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setHeader();
        addControl();
        addEvent();
    }

    private void addEvent() {
//        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intentReply = new Intent();
//                intentReply.putExtra("id_product", productModels.get(position).getId());
//                intentReply.putExtra("name_product", productModels.get(position).getName());
//                intentReply.putExtra("price_product", productModels.get(position).getPrice());
//                setResult(34, intentReply);
//                finish();
//            }
//        });

        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productAdapter.getFilter().filter(s);
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void addControl() {
        searchProduct = findViewById(R.id.searchProduct);

        lvProduct = findViewById(R.id.lvProduct);
        new ProductListTask().execute();

    }


    class ProductListTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            List<Product> productList = new ArrayList<>();
            try {
                String s = new CachingFile().readCache(ProductActivity.this);
                LoginResponse loginResponse = new Gson().fromJson(s, LoginResponse.class);
                productList = new ProductController().getAll(loginResponse.getToken());

                return productList;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return productList;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            try {
                productModels = products;
                productAdapter = new ProductAdapter(productModels,ProductActivity.this);
                lvProduct.setAdapter(productAdapter);
                if (products.size() == 0)
                    Toast.makeText(ProductActivity.this, "Lỗi kết nối server, vui lòng thử lại", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar_common);
        toolbar.setTitle("Khóa học");
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
                    //    startActivity(new Intent(getApplicationContext(), FormUser.class));
                }
                return false;
            }
        });
    }


}