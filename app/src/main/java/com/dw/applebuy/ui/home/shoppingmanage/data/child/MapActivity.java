package com.dw.applebuy.ui.home.shoppingmanage.data.child;

import android.Manifest;
import android.content.Intent;
import android.graphics.Point;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.dw.applebuy.R;
import com.dw.applebuy.ui.Title1Fragment;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.orhanobut.logger.Logger;
import com.wlj.base.ui.BaseFragmentActivity;
import com.wlj.base.util.StringUtils;
import com.wlj.base.util.UIHelper;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MapActivity extends BaseFragmentActivity implements Title1Fragment.TitleInterface, OnGetGeoCoderResultListener {

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstLoc = true;

    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private String address;
    private TextView addresstext;
    private LatLng center;
//    private GeoCoder geoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        addresstext = (TextView) findViewById(R.id.map_addresstext);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        //百度
//        geoCoder = GeoCoder.newInstance();
//        geoCoder.setOnGetGeoCodeResultListener(this);

        initLocation();

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

                LatLngBounds bound = mapStatus.bound;
                final LatLng center = bound.getCenter();

                addresstext.setText("定位中...");

                Observable.just("")
                        .map(new Func1<String, String>() {
                                 @Override
                                 public String call(String str) {
                                     String s = loactiontoString(center);
                                     if (s != null) return s;
                                     return "位置获取失败";
                                 }
                             }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                addresstext.setText("定位失败！！");
                            }

                            @Override
                            public void onNext(String s) {
                                addresstext.setText(s);
                            }
                        });


            }
        });

    }

    private void auth() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).build(), new AcpListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> permissions) {
                UIHelper.toastMessage(getApplicationContext(), permissions.toString() + "权限拒绝");
                finish();
            }
        });

    }

    @Nullable
    private String loactiontoString(LatLng center) {
        this.center = center;
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(center));
        //google
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {

            List<android.location.Address> fromLocation = geocoder.getFromLocation(center.latitude - 0.004001, center.longitude - 0.012, 1);
            if (fromLocation != null && fromLocation.size() > 0) {
                for (int i = 0; i < fromLocation.size(); i++) {

                    android.location.Address address = fromLocation.get(i);
                    StringBuilder sb = new StringBuilder();
                    for (int i1 = 0; i1 < address.getMaxAddressLineIndex(); i1++) {
                        sb.append(address.getAddressLine(i1));
                    }
                    String s1 = address.getAdminArea() + address.getLocality() + address.getSubLocality() + (address.getFeatureName() == null ? "" : address.getFeatureName());
                    String s = sb.toString();
                    if(StringUtils.isEmpty(s)){
                        s = s1;
                    }
//                    String s = address.getAddressLine(1) + address.getFeatureName();
//                    Logger.e(address.toString() + "\n " + s);
                    return s;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLocation();

        mMapView.onDestroy();
    }

    private void closeLocation() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(myListener);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void setTitle(TextView title, TextView right) {
        title.setText("店铺地址");
        right.setText("确认");
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(address)) {
                    UIHelper.toastMessage(getApplication(), "请稍后，正在定位……");
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("address", address);
                intent.putExtra("Latitude", center.latitude);
                intent.putExtra("Longitude", center.longitude);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void initLocation() {

//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        //改图标
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.loaction);
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker,
//                accuracyCircleFillColor, accuracyCircleStrokeColor));

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系,bd09ll
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        String address = result.getAddress();
        Logger.e(address);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            closeLocation();
            if (location.getLocType() == BDLocation.TypeGpsLocation ||           // GPS定位结果
                    location.getLocType() == BDLocation.TypeNetWorkLocation ||   // 网络定位结果
                    location.getLocType() == BDLocation.TypeOffLineLocation) {   // 离线定位结果

                address = location.getAddrStr();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addresstext.setText(address);
                    }
                });

            } else {
                address = "";
//                addresstext.setText("定位中……");
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

}

//        time : 2017-01-19 15:59:23
//        error code : 161
//        latitude : 29.599524
//        lontitude : 106.603974
//        radius : 80.164795
//        addr : 中国重庆市南岸区腾龙大道
//        operationers : 0
//        describe : 网络定位成功
//        locationdescribe : 在重庆市第三十八中学校附近
//        poilist size = : 5
//        poi= : 1675588651307721649 重庆市第三十八中学校 0.99
//        poi= : 17672865441703264255 国际社区 0.99
//        poi= : 15227886502751717104 国际商务大厦C座 0.99
//        poi= : 14296334752139177145 永辉超市(腾龙大道店) 0.99
//        poi= : 11387672001424361576 国际社区观邸 0.99