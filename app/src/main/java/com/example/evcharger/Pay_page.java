package com.example.evcharger;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class Pay_page extends AppCompatActivity {
    private long mExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);
        RadioButton radioButton1 = (RadioButton)this.findViewById(R.id.wechatpay);
        RadioButton radioButton2 = (RadioButton)this.findViewById(R.id.alipay);

    }





}