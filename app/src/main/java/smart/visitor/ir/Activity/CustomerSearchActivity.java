package smart.visitor.ir.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapters.RecyclerViewAdapters.CustomerListAdapter;
import Databases.CustomerDbHelper;
import Entities.Customer;
import Tools.App;
import smart.visitor.ir.R;

public class CustomerSearchActivity extends BaseActivity {
    Context context ;
    SearchView sv ;
    TextView tv_Header,tv_detail;
    RecyclerView rv ;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);
        this.context = this;
        InitView();
        SetListener();
        sv.requestFocus();
    }


    private int loadData(String keyword) {
        CustomerDbHelper db = new CustomerDbHelper(context);
        List<Customer> lst ;
        if(keyword == null){
            lst= db.findAll();
        }
        else {
            lst= db.search(keyword);
        }
        CustomerListAdapter adapter = new CustomerListAdapter(context, lst);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,1);
        this.rv.setAdapter(null);
        if (lst != null) {
            this.rv.setLayoutManager(layoutManager);
            //this.rv.setItemAnimator(new DefaultItemAnimator());
            this.rv.setAdapter(adapter);
            return  lst.size();
        }
        else {
            this.rv.setAdapter(null);
            return 0 ;
        }
    }

    private void SetListener() {
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                int result ;
                if (newText == null  || newText.trim().isEmpty()){
                    reset();
                    return false;
                }else {
                     result = loadData(newText);
                }
                if (result == 0  ){
                    NoResult(newText);
                }else {
                    setVisible(false);
                }
                return true;
            }
        });
    }

    private void NoResult( String Keyword) {
        img.setImageResource(R.drawable.notfound2);
        tv_Header.setText( getResources().getString(R.string.NotFound));
        tv_detail.setText( getResources().getString(R.string.NotFoundMessage).replace("param" , Keyword));
        setVisible(true);
    }

    private void reset() {
        rv.setAdapter(null);
        img.setImageResource(R.drawable.search2);
        tv_Header.setText( getResources().getString(R.string.SearchCustomer));
        tv_detail.setText( getResources().getString(R.string.SearchCustomerDetail));
        setVisible(true);
    }

    private void InitView() {
        this.sv = findViewById(R.id.sv_Customer);
        this.rv = findViewById(R.id.rv_SearchCustomer);
        this.img =findViewById(R.id.img_SearchCustomer);
        this.tv_Header =findViewById(R.id.tv_Header);
        this.tv_detail =findViewById(R.id.tv_detail);
    }


    public void setVisible(boolean visible) {
            if (visible){
                this.tv_detail.setVisibility(View.VISIBLE);
                this.tv_Header.setVisibility(View.VISIBLE);
                this.img.setVisibility(View.VISIBLE);
            }else {
                this.tv_detail.setVisibility(View.INVISIBLE);
                this.tv_Header.setVisibility(View.INVISIBLE);
                this.img.setVisibility(View.INVISIBLE);
            }
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
