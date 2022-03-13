package com.example.appnhanvien711;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<NhanVien> nv_list= new ArrayList<>();
    String[] dv_list;
    String dv;
    ListAdapter listAdapter;
    ListView lv_nhanvien;
    Button chonanh;
    ImageView img;
    Uri urlanh;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    private static final int CAMERA_REQUEST = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText maso= findViewById(R.id.edittext_ma);
        EditText hoten= findViewById(R.id.edittext_hoten);
        lv_nhanvien= findViewById(R.id.listview_nhanvien);
        RadioGroup rg_gioitinh= findViewById(R.id.group_rbgt);
        RadioButton rb_nam= findViewById(R.id.radio_nam);
        RadioButton rb_nu= findViewById(R.id.radio_nu);
        img= findViewById(R.id.imageview);

        Spinner sp_donvi= findViewById(R.id.spiner_dv);
        dv_list= getResources().getStringArray(R.array.donvi_list);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dv_list);
        sp_donvi.setAdapter(adapter);
        sp_donvi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dv=dv_list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button bt_them= findViewById(R.id.them);
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ms=maso.getText().toString();
                String hten= hoten.getText().toString();
                String gt= ((RadioButton)findViewById(rg_gioitinh.getCheckedRadioButtonId())).getText().toString();
                if(ktraDuLieuNhap(ms,hten)==false){
                    return ;
                }
                int masoo= Integer.parseInt(ms);
                if(ktraTrungMaNV(masoo)==false){
                    Toast.makeText(MainActivity.this,"Mã số nhân viên bị trùng!!",Toast.LENGTH_SHORT).show();
                    return ;
                }

                NhanVien nv= new NhanVien(masoo,hten,gt,dv,urlanh);

                nv_list.add(nv);

                ArrayList<NhanVien> listItems= new ArrayList<NhanVien>();
                for (NhanVien nv1:nv_list){
                    listItems.add(nv1);

                }
                listAdapter= new ListAdapter(MainActivity.this,R.layout.list_item, listItems);

//                ArrayAdapter<String> listAdapter= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1,listItems);
                lv_nhanvien.setAdapter(listAdapter);
            }
        });

        lv_nhanvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NhanVien nv= nv_list.get(i);
                maso.setText(nv.getMaso()+"");
                hoten.setText(nv.getHoten()+"");
                img.setImageURI(nv.getUrl());
                urlanh=nv.getUrl();

                if(nv.getGioitinh().equals("Nam"))
                    rb_nam.setChecked(true);
                else
                    rb_nu.setChecked(true);

                for (int j=0;j< dv_list.length;j++)
                    if(dv_list[j].equals(nv.getDonvi()))
                        sp_donvi.setSelection(j);
            }
        });

//        Them 1imageview và 1 nut button trong ui chon hình
//        listview sua lai giong trong fileword
        chonanh= findViewById(R.id.uploadanh);
        chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
//                }else {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permission= {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permission, PERMISSION_CODE);
                        }else{
                            pickImageFromGallery();
                        }
                    }else{
                        pickImageFromGallery();
                    }
//                }

            }
        });

        Button thoat= findViewById(R.id.thoat);
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khoi tao lai Activity main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Tao su kien ket thuc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
            }
        });

        Button update= findViewById(R.id.sua);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ms=maso.getText().toString();
                String hten= hoten.getText().toString();
                String gt= ((RadioButton)findViewById(rg_gioitinh.getCheckedRadioButtonId())).getText().toString();
                if(ktraDuLieuNhap(ms,hten)==false){
                    return ;
                }
                int masoo= Integer.parseInt(ms);

                for (int i=0;i<nv_list.size();i++){
                    if(masoo==nv_list.get(i).getMaso()){
                        nv_list.get(i).setHoten(hten);
                        nv_list.get(i).setGioitinh(gt);
                        nv_list.get(i).setDonvi(dv);
                        nv_list.get(i).setUrl(urlanh);
                    }
                }

                ArrayList<NhanVien> listItems= new ArrayList<NhanVien>();
                for (NhanVien nv1:nv_list){
                    listItems.add(nv1);

                }
                listAdapter= new ListAdapter(MainActivity.this,R.layout.list_item, listItems);

//                ArrayAdapter<String> listAdapter= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1,listItems);
                lv_nhanvien.setAdapter(listAdapter);
            }
        });

        Button truyvan= findViewById(R.id.truyvan);
        truyvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ms=maso.getText().toString();
                String so = "\\d+";
                if(!(ms.matches(so))){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập mã nhân viên là số !!",Toast.LENGTH_SHORT).show();
                    return ;
                }
                int masoo= Integer.parseInt(ms);
                for (int i=0;i<nv_list.size();i++){
                    if(masoo==nv_list.get(i).getMaso()){
                        maso.setText(nv_list.get(i).getMaso()+"");
                        hoten.setText(nv_list.get(i).getHoten()+"");
                        img.setImageURI(nv_list.get(i).getUrl());

                        if(nv_list.get(i).getGioitinh().equals("Nam"))
                            rb_nam.setChecked(true);
                        else
                            rb_nu.setChecked(true);

                        for (int j=0;j< dv_list.length;j++)
                            if(dv_list[j].equals(nv_list.get(i).getDonvi()))
                                sp_donvi.setSelection(j);
                    }

                }
            }
        });
    }
    private void pickImageFromGallery(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(this,"Lỗi",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            urlanh=data.getData();
            return;
        }
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            img.setImageURI(data.getData());
            urlanh=data.getData();

        }
    }

    public boolean ktraTrungMaNV(int ma){
        for (int i=0;i<nv_list.size();i++){
            if(ma==nv_list.get(i).getMaso())
                return false;

        }
        return true;
    }

    public boolean ktraDuLieuNhap(String ms, String hten){
//        String ht = "^[a-z Ạ-ỹ A-Z]$";
        String so = "\\d+";
        if(!(ms.matches(so))){
            Toast.makeText(MainActivity.this,"Vui lòng nhập mã nhân viên là số !!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(urlanh==null){
            Toast.makeText(MainActivity.this,"Vui lòng chọn ảnh nhân viên !!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(hten == " "){
            Toast.makeText(MainActivity.this,"Vui lòng nhập tên nhân viên !!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}