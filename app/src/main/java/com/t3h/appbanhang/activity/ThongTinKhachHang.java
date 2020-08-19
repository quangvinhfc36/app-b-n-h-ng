package com.t3h.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.utils.CheckConnection;
import com.t3h.appbanhang.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity implements View.OnClickListener {
    private EditText edtten,edtsdt,edtemail;
    private Button btnXacnhan,btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        initView();
    }

    private void initView() {
        edtten = findViewById(R.id.edtTenKH);
        edtsdt = findViewById(R.id.edtSDTKH);
        edtemail = findViewById(R.id.edtEmailKH);
        btnXacnhan = findViewById(R.id.btnXacNhan);
        btnTrove = findViewById(R.id.btnTroVe);
        btnTrove.setOnClickListener(this);
        btnXacnhan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnXacNhan:
                XacNhan();
                break;
            case R.id.btnTroVe:
                finish();
                break;
        }
    }

    private void XacNhan() {
        final String ten = edtten.getText().toString().trim();
        final String sdt = edtsdt.getText().toString().trim();
        final String email = edtemail.getText().toString().trim();
        if(ten.length()>0 && sdt.length() > 0 && email.length() > 0){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanDonHang, new Response.Listener<String>() {
                @Override
                public void onResponse(final String madonhang) {
                    Log.d("mã đơn hàng", madonhang);
                    if(Integer.parseInt(madonhang)>0){
                        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.DuongdanChitiet, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    MainActivity.arrayListGioHang.clear();
                                    CheckConnection.showToast(getApplicationContext(),"Bạn đã thêm giỏ hàng thành công");
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    CheckConnection.showToast(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                }else{
                                    CheckConnection.showToast(getApplicationContext(),"Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                JSONArray jsonArray = new JSONArray();
                                for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("madonhang",madonhang);
                                        jsonObject.put("masanpham",MainActivity.arrayListGioHang.get(i).getIdsp());
                                        jsonObject.put("tensanpham",MainActivity.arrayListGioHang.get(i).getTensp());
                                        jsonObject.put("giasanpham",MainActivity.arrayListGioHang.get(i).getGiasp());
                                        jsonObject.put("soluongsanpham",MainActivity.arrayListGioHang.get(i).getSoluong());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("json",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("tenkhachhang",ten);
                    hashMap.put("sodienthoai",sdt);
                    hashMap.put("email",email);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }

    }
}
