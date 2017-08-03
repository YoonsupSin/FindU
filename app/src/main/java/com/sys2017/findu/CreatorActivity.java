package com.sys2017.findu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class CreatorActivity extends AppCompatActivity {

    LinearLayout linearLayout_design;
    LinearLayout linearLayout_programming;

    LinearLayout linearLayout_OHS;
    LinearLayout linearLayout_SYS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    public void ClickBtn(View v){

    }

}
