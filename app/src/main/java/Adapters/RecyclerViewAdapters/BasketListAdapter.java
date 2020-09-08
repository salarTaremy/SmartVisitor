package Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Entities.Order;
import Entities.OrderItem;
import Holders.BasketRecyclerViewHolder;
import Holders.ItemClickListener;
import Tools.Convert;
import Tools.MyNumberPicker;
import smart.visitor.ir.Activity.BasketActivity;
import smart.visitor.ir.R;

public class BasketListAdapter extends RecyclerView.Adapter<BasketRecyclerViewHolder> {
    public Order order;
    Context context;
    public BasketListAdapter(Context context, Order order) {
        this.order = order;
        this.context = context;
    }
    @NonNull
    @Override
    public BasketRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basket, parent,false);
        return new BasketRecyclerViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull BasketRecyclerViewHolder holder, final int i) {
        final OrderItem obj = this.order.Items.get(i);
        holder.tv_ProductCodeBasket.setText(obj.ProductCode);
        holder.tv_ProductNameBasket.setText(obj.ProductName);
        String Rls = Convert.toSeprate(obj.Price * obj.Qty ) + " " +  context.getResources().getString(R.string.Rls);
        if (obj.Tax > 0 ){
            Rls +=" +" +String.valueOf(obj.Tax) + "%" ;
        }
        holder.tv_ProductDetaiBasket.setText(Rls );
        holder.np_Qty_offerBasket.SetIcon(R.mipmap.ic_gift);
        holder.np_Qty_offerBasket.SetBtnColor(R.color.blue);
        holder.np_QtyBasket .setValue(obj.Qty);
        holder.np_Qty_offerBasket.setValue(obj.Offer);
        //======================== Event Listener =============================================
        holder.clDeleteBasketItem.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Remove(i);
            }
        });
        holder.np_QtyBasket.setOnChangeListener(new MyNumberPicker.onChangeListener() {
            @Override
            public void onValueChange(int NewVal) {
                order.Items.get(i).Qty = NewVal;
                CheckForRemoveRow(i);
                ((BasketActivity)context).LoadFragment();
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
        holder.np_Qty_offerBasket.setOnChangeListener(new MyNumberPicker.onChangeListener() {
            @Override
            public void onValueChange(int NewVal) {
                order.Items.get(i).Offer = NewVal;
                CheckForRemoveRow(i);
                ((BasketActivity)context).LoadFragment();
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
                Toast.makeText(context, "Click " + obj.ProductName + " S", Toast.LENGTH_SHORT).show();
                if (isLongClick) {
                    Toast.makeText(context, "Long Click: " + obj.ProductName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Click " + obj.ProductName + " S", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void CheckForRemoveRow(int i) {
        if (order.Items.get(i).Qty <= 0   &&  order.Items.get(i).Offer<= 0 )
        {
            Remove(i);
        }
    }


    private void Remove(int i) {
        order.Items.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, order.Items.size());
        ((BasketActivity)context).LoadFragment();
        if (order.Items.size() == 0 ) {
            ((BasketActivity)context).finish();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return  this.order.Items.size();
        }catch (Exception e){
            return  0 ;
        }
    }
}
