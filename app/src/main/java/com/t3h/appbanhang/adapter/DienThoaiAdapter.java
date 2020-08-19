package com.t3h.appbanhang.adapter;

import android.content.Context;
import android.text.Layout;
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

public class DienThoaiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arrayListDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayListDienThoai) {
        this.context = context;
        this.arrayListDienThoai = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        private TextView tvTenDt, tvGiaDT, tvMotaDT;
        private ImageView imDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_dienthoai, null);
            viewHolder.tvTenDt = view.findViewById(R.id.tvTenDT);
            viewHolder.tvGiaDT = view.findViewById(R.id.tvGiaDT);
            viewHolder.tvMotaDT = view.findViewById(R.id.tvMotaDt);
            viewHolder.imDienThoai = view.findViewById(R.id.imDienthoai);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.tvTenDt.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaDT.setText("Giá: " + decimalFormat.format(sanPham.getGiasp()) + " Đ");
        viewHolder.tvMotaDT.setMaxLines(2);
        viewHolder.tvMotaDT.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaDT.setText(sanPham.getMotasp());
        Picasso.with(context).load(sanPham.getHinhanhsp())
                .placeholder(R.drawable.ic_camera_alt_black_24dp)
                .error(R.drawable.ic_error)
                .into(viewHolder.imDienThoai);
        return view;
    }
}
