package com.example.undercover;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
	int disWidth;
	int disHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		MobclickAgent.setDebugMode(true);
		MobclickAgent.onError(this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		disWidth = (int) dm.widthPixels;
		disHeight = (int) dm.heightPixels;

		// MobclickAgent.setSessionContinueMillis(60000);
		SoundPlayer.init(this);
		SoundPlayer.pushSound(R.raw.ball);
		SoundPlayer.pushSound(R.raw.claps3);
		SoundPlayer.pushSound(R.raw.jishi_2);
		SoundPlayer.pushSound(R.raw.bottle);
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
