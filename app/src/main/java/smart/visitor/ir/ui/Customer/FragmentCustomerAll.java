package smart.visitor.ir.ui.Customer;
import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Adapters.RecyclerViewAdapters.CustomerListAdapter;
import Databases.CustomerDbHelper;
import Entities.Customer;
import smart.visitor.ir.R;

import  androidx.recyclerview.widget.*;

import org.w3c.dom.Text;

import java.util.List;

public class FragmentCustomerAll extends Fragment {
     Boolean ShowAll =false;
    Context context;
    View root;
    RecyclerView rv;
    String PathName = null;

    public FragmentCustomerAll() {
    }
    public FragmentCustomerAll(Boolean ShowAll ) {
        this.ShowAll = ShowAll;
    }
    public FragmentCustomerAll(String PathName) {
        this.PathName = PathName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_customer_all,container,false);
        this.context = getActivity();
        InitView();
        loadData(null);
        return  root;
    }
    private void InitView() {
        this.rv =  root.findViewById(R.id.Rv_CustomerListAll);
    }
    private void loadData(String keyword) {
        CustomerDbHelper db = new CustomerDbHelper(context);
        List<Customer> lst = null ;
        if(keyword == null){
            if (this.ShowAll ){
                lst= db.findAll();
            }else if( this.PathName != null) {
                lst= db.findAll(PathName);
            } else {
                // is null Data
            }
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
        }
    }

}
