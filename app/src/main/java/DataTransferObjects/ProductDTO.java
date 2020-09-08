package DataTransferObjects;



import java.io.Serializable;

import Entities.PriceListDetail;
import Entities.Product;

public class ProductDTO extends Product implements Serializable {


    public int Price;
    public int ConsumerPrice;
    public int Tax;
    public int Duration;

}
