package smart.visitor.ir.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omega_r.libs.OmegaCenterIconButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Databases.GeneralDbHelper;
import Databases.OrderCellInfoDbHelper;
import Databases.OrderIDbHelper;
import Databases.OrderItemDbHelper;
import Entities.Calendar;
import Entities.MyCellInfo;
import Entities.Order;
import Entities.OrderCellInfoViewModel;
import Entities.OrderItem;
import Tools.App;
import Tools.Logcat;
import Tools.MessageBox;
import Tools.MyAnimation;
import Tools.MyDateTime;
import info.hoang8f.android.segmented.SegmentedGroup;
import smart.visitor.ir.R;
import smart.visitor.ir.ui.Basket.BasketRlsFragment;

public class PaymentTypeActivity extends BaseActivity {
    Context context;
    Order order;
    OmegaCenterIconButton Btn_FinalConfirm;
    TextView tv_CustomerName,tv_CustomerDetail,tv_Calendar;
    ImageView img_OrderType,img_PaymentType;
    SegmentedGroup segmented_Payment,segmented_OrderType;
    RadioButton rb_PayTrust,rb_PayCash,rb_PayCheque,rb_Other,rb_Sale,rb_NotSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);
        this.context = this;
        this.order= (Order) getIntent().getSerializableExtra("Order");
        IntView();
        SetListener();
        LoadFragment();
        SetItems();
    }

    private void SetItems() {
        GeneralDbHelper db = new GeneralDbHelper(context);
        Calendar calendar =  db.GetCalendar();
        String StrDate =  calendar.DayTitle + " " + calendar.DayName + " " + calendar.MonthName + " ماه " + calendar.Pr_Year;
        this.tv_CustomerName.setText( this.order.customer.name);
        this.tv_CustomerDetail.setText(getString(R.string.AddNewOrder));
        this.tv_Calendar.setText(StrDate);
        // If Is Fpr Edit :
        if (order.id > 0 ){
          SetOrderType();
          SetPaymentType();
        }
    }

    private void IntView() {
        this.segmented_Payment =  findViewById(R.id.segmented_Payment);
        this.segmented_OrderType =  findViewById(R.id.segmented_OrderType);
        this.img_OrderType =  findViewById(R.id.img_OrderType);
        this.img_PaymentType =  findViewById(R.id.img_PaymentType);
        this.Btn_FinalConfirm =  findViewById(R.id.Btn_FinalConfirm);
        this.tv_CustomerDetail =  findViewById(R.id.tv_CustomerDetail);
        this.tv_CustomerName =  findViewById(R.id.tv_CustomerName);
        this.tv_Calendar =  findViewById(R.id.tv_Calendar);
        this.rb_PayTrust =  findViewById(R.id.rb_PayTrust);
        this.rb_PayCash =  findViewById(R.id.rb_PayCash);
        this.rb_PayCheque =  findViewById(R.id.rb_PayCheque);
        this.rb_Other =  findViewById(R.id.rb_Other);
        this.rb_Sale =  findViewById(R.id.rb_Sale);
        this.rb_NotSale =  findViewById(R.id.rb_NotSale);
    }

    private void SetListener() {
        segmented_Payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (findViewById(checkedId)  ==  findViewById(R.id.rb_PayCash) )
                {
                    //MyAnimation.ChangeImageViewSrc( context , img_PaymentType ,R.drawable.pay03);
                }
                if (findViewById(checkedId)  ==  findViewById(R.id.rb_PayCheque) )
                {
                    //MyAnimation.ChangeImageViewSrc( context , img_PaymentType ,R.drawable.pay02);
                }
                if (findViewById(checkedId)  ==  findViewById(R.id.rb_PayTrust) )
                {
                    //MyAnimation.ChangeImageViewSrc( context , img_PaymentType ,R.drawable.pay01);
                }
            }
        });

        segmented_OrderType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (findViewById(checkedId)  ==  findViewById(R.id.rb_Sale) )
                {
                    //MyAnimation.ChangeImageViewSrc( context , img_OrderType ,R.drawable.sale,android.R.anim.slide_in_left ,android.R.anim.slide_out_right);
                }
                if (findViewById(checkedId)  ==  findViewById(R.id.rb_NotSale) )
                {
                    //MyAnimation.ChangeImageViewSrc( context , img_OrderType ,R.drawable.not_sale,android.R.anim.slide_in_left ,android.R.anim.slide_out_right);
                }
            }
        });
        this.Btn_FinalConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Save())
                {
                    Intent intent = new Intent(context, SuccessActivity.class);
                    intent.putExtra("Order",order);
                    startActivityForResult(intent,App.RequestCodeSuccess);
                }
                else{
                    String Title = context.getResources().getString(R.string.CreateOrder);
                    String Msg = context.getResources().getString(R.string.ErrorInCreateOrder);
                    new MessageBox(context).ShowError(Title,Msg);
                }
            }
        });
    }
    public void LoadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        BasketRlsFragment fragment = new BasketRlsFragment(this.order);
        fm.beginTransaction().replace(R.id.fragmentContainerBasket , fragment).commit();
    }
    private Integer GetOrderType() {
        if (rb_Sale.isChecked()) {
            return 1;
        }
        if (rb_NotSale.isChecked()) {
            return 2;
        }
        if (rb_Other.isChecked()) {
            return 3;
        }
        return 0;
    }
    private void SetOrderType() {
        if (  order.ID_OrderType  == 1 ) {
            rb_Sale.setChecked(true);
        }
        if (order.ID_OrderType  == 2 ) {
            rb_NotSale.setChecked(true);
        }
        if (order.ID_OrderType  == 3 ) {
            rb_Other.setChecked(true);
        }
    }
    private Integer GetPaymentType() {
        if (rb_PayTrust.isChecked()) {
            return 3;
        }
        if (rb_PayCash.isChecked()) {
            return 1;
        }
        if (rb_PayCheque.isChecked()) {
            return 2;
        }
        return 0;
    }
    private void SetPaymentType() {
        if ( order.ID_PaymentType == 3) {
            rb_PayTrust.setChecked(true);

        }
        if (order.ID_PaymentType == 1) {
            rb_PayCash.setChecked(true);
        }
        if (order.ID_PaymentType == 2) {
            rb_PayCheque.setChecked(true);
        }
    }
    private boolean Save() {
        try {
            OrderIDbHelper odb = new OrderIDbHelper(context);
            OrderItemDbHelper idb = new OrderItemDbHelper(context);
            MessageBox MsgBox = new MessageBox(context);
            String Title,Msg;
            boolean IsNewOrder = this.order.id == 0;


            if (IsNewOrder){
                Title=context.getResources().getString(R.string.CreateOrder);
            }else {
                Title = context.getResources().getString(R.string.EditOrder);
            }
            order.ID_PaymentType = GetPaymentType();
            order.ID_OrderType = GetOrderType();


            if (IsNewOrder){
                order.id = odb.Max("id") + 1;
                order.ClientTime = Integer.valueOf(MyDateTime.CurrentTime());
                order.ClientDate = new  GeneralDbHelper(this).GetCalendar().Persian_Date ;
                if( android.os.Build.VERSION.SDK_INT >=   Build.VERSION_CODES.JELLY_BEAN_MR2){
                    order.MyCells =  GetMyCells();
                }
            } else {
                if (idb.deleteByIDORder( order.id)){
                    Logcat.i("Items of Order Deleted");
                    if (odb.delete(order.id)){
                        Logcat.i("Order Deleted");
                    }else {
                        Logcat.e("Error Delete Order Header ");
                        Msg = context.getResources().getString(R.string.ErrorInEditOrder);
                        MsgBox.ShowError(Title,Msg);
                        return  false;
                    }
                }
                else {
                    Logcat.e("Error Delete Order Items ");
                    Msg = context.getResources().getString(R.string.ErrorInEditOrder);
                    MsgBox.ShowError(Title,Msg);
                    return  false;
                }
            }

            if (odb.create(order)) {
                for (OrderItem it : order.Items) {
                    it.ID_order = order.id;
                    if (idb.create(it)) {
                        //code
                    } else {
                        return false;
                    }
                }
                if (IsNewOrder){
                    OrderCellInfoDbHelper cdb = new OrderCellInfoDbHelper(context);
                    for (MyCellInfo Cell : order.MyCells) {
                        if (cdb.create( new OrderCellInfoViewModel( order.id , Cell  ))) {
                            //code
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == App.RequestCodeSuccess){
            if (resultCode == Activity.RESULT_OK){
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED){
                //Toast.makeText(this,"CANCELED" ,   Toast.LENGTH_LONG ).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public List<MyCellInfo> GetMyCells()
    {
        try {
            final TelephonyManager telMgr = (TelephonyManager)
                    getSystemService(this.TELEPHONY_SERVICE);


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            List<MyCellInfo> MyCells = new ArrayList<MyCellInfo>();
            List<CellInfo> cellLocation = telMgr.getAllCellInfo();


            if (cellLocation == null){
                return  MyCells;
            }

            Iterator<CellInfo> i = cellLocation.iterator();
            while (i.hasNext()) {
                CellInfo c = i.next();
                if (c.isRegistered() == false) {
                    i.remove();
                }
            }

            for (CellInfo item : cellLocation) {

                if (item.getClass() == CellInfoLte.class) {
                    CellIdentityLte Idn =  ((CellInfoLte) item).getCellIdentity();
                    MyCells.add(new MyCellInfo(Idn.toString(), item.isRegistered() , Idn.getMcc(),Idn.getMnc(),Idn.getCi(), Idn.getTac()  ));
                }
                else if (item.getClass() == CellInfoWcdma.class) {
                    CellIdentityWcdma Idn =  ((CellInfoWcdma) item).getCellIdentity();
                    MyCells.add(new MyCellInfo(Idn.toString(), item.isRegistered() , Idn.getMcc(),Idn.getMnc(),Idn.getCid(), Idn.getLac() ));
                }
                else if (item.getClass() == CellInfoGsm.class) {
                    CellIdentityGsm Idn =  ((CellInfoGsm) item).getCellIdentity();
                    MyCells.add(new MyCellInfo(Idn.toString(), item.isRegistered() , Idn.getMcc(),Idn.getMnc(),Idn.getCid(), Idn.getLac() ));
                }
                else{
                    MyCells.add(new MyCellInfo("unknown", item.isRegistered() , 0,0,0,0));
                }
            }
            return  MyCells;
        } catch (Exception e ){
            Logcat.e( "Error Reading Cells Info  : "  +  e.getMessage());
            return  null;
        }
    }

}
