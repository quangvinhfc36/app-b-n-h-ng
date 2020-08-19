package com.t3h.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.t3h.appbanhang.R;
import com.t3h.appbanhang.activity.ChiTietSanPham;
import com.t3h.appbanhang.model.SanPham;
import com.t3h.appbanhang.utils.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamHolder> {
    Context context;
    private ArrayList<SanPham> sanPhams;

    public SanPhamAdapter(Context context, ArrayList<SanPham> sanPhams) {
        this.context = context;
        this.sanPhams = sanPhams;
    }

    @NonNull
    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,null);
        SanPhamHolder sanPhamHolder = new SanPhamHolder(v);
        return sanPhamHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, int position) {
        SanPham sanPham = sanPhams.get(position);
        holder.tvTenSp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiasp.setText("Giá: " +decimalFormat.format(sanPham.getGiasp())+" Đ");
        Picasso.with(context).load(sanPham.getHinhanhsp()).placeholder(R.drawable.ic_camera_alt_black_24dp).
                error(R.drawable.ic_error).into(holder.imageViewsanpham);

    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageViewsanpham;
        private TextView tvTenSp;
        private TextView tvGiasp;
        public SanPhamHolder(@NonNull View itemView) {
            super(itemView);
            imageViewsanpham = itemView.findViewById(R.id.imSanpham);
            tvTenSp = itemView.findViewById(R.id.tv_tensp);
            tvGiasp = itemView.findViewById(R.id.tv_giasp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ChiTietSanPham.class);
            intent.putExtra("Thông tin sản phẩm",sanPhams.get(getPosition()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            CheckConnection.showToast(context,sanPhams.get(getPosition()).getTensp());
            context.startActivity(intent);
        }
    }
}
