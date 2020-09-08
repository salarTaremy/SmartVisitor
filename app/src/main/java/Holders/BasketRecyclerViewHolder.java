package Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import javax.sql.RowSetListener;

import Tools.MyNumberPicker;
import smart.visitor.ir.R;

public class BasketRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    
    public TextView tv_ProductCodeBasket , tv_ProductNameBasket , tv_ProductDetaiBasket ;
    public ImageView imgDeleteBasketItem ;
    public MyNumberPicker np_QtyBasket , np_Qty_offerBasket;
    public ConstraintLayout clDeleteBasketItem ;
    private ItemClickListener itemClickListener;


    public BasketRecyclerViewHolder(@NonNull View v  ) {
        super(v);
        this.tv_ProductCodeBasket =  v.findViewById(R.id.tv_ProductCodeBasket);
        this.tv_ProductNameBasket = v.findViewById(R.id.tv_ProductNameBasket);
        this.tv_ProductDetaiBasket = v.findViewById(R.id.tv_ProductDetaiBasket);
        this.np_QtyBasket = v.findViewById(R.id.np_QtyBasket);
        this.np_Qty_offerBasket = v.findViewById(R.id.np_Qty_offerBasket);
        this.imgDeleteBasketItem = v.findViewById(R.id.imgDeleteBasketItem);
        this.clDeleteBasketItem =  v.findViewById(R.id.clDeleteBasketItem);
        SetListener( getAdapterPosition());

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        this.np_QtyBasket.setFocusable(false);
        this.np_Qty_offerBasket.setFocusable(false);

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
            private void SetListener( final int i) {
                // can implement listener here
            }
        }
