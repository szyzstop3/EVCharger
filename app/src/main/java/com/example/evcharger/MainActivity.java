package com.example.evcharger;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.example.evcharger.SQLite.MySQliteHelper;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;


public class MainActivity extends AppCompatActivity {
    private final Integer REQUEST_CODE_SCAN_ONE = 100;
    private long mExitTime;
    private boolean isInterception = false;
    private boolean isFirstIn = true;

    BaiduMap mBaiduMap = null;
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;



    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }

    public void resetFirstIn(){
        this.isFirstIn = true;
    }

    public boolean isFirstIn(){
        if(this.isFirstIn){
            this.isFirstIn = false;
            return true;
        }else {
            return false;
        }
    }



    //双击退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&isInterception==true) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show();
                //System.currentTimeMillis()系统当前时间
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView viewById = findViewById(R.id.textView5);
        viewById.setText("4调用回调接口");
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            //导入图片扫描返回结果
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                Bundle bundle = new Bundle();
                bundle.putString("testvalue",obj.getOriginalValue());
                //展示解码结果
                Navigation.findNavController(viewById).navigate(R.id.action_main_page_to_charge_page,bundle);
//                TextView viewById1 = findViewById(R.id.textView10);
//                viewById1.setText(obj.getOriginalValue());
            }
        }
    }


//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null){
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
//        mMapView = findViewById(R.id.bmapView);



//        findViewById(R.id.findlocation).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mBaiduMap = mMapView.getMap();
//                mBaiduMap.setMyLocationEnabled(true);
//
//
//                Toast.makeText(getApplicationContext(), "开启定位 ", Toast.LENGTH_LONG).show();
//                //定位初始化
//                mLocationClient = new LocationClient(getApplicationContext());
//
////通过LocationClientOption设置LocationClient相关参数
//                LocationClientOption option = new LocationClientOption();
//                option.setOpenGps(true); // 打开gps
//                option.setCoorType("GCJ02"); // 设置坐标类型
//                option.setScanSpan(1000);
//
////设置locationClientOption
//                mLocationClient.setLocOption(option);
//
////注册LocationListener监听器
//                MyLocationListener myLocationListener = new MyLocationListener();
//                mLocationClient.registerLocationListener(myLocationListener);
////开启地图定位图层
//                mLocationClient.start();
//            }
//        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();
//        mLocationClient.stop();
//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//        mMapView = null;

    }


}