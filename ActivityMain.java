package com.ancientlore.squash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMain extends Activity implements View.OnClickListener {
    static SharedPreferences prefs;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs =this.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        editor=prefs.edit();
        ((TextView)findViewById(R.id.textViewHiScore)).setText(""+prefs.getLong("HiScore", 0));
        ActivityMain.editor.apply();
        final Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.buttonPlay:
                i = new Intent(this,ActivityGame.class);
                startActivity(i);
                finish();
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        else return false;
    }
}
