package Holders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import smart.visitor.ir.R;

public class CustomerRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    public TextView tv_CustomerName,tv_CustomerCode,tv_CustomerDescription;
    private ItemClickListener itemClickListener;
    public CustomerRecyclerViewHolder(@NonNull View v) {
        super(v);
        this.tv_CustomerCode =  v.findViewById(R.id.Tv_CustomerCode);
        this.tv_CustomerName =  v.findViewById(R.id.Tv_CustomerName);
        this.tv_CustomerDescription=  v.findViewById(R.id.tv_CustomerDescription);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener icl) {
        this.itemClickListener = icl;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}
