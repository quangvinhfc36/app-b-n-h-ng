package com.t3h.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.t3h.appbanhang.R;
import com.t3h.appbanhang.adapter.GioHangAdapter;
import com.t3h.appbanhang.utils.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHang extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {
    private Toolbar toolbarGiohang;
    private ListView lvGiohang;
    private TextView tvThongbao;
    public static TextView tvTongTien;
    private Button btnTiepTuc,btnThanhToan;
    private GioHangAdapter gioHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        actionBar();
        checkData();
        eventUtils();
    }

    public static void eventUtils() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.arrayListGioHang.size(); i++) {
            tongtien += MainActivity.arrayListGioHang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText("Giá: " +decimalFormat.format(tongtien)+ "Đ");
    }

    private void checkData() {
        if(MainActivity.arrayListGioHang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);

        }else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void actionBar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarGiohang = findViewById(R.id.toolbarGioHang);
        lvGiohang = findViewById(R.id.listViewGiohang);
        tvThongbao = findViewById(R.id.tvThongbao);
        tvTongTien = findViewById(R.id.tvTongtien);
        btnThanhToan = findViewById(R.id.btnThanhtoan);
        btnTiepTuc = findViewById(R.id.btnReturn);
        gioHangAdapter = new GioHangAdapter(GioHang.this,MainActivity.arrayListGioHang);
        lvGiohang.setAdapter(gioHangAdapter);
        lvGiohang.setOnItemLongClickListener(this);
        btnTiepTuc.setOnClickListener(this);
        btnThanhToan.setOnClickListener(this);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa sản phẩm?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(MainActivity.arrayListGioHang.size() <= 0){
                    tvThongbao.setVisibility(View.VISIBLE);
                }else {
                    MainActivity.arrayListGioHang.remove(position);
                    gioHangAdapter.notifyDataSetChanged();
                    eventUtils();
                    if(MainActivity.arrayListGioHang.size() <=0){
                        tvThongbao.setVisibility(View.VISIBLE);
                    }else {
                        tvThongbao.setVisibility(View.INVISIBLE);
                        gioHangAdapter.notifyDataSetChanged();
                        eventUtils();
                    }
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gioHangAdapter.notifyDataSetChanged();
                eventUtils();
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnReturn:
                TiepTuc();
                break;
            case R.id.btnThanhtoan:
                ThanhToan();
                break;
        }
    }

    private void ThanhToan() {
        if(MainActivity.arrayListGioHang.size()>0){
            Intent intent = new Intent(getApplicationContext(),ThongTinKhachHang.class);
            startActivity(intent);
        }else {
            CheckConnection.showToast(getApplicationContext(),"Giỏ hàng của bạn đang còn trống");
        }
    }

    private void TiepTuc() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
