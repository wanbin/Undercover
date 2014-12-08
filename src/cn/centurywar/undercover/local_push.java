package cn.centurywar.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class local_push extends BaseActivity {
	int click_times;
	private Button restart_button;
	private Button punishment_button;
	private LinearLayout layoutBtn;

	private Random random = new Random();
	private TableLayout pushLayout;
	private int randomNum;
	private int maxNum = 96;
	private int min;
	private int max;
	private int rowcount = 8;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_push);


		if (disWidth < 400) {
			maxNum = 72;
			rowcount = 6;
		}
		// 设置人数的相关按钮和层
		punishment_button = (Button) findViewById(R.id.btnPunish);
//		punishment_button.setVisibility(View.INVISIBLE);
		restart_button = (Button) findViewById(R.id.btnReflash);
//		restart_button.setVisibility(View.INVISIBLE);
		pushLayout = (TableLayout) findViewById(R.id.tablePunish);
		layoutBtn = (LinearLayout) findViewById(R.id.chengfa);
		layoutBtn.setVisibility(View.GONE);
		
		restart_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				initGame();
				layoutBtn.setVisibility(View.GONE);
			}
		});

		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(local_push.this, local_punish.class);
				startActivity(intentGo);
			}
		});
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
					select.setBackgroundResource(R.drawable.fang_pink_pressed);
					select.setTag(num);
					select.setTextSize(12);
					select.setTextColor(getResources().getColor(
							R.color.Writegray));
					select.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							tapIndex((Integer) v.getTag());
						}
					});
					;

					newrow.addView(select, disWidth / rowcount, disHeight/12);
					num++;
				}
				pushLayout.addView(newrow);
			}
		} else {
			for (int i = 1; i <= maxNum; i++) {
				Button tembtn = (Button) pushLayout.findViewWithTag(i);
				tembtn.setClickable(true);
				tembtn.setBackgroundResource(R.drawable.fang_pink_pressed);
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
		}
		SoundPlayer.click();
		updateBtnStatus();
	}

	protected void updateBtnStatus() {
		for (int i = 1; i <= maxNum; i++) {
			Button tembtn = (Button) pushLayout.findViewWithTag(i);
			if (i >= max || i <= min) {
				tembtn.setBackgroundResource(R.drawable.fang_blue_pressed);
//				setBtnGrayCer(tembtn);
				tembtn.setClickable(false);
			} else {
//				setBtnPinkCer(tembtn);
				tembtn.setBackgroundResource(R.drawable.fang_pink_pressed);
				tembtn.setClickable(true);
			}
		}
	}

	protected void setFinish() {
		SoundPlayer.faile();
		layoutBtn.setVisibility(View.VISIBLE);
		setGameIsNew(ConstantControl.GAME_PUSH,false);
	}
}
