package smart.visitor.ir.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.omega_r.libs.OmegaCenterIconButton;

import pl.droidsonroids.gif.GifImageView;
import smart.visitor.ir.R;

public class SuccessActivity extends BaseActivity {

    Context context;
    OmegaCenterIconButton Btn_SuccessAddOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        this.context = this;
        InitView();
        SetListener();
    }

    private void SetListener() {
        this.Btn_SuccessAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void InitView() {
        this.Btn_SuccessAddOrder  = findViewById(R.id.Btn_SuccessAddOrder);
    }

    public void test(View view) {

        GifImageView  gif =  findViewById(R.id.gif_updating);
        gif.setImageResource( R.drawable.loading_cloud1);
    }
}
