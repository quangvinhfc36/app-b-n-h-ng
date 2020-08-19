package com.t3h.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.adapter.LoaiSanPhamAdapter;
import com.t3h.appbanhang.adapter.SanPhamAdapter;
import com.t3h.appbanhang.model.GioHang;
import com.t3h.appbanhang.model.LoaiSanPham;
import com.t3h.appbanhang.model.SanPham;
import com.t3h.appbanhang.utils.CheckConnection;
import com.t3h.appbanhang.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ListView listView;
    private ArrayList<LoaiSanPham> mangloaisp;
    private ArrayList<SanPham> mangSanpham;
    private SanPhamAdapter sanPhamAdapter;
    private LoaiSanPhamAdapter loaiSanPhamAdapter;
    public static ArrayList<GioHang> arrayListGioHang;
    int id = 0;
    String tenloaiSP = "";
    String hinhanhLoaiSP = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            getDulieuLoaiSp();
            getDuLieuSanPham();
            CatchOnItemListView();
        }else {
            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối internet");
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

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getDuLieuSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanSanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int Id= 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String hinhanhsp = "";
                    String motasp = "";
                    int Idsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("motasp");
                            Idsanpham = jsonObject.getInt("idsp");
                            mangSanpham.add(new SanPham(Id,tensp,giasp,hinhanhsp,motasp,Idsanpham));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDulieuLoaiSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.Duongdanloaisanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaiSP = jsonObject.getString("tenloaisanpham");
                            hinhanhLoaiSP = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new LoaiSanPham(id,tenloaiSP,hinhanhLoaiSP));
                            loaiSanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mangloaisp.add(3,new LoaiSanPham(0,
                            "Liên hệ",
                            "https://lh3.googleusercontent.com/proxy/Jz72CCoeVoRD8zIXPn0q3XEUjuarX4XajG8naXOGXGCenci7UT7NVNi0vV3gmwWEIPKRpg6yzr_TVlyTjFQoS8QO9cJVtmBtl5xTTw"));
                    mangloaisp.add(4,new LoaiSanPham(0,"Thông tin",
                            "https://png.pngtree.com/png-vector/20190916/ourlarge/pngtree-info-icon-for-your-project-png-image_1731084.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    private void actionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://alosoft.vn/wp-content/uploads/2018/12/d2-kinh-doanh-dien-thoai-va-phu-kien-2.jpg");
        mangquangcao.add("https://cellphones.com.vn/sforum/wp-content/uploads/2019/11/He-lo-thong-so-camera-chi-tiet-cua-xiaomi-mi-note-10-1.jpg");
        mangquangcao.add("https://static.dientutieudung.vn/resources/9b94287f2d18e5393ab7e194ee268ddc/2014/T03/14/Asus/in1.jpg");
        mangquangcao.add("https://gamek.mediacdn.vn/2017/rog-faker-edition-696x344-1512466278633.jpg");
        for (int i = 0; i <mangquangcao.size() ; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.flippermanhinhchinh);
        recyclerView =findViewById(R.id.lvSanpham);
        listView = findViewById(R.id.listViewmanhinhchinh);
        navigationView = findViewById(R.id.nav_manhinhchinh);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new LoaiSanPham(0,"Trang chính","https://sites.google.com/site/khoivinhphankhoivinhseo/_/rsrc/1489649407204/noi-bat/cac-mau-tick-icon-dep/home.png"));

        loaiSanPhamAdapter = new LoaiSanPhamAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaiSanPhamAdapter);
        mangSanpham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),mangSanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
        if(arrayListGioHang != null){

        }else {
            arrayListGioHang = new ArrayList<>();
        }
    }
}
