package Entities;


import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Databases.CustomerDbHelper;
import Databases.OrderIDbHelper;
import Databases.OrderItemDbHelper;

public class Order  implements Serializable {
    public Order(){
        this.Items = new ArrayList<OrderItem>();
    }

    @SerializedName("UUID")
    @Expose
    public String UUID;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("ID_Customer")
    @Expose
    public int ID_Customer;

    @SerializedName("ID_PaymentType")
    @Expose
    public int ID_PaymentType;

    @SerializedName("ID_OrderType")
    @Expose
    public int ID_OrderType;

    @SerializedName("ClientDate")
    @Expose
    public int ClientDate;

    @SerializedName("ClientTime")
    @Expose
    public int ClientTime;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Items")
    @Expose
    public List<OrderItem> Items;

    @SerializedName("customer")
    @Expose
    public  Customer customer;

    @SerializedName("Latitude")
    @Expose
    public  double Latitude;

    @SerializedName("Longitude")
    @Expose
    public  double Longitude;

    public  int ID_ServerResult;

    public List<MyCellInfo> MyCells;

    public Customer getCustomer(Context context) {
        try {
            if (this.customer != null){
                return customer;
            }else if ( ID_Customer != 0 ){
                customer = new CustomerDbHelper(context).find(ID_Customer);
                return  customer;
            }else{
                return  customer;
            }
        }catch (Exception ex){
            return  null;
        }
    }

    public List<OrderItem> getItems(Context context) {
        try {
            if (this.Items != null && Items.size()>0 ){
                return Items;
            }else if ( this.id != 0 ){
                this.Items = new OrderItemDbHelper(context).findByIDOrder(this.id);
                return  Items;
            }else{
                return  Items;
            }
        }catch (Exception ex){
            return  null;
        }
    }
}
