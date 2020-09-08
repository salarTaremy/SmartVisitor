package smart.visitor.ir.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.nio.channels.Channels;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import Tools.Logcat;
import Tools.MyDateTime;

public class BaseActivity extends AppCompatActivity {
    private static final int PERMISSION_ID = 1;
    final Context context;
    boolean IsRequiredLocation = false;
    boolean IsRequiredPermissions = false;
    //private FusedLocationProviderClient mFusedLocationClient;
    private int Location_Access_Code = 10001;
    private LocationManager mLocationManager;
    private long LOCATION_REFRESH_TIME = 0;
    private float LOCATION_REFRESH_DISTANCE = 0f;
    private Location location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitLocMgr();
    }


    public String GetIMEI(){
        String IMEI;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 10);
            return "";
        }
        IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }


    public BaseActivity() {
        this.context = this;
    }

    public BaseActivity(int contentLayoutId) {
        super(contentLayoutId);
        this.context = this;
    }

    private void InitLocMgr() {
        ChkNetworkConn();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String Provider = mLocationManager.getBestProvider(getFineCriteria(), true);
        if (Provider == null && Provider.isEmpty()) {
            Provider = mLocationManager.getBestProvider(getCoarseCriteria(), true);
        }
        mLocationManager.requestLocationUpdates(Provider, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);
    }

    private void ChkNetworkConn() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (IsNetworkEnabled()) {
            Location NetLoc;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            NetLoc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(NetLoc!=null && this.location == null){
                this.location = NetLoc;
            }
        }
    }


    public  String getActivityName(){
        String ActivityName = "";
        if(this instanceof MainActivity){
            ActivityName = "MainActivity";
        }
        if(this instanceof OrderManageActivity){
            ActivityName = "OrderManageActivity";
        }
        if(this instanceof AddOrderActivity){
            ActivityName = "AddOrderActivity";
        }
        return  ActivityName;
    }

    public   boolean  IsNetworkEnabled(){
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location L) {

            location = L;
            Logcat.i(L.getProvider() + " chenged " + L.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Logcat.i(provider + " Status Changed To " + String.valueOf(status));
        }

        @Override
        public void onProviderEnabled(String provider) {
            Logcat.i(provider + " Enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Logcat.i(provider + " Disabled");
        }
    };


    public void CheckLocationStatus() {
        if (!IsLocationProviderEnabled()) {
            buildAlertMessageNoGps();
        }
    }

    public void CheckLocationStatus(boolean IsRequiredLocation) {
        this.IsRequiredLocation = IsRequiredLocation;
        if (!IsLocationProviderEnabled()) {
            buildAlertMessageNoGps();
        }
    }

    /** this criteria will settle for less accuracy, high power, and cost */
    private static Criteria getCoarseCriteria() {

        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_COARSE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        return c;

    }

    /** this criteria needs high accuracy, high power, and cost */
    private static Criteria getFineCriteria() {

        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        return c;

    }


    public boolean IsLocationProviderEnabled(){
        try {
            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria =  getFineCriteria();
            String bestProvider = manager.getBestProvider(criteria,true);
            Logcat.i("bestProvider is : " + bestProvider);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Logcat.i("GPS_PROVIDER Enabled");
            }
            if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                Logcat.i("NETWORK_PROVIDER Enabled");
            }
            if (manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
                Logcat.i("PASSIVE_PROVIDER Enabled");
            }
            if (manager.isProviderEnabled(LocationManager.EXTRA_PROVIDER_NAME)){
                Logcat.i("EXTRA_PROVIDER_NAME Enabled");
            }

            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch ( Exception ex){
            return  false ;
        }
     }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String Msg ;
        if (IsRequiredLocation){
            Msg = "سیستم مکان یابی دستگاه شما الزاما باید فعال باشد.آیا اکنون این سیستم را فعال میکنید؟";
        }else {
            Msg = "سیستم مکان یابی دستگاه شما غیر فعال است.آیا تمایل به فعال سازی این سیستم دارید؟";
        }
        builder.setMessage(Msg)
                .setCancelable(false)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Location_Access_Code);
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        if (IsRequiredLocation){
                            finish();
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public  Location getCurrentLocation(){
        return  this.location;
    }


    public boolean checkPermissions( String[] PermissionList){
            String PermissionName ;
        for(int i = 0; i< PermissionList.length; i++){
            PermissionName = PermissionList[i];
            Logcat.i("Check Permission : " + PermissionName );
            if(ActivityCompat.checkSelfPermission(this, PermissionName) == PackageManager.PERMISSION_DENIED){
                Logcat.i("Permission Denied: " + PermissionName );
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(String[] PermissionList){
        ActivityCompat.requestPermissions(
                this,PermissionList, PERMISSION_ID);
    }
    public void requestPermissions(String[] PermissionList,boolean IsRequiredPermissions){
        this.IsRequiredPermissions = IsRequiredPermissions;
        ActivityCompat.requestPermissions(
                this,PermissionList, PERMISSION_ID);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // code
            }
        }
    }


}
