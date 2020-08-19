package com.t3h.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arrayListSamPham;

    public LaptopAdapter(Context context, ArrayList<SanPham> arrayListSamPham) {
        this.context = context;
        this.arrayListSamPham = arrayListSamPham;
    }

    @Override
    public int getCount() {
        return arrayListSamPham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSamPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        private ImageView imLaptop;
        private TextView tvTenLaptop,tvGiaLaptop,tvMotaLaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_laptop,null);
            viewHolder.tvTenLaptop = view.findViewById(R.id.tvTenLaptop);
            viewHolder.tvGiaLaptop = view.findViewById(R.id.tvGiaLaptop);
            viewHolder.tvMotaLaptop = view.findViewById(R.id.tvMotaLaptop);
            viewHolder.imLaptop = view.findViewById(R.id.imlaptop);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.tvTenLaptop.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaLaptop.setText("Giá: " + decimalFormat.format(sanPham.getGiasp()) + " Đ");
        viewHolder.tvMotaLaptop.setMaxLines(2);
        viewHolder.tvMotaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaLaptop.setText(sanPham.getMotasp());
        Picasso.with(context).load(sanPham.getHinhanhsp())
                .placeholder(R.drawable.ic_camera_alt_black_24dp)
                .error(R.drawable.ic_error)
                .into(viewHolder.imLaptop);
        return view;
    }
}
