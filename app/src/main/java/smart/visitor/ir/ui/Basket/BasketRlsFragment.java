package smart.visitor.ir.ui.Basket;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Entities.Order;
import Entities.OrderItem;
import Tools.Convert;
import smart.visitor.ir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketRlsFragment extends Fragment {

    Order order;
    Context context;
    TextView tv_totalRls,tv_SumRls,tv_DiscountRls,tv_TaxRls;

    public BasketRlsFragment() {
        this.context = getActivity();
    }

    public BasketRlsFragment(Order order) {
        this.context = getActivity();
        this.order = order;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_basket_rls, container, false);
        InitView(root);
        return  root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Campute();

    }

    private void Campute() {


        if (this.order == null){
        return;
        }

        int Total = 0 ;
        int Sum = 0 ;
        int Discount = 0 ;
        int Tax = 0 ;
    for (OrderItem item : this.order.Items){
        Sum += (item.Price * item.Qty);
        Discount += (item.Price * item.Offer);
        Tax +=   ((item.Price    * item.Qty)  *  item.Tax  / 100) ;
        Total +=   ((item.Price * item.Qty) +   ((item.Price    * item.Qty)  *  item.Tax  / 100)) ;
    }
    this.tv_totalRls.setText( Convert.toSeprate(  Total) );
        this.tv_SumRls.setText( Convert.toSeprate(  Sum) );
        this.tv_DiscountRls.setText( Convert.toSeprate(  Discount) );
        this.tv_TaxRls.setText( Convert.toSeprate(  Tax) );
    }

    private void InitView(View root) {
        this.tv_totalRls = root.findViewById(R.id.tv_totalRls);
        this.tv_SumRls = root.findViewById(R.id.tv_SumRls);
        this.tv_DiscountRls = root.findViewById(R.id.tv_DiscountRls);
        this.tv_TaxRls = root.findViewById(R.id.tv_TaxRls);
    }
}
