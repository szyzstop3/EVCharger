package com.example.evcharger;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.evcharger.DAO.impl.Toolimpl;
import com.example.evcharger.vo.Charger;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  new Thread(new Runnable() {
 *                          @Override
 *                          public void run() {
 *                              Looper.prepare();
 *
 *
 *                              option = ui.LoginUser(user);
 *                              System.out.println(option);
 *                              Looper.loop();
 *                          }
 *                      }).start();
 */

public class MainActivity extends AppCompatActivity {
    private final Integer REQUEST_CODE_SCAN_ONE = 100;
    private long mExitTime;
    private boolean isInterception = false;
    private boolean isFirstIn = true;

    boolean ri;

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

        Log.d("validCharger","1");

        TextView viewById = findViewById(R.id.textView5);
        viewById.setText("4调用回调接口");
        if (resultCode != RESULT_OK || data == null) {

            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            Log.d("validCharger","100");
            //导入图片扫描返回结果
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            String scans = obj.getOriginalValue();
            String[] scnss = scans.split("-");
            if(scnss.length!=2){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("信息" ) ;
                        builder.setMessage("请扫描EVCharger充电桩上的二维码！" ) ;
                        builder.setPositiveButton("是", null);
                        builder.show();
            }else {
                Charger charger = new Charger();
                charger.setChargerid(Integer.parseInt(scnss[0]));
                charger.setCSA(scnss[1]);
//                Toast.makeText(getContext(),charger.getChargerid() , Toast.LENGTH_SHORT).show();


                Log.d("validCharger","1");

                if (obj != null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();

                            Log.d("validCharger","1");
                            Toolimpl toolimpl = new Toolimpl();

                            Bundle bundle = new Bundle();
                            if(toolimpl.validCharger(charger)){
                                try {
                                    JSONObject jsonObject = new JSONObject(toolimpl.getCharger(charger));
                                    bundle.putString("chargername",jsonObject.getString("chargername"));
                                    bundle.putString("longitude",jsonObject.getString("longitude"));
                                    bundle.putString("latitude",jsonObject.getString("latitude"));
                                    bundle.putString("state",jsonObject.getString("state"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                bundle.putString("chargerid",""+charger.getChargerid());
                                Log.d("validCharger","chargerid "+charger.getChargerid());
                                //展示解码结果
                                Navigation.findNavController(viewById).navigate(R.id.action_main_page_to_charge_page,bundle);
//                TextView viewById1 = findViewById(R.id.textView10);
//                viewById1.setText(obj.getOriginalValue());

                            }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("信息" ) ;
                        builder.setMessage("二维码已过期！" ) ;
                        builder.setPositiveButton("是", null);
                        builder.show();
                            }
                            Looper.loop();
                        }
                    }).start();



                }

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