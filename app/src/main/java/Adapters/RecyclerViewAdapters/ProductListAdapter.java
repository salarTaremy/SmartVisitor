package Adapters.RecyclerViewAdapters;


import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import DataTransferObjects.ProductDTO;
import Entities.Product;
import Holders.ItemClickListener;
import Holders.ProductRecyclerViewHolder;

import Tools.App;
import Tools.Convert;
import Tools.MyNumberPicker;
import smart.visitor.ir.Activity.ProductListActivity;
import smart.visitor.ir.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductRecyclerViewHolder> {
    public List<ProductDTO> products;
    Context context;
    public ProductListAdapter(Context context, List<ProductDTO> lst) {
        this.products = lst;
        this.context = context;
    }


    @NonNull
    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,  final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent,false);
        return new ProductRecyclerViewHolder(view );
    }
    @Override
    public void onBindViewHolder(@NonNull final ProductRecyclerViewHolder holder,  final int i) {
        final ProductDTO obj = products.get(i);
            //String Duration = " "+ Convert.toSeprate(obj.Duration) + " " +  context.getResources().getString(R.string.Day) ;
            String Rls = Convert.toSeprate(obj.Price) + " " +  context.getResources().getString(R.string.Rls);
            holder.tv_ProductCode.setText(obj.code);
            holder.tv_ProductName.setText(obj.name);
            holder.tv_ProductDescription.setText(obj.detail );
            holder.tv_Price.setText(Rls );
            holder.numberPickerOffer.SetIcon(R.mipmap.ic_gift);
            holder.numberPickerOffer.SetBtnColor(R.color.blue);
            holder.numberPicker.setValue((int) App.isNull(obj.SelectedCount,0));
            holder.numberPickerOffer.setValue((int) App.isNull(obj.SelectedOffer,0));
            if(obj.Tax > 0) {
                holder.tv_Tax.setVisibility(View.VISIBLE);
                holder.tv_Tax.setText(  "+" + String.valueOf(obj.Tax) + "%");
            }else {
                holder.tv_Tax.setVisibility(View.INVISIBLE);
            }
        //======================== Event Listener =============================================
            holder.numberPicker.setOnChangeListener(new MyNumberPicker.onChangeListener() {
                @Override
                public void onValueChange( int NewVal) {
                    products.get(i).SelectedCount = NewVal;
                    ((ProductListActivity) context).DoChange();
                }

                @Override
                public void onValueSet(int NewVal) {
                }

                @Override
                public void onValuePlus(int OldVal, int NewVal) {
                }

                @Override
                public void onValueMinus(int OldVal, int NewVal) {
                }
            });
            holder.numberPickerOffer.setOnChangeListener(new MyNumberPicker.onChangeListener() {
                @Override
                public void onValueChange(int NewVal) {
                    products.get(i).SelectedOffer = NewVal;
                    ((ProductListActivity) context).DoChange();
                }

                @Override
                public void onValueSet(int NewVal) {
                }

                @Override
                public void onValuePlus(int OldVal, int NewVal) {
                }

                @Override
                public void onValueMinus(int OldVal, int NewVal) {
                }
            });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return products.size();
    }
}
