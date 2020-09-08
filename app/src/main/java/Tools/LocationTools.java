package Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationTools {
//    Context context;
//    Location CurentLocation = null;
//    FusedLocationProviderClient mFusedLocationClient;
//    public LocationTools(Context context) {
//        this.context = context;
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        getLastLocation();
//    }
//    // getting distance in kilometers
//    public double distance(double lat1, double lon1, double lat2, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }
//    public boolean isLocationEnabled(){
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//    @SuppressLint("MissingPermission")
//    public void getLastLocation(){
//        try {
//            if (isLocationEnabled()) {
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                        new OnCompleteListener<Location>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Location> task) {
//                                Location location = task.getResult();
//                                if (location == null) {
//                                    requestNewLocationData();
//                                } else {
//                                    Logcat.send(String.valueOf(location.getLatitude()));
//                                    Logcat.send(String.valueOf(location.getLongitude()));
//                                    CurentLocation = location;
//                                }
//                            }
//                        }
//                );
//            } else {
//                // Location not Enable
//            }
//        }catch (Exception ex){
//            Logcat.e(ex.getMessage());
//        }
//    }
//    private LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//        }
//    };
//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData(){
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(0);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        mFusedLocationClient.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                Looper.myLooper()
//        );
//    }
//
//    public Location getCurentLocation() {
//        return CurentLocation;
//    }
//
//




}
