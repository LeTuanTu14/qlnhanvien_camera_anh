package com.example.appnhanvien711;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<NhanVien> {

    Context context;
    ArrayList<NhanVien> listNV;
    int layoutResource;

    public ListAdapter(Context contextt,int resource, ArrayList<NhanVien> nhanVienArrayList) {
        super(contextt, resource, nhanVienArrayList);
        this.context=contextt;
        this.listNV=nhanVienArrayList;
        this.layoutResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        convertView= inflater.inflate(layoutResource,null);

        ImageView imageView= convertView.findViewById(R.id.imageViewitem);
        TextView ma= convertView.findViewById(R.id.textView_ma);
        TextView hoten= convertView.findViewById(R.id.textView_hoten);
        TextView gt= convertView.findViewById(R.id.textView_gt);
        TextView dv= convertView.findViewById(R.id.textView_dv);

        imageView.setImageURI(listNV.get(position).getUrl());
        ma.setText(listNV.get(position).getMaso()+"");
        hoten.setText(listNV.get(position).getHoten()+"");
        gt.setText(listNV.get(position).getGioitinh()+"");
        dv.setText(listNV.get(position).getDonvi());

        return convertView;
    }


}
