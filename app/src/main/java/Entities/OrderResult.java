package Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResult {

    @SerializedName(  value ="IsSuccess" ,alternate = { "isSuccess"  })
    @Expose
    public boolean IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("OrderID")
    @Expose
    public int OrderID;

}
