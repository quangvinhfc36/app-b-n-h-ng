package com.t3h.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.activity.MainActivity;
import com.t3h.appbanhang.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> arrayGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        private TextView tvTenGH, tvGiaGH;
        private ImageView imGioHang;
        private Button btnTru, btnValues, btnCong;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_giohang, null);
            viewHolder.tvTenGH = view.findViewById(R.id.tvTenGiohang);
            viewHolder.tvGiaGH = view.findViewById(R.id.tvGiaGiohang);
            viewHolder.imGioHang = view.findViewById(R.id.imGiohang);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);
            viewHolder.btnValues = view.findViewById(R.id.btnValues);
            viewHolder.btnCong = view.findViewById(R.id.btnCong);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.tvTenGH.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaGH.setText("Giá: " + decimalFormat.format(gioHang.getGiasp()) + " Đ");
        Picasso.with(context).load(gioHang.getHinhanhsp())
                .placeholder(R.drawable.ic_camera_alt_black_24dp)
                .error(R.drawable.ic_error)
                .into(viewHolder.imGioHang);
        viewHolder.btnValues.setText(gioHang.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl >= 10) {
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnCong.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) + 1;
                int slht = MainActivity.arrayListGioHang.get(i).getSoluong();
                long giaht = MainActivity.arrayListGioHang.get(i).getGiasp();
                MainActivity.arrayListGioHang.get(i).setSoluong(slMoinhat);
                long giamoinhat = (giaht * slMoinhat) / slht;
                MainActivity.arrayListGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaGH.setText("Giá: " + decimalFormat.format(giamoinhat) + " Đ");
                com.t3h.appbanhang.activity.GioHang.eventUtils();
                if (slMoinhat > 9) {
                    finalViewHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoinhat));

                } else {
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoinhat));
                }
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoinhat = Integer.parseInt(finalViewHolder1.btnValues.getText().toString()) - 1;
                int slht = MainActivity.arrayListGioHang.get(i).getSoluong();
                long giaht = MainActivity.arrayListGioHang.get(i).getGiasp();
                MainActivity.arrayListGioHang.get(i).setSoluong(slMoinhat);
                long giamoinhat = (giaht * slMoinhat) / slht;
                MainActivity.arrayListGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.tvGiaGH.setText("Giá: " + decimalFormat.format(giamoinhat) + " Đ");
                com.t3h.appbanhang.activity.GioHang.eventUtils();
                if (slMoinhat < 2) {
                    finalViewHolder1.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder1.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnValues.setText(String.valueOf(slMoinhat));

                } else {
                    finalViewHolder1.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnValues.setText(String.valueOf(slMoinhat));
                }
            }
        });
        return view;

    }
}
