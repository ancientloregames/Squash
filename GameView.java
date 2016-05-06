package ancientlore.squash;

import android.content.Context;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

class GameView extends SurfaceView implements Runnable {
    Thread gameThread = null;
    private volatile boolean running;
    private int displayX, displayY;

    private Paint paint;
    private Paint paintInfo;
    private Paint paintTip;
    private Paint paintState;//big text in the center, like "Tutorial", or "Pause"
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int upperPanelY;

    private ManagerGame _gm;
    private ManagerInput _im;
    private ManagerLevel _lm;
    private ManagerSound _sm;

    public GameView(Context context, int displayX, int displayY) {
        super(context);
        this.displayX = displayX;
        this.displayY = displayY;
        upperPanelY = displayY / 12;

        surfaceHolder = getHolder();
        paint = new Paint();
        paintInfo = new Paint();
        paintInfo.setTextSize(displayX/20);
        paintInfo.setColor(Color.WHITE);
        paintInfo.setTextAlign(Paint.Align.LEFT);
        paintTip= new Paint();
        paintTip.setTextSize(displayX/26);
        paintTip.setColor(Color.WHITE);
        paintTip.setTextAlign(Paint.Align.CENTER);
        paintState= new Paint();
        paintState.setTextSize(displayX/9);
        paintState.setColor(Color.WHITE);
        paintState.setTextAlign(Paint.Align.CENTER);

        _gm = ManagerGame.getInstance();
        _gm.initialise(10);
        _lm = ManagerLevel.getInstance();
        _lm.initialize("1",new Rect(0,upperPanelY,displayX,displayY));
        _im = ManagerInput.getInstance();
        _im.initialise(context,displayX,upperPanelY);
        _sm = ManagerSound.getInstance();
        _sm.initialise(context);

        _gm.setBestScore(ActivityMain.prefs.getLong("HiScore", 0));
        ActivityMain.editor.apply();
    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            //-----------------
            canvas.drawColor(Color.argb(255, 15, 60, 90));
            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawRect(0,0,displayX,upperPanelY, paint);
            paint.setColor(Color.argb(255, 255, 255, 255));

            canvas.drawText("Score: " + _gm.getScore() +
                    "    Lives: " + _gm.getLives(), 20, 80, paintInfo);

            for (int i=0;i< _lm.getGridHeight();i++)
                for (int j=0;j< _lm.getGridWidth();j++)
                    if (!_lm.getBlock(i,j).getContent().equals("none")) {
                        paint.setColor(_lm.getBlock(i, j).getColor());
                        canvas.drawRect(_lm.getBlock(i, j).getRect(), paint);
                    }

            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRoundRect(new RectF(_lm.getBall().getRect()), 20f, 20f, paint);
            canvas.drawRect(_lm.getRacket().getRect(), paint);

            canvas.drawBitmap(_im.getMenuButton().getBitmap(),
                    _im.getMenuButton().getRect().left,_im.getMenuButton().getRect().top,paint);
            canvas.drawBitmap(_im.getPauseButton().getBitmap(),
                    _im.getPauseButton().getRect().left,_im.getPauseButton().getRect().top,paint);

            if (!_gm.isPlaying()){
                canvas.drawColor(Color.argb(200,0,0,0));
                canvas.drawText("Pause",displayX/2,displayY/2+upperPanelY,paintState);
                canvas.drawText("Hold finger left or right side of the racket, to move it.",
                        displayX/2,displayY/2+upperPanelY+paintState.getTextSize()+5,paintTip);
                canvas.drawText("You can change input type in Menu->Settings.",
                        displayX/2,displayY/2+upperPanelY+paintState.getTextSize()+5+paintTip.getTextSize(),paintTip);
                canvas.drawText("Also, you can change racket and ball speed there.",
                        displayX/2,displayY/2+upperPanelY+paintState.getTextSize()+10+2*paintTip.getTextSize(),paintTip);
                canvas.drawText("[Tap on screen, to start game]",
                        displayX/2,displayY/2+upperPanelY+paintState.getTextSize()+15+3*paintTip.getTextSize(),paintTip);
            }

            //-----------------
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void fps(){
        try {
            Thread.sleep(17);//17 = 1000(milliseconds)/60(FPS)
        }catch(InterruptedException e){
            Log.e("error", "failed to sleep");
        }
    }

    @Override
    public void run() {
        while (running){
            if (_gm.isPlaying())_lm.update(_gm, _sm);
            draw();
            fps();
        }
    }
    protected void pause(){
        running=false;
        try{
            gameThread.join();
        }catch (InterruptedException e){
            Log.e("error", "failed to pause thread");
        }
    }
    protected void resume(){
        running=true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    protected void stop(){
        running=false;
        try{
            gameThread.stop();
        }catch (RuntimeException e){
            Log.e("error", "failed to stop thread");
        }
    }
}
