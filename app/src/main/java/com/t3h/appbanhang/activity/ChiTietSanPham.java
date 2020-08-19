package com.t3h.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.model.GioHang;
import com.t3h.appbanhang.model.SanPham;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbarChitiet;
    private ImageView imChitiet;
    private TextView tvTensp,tvGiasp,tvMotasp;
    private Spinner spinner;
    private Button btnGiohang;
    int id = 0;
    String tenSp = "";
    int giaSp = 0;
    String hinhanhSp = "";
    String motaSp = "";
    int idSp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        initView();
        actionToolBar();
        getInformation();
        CatchSpinner();
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

    private void CatchSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInformation() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("Thông tin sản phẩm");
        id = sanPham.getId();
        tenSp = sanPham.getTensp();
        giaSp = sanPham.getGiasp();
        hinhanhSp = sanPham.getHinhanhsp();
        motaSp = sanPham.getMotasp();
        idSp = sanPham.getIDsanpham();
        tvTensp.setMaxLines(2);
        tvTensp.setEllipsize(TextUtils.TruncateAt.END);
        tvTensp.setText(tenSp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiasp.setText("Giá: " +decimalFormat.format(giaSp)+ "Đ");
        tvMotasp.setText(motaSp);
        Picasso.with(getApplicationContext()).load(sanPham.getHinhanhsp()).
        placeholder(R.drawable.ic_camera_alt_black_24dp).
        error(R.drawable.ic_error).into(imChitiet);
        
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarChitiet = findViewById(R.id.toolbarChitiet);
        imChitiet = findViewById(R.id.imChitiet);
        tvTensp = findViewById(R.id.tvTenSanPham);
        tvGiasp = findViewById(R.id.tvGiaSanPham);
        tvMotasp = findViewById(R.id.tvMotaChitietSP);
        spinner = findViewById(R.id.spinner);
        btnGiohang = findViewById(R.id.btnGiohang);
        btnGiohang.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(MainActivity.arrayListGioHang.size() > 0){
            int s1 = Integer.parseInt(spinner.getSelectedItem().toString());
            boolean exits = false;
            for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++) {
                if(MainActivity.arrayListGioHang.get(i).getIdsp() == id){
                    MainActivity.arrayListGioHang.get(i).setSoluong(MainActivity.arrayListGioHang.get(i).getSoluong() +s1);
                    if(MainActivity.arrayListGioHang.get(i).getSoluong() >= 10){
                        MainActivity.arrayListGioHang.get(i).setSoluong(10);

                    }
                    MainActivity.arrayListGioHang.get(i).setGiasp(giaSp* MainActivity.arrayListGioHang.get(i).getSoluong());
                    exits = true;
                }
                if(exits == false){
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong*giaSp;
                    MainActivity.arrayListGioHang.add(new GioHang(id,tenSp,giamoi,hinhanhSp,soluong));
                }
                
            }
        }else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long giamoi = soluong*giaSp;
            MainActivity.arrayListGioHang.add(new GioHang(id,tenSp,giamoi,hinhanhSp,soluong));

        }
        Intent intent = new Intent(getApplicationContext(), com.t3h.appbanhang.activity.GioHang.class);
        startActivity(intent);
    }
}
