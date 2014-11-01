package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Push extends BaseActivity {
	int click_times;
	private Button restart_button;
	private Button punishment_button;

	private int peopleCount = 6;
	private Random random = new Random();
	private TableLayout pushLayout;
	private int randomNum;
	private int maxNum = 80;
	private int min;
	private int max;
	private int rowcount = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.push);
		initBtnBack(R.id.btnback);
		initShareBtn();

		peopleCount = gameInfo.getInt("peopleCount", 4);
		// initBtnInfo(R.id.btninfo, strFromId("clicksay"));

		if (disWidth < 400) {
			maxNum = 480;
			rowcount = 6;
		}
		// 设置人数的相关按钮和层
		punishment_button = (Button) findViewById(R.id.btn_punish);
		punishment_button.setVisibility(View.INVISIBLE);
		restart_button = (Button) findViewById(R.id.btn_restart);
		restart_button.setVisibility(View.INVISIBLE);
		pushLayout = (TableLayout) findViewById(R.id.pushTable);
		restart_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				initGame();
				restart_button.setVisibility(View.INVISIBLE);
				punishment_button.setVisibility(View.INVISIBLE);

			}
		});

		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(Push.this, local_punish.class);
				startActivity(intentGo);
				SoundPlayer.playball();
			}
		});
		setBtnGreen(restart_button);
		setBtnGreen(punishment_button);
		initGame();
	}

	private void initGame() {
		uMengClick("game_push_start");
		min = 0;
		max = maxNum + 1;
		randomNum = Math.abs(random.nextInt()) % maxNum + 1;
		if (pushLayout.getChildCount() < maxNum / rowcount) {
			int num = 1;
			for (int i = 0; i <= Math.ceil(maxNum / rowcount); i++) {
				if (num > maxNum) {
					break;
				}
				TableRow newrow = new TableRow(this);
				for (int m = 0; m < rowcount; m++) {
					Button select = new Button(this);
					select.setText("" + num);
					select.setTag(num);
					select.setTextSize(12);
					select.setTextColor(getResources().getColor(
							R.color.Writegray));
					setBtnPinkCer(select);
					select.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							tapIndex((Integer) v.getTag());
						}
					});

					newrow.addView(select, disWidth / rowcount, disWidth
							/ rowcount);
					num++;
				}
				pushLayout.addView(newrow);
			}
		} else {
			for (int i = 1; i <= maxNum; i++) {
				Button tembtn = (Button) pushLayout.findViewWithTag(i);
				setBtnPinkCer(tembtn);
				tembtn.setClickable(true);
			}
		}
	}

	// protected void initStart() {
	// int num = 1;
	// for (int i = 1; i < Math.ceil(maxNum / rowcount); i++) {
	// TableRow newrow = new TableRow(this);
	// for (int m = 0; m < rowcount; m++) {
	// if (num > maxNum) {
	// break;
	// }
	// Button select = new Button(this);
	// select.setText("" + num);
	// select.setTag(num);
	// select.setTextColor(getResources().getColor(R.color.Writegray));
	// newrow.addView(select, disWidth / rowcount, disWidth / rowcount);
	// num++;
	// if (num <= max && num >= min) {
	// setBtnPinkCer(select);
	// } else {
	// setBtnPinkCer(select);
	// }
	// }
	// pushLayout.addView(newrow);
	// }
	// }

	protected void tapIndex(int index) {
		if (randomNum > index) {
			min = index;
		} else if (randomNum < index) {
			max = index;
		} else {
			max = index + 1;
			min = index - 1;
			// siampleTitle("GameOver");
			setFinish();
			SoundPlayer.playNormalSoure();
		}
		updateBtnStatus();
	}

	protected void updateBtnStatus() {
		for (int i = 1; i <= maxNum; i++) {
			Button tembtn = (Button) pushLayout.findViewWithTag(i);
			if (i >= max || i <= min) {
				setBtnGrayCer(tembtn);
				tembtn.setClickable(false);
			} else {
				setBtnPinkCer(tembtn);
				tembtn.setClickable(true);
			}
		}
	}

	protected void setFinish() {
		restart_button.setVisibility(View.VISIBLE);
		punishment_button.setVisibility(View.VISIBLE);
	}
}
