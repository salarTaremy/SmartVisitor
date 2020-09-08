package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inventory {

    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("ID_Warehouse")
    @Expose
    public int ID_Warehouse;
    @SerializedName("ID_Product")
    @Expose
    public long ID_Product;
    @SerializedName("Quantity")
    @Expose
    public int Quantity;


}