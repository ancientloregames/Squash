package com.ancientlore.squash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class ActivityGame extends Activity implements SensorEventListener {
    GameView gameView;

    private ManagerGame _gm;
    private ManagerInput _im;
    private ManagerLevel _lm;

    private Sensor sensor;
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);

        _gm = ManagerGame.getInstance();
        _lm = ManagerLevel.getInstance();
        _im = ManagerInput.getInstance();

        sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        gameView=new GameView(this,displaySize.x,displaySize.y);
        setContentView(gameView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onStop(){
        super.onStop();
        gameView.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        int x = (int) me.getX();
        int y = (int) me.getY();
        switch (me.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (_im.getPauseButton().getRect().contains(x,y)) {
                    _gm.switchPlaying();
                    return true;
                }
                else if (_im.getMenuButton().getRect().contains(x,y)){
                    Intent i = new Intent(this,ActivityMenu.class);
                    startActivity(i);
                }
                break;
        }
        if (_gm.isPlaying() && !_gm.isAccelerometerOn())
            _im.handleTouchInput(me, _lm);
        else {
            _gm.setPlaying(true);
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        if (_gm.isAccelerometerOn())
            _im.handleAccelerometerInput(se,_lm);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //none
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_HOME:
                break;
        }
        return false;
    }
}
