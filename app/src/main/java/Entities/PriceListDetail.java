package Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceListDetail implements Serializable {

    @SerializedName("ID_PriceList")
    @Expose
    public int ID_PriceList;
    @SerializedName("ID_Product")
    @Expose
    public long ID_Product;
    @SerializedName("Price")
    @Expose
    public int Price;
    @SerializedName("ConsumerPrice")
    @Expose
    public int ConsumerPrice;
    @SerializedName("Tax")
    @Expose
    public int Tax;
    @SerializedName("Duration")
    @Expose
    public int Duration;
    @SerializedName("ID")
    @Expose
    public int ID;

    public void setID_PriceList(Integer ID_PriceList) {
        this.ID_PriceList = ID_PriceList;
    }

    public void setiD_Product(Integer iD_Product) {
        this.ID_Product = iD_Product;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public void setConsumerPrice(Integer consumerPrice) {
        ConsumerPrice = consumerPrice;
    }

    public void setTax(Integer tax) {
        Tax = tax;
    }

    public void setDuration(Integer duration) {
        Duration = duration;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}