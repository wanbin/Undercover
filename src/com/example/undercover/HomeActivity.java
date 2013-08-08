package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends BaseActivity {
	private Button btnPlayRule;
	private Button btnWeixin;
	private Button btnMake;
	private Button btnMore;
	private ImageView imgPlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		btnPlayRule = (Button) findViewById(R.id.btnPlayRule);
		btnWeixin = (Button) findViewById(R.id.btnWeixin);
		btnMake = (Button) findViewById(R.id.btnMake);
		btnMore = (Button) findViewById(R.id.btnMore);
		imgPlay = (ImageView) findViewById(R.id.imageView2);

		ScaleAnimation scaleAni = new ScaleAnimation(1.0f, 1.02f, 1.0f, 1.02f);
		scaleAni.setRepeatMode(Animation.REVERSE);
		scaleAni.setRepeatCount(-1);
		scaleAni.setDuration(1000);
		imgPlay.startAnimation(scaleAni);


		imgPlay.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true) {
					// finish();
					Intent goChat = new Intent();
					goChat.setClass(HomeActivity.this, Setting.class);
					startActivity(goChat);
				}
			}
		});

		btnPlayRule.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true) {
					// finish();
					Intent goChat = new Intent();
					goChat.setClass(HomeActivity.this, UnderCoverContent.class);
					startActivity(goChat);
				}
			}
		});
		btnWeixin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true) {
					// finish();
					Intent goChat = new Intent();
					goChat.setClass(HomeActivity.this, weixin.class);
					startActivity(goChat);
				}
			}
		});
		btnMake.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true) {
					// finish();
					Intent goChat = new Intent();
					goChat.setClass(HomeActivity.this,
							RotaryBottleActivity.class);
					startActivity(goChat);
				}
			}
		});
		btnMore.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true) {
					// finish();
					Intent goChat = new Intent();
					goChat.setClass(HomeActivity.this, home_page.class);
					startActivity(goChat);
				}
			}
		});
	}


}
