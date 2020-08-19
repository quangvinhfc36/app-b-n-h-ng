package com.t3h.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.model.LoaiSanPham;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter {
    ArrayList<LoaiSanPham> arrayListLoaisp;
    Context context;

    public LoaiSanPhamAdapter(ArrayList<LoaiSanPham> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView tvTenloaisp;
        ImageView imLoaisp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview,null);
            viewHolder.tvTenloaisp = view.findViewById(R.id.tv_loaisanpham);
            viewHolder.imLoaisp = view.findViewById(R.id.imLoaisanpham);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(i);
        viewHolder.tvTenloaisp.setText(loaiSanPham.getTenLoaisp());
        Picasso.with(context).load(loaiSanPham.getHinhloaisp()).placeholder(R.drawable.ic_camera_alt_black_24dp).error(R.drawable.ic_error)
                .into(viewHolder.imLoaisp);
        return view;
    }
}
