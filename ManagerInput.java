package ancientlore.squash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

class ManagerInput {
    private static volatile ManagerInput instance=null;
    private GameButton menuButton;
    private GameButton pauseButton;
    private float accXPrevPos=0;

    private ManagerInput(){
    }

    static ManagerInput getInstance(){
        if (instance==null) {
            synchronized (ManagerInput.class) {
                if (instance == null)
                    instance = new ManagerInput();
            }
        }
        return instance;
    }

    void initialise(Context context, final int maxX, final int maxY){
        final int padding=maxY/10;
        Bitmap menuBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.button_menu);
        int ratio = menuBitmap.getWidth()/menuBitmap.getHeight();
        menuBitmap=Bitmap.createScaledBitmap(menuBitmap,maxX/10*ratio,
                (maxY-2*padding),false);
        menuButton=new GameButton("Menu",new Rect(maxX-menuBitmap.getWidth()-padding,
                padding, maxX-padding,menuBitmap.getHeight()+padding),menuBitmap);
        Bitmap pauseBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.button_pause);
        pauseBitmap=Bitmap.createScaledBitmap(pauseBitmap,menuBitmap.getWidth(),
                menuBitmap.getHeight(),false);
        pauseButton=new GameButton("Pause",new Rect(
                menuButton.getRect().left-pauseBitmap.getWidth()-padding,
                padding, menuButton.getRect().left-padding,menuBitmap.getHeight()+padding),pauseBitmap);
    }

    void handleTouchInput(MotionEvent me, ManagerLevel lm){
        switch (me.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (me.getX() < lm.getRacket().getRect().left)
                    lm.getRacket().setState(GOStateHorizontal.LEFT);
                else if (me.getX() > lm.getRacket().getRect().right)
                    lm.getRacket().setState(GOStateHorizontal.RIGHT);
                break;
            case MotionEvent.ACTION_UP:
                lm.getRacket().setState(GOStateHorizontal.STOP);
                break;
        }
    }
    void handleAccelerometerInput(SensorEvent se, ManagerLevel lm){
        if (se.values[0] != accXPrevPos){
            if (se.values[0]-10 > accXPrevPos)
                lm.getRacket().setState(GOStateHorizontal.LEFT);
            else
                lm.getRacket().setState(GOStateHorizontal.RIGHT);
            accXPrevPos = se.values[0];
        }
    }

    public GameButton getMenuButton() {
        return menuButton;
    }

    public GameButton getPauseButton() {
        return pauseButton;
    }
}
