package ancientlore.squash;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class ActivitySettings extends Activity implements OnClickListener {
    private ManagerLevel _lm;
    private SeekBar ballSB, racketSB;
    //private int racketSpeed;
    private RadioButton accelCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //i = getIntent();
        //racketSpeed = i.getIntExtra("racketSpeed",15);

        _lm = ManagerLevel.getInstance();
        final Button buttonApply = (Button) findViewById(R.id.buttonSettingsApply);
        buttonApply.setOnClickListener(this);

        ballSB = (SeekBar) findViewById(R.id.seekBarBallSpeed);
        ballSB.setOnClickListener(this);
        racketSB = (SeekBar) findViewById(R.id.seekBarRacketSpeed);
        racketSB.setOnClickListener(this);
        accelCB = (RadioButton) findViewById(R.id.radioButtonAccelerometer);

        ballSB.setProgress(_lm.getBall().getSpeed());
        racketSB.setProgress(_lm.getRacket().getSpeed());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSettingsApply:
                ManagerGame _gm = ManagerGame.getInstance();
                _gm.setAccelerometerOn(accelCB.isChecked());
                _lm.getBall().setSpeedX(ballSB.getProgress() / 3);
                _lm.getBall().setSpeedY(ballSB.getProgress() * 2 / 3);
                _lm.getRacket().setSpeed(racketSB.getProgress());
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
    }
}
