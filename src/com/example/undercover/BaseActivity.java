package com.example.undercover;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		MobclickAgent.setDebugMode(true);
		MobclickAgent.onError(this);
		// MobclickAgent.setSessionContinueMillis(60000);
		SoundPlayer.init(this);
		SoundPlayer.pushSound(R.raw.ball);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void uMengClick(String clickname) {
		MobclickAgent.onEvent(this, clickname);
	}
}
