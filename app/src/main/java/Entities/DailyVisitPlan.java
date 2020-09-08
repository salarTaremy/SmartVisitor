package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyVisitPlan {

    @SerializedName("iD_Visitor")
    @Expose
    public int ID_Visitor;
    @SerializedName("prDay")
    @Expose
    public int PrDay;
    @SerializedName("dailyVisitPlanDetails")
    @Expose
    public List<DailyVisitPlanDetail> DailyVisitPlanDetails = null;
    @SerializedName("id")
    @Expose
    public int ID;
}
