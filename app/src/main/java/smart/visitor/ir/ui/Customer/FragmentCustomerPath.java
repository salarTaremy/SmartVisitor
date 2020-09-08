package smart.visitor.ir.ui.Customer;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Adapters.RecyclerViewAdapters.CustomerPathDTOAdapter;
import DataTransferObjects.CustomerPathDTO;
import Databases.GeneralDbHelper;
import smart.visitor.ir.R;

public class FragmentCustomerPath extends Fragment {

    Context context;
    View root;
    RecyclerView Rv;
    public FragmentCustomerPath() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_customer_per_path,container,false);
        this.context = getActivity();
        InitView();
        loadData();
        return  root;
    }
    private void InitView() {
        this.Rv =  root.findViewById(R.id.Rv_CustomerListPerPath);
    }
    private void loadData() {
        GeneralDbHelper db = new GeneralDbHelper(context);
        List<CustomerPathDTO> lst ;
        lst= db.GetCustomerPathDTOList();
        CustomerPathDTOAdapter adapter = new CustomerPathDTOAdapter(context, lst);
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
