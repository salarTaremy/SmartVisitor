package DataTransferObjects;

import java.io.Serializable;

import Entities.Order;

public class OrderDTO
        extends Order
        implements Serializable {

    public String CustomerName;
    public String PaymentType;
    public String PersianDate;
    public long Amount;
    public long Offer;
    public long Tax;
}
