package Entities;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyVisitPlanDetail {

    @SerializedName("iD_Plan")
    @Expose
    public int ID_Plan;
    @SerializedName("iD_Customer")
    @Expose
    public int ID_Customer;
    @SerializedName("id")
    @Expose
    public int ID;

}