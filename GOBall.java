package ancientlore.squash;

import android.graphics.Rect;

class GOBall extends GameObject {
    private boolean boosted=false;
    private GOStateHorizontal stateX;
    private GOStateVertical stateY;

    private int speedX;
    private int speedY;
    private int velocity;

    GOBall(int displayX, int display0, int displayY){
        Stop();
        stateX=GOStateHorizontal.LEFT;
        stateY=GOStateVertical.DOWN;
        speedX=displayX/48;
        speedY=displayY/128;
        velocity=0;

        int newSize = displayX/24;
        //image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        //image =  Bitmap.createScaledBitmap(image,newSize, newSize, false);
        this.setRect(new Rect(displayX/2-newSize/2,displayY/2+ display0-newSize/2,
                displayX/2+newSize/2,displayY/2+ display0+newSize/2));
    }
    void moveLeft(){
        getRect().left -= getSpeedX();
        getRect().right -= getSpeedX();
    }
    void moveRight(){
        getRect().left += getSpeedX();
        getRect().right += getSpeedX();
    }
    void moveUp(){
        getRect().top -= getSpeedY();
        getRect().bottom -= getSpeedY();
    }
    void moveDown(){
        getRect().top += getSpeedY();
        getRect().bottom += getSpeedY();
    }

    boolean isBoosted(){return boosted;}
    void Stop(){stateY=GOStateVertical.STOP;stateX=GOStateHorizontal.STOP;}
    void switchDirectionX(){
        stateX=(stateX==GOStateHorizontal.LEFT)?GOStateHorizontal.RIGHT:GOStateHorizontal.LEFT;
    }
    void switchDirectionY(){
        stateY=(stateY==GOStateVertical.DOWN)?GOStateVertical.UP:GOStateVertical.DOWN;
    }
    GOStateHorizontal getStateX() {return stateX;}
    GOStateVertical getStateY() {return stateY;}


    private int getSpeedX() {return speedX+velocity;}
    private int getSpeedY() {
        return speedY+velocity;
    }
    void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    int getSpeed(){return speedX+speedY;}
    void setBoost(int val){velocity=val;boosted=true;}
    void slowdown(){
        if (velocity>0) velocity--;
        else {
            velocity=0;
            boosted=false;
        }
    }
}
