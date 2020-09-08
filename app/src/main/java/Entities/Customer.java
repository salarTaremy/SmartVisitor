package Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("ID")
    @Expose
    public int id;

    @SerializedName("Code")
    @Expose
    public String code;

    @SerializedName("Name")
    @Expose
    public String name;

    @SerializedName("F_Name")
    @Expose
    public String fName;

    @SerializedName("L_name")
    @Expose
    public String lName;

    @SerializedName("IdType")
    @Expose
    public int idType;

    @SerializedName("IdGroup")
    @Expose
    public int idGroup;

    @SerializedName("Tel")
    @Expose
    public String tel;

    @SerializedName("Mobile")
    @Expose
    public String mobile;

    @SerializedName("Mail")
    @Expose
    public String mail;

    @SerializedName("OpenInvoice")
    @Expose
    public int openInvoice;

    @SerializedName("Debt")
    @Expose
    public Long debt;

    @SerializedName("avgDate")
    @Expose
    public int avgDate;

    @SerializedName("rate")
    @Expose
    public int rate;

    @SerializedName("Description")
    @Expose
    public String description;

    @SerializedName("Address")
    @Expose
    public String address;


    @SerializedName("PathName")
    @Expose
    public String PathName;


    @SerializedName("y")
    @Expose
    public String Latitude;

    @SerializedName("x")
    @Expose
    public String Longitude;



    public int getId() {
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

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getOpenInvoice() {
        return openInvoice;
    }

    public void setOpenInvoice(Integer openInvoice) {
        this.openInvoice = openInvoice;
    }

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public int getAvgDate() {
        return avgDate;
    }

    public void setAvgDate(Integer avgDate) {
        this.avgDate = avgDate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}