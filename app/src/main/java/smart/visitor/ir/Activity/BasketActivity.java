package smart.visitor.ir.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.omega_r.libs.OmegaCenterIconButton;

import Adapters.RecyclerViewAdapters.BasketListAdapter;
import Entities.Order;
import Tools.App;
import smart.visitor.ir.R;
import smart.visitor.ir.ui.Basket.BasketRlsFragment;

public class BasketActivity extends BaseActivity {
    Context context;
    RecyclerView rv ;
    Toolbar Toolbar_Order;
    Order order;
    LinearLayout BottomSheetBasketPriceDetail;
    BottomSheetBehavior bottomSheet;
    FloatingActionButton fab_DetailBasket;
    OmegaCenterIconButton BtnConfirmAndContinueBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        this.context = this;
        this.order= (Order) getIntent().getSerializableExtra("Order");
        InitView();
        SetListener();
        LoadFragment();
        loadData();
    }

    private void SetListener() {
        bottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED  ){
                    //fab_DetailBasket.setVisibility(View.VISIBLE);
                    if(fab_DetailBasket.getVisibility() == View.INVISIBLE ){
                        Animation in = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                        fab_DetailBasket.startAnimation(in);
                        fab_DetailBasket.setVisibility(View.VISIBLE);
                    }

                }else {
                    //fab_DetailBasket.setVisibility(View.INVISIBLE);
                    if(fab_DetailBasket.getVisibility() == View.VISIBLE ){
                        Animation out = AnimationUtils.makeOutAnimation(context, false);
                        fab_DetailBasket.startAnimation(out);
                        fab_DetailBasket.setVisibility(View.INVISIBLE);
                    }

                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        BtnConfirmAndContinueBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(context, PaymentTypeActivity.class);
                intent.putExtra("Order",order);
                startActivityForResult(intent,App.RequestCodeOrder);
            }
        });
    }

    private void InitView() {
        this.context = this;
        this.Toolbar_Order= findViewById(R.id.Toolbar_Main);
        //this.Toolbar_Order.setVisibility(View.VISIBLE);
        this.Toolbar_Order.setTitle(this.order.customer.name);
        this.rv = findViewById(R.id.rv_Basket_List);
        this.BottomSheetBasketPriceDetail     = findViewById(R.id.BottomSheetBasketPriceDetail);
        this.bottomSheet = BottomSheetBehavior.from(BottomSheetBasketPriceDetail);
        this.fab_DetailBasket = findViewById(R.id.fab_DetailBasket);
        this.BtnConfirmAndContinueBasket  = findViewById(R.id.BtnConfirmAndContinueBasket);
        ((TextView)findViewById(R.id.tv_Header)).setText(R.string.ViewCard);
        ((TextView)findViewById(R.id.tv_Detail)).setText(this.order.customer.name);
        ((RatingBar)findViewById(R.id.ratingBar)).setVisibility(View.GONE);
        ((ImageView)findViewById(R.id.img_Logo)).setImageResource(R.drawable.cart);
    }

    public void LoadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        BasketRlsFragment fragment = new BasketRlsFragment(this.order);
        fm.beginTransaction().replace(R.id.fragmentContainerBasket , fragment).commit();
    }
    private void loadData() {

        BasketListAdapter adpt = new BasketListAdapter(context, order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.rv.setAdapter(null);
        if (this.order.Items != null) {
            this.rv.setLayoutManager(layoutManager);
            this.rv.setItemAnimator(new DefaultItemAnimator());
            this.rv.setAdapter(adpt);
        }
        else {
            this.rv.setAdapter(null);
        }
    }

    public void fabDetailBasket_Clickإ(View view) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("Description",this.order.Description);
        startActivityForResult(intent, App.RequestCodeDescription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == App.RequestCodeDescription){
            if (resultCode == Activity.RESULT_OK){
                String Description =  data.getStringExtra("Description");
                this.order.Description = Description;
            }
            if (resultCode == Activity.RESULT_CANCELED){
                //Toast.makeText(this,"CANCELED" ,   Toast.LENGTH_LONG ).show();
            }
        }
        if (requestCode == App.RequestCodeOrder){
            if (resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED){
                //Toast.makeText(this,"CANCELED" ,   Toast.LENGTH_LONG ).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Order",order);
        setResult(Activity.RESULT_CANCELED,returnIntent);
        super.onBackPressed();
    }


}
