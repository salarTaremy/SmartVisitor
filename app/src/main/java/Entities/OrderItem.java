package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderItem   implements Serializable {


    public int ID ;
    public int ID_order ;

    @SerializedName("ID_Product")
    @Expose
    public Long ID_Product ;

    public String ProductCode ;
    public String ProductName ;
    @SerializedName("Qty")
    @Expose
    public int Qty ;

    @SerializedName("Offer")
    @Expose
    public int Offer ;

    @SerializedName("Price")
    @Expose
    public int Price ;

    @SerializedName("Tax")
    @Expose
    public int Tax ;
    public boolean IsChecked;


    public OrderItem() {

    }
    public OrderItem(int ID, int ID_order,Product product,int Price, int Tax) {
        this.ID = ID;
        this.ID_order = ID_order;
        this.ID_Product = product.id;
        this.ProductCode = product.code;
        this.ProductName = product.name;
        this.Qty = product.SelectedCount;
        this.Offer = product.SelectedOffer;
        this.Price = Price;
        this.Tax =Tax ;
    }


}
