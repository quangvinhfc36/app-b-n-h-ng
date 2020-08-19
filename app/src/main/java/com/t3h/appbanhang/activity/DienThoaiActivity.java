package com.t3h.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.adapter.DienThoaiAdapter;
import com.t3h.appbanhang.model.SanPham;
import com.t3h.appbanhang.utils.CheckConnection;
import com.t3h.appbanhang.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DienThoaiAdapter adapter;
    private ListView lvDienthoai;
    private ProgressBar progressBar;
    private View footterView;
    ArrayList<SanPham> sanPhams;
    int iddt = 0;
    int page = 1;
    boolean isLoading = false;
    private mhandler mhandler;
    boolean limitData = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        initView();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getData(page);
            getIdLoaiSanPham();
            actionToolbar();
            loadMoreData();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối internet");
            finish();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.t3h.appbanhang.activity.GioHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoreData() {
        lvDienthoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("Thông tin sản phẩm",sanPhams.get(i));
                startActivity(intent);
            }
        });
        lvDienthoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirtView, int Visible, int TotalItem) {
                if(FirtView+Visible == TotalItem && TotalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    threadData threadData = new threadData();
                    threadData.start();
                }
            }
        });
    }


    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongdanDienThoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tendt = "";
                int giadt = 0;
                String hinhanhdt;
                String mota = "";
                int IdSanphamDienthoai = 0;
                if (response != null && response.length() != 2) {
                    lvDienthoai.removeFooterView(footterView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            IdSanphamDienthoai = jsonObject.getInt("idsanpham");
                            sanPhams.add(new SanPham(id, tendt, giadt, hinhanhdt, mota, IdSanphamDienthoai));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitData = true;
                    lvDienthoai.removeFooterView(footterView);
                    CheckConnection.showToast(getApplicationContext(),"Đã hết dữ liệu");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idsanpham", String.valueOf(iddt));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIdLoaiSanPham() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("gia tri loai san pham", iddt + "");
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarDienthoai);
        lvDienthoai = findViewById(R.id.listViewDienThoai);
        sanPhams = new ArrayList<>();
        adapter = new DienThoaiAdapter(getApplicationContext(), sanPhams);
        lvDienthoai.setAdapter(adapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footterView = inflater.inflate(R.layout.progress_bar,null);
        mhandler = new mhandler();

    }
    public class mhandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvDienthoai.addFooterView(footterView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class threadData extends Thread{
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
        }
    }
}
