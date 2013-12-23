package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class random_50 extends BaseActivity {
	int click_times;
	private Button click_button;
	private Button restart_button;

	private Button punishment_button;

	private TextView lose;
	// 参与人数，默认为 6
	private int peopleCount = 6;
	private static int randomLimit;

	Random random = new Random();
	int random_times;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_50);
		initBtnBack(R.id.btnback);
		initShareBtn();

		click_button = (Button) findViewById(R.id.imageBtnMain);
		peopleCount = gameInfo.getInt("peopleCount", 4);
		initBtnInfo(R.id.btninfo, strFromId("clicksay"));

		// 设置人数的相关按钮和层
		punishment_button = (Button) findViewById(R.id.btn_punish);
		punishment_button.setVisibility(View.INVISIBLE);
		restart_button = (Button) findViewById(R.id.btn_restart);
		restart_button.setVisibility(View.INVISIBLE);

		lose = (TextView) findViewById(R.id.textView5);
		setBtnBlueColor(click_button);

		click_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				click_times--;
				DisplayParameter(randomLimit - click_times);
				if (click_times <= random_times) {
					SoundPlayer.playclaps();
					setFinish();
				} else {
					SoundPlayer.playball();
				}
			}
		});

		restart_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				initStart();
			}
		});

		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(random_50.this, PunishActivity.class);
				startActivity(intentGo);
				SoundPlayer.playball();
			}
		});
		initStart();
		setBtnPink(restart_button);
		setBtnPink(punishment_button);
	}

	protected void initStart() {
		uMengClick("count_game_click");
		randomLimit = peopleCount * 5;
		click_times = randomLimit;
		random_times = Math.abs(random.nextInt()) % randomLimit;
		click_button.setClickable(true);
		click_button.setBackgroundResource(R.color.bluebtn1);
		restart_button.setVisibility(View.INVISIBLE);
		punishment_button.setVisibility(View.INVISIBLE);
		DisplayParameter(0);
	}

	protected void setFinish() {
		click_button.setClickable(false);
		restart_button.setVisibility(View.VISIBLE);
		punishment_button.setVisibility(View.VISIBLE);
		lose.setText(strFromId("lose"));
		setTouchActionFactory(click_button, R.color.graybtn, R.color.graybtn);
	}

	protected void DisplayParameter(int time) {
		int loseRate = (int) ((double) time * 10 / (double) randomLimit) + 1;
		String strshow=String.format(strFromId("click"), time);
		lose.setText( strshow);
		loseRate = Math.min(9, loseRate);
		int tem1 = stringToId("clickbt_" + loseRate + "1", "color");
		int tem2 = stringToId("clickbt_" + loseRate + "2", "color");
		setTouchActionFactory(click_button, tem1, tem2);
	}

}
