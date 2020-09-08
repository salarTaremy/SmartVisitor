package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceList  implements Serializable {


    public PriceList() {
        this.Details = new ArrayList<PriceListDetail>();
    }
    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("BeginDate")
    @Expose
    public int BeginDate;

    @SerializedName("EndDate")
    @Expose
    public int EndDate;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Details")
    @Expose
    public List<PriceListDetail> Details ;




}
