package ancientlore.squash;

class ManagerGame {
    private static volatile ManagerGame instance=null;
    private boolean playing;

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    private boolean pause;
    private boolean debug;
    private boolean win;
    private boolean accelerometerOn=false;

    private long score = 0;
    private long bestScore = 0;
    private int maxLives;
    private int lives;

    private ManagerGame(){}

    static ManagerGame getInstance(){
        if (instance==null) {
            synchronized(ManagerGame.class) {
                if (instance == null)
                    instance = new ManagerGame();
            }
        }
        return instance;
    }

    void initialise(int newMaxLives){
        maxLives=newMaxLives;
        reset();
    }

    void reset(){
        playing=false;
        debug=false;
        win=false;
        score=0;
        lives=maxLives;
    }

    boolean isDebug(){return debug;}
    boolean isWin(){return win;}
    boolean isLoose(){return lives==0;}
    boolean isPlaying() {return playing;}
    long getScore(){return score;}
    int getLives(){return lives;}
    boolean isAccelerometerOn() {return accelerometerOn;}
    public long getBestScore() {return bestScore;}

    void setDebug(boolean value){debug=value;}
    void setWin(boolean value){win=value;}
    void setPlaying(boolean value) {playing = value;}
    void switchPlaying() {playing = !playing;}
    void updateScore(int value){score+=value;}
    public void setScore(long score) {this.score = score;}
    boolean takeLive(){return --lives==0;}
    void setAccelerometerOn(boolean accelerometerOn) {this.accelerometerOn = accelerometerOn;}
    public void setBestScore(long bestScore) {this.bestScore = bestScore;}

}
