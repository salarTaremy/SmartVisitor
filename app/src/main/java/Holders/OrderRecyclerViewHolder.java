package Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import smart.visitor.ir.R;

public class OrderRecyclerViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener  {

    public TextView tv_OrderNo , tv_OrderDate , tv_OrderCustomerName ,tv_PaymentType,tv_Description,tv_Amount,tv_ServerResultID;
    public ImageView img_OrderItemUpload,img_OrderItemEdit,img_OrderItemDelete,img_IsSended,img_OrderItemLogo;
    private ItemClickListener itemClickListener;


    public OrderRecyclerViewHolder(@NonNull View v) {
        super(v);
        this.tv_OrderNo =  v.findViewById(R.id.tv_OrderNo);
        this.tv_OrderDate =  v.findViewById(R.id.tv_OrderDate);
        this.tv_OrderCustomerName =  v.findViewById(R.id.tv_OrderCustomerName);
        this.tv_PaymentType =  v.findViewById(R.id.tv_PaymentType);
        this.tv_Description =  v.findViewById(R.id.tv_Description);
        this.tv_Amount =  v.findViewById(R.id.tv_Amount);
        this.tv_ServerResultID =  v.findViewById(R.id.tv_ServerResultID);

        this.img_OrderItemLogo =  v.findViewById(R.id.img_OrderItemLogo);
        this.img_OrderItemUpload =  v.findViewById(R.id.img_OrderItemUpload);
        this.img_OrderItemEdit =  v.findViewById(R.id.img_OrderItemEdit);
        this.img_OrderItemDelete =  v.findViewById(R.id.img_OrderItemDelete);
        this.img_IsSended =  v.findViewById(R.id.img_IsSended);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
