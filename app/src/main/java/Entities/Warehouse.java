package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warehouse {
    @SerializedName("ID")
    @Expose
    public int ID;
    @SerializedName("Name")
    @Expose
    public String Name;


    public void setName(String name) {
        Name = name;
    }

    public void setId(Integer id) {
        ID = id;
    }
}