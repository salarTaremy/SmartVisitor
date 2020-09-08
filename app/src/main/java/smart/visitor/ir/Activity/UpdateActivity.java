package smart.visitor.ir.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.omega_r.libs.OmegaCenterIconButton;
import com.polyak.iconswitch.IconSwitch;

import java.util.ArrayList;
import java.util.List;

import smart.visitor.ir.R;

public class UpdateActivity extends BaseActivity {

    TextView tv_Header , tv_Detail;
    RatingBar ratingBar;
    OmegaCenterIconButton BtnConfirmContinueUpdate;
    ImageView img_Logo;
    IconSwitch sw_SwitchUpdateAcc,sw_SwitchUpdateAll,sw_SwitchUpdateOpenInvoice,sw_SwitchUpdateWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        InitView();
        SetListener();
    }

    private void SetListener() {
        this.sw_SwitchUpdateAll.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                if (current == IconSwitch.Checked.LEFT) // true
                {
                    sw_SwitchUpdateAcc.setChecked(current);
                    sw_SwitchUpdateOpenInvoice.setChecked(current);
                    sw_SwitchUpdateWarehouse.setChecked(current);
                }
            }
        });
        this.BtnConfirmContinueUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ip = "94.101.128.139";
                String Port = "100";
                String IMEI  = GetIMEI();
                List<Integer> Lst = GetSelectedUpdate();


                String Url ="http://"+Ip+":" + Port+ "/api/v1/fulldata/GetCompress/"+ IMEI +"?";
                    if (Lst.size()> 0 )
                    {
                        for (int UpdateID : Lst) {
                            Url +="listOfIds="+String.valueOf(UpdateID)+"&";
                        }
                    }else {
                        return;
                    }
                Url = Url.substring(0,Url.length()-1);

                Intent intent = new Intent(context, UpdateStatusActivity.class);
                intent.putExtra("Url",Url);
                intent.putExtra("Ip",Ip);
                intent.putExtra("Port",Port);
                startActivity(intent);
            }
        });
    }

    private List<Integer> GetSelectedUpdate() {
        List<Integer> Lst = new ArrayList<Integer>();
        if (sw_SwitchUpdateAll.getChecked() == IconSwitch.Checked.LEFT){
            Lst.add(1);
        }
        if (sw_SwitchUpdateAcc.getChecked() == IconSwitch.Checked.LEFT){
            Lst.add(2);
        }
        if (sw_SwitchUpdateOpenInvoice.getChecked() == IconSwitch.Checked.LEFT){
            Lst.add(3);
        }
        if (sw_SwitchUpdateWarehouse.getChecked() == IconSwitch.Checked.LEFT){
            Lst.add(4);
        }
        return  Lst;
    }

    private void InitView() {
        this.tv_Header = findViewById(R.id.tv_Header);
        this.tv_Detail = findViewById(R.id.tv_Detail);
        this.ratingBar = findViewById(R.id.ratingBar);
        this.img_Logo = findViewById(R.id.img_Logo);
        this.BtnConfirmContinueUpdate = findViewById(R.id.BtnConfirmContinueUpdate);

        this.sw_SwitchUpdateAll = findViewById(R.id.sw_SwitchUpdateAll);
        this.sw_SwitchUpdateAcc = findViewById(R.id.sw_SwitchUpdateAcc);
        this.sw_SwitchUpdateOpenInvoice = findViewById(R.id.sw_SwitchUpdateOpenInvoice);
        this.sw_SwitchUpdateWarehouse = findViewById(R.id.sw_SwitchUpdateWarehouse);

        this.tv_Header.setText(R.string.Update);
        this.tv_Detail.setText("");
        this.ratingBar.setVisibility( View.INVISIBLE);
        this.img_Logo.setImageResource(R.drawable.update_cloud3);
    }
}