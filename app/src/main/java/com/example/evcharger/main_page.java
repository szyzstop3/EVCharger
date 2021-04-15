package com.example.evcharger;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.example.evcharger.SQLite.MySQliteHelper;
import com.huawei.hms.hmsscankit.ScanUtil;

import com.baidu.mapapi.map.BaiduMap;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link main_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_page extends Fragment {

//    private map_card.MyLocationListener myListener = new map_card.MyLocationListener();




    private SQLiteOpenHelper litedb;
    private final Integer REQUEST_CODE_SCAN_ONE = 100;
    private Integer CAMERA_REQ_CODE = 100;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity MainActivity;


    //和主activity连接时更改标记


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setInterception(true);
    }

    public main_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main_page.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.evcharger.main_page newInstance(String param1, String param2) {
        com.example.evcharger.main_page fragment = new com.example.evcharger.main_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //((map_card)getTargetFragment()).locate();

//        getView().findViewById(R.id.findlocation).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "开启定位 ", Toast.LENGTH_LONG).show();
//
//                //定位初始化
//                mLocationClient = new LocationClient(getContext());
//
//
////通过LocationClientOption设置LocationClient相关参数
//                LocationClientOption option = new LocationClientOption();
//                option.setOpenGps(true); // 打开gps
//                option.setCoorType("bd09ll"); // 设置坐标类型
//                option.setScanSpan(1000);
//
////设置locationClientOption
//                mLocationClient.setLocOption(option);
//
////注册LocationListener监听器
//                com.example.evcharger.MyLocationListener myLocationListener = new com.example.evcharger.MyLocationListener();
//                mLocationClient.registerLocationListener(myLocationListener);
////开启地图定位图层
//                mLocationClient.start();
//                // Inflate the layout for this fragment
//
//
//            }
//        });


        Log.d("Mainpage","name1");
        litedb = new MySQliteHelper(getContext(), "User", null, 1);
        SQLiteDatabase db = litedb.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        String name = null;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("username"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        if(((MainActivity)getActivity()).isFirstIn()){
            Toast.makeText(getContext(), "欢迎回来 "+name, Toast.LENGTH_LONG).show();
        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        Log.d("Mainpage",name);
//        builder.setTitle("信息");
//        builder.setMessage("欢迎！" + name);
//        builder.setPositiveButton("是", null);
//        builder.show();




//        getView().findViewById(R.id.floatingActionButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.setFragment));
        getView().findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            //“QRCODE_SCAN_TYPE”和“DATAMATRIX_SCAN_TYPE”表示只扫描QR和Data Matrix的码
            //HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE , HmsScan.DATAMATRIX_SCAN_TYPE).create();
            @Override
            public void onClick(View view) {
                TextView viewById = getView().findViewById(R.id.textView5);
                viewById.setText("1启动");
                //CAMERA_REQ_CODE为用户自定义，用于接收权限校验结果
                main_page.this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQ_CODE);
                viewById.setText("99启动");
            }
        });

        getView().findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Mainpage","name1");
//                litedb = new MySQliteHelper(getContext(), "User", null, 1);
//                SQLiteDatabase db = litedb.getWritableDatabase();
//                Cursor cursor = db.query("User", null, null, null, null, null, null);
//                String name = null;
//                if (cursor.moveToFirst()) {
//                    do {
//                        name = cursor.getString(cursor.getColumnIndex("username"));
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//                db.close();
//                Toast.makeText(getContext(), "你好！ "+name, Toast.LENGTH_LONG).show();
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_main_page_to_userinfo_page);
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                Log.d("Mainpage",name);
//                builder.setTitle("信息");
//                builder.setMessage("欢迎！" + name);
//                builder.setPositiveButton("是", null);
//                builder.show();
            }
        });

    }

    //实现“onRequestPermissionsResult”函数接收校验权限结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        TextView viewById = getView().findViewById(R.id.textView5);
        viewById.setText("2调用接口");
        //判断“requestCode”是否为申请权限时设置请求码CAMERA_REQ_CODE，然后校验权限开启状态
        if (requestCode == CAMERA_REQ_CODE && grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            viewById.setText("3调用接口成功");
            //调用扫码接口，构建扫码能力，需您实现
            ScanUtil.startScan(this.getActivity(), REQUEST_CODE_SCAN_ONE, null);
            //viewById.setText("houmian");
        }
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}