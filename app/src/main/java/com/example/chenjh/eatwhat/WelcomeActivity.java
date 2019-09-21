package com.example.chenjh.eatwhat;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;


public class WelcomeActivity extends Activity {
	//private TextView tip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(1500);
		animation.setStartOffset(200);
		animationSet.addAnimation(animation);
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
					Intent it = new Intent(WelcomeActivity.this, privatecustom.activity.MainActivity.class);
					startActivity(it);
					WelcomeActivity.this.finish();
			}
		};
		timer.schedule(timerTask, 2000 * 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			;
		}
		return false;
	}
}
