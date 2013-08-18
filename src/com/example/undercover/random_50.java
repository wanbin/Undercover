package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class random_50 extends BaseActivity {
	// int random_times;
	int click_times = 50;
	private ImageView click_button;
	private ImageView btnreturn;
	private Button restart_button;

	private Button punishment_button;
	private Button back_button;

	private TextView Probability;
	private TextView clicktimes;
	private TextView preference;
	private TextView lose;
	Random random = new Random();
	int random_times = Math.abs(random.nextInt()) % 50;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_50);
		click_button = (ImageView) findViewById(R.id.imageBtnMain);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		restart_button = (Button) findViewById(R.id.button2);

		punishment_button = (Button) findViewById(R.id.button1);
		// back_button = (Button) findViewById(R.id.button3);
		// punishBtn = (Button) findViewById(R.id.punishBtn);
		lose = (TextView) findViewById(R.id.textView5);
		click_button.setBackgroundResource(R.drawable.click);

		// v.setVisibility(View.INVISIBLE);
		// punishBtn.setVisibility(View.INVISIBLE);
		restart_button.setVisibility(View.INVISIBLE);
		punishment_button.setVisibility(View.INVISIBLE);
		preference = (TextView) findViewById(R.id.textView3);
		DisplayParameter(0);
		final AnimationSet aniSet = new AnimationSet(true);
		final ScaleAnimation scaleAn = new ScaleAnimation(1.0f, 1.02f, 1.0f,
				1.02f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		final ScaleAnimation scaleAni = new ScaleAnimation(1.02f, 1.0f, 1.02f,
				1.02f);

		scaleAni.setDuration(20);
		scaleAn.setDuration(20);
		scaleAni.setStartOffset(20);

		aniSet.addAnimation(scaleAn);
		aniSet.addAnimation(scaleAni);

		lose.setVisibility(View.INVISIBLE);
		click_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				click_times--;
				DisplayParameter(50 - click_times);
				preference.setVisibility(View.INVISIBLE);
				if (click_times <= random_times) {
					SoundPlayer.playclaps();
					setFinish();
				} else {
					click_button.startAnimation(aniSet);
					SoundPlayer.playball();
				}
			}
		});

		restart_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				uMengClick("count_game_click");
				random_times = Math.abs(random.nextInt()) % 50;
				click_times = 50;
				click_button.setClickable(true);
				click_button.setBackgroundResource(R.drawable.click);
				lose.setVisibility(View.INVISIBLE);
				restart_button.setVisibility(View.INVISIBLE);
				DisplayParameter(50 - click_times);
			}
		});

		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				finish();
			}
		});

		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(random_50.this, PunishActivity.class);
				startActivity(intentGo);
				finish();
			}
		});
	}

	protected void setFinish() {
		click_button.setClickable(false);
		click_button.setBackgroundResource(R.drawable.over);
		lose.setVisibility(View.VISIBLE);
		restart_button.setVisibility(View.VISIBLE);
		punishment_button.setVisibility(View.VISIBLE);
		// punishment_button.setVisibility(View.VISIBLE);
	}

	protected void DisplayParameter(int time) {
		Probability = (TextView) findViewById(R.id.textView2);
		Probability.setText("危险级数：" + Math.min((time + 1) * 2, 100) + "%\n点击了"
				+ time + "次");
	}
}
