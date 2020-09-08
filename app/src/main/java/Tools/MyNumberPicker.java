package Tools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import smart.visitor.ir.R;

public class MyNumberPicker extends ConstraintLayout {

    private  int Icon  = 0 ;
    private View rootView;
    private TextView tv_Value ;
    private ImageView btn_Minus,btn_plus ;
    final String StrMin = "-";
    final String StrPlus = "+";
    private boolean IsFromSetter = false;
    Context context;

    public MyNumberPicker(Context context) {
        super(context);
        init(context);
        SetListener();
    }
    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        SetListener();
    }

    public  void setFocusable(boolean value){
        this.tv_Value.setFocusable(value);
    }

    private  void SetVisibility(){
        int val = getValue();
        if(val  == 0) {
            tv_Value.setVisibility(INVISIBLE);
            btn_plus.setVisibility(INVISIBLE);
            if (this.Icon == 0 ){
                btn_Minus.setImageResource(R.mipmap.ic_add_circle);
            }else {
                btn_Minus.setImageResource(this.Icon);
            }

        }else  {

            if (  tv_Value.getVisibility() != VISIBLE  ){
                tv_Value.setVisibility(VISIBLE);
                //tv_Value.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide));
            }
            if (  btn_plus.getVisibility() != VISIBLE  ){
                btn_plus.setVisibility(VISIBLE);
                //btn_plus.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide));
                btn_Minus.setImageResource(R.mipmap.ic_min_circle);
            }

        }
    }


    private void SetListener() {
        this.btn_Minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = getValue();
                if(val  == 0) {
                    Plus(1);
                }else {
                    Minus(1);
                }
                SetVisibility();
            }
        });
        this.btn_plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Plus(1);
                SetVisibility();
            }
        });
        tv_Value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int value ;
                if (   s.toString() == null || s.toString().isEmpty() || s.toString().equals( "")   ){
                    value = 0 ;
                    tv_Value.setText("0");
                }else {
                    value = Integer.parseInt( s.toString());
                }

                if (IsFromSetter){
                    onChangeListener.onValueSet(value);
                }else {
                    onChangeListener.onValueChange(value);
                }
                SetVisibility();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    private  void Plus(int Newvalue){
        int val = Integer.parseInt(tv_Value.getText().toString());
        tv_Value.setText(String.valueOf(val+Newvalue));
        this.onChangeListener.onValuePlus(val, val+Newvalue);
        //this.onChangeListener.onValueChange(val+Newvalue);
    }
    private  void Minus(int Newvalue){
        int val = Integer.parseInt(tv_Value.getText().toString());
        tv_Value.setText(String.valueOf(val-Newvalue));
        this.onChangeListener.onValueMinus(val, val-Newvalue);
        //this.onChangeListener.onValueChange( val-Newvalue);
    }


    public int getValue() {

        CharSequence s = tv_Value.getText();
        int value ;
        if (   s.toString() == null || s.toString().isEmpty() || s.toString().equals( "")   ){
            value = 0 ;
        }else {
            value = Integer.parseInt( s.toString());
        }
         return value;
    }
    public void setValue(int value) {
        this.IsFromSetter = true;
        this.tv_Value.setText(String.valueOf(value));
        SetVisibility();
        this.IsFromSetter = false;
    }
    private void init(Context context) {
        this.context = context;
        rootView = inflate(context, R.layout.vw_number_picker, this);
        tv_Value = findViewById(R.id.tv_Value);
        btn_Minus = findViewById(R.id.btn_Minus);
        btn_plus = findViewById(R.id.btn_plus);

        btn_Minus.setImageResource(R.mipmap.ic_add_circle);
        btn_plus.setImageResource(R.mipmap.ic_add_circle);


        this.tv_Value.setVisibility(GONE);
        this.btn_plus.setVisibility(GONE);

    }

    public void setOnChangeListener( onChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    private  onChangeListener onChangeListener = new onChangeListener() {
        @Override
        public void onValueChange( int NewVal) {

        }

        @Override
        public void onValueSet(int NewVal) {

        }

        @Override
        public void onValuePlus(int OldVal, int NewVal) {

        }

        @Override
        public void onValueMinus(int OldVal, int NewVal) {

        }
    };

    public interface onChangeListener{
        void onValueChange (int NewVal);
        void onValueSet (int NewVal);
        void onValuePlus (int OldVal , int NewVal);
        void onValueMinus (int OldVal , int NewVal);
    }


    public  void SetBtnColor(int color){
        //this.btn_Minus.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
        this.btn_Minus.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
        this.btn_plus.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    public  void SetPlusBtnColor(int color){
        this.btn_plus.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    public  void SetMinBtnColor(int color){
        this.btn_Minus.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    public  void SetTextColor(int color){
        this.tv_Value.setTextColor(color);
    }
    public  void SetIcon(int ResID){
        this.Icon = ResID;
    }


}
