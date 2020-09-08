package smart.visitor.ir.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecyclerViewAdapters.OrderListAdapter;
import DataTransferObjects.OrderDTO;
import Databases.GeneralDbHelper;
import Entities.Customer;
import Entities.Order;
import Tools.App;
import smart.visitor.ir.R;

public class OrderManageActivity extends BaseActivity {
    RecyclerView rv_Order_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        initView();
        loadData();
    }
    private void initView() {
        rv_Order_List = findViewById(R.id.rv_Order_List);
        ((TextView)findViewById(R.id.tv_Header)).setText(R.string.ManageOrders);
        ((TextView)findViewById(R.id.tv_Detail)).setVisibility(View.GONE);
        ((RatingBar)findViewById(R.id.ratingBar)).setVisibility(View.GONE);
        ((ImageView)findViewById(R.id.img_Logo)).setImageResource(R.drawable.order7);
    }
    private void loadData() {

        GeneralDbHelper db = new GeneralDbHelper( context);
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        orders =  db.GetOrderDTOList();
        OrderListAdapter Adpt = new OrderListAdapter(this, orders);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rv_Order_List.setLayoutManager(layoutManager);
        rv_Order_List.setItemAnimator(new DefaultItemAnimator());
        rv_Order_List.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_Order_List.setAdapter(Adpt);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.RequestCodeSuccess){
            if (resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED){
                //Toast.makeText(this,"CANCELED" ,   Toast.LENGTH_LONG ).show();
            }
        }
    }
}