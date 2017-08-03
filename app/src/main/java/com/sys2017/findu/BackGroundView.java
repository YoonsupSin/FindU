package com.sys2017.findu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by N552VW on 2017-07-04.
 */

public class BackGroundView extends View {

    int width, height;

    //bitmap menu back
    Bitmap bitmap_background;
    Bitmap[] bitmap_backLight;
    Bitmap bitmap_insectLight;

    int fade = 0;
    int df = 3;
    Paint paint_alpha;
    int num;
    int count = 0;

    Bitmap bitmap_logo_menu;
    Bitmap bitmap_logo_newStart;
    Bitmap bitmap_logo_continue;
    Bitmap bitmap_logo_setting;
    Bitmap bitmap_logo_creator;
    Bitmap bitmap_logo_myStory;
    Bitmap bitmap_logo_note;

    public BackGroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        WindowManager wm= (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display dis= wm.getDefaultDisplay();
        width= dis.getWidth();
        height= dis.getHeight();

        createBitmap();
        paint_alpha = new Paint();

        handler.sendEmptyMessageDelayed(0,16);

    }

    void createBitmap(){
        Bitmap img = null;
        Resources res = getResources();
//        bitmap_backLight = new Bitmap[16];

        //back
        img = BitmapFactory.decodeResource(res,R.drawable.back);
        bitmap_background = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;

//        //back effect
//        for ( int i = 0 ; i < bitmap_backLight.length ; i ++ ){
//            img = BitmapFactory.decodeResource(res,R.drawable.backlight_00+i);
//            bitmap_backLight[i] = Bitmap.createScaledBitmap(img,width,height,true);
//            img.recycle(); img = null;
//        }

        //back light 2
        img = BitmapFactory.decodeResource(res,R.drawable.backlioght);
        bitmap_insectLight = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;

        //menu-ui
        img = BitmapFactory.decodeResource(res,R.drawable.btn00);
        bitmap_logo_menu = Bitmap.createScaledBitmap(img,100,100,true);
        img.recycle(); img = null;

    }

    void removeResources(){
        bitmap_background.recycle();    bitmap_background = null;
        bitmap_insectLight.recycle();   bitmap_insectLight = null;
        bitmap_logo_menu.recycle();     bitmap_logo_menu = null;

//        for ( int i = 0 ; i < bitmap_backLight.length ; i ++ ){
//            bitmap_backLight[i].recycle();
//            bitmap_backLight[i] = null;
//        }

    }

    void check(){
        //반짝이 알파값 계산!
        fade+=df;
        paint_alpha.setAlpha(fade);
        if ( fade >= 255 || fade <= 0 ){
            df = -df;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap_background,0,0,null);
        check();
        canvas.drawBitmap(bitmap_insectLight,0,0,paint_alpha);
    }

    void handlerStop(){
        handler.removeMessages(0);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();

            handler.sendEmptyMessageDelayed(0,16);
        }
    };

}
