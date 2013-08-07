package com.example.undercover;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class random_50 extends Activity {
	// int random_times;
	int click_times = 50;
	private Button punishBtn;
	private Button click_button;
	private Button restart_button;
	private Button punishment_button;
	private ImageView image_random1;
	private TextView Probability;
	private TextView clicktimes;
	private TextView preference;
	private TextView lose;
	Random random = new Random();
	int random_times = Math.abs(random.nextInt()) % 50;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_50);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		click_button = (Button) findViewById(R.id.button1);
		restart_button = (Button) findViewById(R.id.button2);
		punishment_button = (Button) findViewById(R.id.button3);
		punishBtn = (Button) findViewById(R.id.punishBtn);
		image_random1 = (ImageView) findViewById(R.id.imageView1);
		lose = (TextView) findViewById(R.id.textView5);

		// v.setVisibility(View.INVISIBLE);
		punishBtn.setVisibility(View.INVISIBLE);
		restart_button.setVisibility(View.INVISIBLE);
		punishment_button.setVisibility(View.INVISIBLE);
		image_random1.setVisibility(View.INVISIBLE);
		preference = (TextView) findViewById(R.id.textView3);
		DisplayParameter(0);
		lose.setVisibility(View.INVISIBLE);
		click_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				click_times--;
				DisplayParameter(50-click_times);
				preference.setVisibility(View.INVISIBLE);
				// click_button.setText();
				if (click_times == random_times) {

					// Intent goChat = new Intent();
					// goChat.setClass(random_50.this,randome_gameover.class);
					// startActivity(goChat);
					v.setVisibility(View.INVISIBLE);
					click_button.setVisibility(View.INVISIBLE);
					punishBtn.setVisibility(View.VISIBLE);
					restart_button.setVisibility(View.VISIBLE);
					lose.setVisibility(View.VISIBLE);
					punishment_button.setVisibility(View.VISIBLE);
					image_random1.setVisibility(View.VISIBLE);

				}
			}
		});

		restart_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				random_times = Math.abs(random.nextInt()) % 50;
				click_times = 50;
				image_random1.setVisibility(View.INVISIBLE);
				click_button.setVisibility(View.VISIBLE);
				lose.setVisibility(View.INVISIBLE);
				restart_button.setVisibility(View.INVISIBLE);
				DisplayParameter(50-click_times);
			}
		});
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo	= new Intent();
				intentGo.setClass(random_50.this, PunishActivity.class);
				startActivity(intentGo);
				finish();
			}
		});
		
		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 进入村龙单独的惩罚页面
				finish();
			}
		});
		// 显示概率和点击次数

	}

	protected void DisplayParameter(int time) {
		Probability = (TextView) findViewById(R.id.textView2);
		clicktimes = (TextView) findViewById(R.id.textView1);
		Probability.setText("危险级数：" + (time + 1) * 2 + "%");
		clicktimes.setText("（点击了" + time + "次）");
	}
}
