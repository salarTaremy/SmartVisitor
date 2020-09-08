package Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import Tools.MyNumberPicker;
import smart.visitor.ir.R;

public class ProductRecyclerViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener  {


    public TextView tv_ProductCode , tv_ProductName , tv_ProductDescription ,tv_Tax,tv_Price;
    public ImageView Img ;
    private ItemClickListener itemClickListener;
    public MyNumberPicker numberPicker , numberPickerOffer;



    public ProductRecyclerViewHolder(@NonNull View v  ) {
        super(v);
        this.tv_ProductCode =  v.findViewById(R.id.tv_ProductCode);
        this.tv_ProductName = v.findViewById(R.id.tv_ProductName);
        this.tv_ProductDescription = v.findViewById(R.id.tv_ProductDescription);
        this.tv_Tax = v.findViewById(R.id.tv_Tax);
        this.tv_Price = v.findViewById(R.id.tv_Price);
        this.numberPicker = v.findViewById(R.id.np_Qty);
        this.numberPickerOffer = v.findViewById(R.id.np_Qty_offer);
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

