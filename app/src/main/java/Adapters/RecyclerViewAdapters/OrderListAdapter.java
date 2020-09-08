package Adapters.RecyclerViewAdapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.text.MessageFormat;
import java.util.List;

import Api.APIClient;
import Api.IOrderAPI;
import DataTransferObjects.OrderDTO;
import Databases.OrderIDbHelper;
import Databases.OrderItemDbHelper;
import Entities.Order;
import Entities.OrderItem;
import Entities.OrderResult;
import Entities.PriceList;
import Holders.ItemClickListener;
import Holders.OrderRecyclerViewHolder;
import Tools.App;
import Tools.Convert;
import Tools.Logcat;
import Tools.MessageBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smart.visitor.ir.Activity.BaseActivity;
import smart.visitor.ir.Activity.BasketActivity;
import smart.visitor.ir.Activity.ProductListActivity;
import smart.visitor.ir.R;

public class OrderListAdapter   extends RecyclerView.Adapter<OrderRecyclerViewHolder>{


    private List<OrderDTO> Orders;
    private Context context;
    private RecyclerView.Adapter CurentAdpt;

    public OrderListAdapter( Context context, List<OrderDTO> orders) {
        this.context = context;
        Orders = orders;
        CurentAdpt = this;
    }

    @NonNull
    @Override
    public OrderRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order, null);
        return new OrderRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerViewHolder holder,  final int position) {
        final OrderIDbHelper db = new OrderIDbHelper(context);
        final OrderDTO order = Orders.get(position);
        holder.tv_OrderNo.setText(String.valueOf(order.id));
        holder.tv_OrderDate.setText(order.PersianDate);
        holder.tv_OrderCustomerName.setText(order.CustomerName);
        holder.tv_PaymentType.setText(order.PaymentType);
        holder.tv_Description.setText(order.Description);
        holder.tv_Amount.setText(Convert.toSeprate( order.Amount));
        if (order.ID_OrderType == 1 ){
            holder.img_OrderItemLogo.setBackgroundResource (R.drawable.cercle_background_blue);
        }else {
            holder.img_OrderItemLogo.setBackgroundResource(R.drawable.cercle_background_pink);
        }
        if (order.ID_ServerResult == 0  ){
            holder.img_IsSended.setVisibility(View.INVISIBLE);
            holder.img_OrderItemUpload.setVisibility(View.VISIBLE);
            holder.tv_ServerResultID.setVisibility(View.INVISIBLE);
        }else {
            holder.img_IsSended.setVisibility(View.VISIBLE);
            holder.img_OrderItemUpload.setVisibility(View.INVISIBLE);
            holder.img_OrderItemEdit.setVisibility(View.INVISIBLE);
            holder.tv_ServerResultID.setVisibility(View.VISIBLE);
            String Msg = MessageFormat.format("{0}" , order.ID_ServerResult);
            holder.tv_ServerResultID.setText(Msg);
        }
        holder.img_OrderItemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.getCustomer(context);
                order.getItems(context);
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("Order", order);
                ((BaseActivity)context).startActivityForResult(intent, App.RequestCodeBasket);
            }
        });


        holder.img_OrderItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessageBox.OnYesClickListener yesClickListener = new MessageBox.OnYesClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean IsDeleteHeader = false;
                        boolean IsDeleteItems = new OrderItemDbHelper(context).deleteByIDORder(order.id);
                        if(IsDeleteItems){
                            IsDeleteHeader  = db.delete(order.id);
                        }
                        if (IsDeleteItems && IsDeleteHeader){
                            Orders.remove(position);
                            //recycler.removeViewAt(position);
                            CurentAdpt.notifyItemRemoved(position);
                            CurentAdpt.notifyItemRangeChanged(position, Orders.size());
                            //CurentAdpt.notifyDataSetChanged();
                        }else {
                            String Title = context.getResources().getString(R.string.DeleteOrder);
                            String Msg = context.getResources().getString(R.string.ErrorInDeleteOrder);
                             new MessageBox(context).ShowError(Title,Msg);
                        }
                    }
                };
                String Title = context.getResources().getString(R.string.DeleteOrder);
                String Msg = MessageFormat.format(context.getResources().getString(R.string.AreYouSoureToDeleteDarkhast),order.CustomerName);
                new MessageBox(context).ShowQuestion(Title,Msg,yesClickListener);
            }
        });
        holder.img_OrderItemUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Waiting :
                final MessageBox msg = new MessageBox(context);
                String Title = context.getResources().getString(R.string.InProgres);
                String Msg = context.getResources().getString(R.string.PleaceWait);
                msg.ShowWaiting(Title,Msg);
                //Sending Request:
                BaseActivity activity = (BaseActivity)context;
                IOrderAPI Api = APIClient.getClient().create(IOrderAPI.class);
                Call<OrderResult> ApiCall =  Api.Create(activity.GetIMEI(),order );
                order.Items =  new OrderItemDbHelper(context).findByIDOrder(order.id);
                ApiCall.enqueue(new Callback<OrderResult>() {
                    @Override
                    public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                        OrderResult result = response.body();
                        if (result.IsSuccess){
                            Logcat.i(   "order: "+ result.OrderID + " saved");
                            if (db.updateServerResultID( order.id , result.OrderID)){
                                Orders.get(position).ID_ServerResult = result.OrderID;
                                CurentAdpt.notifyDataSetChanged();
                            }
                            //String Title = context.getResources().getString(R.string.Success);
                            //String Msg = MessageFormat.format(context.getResources().getString(R.string.OrdeSavedInserver),order.ID_ServerResult);
                            //new MessageBox(context).ShowOk(Title,Msg);
                        } else {
                            String Title = context.getResources().getString(R.string.SendFailed);
                            new MessageBox(context).ShowError(Title , result.Message);
                        }
                        msg.dismiss();
                    }
                    @Override
                    public void onFailure(Call<OrderResult> call, Throwable t) {
                        msg.dismiss();
                        String Title = context.getResources().getString(R.string.ServerNotResponse);
                        new MessageBox(context).ShowError(Title , t.getMessage());
                        Logcat.i("onFailure");
                    }
                });
            }
        });



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view,  final int position, boolean isLongClick) {
                    //click Item
            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.Orders == null){
            return 0;
        }else {
            return this.Orders.size() ;
        }

    }
}
