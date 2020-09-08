package smart.visitor.ir.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import  androidx.appcompat.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import Adapters.FragmentPagerAdapter.CustomerFragmentAdapter;
import Adapters.RecyclerViewAdapters.CustomerListAdapter;
import Entities.Order;
import Tools.App;
import Tools.LocationTools;
import Tools.Logcat;
import Tools.MyDateTime;
import smart.visitor.ir.R;
import smart.visitor.ir.ui.Customer.FragmentCustomerAll;
import smart.visitor.ir.ui.Customer.FragmentCustomerLocation;
import smart.visitor.ir.ui.Customer.FragmentCustomerPath;

public class AddOrderActivity extends BaseActivity   {
    Context context;
    ViewPager Vp_Order;
    TabLayout Tab_Order;
    FloatingActionButton Fab_Order ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        GetLocationFromIntent();
        InitView();
        SetOnClickListener();
        ConfigViewPager();
        this.Tab_Order.setupWithViewPager(this.Vp_Order);
    }

    private void GetLocationFromIntent() {
//        double Latitude = getIntent().getDoubleExtra ("Latitude",1363);
//        double Longitude = getIntent().getDoubleExtra ("Longitude",1363);
//        if (Latitude > 0  && Longitude > 0 )
//        {
//            this.location = new Location( "");
//            location.setLongitude(Longitude);
//            location.setLatitude(Latitude);
//        }
//        else
//        {
//            this.location = null;
//        }
    }


    private void SetOnClickListener() {
        this.Fab_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , CustomerSearchActivity.class);
                startActivityForResult(intent,App.RequestCodeProductList);
            }
        });
        this.Vp_Order.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                CustomerFragmentAdapter Adpt = (CustomerFragmentAdapter)Vp_Order.getAdapter();
                Fragment SelectedFragment  = Adpt.getItem(position);
                if (SelectedFragment instanceof FragmentCustomerLocation ){
                }
            }
        });
    }
    private void InitView() {
        this.context = this;
        //this.Toolbar_Order= findViewById(R.id.Toolbar_Main);
        this.Vp_Order = findViewById(R.id.Vp_Order);
        this.Tab_Order = findViewById(R.id.Tab_Order);
        this.Fab_Order = findViewById(R.id.Fab_Order);
        ((TextView)findViewById(R.id.tv_Header)).setText(R.string.select_customer);
        ((TextView)findViewById(R.id.tv_Detail)).setText(null);
        ((RatingBar)findViewById(R.id.ratingBar)).setVisibility(View.GONE);
        ((ImageView)findViewById(R.id.img_Logo)).setImageResource(R.drawable.shop2);
    }
    private void ConfigViewPager() {
        CustomerFragmentAdapter Adpt = new CustomerFragmentAdapter(getSupportFragmentManager());
        Adpt.AddToList(new FragmentCustomerAll(true), getResources().getString(R.string.all_customers));
        Adpt.AddToList(new FragmentCustomerPath() , getResources().getString(R.string.customer_per_path));
        Location l  = getCurrentLocation();
        Adpt.AddToList(new FragmentCustomerLocation(l), getResources().getString(R.string.customers_who_are_close_to_me));
        this.Vp_Order.setAdapter(Adpt);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == App.RequestCodeProductList) {
            if(resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

