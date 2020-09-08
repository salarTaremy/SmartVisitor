package smart.visitor.ir.ui.Customer;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Adapters.RecyclerViewAdapters.CustomerListAdapter;
import Databases.CustomerDbHelper;
import Entities.Customer;
import smart.visitor.ir.R;

public class FragmentCustomerLocation extends Fragment {

    Context context;
    View root;
    RecyclerView Rv;
    Location loc;
    public FragmentCustomerLocation(Location loc) {
        this.loc = loc;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_customer_location,container,false);
        this.context = getActivity();
        InitView();
        loadData(null);
        return  root;
    }
    private void InitView() {
        this.Rv =  root.findViewById(R.id.Rv_CustomerListLocation);
    }
    private void loadData(String keyword) {
        if (this.loc == null){
            return;
        }
        CustomerDbHelper db = new CustomerDbHelper(context);
        List<Customer> lst ;
        if(keyword == null){
            lst= db.findAll( loc);
        }
        else {
            lst= db.search(keyword);
        }
        CustomerListAdapter adapter = new CustomerListAdapter(context, lst);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.Rv.setAdapter(null);
        if (lst != null) {
            this.Rv.setLayoutManager(layoutManager);
            this.Rv.setItemAnimator(new DefaultItemAnimator());
            this.Rv.setAdapter(adapter);
        }
        else {
            this.Rv.setAdapter(null);
        }
    }

}
