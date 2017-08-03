package com.sys2017.findu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by N552VW on 2017-07-04.
 */

public class GameView extends View {

    Context context;

    int width;
    int height;

    double ratioW;
    double ratioH;

    //bitmap background
    Bitmap bitmap_background;
    Bitmap bitmap_land;
    Bitmap bitmap_test_light;   Paint paint_alpha_light;    int cnt_light = 0;    int light_delta = 3;
    Bitmap[] bitmap_back_water;     int i = 0;  int cnt_water = 0; int cnt_water_d = 1;
    Bitmap[] bitmap_inMap;

    //test map
    Bitmap bitmap_background_test;

    //bitmap Aru----------------------------------------------Aru 관련변수
    Bitmap bitmap_Aru;      boolean Aru_canMove = false;

    //bitmap npc_hs         //bitmap npc_ys         //bitmap npc_mj
    Bitmap bitmap_npcHS;    Bitmap bitmap_npcYS;    Bitmap bitmap_npcMJ;
    Bitmap bitmap_test_ch;


    //interface-----------------------------------------------interface 관련변수
    Bitmap bitmap_joy;          Rect joyRect;   //얜 수작업.
    Bitmap bitmap_jump;         Rect jumpRect;
    Bitmap bitmap_action;       Rect actionRect;

    int joySize_width = height/2;
    int joySize_height = height/6;

    //inGame---------------------------------------------------object

    Boolean touch_joy_00 = false;
    Boolean touch_jump_00 = false;
    Boolean touch_action_00 = false;

    Boolean touch_joy_01 = false;
    Boolean touch_jump_01 = false;
    Boolean touch_action_01 = false;

    Paint paint_alpha_joy;
    Paint paint_alpha_jump;
    Paint paint_alpha_action;

    Player player;
    //player rect
    Rect playerRect;

    int dx = 5;
    int dy = 5;

    int joy_center_x;
    int joy_center_y;

    public static boolean isPause = false;

    Msg msg;

    //checkBack
    boolean isBackMove = false;

    //map stage cnt
    int stage_cnt = 1;

    //map change range
    Rect right;         boolean rightMapChange = true;
    Rect left;          boolean leftMapChange = true;

    //map position
    int backPos = 0;
    int backPos_dx = 0;

    int ch_W;
    int ch_H;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        player = new Player(width,height);

        ratioW = width/600;
        ratioH = height/336;



        CreateBitmap();

        msg = new Msg();
        msg.sendEmptyMessageDelayed(0,16);

//        handler.sendEmptyMessageDelayed(0,16);

        paint_alpha_joy = new Paint();
        paint_alpha_jump = new Paint();
        paint_alpha_action = new Paint();
        paint_alpha_light = new Paint();

        paint_alpha_joy.setAlpha(80);
        paint_alpha_jump.setAlpha(80);
        paint_alpha_action.setAlpha(80);

    }

    void CreateBitmap(){

        Bitmap img=null;



        //joy
        joySize_width = height/3;
        joySize_height = height/10;
        img = BitmapFactory.decodeResource(getResources(),R.drawable.joypad_on);
        bitmap_joy = Bitmap.createScaledBitmap(img,joySize_width,joySize_height,false);

        joyRect = new Rect( height/20, height - joySize_height - height/20 , joySize_width + height/20, height-height/20);

        joy_center_x = joyRect.width()/2+height/20;
        joy_center_y = joyRect.height()/2-height/20;

        player.joy_cx = joy_center_x;
        player.joy_cy = joy_center_y;


        //jump
        img = BitmapFactory.decodeResource(getResources(),R.drawable.btn_jump_on);
        bitmap_jump = Bitmap.createScaledBitmap(img,height/4,height/4,false);
        int jumpSize = height/4;
        jumpRect = new Rect(width - jumpSize, height - jumpSize, width, height);

        //action
        img = BitmapFactory.decodeResource(getResources(),R.drawable.btn_action_on);
        bitmap_action = Bitmap.createScaledBitmap(img,height/5,height/5,false);
        int actionSize = height/5;
        actionRect = new Rect(width - jumpSize - actionSize, height - jumpSize - actionSize, width - jumpSize, height);

        //back_
        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00);
        bitmap_background = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;

        //back_land
        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00_land);
        bitmap_land = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;

        //back_light
        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00_light);
        bitmap_test_light = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;

        //back_water
        bitmap_back_water = new Bitmap[2];

        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00_water00);
        Bitmap bitmap00 = Bitmap.createScaledBitmap(img,width,height,true);
        bitmap_back_water[0] = bitmap00;
        img.recycle(); img = null;

        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00_water01);
        Bitmap bitmap01 = Bitmap.createScaledBitmap(img,width,height,true);
        bitmap_back_water[1] = bitmap01;
        img.recycle(); img = null;

        //rect
        left = new Rect( 0 , height-height/5 , height/5, height );
        right = new Rect( width - height/5 , height - height/5  , width , height);

        player.setLeft(left);   player.setRight(right);

        //test ch
        img = BitmapFactory.decodeResource(getResources(),R.drawable.logo_setting);
        ch_W = height/5;
        ch_H = height/5;
        bitmap_test_ch = Bitmap.createScaledBitmap(img,ch_W,ch_H,true);
        player.setCh_W(ch_W/2);   player.setCh_Y(ch_W/2);
        img.recycle(); img = null;

        //test map
        img = BitmapFactory.decodeResource(getResources(),R.drawable.inmap00);
        bitmap_background_test = Bitmap.createScaledBitmap(img,width,height,true);
        img.recycle(); img = null;



    }

    void RemoveBitmaps(){
        bitmap_joy.recycle(); bitmap_joy = null;
        bitmap_jump.recycle(); bitmap_jump = null;
        bitmap_action.recycle(); bitmap_action = null;
    }

    void RemoveInGameMap(){

    }

    void moveAll(){

        player.move();

        //light
        cnt_light += light_delta;
        paint_alpha_light.setAlpha(cnt_light);
        if ( cnt_light <= 0 || cnt_light >= 255 ){
            light_delta = -light_delta;
        }

        //water
        cnt_water += cnt_water_d;
        if ( cnt_water <= 0 ){
            i = 0;
            cnt_water_d = - cnt_water_d;
        }else if ( cnt_water >= 40 ){
            i = 1;
            cnt_water_d = - cnt_water_d;
        }

        //player Rect
        playerRect = new Rect(player.player_x ,player.player_y ,player.player_x+ch_W ,player.player_y+ ch_W);

    }





    void TouchDown( int x, int y, int id ){

        if ( joyRect.contains(x,y) ){

            if ( isBackMove == false ){
                paint_alpha_joy.setAlpha(240);
                touch_joy_00 = true;
                player.canMove = true;
                player.touch_x = x;
                player.touch_y = y;
            }

        }

        if ( actionRect.contains(x,y) ){
            if ( isBackMove == false ){
                paint_alpha_action.setAlpha(240);
                touch_action_00 = true;
            }
        }

        if ( jumpRect.contains(x,y) ){
            if ( isBackMove == false ){
                paint_alpha_jump.setAlpha(240);
                touch_jump_00 = true;
                player.canJump = true;
                player.isjump = true;
            }
        }


    }

    void TouchUp( int x, int y, int id ){
        if  ( touch_joy_00 ){
            if ( isBackMove == false ){
                paint_alpha_joy.setAlpha(80);
                touch_joy_00 = false;
                player.canMove = false;
            }

        }

        if  ( touch_action_00 ){
            if ( isBackMove == false ){
                paint_alpha_action.setAlpha(80);
                touch_action_00 = false;
            }

        }

        if  ( touch_jump_00 ){
            if ( isBackMove == false ){
                paint_alpha_jump.setAlpha(80);
                touch_jump_00 = false;
            }

        }
    }

    void TouchMove( int x, int y, int id ){
        if ( id == 0 ){
            Log.e("id","0");
        }

        if ( id == 1 ){
            Log.e("id","1");
        }
    }

    void PointerDown( int x, int y, int id ){
        if ( id > 0 ){
            if ( joyRect.contains(x,y) ){
                if ( isBackMove == false ){
                    paint_alpha_joy.setAlpha(240);
                    touch_joy_01 = true;
                    player.canMove = true;
                }

            }

            if ( actionRect.contains(x,y) ){
                if ( isBackMove == false ){
                    paint_alpha_action.setAlpha(240);
                    touch_action_01 = true;
                }

            }

            if ( jumpRect.contains(x,y) ){
                if ( isBackMove == false ){
                    paint_alpha_jump.setAlpha(240);
                    touch_jump_01 = true;
                    player.canJump = true;
                }

            }
        }
    }

    void PointerUp( int x, int y, int id ){
        if ( touch_joy_01 ){
            if ( isBackMove == false ){
                touch_joy_01 = false;
                paint_alpha_joy.setAlpha(80);
            }

        }

        if ( touch_action_01 ){
            if ( isBackMove == false ){
                touch_action_01 = false;
                paint_alpha_action.setAlpha(80);
            }

        }

        if ( touch_jump_01 ){
            if ( isBackMove == false ){
                touch_jump_01 = false;
                paint_alpha_jump.setAlpha(80);
            }

        }


    }

    void playerCheck(){

    }

    void reckoningBack(){
        //맵의 특정 범위에!
        if ( left.intersect(playerRect) ){
            Log.e("위치",player.player_x+"");
            if( leftMapChange ){
                stage_cnt = 1;
                leftMapChange = false;
                backPos_dx = 10;

            }


        }


        if ( right.intersect(playerRect) ){
            Log.e("위치",player.player_x+"");
            if ( rightMapChange ){
                stage_cnt = 2;
                rightMapChange = false;
                backPos_dx = 10;

                player.canMove = true;

            }

        }
    }

    void drawMap(Canvas canvas){
        if ( stage_cnt == 1 ){
            canvas.drawBitmap(bitmap_background,backPos,0,null);
            canvas.drawBitmap(bitmap_test_light,backPos,0,paint_alpha_light);
            canvas.drawBitmap(bitmap_back_water[i],backPos,0,null);
            canvas.drawBitmap(bitmap_land,backPos,0,null);
        }

        if ( stage_cnt == 2 ){

            canvas.drawBitmap(bitmap_background,backPos,0,null);
            canvas.drawBitmap(bitmap_test_light,backPos,0,paint_alpha_light);
            canvas.drawBitmap(bitmap_back_water[i],backPos,0,null);
            canvas.drawBitmap(bitmap_land,backPos,0,null);

            canvas.drawBitmap(bitmap_background_test,backPos + width ,0,null);

            backPos -= backPos_dx;
            if(backPos<-width) backPos = -width;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        moveAll();
        //배경
        reckoningBack();
        drawMap(canvas);



        //맵이동범위
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(100);
        canvas.drawRect(left,paint);
        canvas.drawRect(right,paint);

        //테스트 캐릭터 범위
        canvas.drawRect(playerRect,paint);

        //조작
        canvas.drawBitmap(bitmap_joy,height/20,height - bitmap_joy.getHeight()-height/20,paint_alpha_joy);
        canvas.drawBitmap(bitmap_jump,width-bitmap_jump.getWidth(),height-bitmap_jump.getHeight(),paint_alpha_jump);
        canvas.drawBitmap(bitmap_action,width-bitmap_jump.getWidth()-bitmap_action.getWidth(),height-bitmap_action.getHeight(),paint_alpha_action);

        //테스트캐릭터
        canvas.drawBitmap(bitmap_test_ch,player.player_x,player.player_y,null);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        int touch_x = 0;
        int touch_y = 0;

        int cnt;

        int id = 0;

        switch (action){
            case MotionEvent.ACTION_DOWN:
                id = event.getPointerId(0);
                touch_x = (int) event.getX(0);
                touch_y = (int) event.getY(0);
                TouchDown(touch_x, touch_y, id);
                break;

            case MotionEvent.ACTION_UP:
                id = event.getPointerId(0);
                touch_x = (int) event.getX(0);
                touch_y = (int) event.getY(0);
                TouchUp(touch_x, touch_y, id);
                break;

            case MotionEvent.ACTION_MOVE:
                cnt= event.getPointerCount();
                for(int i=0; i<cnt; i++){
                    touch_x = (int) event.getX(i);
                    touch_y = (int) event.getY(i);
                    id = event.getPointerId(i);
                    TouchMove(touch_x, touch_y, id);
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                id = event.getPointerId(1);
                touch_x = (int) event.getX(1);
                touch_y = (int) event.getY(1);
                PointerDown(touch_x, touch_y, id);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                id = event.getPointerId(event.getActionIndex());
                touch_x = (int) event.getX(event.getActionIndex());
                touch_y = (int) event.getY(event.getActionIndex());
                PointerUp(touch_x, touch_y, id);
                break;
        }
        return true;
    }

    public class Msg extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( isPause ) return;
            invalidate();
            sendEmptyMessageDelayed(0,16);
        }
    }

    void EmptyMsg(){
        msg.sendEmptyMessageDelayed(0,16);
    }

}
