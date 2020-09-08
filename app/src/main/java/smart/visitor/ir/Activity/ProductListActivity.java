package smart.visitor.ir.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;
import com.omega_r.libs.OmegaCenterIconButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Adapters.RecyclerViewAdapters.ProductListAdapter;
import DataTransferObjects.ProductDTO;
import Databases.GeneralDbHelper;
import Databases.ProductDbHelper;
import Entities.Order;
import Entities.OrderItem;
import Entities.Product;
import Tools.App;
import smart.visitor.ir.R;

public class ProductListActivity extends BaseActivity {
    Context context ;
    RecyclerView rv ;
    SearchView sv;
    Order order;
    List<ProductDTO> lst ;
    TextView tv_Header , tv_Detail;
    RatingBar ratingBar;
    OmegaCenterIconButton BtnConfirmAndContinue;
    ImageView img_Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        this.context = this;
        this.order= (Order) getIntent().getSerializableExtra("Order");
        InitView();
        SetView();
        SetListener();
        loadData(null);
        SetBtnVisible();
    }
    private void InitView() {
        this.rv = findViewById(R.id.rv_ProductList);
        this.sv = findViewById(R.id.sv_Product);
        this.tv_Header = findViewById(R.id.tv_Header);
        this.tv_Detail = findViewById(R.id.tv_Detail);
        this.ratingBar = findViewById(R.id.ratingBar);
        this.img_Logo = findViewById(R.id.img_Logo);
        this.BtnConfirmAndContinue = findViewById(R.id.BtnConfirmAndContinue);
    }

    private void SetView() {
        this.tv_Header.setText(R.string.AddNewOrder);
        this.tv_Detail.setText(this.order.customer.name);
        this.ratingBar.setRating(this.order.customer.rate);
        this.img_Logo.setImageResource(R.drawable.product_add);
    }

    private  void SetListener(){
     this.sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {
             return false;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             if (newText == null  || newText.trim().isEmpty()){
                 loadData(null);
             }else {
                 loadData(newText);
             }
             return false;
         }
     });
        BtnConfirmAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(context, BasketActivity.class);
                intent.putExtra("Order", order);
                startActivityForResult(intent,App.RequestCodeBasket);
            }
        });
    }

    private void loadData(String keyword) {
        GeneralDbHelper db = new GeneralDbHelper(context);
        if(keyword == null){
            lst= db.GetProductDTOList(this.order.customer.id , "" );
        }
        else {
            lst= db.GetProductDTOList(this.order.customer.id , keyword );
        }
        SetSelectedItem();
        ProductListAdapter adpt = new ProductListAdapter(context, lst);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.rv.setAdapter(null);
        if (lst != null) {
            this.rv.setLayoutManager(layoutManager);
            this.rv.setItemAnimator(new DefaultItemAnimator());
            this.rv.setAdapter(adpt);
        }
        else {
            this.rv.setAdapter(null);
        }
    }

    public  void SetBtnVisible(){
        if (order.Items.size() > 0){
            findViewById(R.id.BottomSheetContinue).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.BottomSheetContinue).setVisibility(View.INVISIBLE);
        }
    }


    public void DoChange() {
        if (this.lst == null){
            return;
        }
        for (ProductDTO p : this.lst)
        {
            AddProduct(p);
        }
        SetBtnVisible();
    }


    private void SetSelectedItem() {
        if (lst == null){
            return;
        }
        for (Product p : lst) {
            for (OrderItem item  : this.order.Items) {
                if (item.ID_Product == p.id){
                    p.SelectedCount = item.Qty;
                    p.SelectedOffer = item.Offer;
                }
            }
        }
    }

    private void AddProduct(ProductDTO product) {
        for (Iterator<OrderItem> iter = this.order.Items.listIterator(); iter.hasNext(); ) {
            OrderItem it = iter.next();
            if (it.ID_Product == product.id){
                iter.remove();
            }
        }
        if (product.SelectedCount > 0 || product.SelectedOffer > 0  ){
            this.order.Items.add(new OrderItem(0,0,product, product.Price , product.Tax));

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.RequestCodeBasket) {
            if(resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                this.order= (Order) data.getSerializableExtra("Order");
                loadData(null);
                SetBtnVisible();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (this.order.Items.size() == 0 ){
            finish();
        }
        CoordinatorLayout crdLayout = findViewById(R.id.crd_ProductList);
        Snackbar snkbr = Snackbar.make(crdLayout, R.string.AreYouSoureForExit, Snackbar.LENGTH_LONG);
        snkbr.setDuration(2000);
        snkbr.setAction(R.string.Yes, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ViewCompat.setLayoutDirection(snkbr.getView(),ViewCompat.LAYOUT_DIRECTION_RTL);
        snkbr.show();
        //super.onBackPressed();
    }
}


