package smart.visitor.ir.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import Tools.App;
import smart.visitor.ir.R;
import smart.visitor.ir.ui.Basket.BasketRlsFragment;
import smart.visitor.ir.ui.Customer.FragmentCustomerAll;
import smart.visitor.ir.ui.Customer.FragmentCustomerPath;

public class CustomerPerPathActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_per_path);
        LoadFragment();
    }
    public void LoadFragment() {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String PathName = extras.getString("PathName");
            FragmentManager fm = getSupportFragmentManager();
            FragmentCustomerAll fragment = new FragmentCustomerAll(PathName);
            fm.beginTransaction().replace(R.id.fragmentCusPerPath , fragment).commit();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == App.RequestCodeProductList) {
            if(resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
