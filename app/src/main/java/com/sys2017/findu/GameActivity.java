package com.sys2017.findu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import static com.sys2017.findu.R.id.toggleButton_ingame_bgm;

public class GameActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    GameView gameView;
    ImageView imageView_pause;
    ImageView imageView_logo_setting;
    ImageView imageView_logo_setting_close;
    ImageView imageView_logo_setting_resume;
    ImageView imageView_logo_setting_exit;
    ToggleButton toggleButton_bgm;
    ToggleButton toggleButton_fx;
    ToggleButton toggleButton_vib;

    RelativeLayout relativeLayout_setting;      boolean isClickSetting = false;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);

        gameView = (GameView) findViewById(R.id.gameView);
        imageView_pause = (ImageView) findViewById(R.id.imageView_pause);
        imageView_logo_setting = ( ImageView)  findViewById(R.id.imageView_setting_logo);
        imageView_logo_setting_close = ( ImageView ) findViewById(R.id.imageView_setting_logo_close);
        imageView_logo_setting_resume = ( ImageView ) findViewById(R.id.imageView_setting_resume);
        imageView_logo_setting_exit = ( ImageView ) findViewById(R.id.imageView_setting_exit);

        toggleButton_bgm = ( ToggleButton ) findViewById(toggleButton_ingame_bgm);
        toggleButton_fx = ( ToggleButton ) findViewById(R.id.toggleButton_ingame_fx);
        toggleButton_vib = ( ToggleButton ) findViewById(R.id.toggleButton_ingame_vibration);

        toggleButton_bgm.setChecked(SaveAll.isBGM);
        toggleButton_fx.setChecked(SaveAll.isFX);
        toggleButton_vib.setChecked(SaveAll.isVIB);

        relativeLayout_setting = ( RelativeLayout ) findViewById(R.id.relative_settingLogo);

        Glide.with(this).load(R.drawable.ingame_pause01).into(imageView_logo_setting);
        Glide.with(this).load(R.drawable.ingame_pause00).into(imageView_logo_setting_close);
        Glide.with(this).load(R.drawable.restart).into(imageView_logo_setting_resume);
        Glide.with(this).load(R.drawable.exit).into(imageView_logo_setting_exit);

        imageView_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if ( v.isClickable() ){
                     if ( isClickSetting == false ) {
                         gameView.isPause = true;
                         relativeLayout_setting.setVisibility(View.VISIBLE);
                         Animation animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.slide_logo);
                         relativeLayout_setting.startAnimation(animation);
                         isClickSetting = true;
                     }
                 }
            }
        });

        imageView_logo_setting_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isClickSetting ){
                    relativeLayout_setting.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(GameActivity.this,R.anim.slide_logo_close);
                    relativeLayout_setting.startAnimation(animation);
                    isClickSetting = false;
                    gameView.isPause = false;
                    gameView.EmptyMsg();
                }
            }
        });

        imageView_logo_setting_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout_setting.setVisibility(View.GONE);
                Animation animation = AnimationUtils.loadAnimation(GameActivity.this,R.anim.slide_logo_close);
                relativeLayout_setting.startAnimation(animation);
                isClickSetting = false;
                gameView.isPause = false;
                gameView.EmptyMsg();
            }
        });

        imageView_logo_setting_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.toggleButton_ingame_bgm:
                sharedPreferences.edit().putBoolean("bgm",buttonView.isChecked());
                break;
            case R.id.toggleButton_ingame_fx:
                sharedPreferences.edit().putBoolean("fx",buttonView.isChecked());
            break;
            case R.id.toggleButton_ingame_vibration:
                sharedPreferences.edit().putBoolean("vib",buttonView.isChecked());
                break;
        }
    }
}
