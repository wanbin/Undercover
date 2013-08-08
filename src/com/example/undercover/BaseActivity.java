package com.example.undercover;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}
}
