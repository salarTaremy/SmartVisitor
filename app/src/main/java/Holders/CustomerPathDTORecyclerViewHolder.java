package Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import smart.visitor.ir.R;

public class CustomerPathDTORecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    public TextView  tv_PathName, tv_PathCount ;
    private ItemClickListener itemClickListener;
    public CustomerPathDTORecyclerViewHolder(@NonNull View v) {
        super(v);
        this.tv_PathName =  v.findViewById(R.id.tv_PathName);
        this.tv_PathCount =  v.findViewById(R.id.tv_PathCount);
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
        return false;
    }
}
