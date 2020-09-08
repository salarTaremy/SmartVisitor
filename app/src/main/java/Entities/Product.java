package Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("ID")
    @Expose
    public long id;
    @SerializedName("Code")
    @Expose
    public String code;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("IdBrand")
    @Expose
    public int idBrand;
    @SerializedName("IdGroup")
    @Expose
    public int idGroup;
    @SerializedName("Count")
    @Expose
    public int count;
    @SerializedName("CountInBox")
    @Expose
    public int countInBox;
    @SerializedName("Weight")
    @Expose
    public int weight;
    @SerializedName("Detail")
    @Expose
    public String detail;
    @SerializedName("rate")
    @Expose
    public int rate;

    public int SelectedCount;
    public int SelectedOffer;



    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getCountInBox() {
        return countInBox;
    }

    public void setCountInBox(Integer countInBox) {
        this.countInBox = countInBox;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

}
