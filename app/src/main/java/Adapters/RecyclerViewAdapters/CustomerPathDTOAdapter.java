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

import DataTransferObjects.CustomerPathDTO;
import Entities.Order;
import Holders.CustomerPathDTORecyclerViewHolder;
import Holders.ItemClickListener;
import Tools.App;
import smart.visitor.ir.Activity.CustomerPerPathActivity;
import smart.visitor.ir.R;

public class CustomerPathDTOAdapter extends RecyclerView.Adapter<CustomerPathDTORecyclerViewHolder> {
    private List<CustomerPathDTO> Lst;
    private Context context;
    public CustomerPathDTOAdapter(Context context, List<CustomerPathDTO> Lst ) {
        this.context = context;
        this.Lst = Lst;
    }
    @NonNull
    @Override
    public CustomerPathDTORecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_path, parent,false);
        return new CustomerPathDTORecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerPathDTORecyclerViewHolder holder, int i) {
        CustomerPathDTO obj = this.Lst.get(i);
        holder.tv_PathName.setText(obj.PathName);
        holder.tv_PathCount.setText( String.valueOf(obj.Count));
        //======================== Event Listener =============================================

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                CustomerPathDTO SelectedObj = Lst.get(position);
                    Intent myIntent = new Intent(context , CustomerPerPathActivity.class);
                    myIntent.putExtra("PathName",SelectedObj.PathName);
                    ((Activity) context).startActivityForResult(myIntent, App.RequestCodeProductList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.Lst.size();
    }
}
