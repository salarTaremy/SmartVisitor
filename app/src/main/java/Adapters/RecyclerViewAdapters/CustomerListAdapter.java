package Adapters.RecyclerViewAdapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entities.Customer;
import Entities.Order;
import Holders.CustomerRecyclerViewHolder;
import Holders.ItemClickListener;
import Tools.App;
import smart.visitor.ir.Activity.AddOrderActivity;
import smart.visitor.ir.Activity.ProductListActivity;
import smart.visitor.ir.R;
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerRecyclerViewHolder> {
    private List<Customer> customers;
    private Context context;
    private  boolean CallFromOrder;
    public CustomerListAdapter(Context context, List<Customer> customers ) {
        this.context = context;
        this.customers = customers;
        this.CallFromOrder =  false;
    }
    @NonNull
    @Override
    public CustomerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_list, parent,false);
        return new CustomerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerRecyclerViewHolder holder, int i) {
        Customer obj = this.customers.get(i);
        holder.tv_CustomerCode.setText(obj.code);
        holder.tv_CustomerName.setText(obj.name);
        holder.tv_CustomerDescription.setText(obj.description);
        //======================== Event Listener =============================================
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Customer selectedCustomer = customers.get(position);
                if (isLongClick) {

                } else {
                    Intent myIntent = new Intent(context , ProductListActivity.class);
                    Order order = new Order();
                    order.customer = selectedCustomer;
                    myIntent.putExtra("Order",order);
                    //context.startActivity(myIntent);
                    ((Activity) context).startActivityForResult(myIntent, App.RequestCodeProductList);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.customers.size();
    }
}
