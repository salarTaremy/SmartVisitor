package Api;

import java.util.List;

import Entities.Order;
import Entities.OrderResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IOrderAPI {


    @POST("Order/Create/{IMEI}")
    Call<OrderResult> Create(@Path("IMEI")String IMEI  , @Body Order product);

}
