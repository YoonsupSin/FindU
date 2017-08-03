package com.sys2017.findu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgView_newStart;
    ImageView imgView_continue;
    ImageView imgView_setting;

    ImageView imgView_newStart2;
    ImageView imgView_continue2;
    ImageView imgView_setting2;

    //설정 눌렀을때...
    LinearLayout setting;          boolean isSettingClick = true;
    ToggleButton toggleButton_bgm;
    ToggleButton toggleButton_fx;
    ToggleButton toggleButton_vibration;
    ImageView imageView_scale;
    ImageView imageView_close;

    ImageView imgView_menu;     boolean isMenuClick = true;

    Bitmap[] bitmaps_OnOff;
    Bitmap[] bitmaps_Scale;


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();

        sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);

        imgView_menu = ( ImageView ) findViewById(R.id.imageView_logo_Menu);

        //게임시작, 계속하기, 설정 아이디들.
        imgView_newStart = ( ImageView ) findViewById(R.id.imageView_logo_newStart);     imgView_continue = ( ImageView ) findViewById(R.id.imageView_logo_continue);
        imgView_setting = ( ImageView ) findViewById(R.id.imageView_logo_setting);

        imgView_newStart2 = ( ImageView ) findViewById(R.id.imageView_logo_newStart2);     imgView_continue2 = ( ImageView ) findViewById(R.id.imageView_logo_continue2);
        imgView_setting2 = ( ImageView ) findViewById(R.id.imageView_logo_setting2);

        //설정창
        setting = (LinearLayout) findViewById(R.id.setting);

        //설정 안의 버튼들
        toggleButton_bgm = ( ToggleButton ) findViewById(R.id.toggleButton_bgm);
        toggleButton_fx = ( ToggleButton ) findViewById(R.id.toggleButton_fx);
        toggleButton_vibration = ( ToggleButton ) findViewById(R.id.toggleButton_vibration);
        imageView_scale = ( ImageView ) findViewById(R.id.imageView_scale);
        imageView_close = ( ImageView ) findViewById(R.id.imageView_close);

        toggleButton_bgm.setChecked(sharedPreferences.getBoolean("bgm",true));
        toggleButton_fx.setChecked(sharedPreferences.getBoolean("fx",true));
        toggleButton_vibration.setChecked(sharedPreferences.getBoolean("vib",true));

        SaveAll.isBGM = sharedPreferences.getBoolean("bgm",true);
        SaveAll.isFX = sharedPreferences.getBoolean("fx",true);
        SaveAll.isVIB = sharedPreferences.getBoolean("vib",true);

        Log.e("bgm",sharedPreferences.getBoolean("bgm",true)+"");
        Log.e("fx",sharedPreferences.getBoolean("fx",true)+"");
        Log.e("vib",sharedPreferences.getBoolean("vib",true)+"");

        //버튼에 리스너 달기.
        imgView_menu.setOnClickListener(this);
        imgView_newStart.setOnClickListener(this);
        imgView_setting.setOnClickListener(this);
        imgView_continue.setOnClickListener(this);

        toggleButton_bgm.setOnCheckedChangeListener(listener);
        toggleButton_fx.setOnCheckedChangeListener(listener);
        toggleButton_vibration.setOnCheckedChangeListener(listener);

    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.toggleButton_bgm:
                    SaveAll.isBGM = buttonView.isChecked();
                    Log.e("bgm",SaveAll.isBGM+"");
                    sharedPreferences.edit().putBoolean("bgm",buttonView.isChecked()).commit();
                    break;
                case R.id.toggleButton_fx:
                    SaveAll.isFX = buttonView.isChecked();
                    Log.e("fx",SaveAll.isFX+"");
                    sharedPreferences.edit().putBoolean("fx",buttonView.isChecked()).commit();
                    break;
                case R.id.toggleButton_vibration:
                    SaveAll.isVIB = buttonView.isChecked();
                    Log.e("vib",SaveAll.isVIB+"");
                    sharedPreferences.edit().putBoolean("vib",buttonView.isChecked()).commit();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this,GameActivity.class);

        switch (v.getId()){
            case R.id.imageView_logo_Menu:

                if ( isMenuClick == true ){
                    Toast.makeText(this,"메뉴열기!", Toast.LENGTH_SHORT).show();
                    imgView_newStart.setVisibility(View.VISIBLE);
                    imgView_continue.setVisibility(View.VISIBLE);
                    imgView_setting.setVisibility(View.VISIBLE);

                    imgView_newStart.setClickable(true);
                    imgView_continue.setClickable(true);
                    imgView_setting.setClickable(true);

                    imgView_newStart2.setVisibility(View.VISIBLE);
                    imgView_continue2.setVisibility(View.VISIBLE);
                    imgView_setting2.setVisibility(View.VISIBLE);

                    imgView_newStart2.setClickable(true);
                    imgView_continue2.setClickable(true);
                    imgView_setting2.setClickable(true);

                    Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo_ani);
                    imgView_newStart.startAnimation(animation);         imgView_continue.startAnimation(animation);     imgView_setting.startAnimation(animation);
                    animation = AnimationUtils.loadAnimation(this,R.anim.logo_ani2);
                    imgView_newStart2.startAnimation(animation);        imgView_continue2.startAnimation(animation);     imgView_setting2.startAnimation(animation);

                    isMenuClick = false;
                }else {
                    Toast.makeText(this,"메뉴닫기!", Toast.LENGTH_SHORT).show();
                    imgView_newStart.setVisibility(View.INVISIBLE);
                    imgView_continue.setVisibility(View.INVISIBLE);
                    imgView_setting.setVisibility(View.INVISIBLE);

                    imgView_newStart2.setVisibility(View.INVISIBLE);
                    imgView_continue2.setVisibility(View.INVISIBLE);
                    imgView_setting2.setVisibility(View.INVISIBLE);

                    imgView_newStart.setClickable(false);
                    imgView_continue.setClickable(false);
                    imgView_setting.setClickable(false);

                    imgView_newStart2.setClickable(false);
                    imgView_continue2.setClickable(false);
                    imgView_setting2.setClickable(false);


                    Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo_ani_close);
                    imgView_newStart.startAnimation(animation);     imgView_continue.startAnimation(animation);     imgView_setting.startAnimation(animation);
                    animation = AnimationUtils.loadAnimation(this,R.anim.logo_ani_close2);
                    imgView_newStart2.startAnimation(animation);     imgView_continue2.startAnimation(animation);     imgView_setting2.startAnimation(animation);

                    isMenuClick = true;
                }
                break;
            case R.id.imageView_logo_newStart:
                Toast.makeText(this,"새로운시작!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
            case R.id.imageView_logo_continue:
                Toast.makeText(this,"계속하기!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
            case R.id.imageView_logo_setting:
                Toast.makeText(this,"설정!", Toast.LENGTH_SHORT).show();

                imgView_newStart.setClickable(false);
                imgView_newStart2.setClickable(false);

                imgView_continue.setClickable(false);
                imgView_continue2.setClickable(false);

                imgView_setting.setClickable(false);
                imgView_setting2.setClickable(false);


                Animation animation = AnimationUtils.loadAnimation(this,R.anim.logo_ani);
                setting.startAnimation(animation);
                setting.setVisibility(View.VISIBLE);
                break;
            case R.id.imageView_close:

                imgView_newStart.setClickable(true);
                imgView_newStart2.setClickable(true);

                imgView_continue.setClickable(true);
                imgView_continue2.setClickable(true);

                imgView_setting.setClickable(true);
                imgView_setting2.setClickable(true);

                Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.logo_ani_close);
                setting.startAnimation(animation2);
                setting.setVisibility(View.GONE);
                break;

        }

    }
}
