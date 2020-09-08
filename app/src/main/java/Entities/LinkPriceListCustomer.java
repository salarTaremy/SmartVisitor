package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkPriceListCustomer {
    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("ID_PriceList")
    @Expose
    public int ID_PriceList;
    @SerializedName("ID_CustomerType")
    @Expose
    public int ID_CustomerType;
    @SerializedName("ID_CustomerGroup")
    @Expose
    public int ID_CustomerGroup;
}


