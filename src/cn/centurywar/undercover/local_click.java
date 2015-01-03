package cn.centurywar.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class local_click extends BaseActivity {
	int click_times;
	private Button click_button;
	private Button punishment_button;
	
	// 参与人数，默认为 6
	private int peopleCount = 5;
	private static int randomLimit;
	Random random = new Random();
	int random_times;
	boolean isOver=false;
	TextView txtDes;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_click);
		click_button = (Button) findViewById(R.id.imageBtnMain);
		// 设置人数的相关按钮和层
		punishment_button = (Button) findViewById(R.id.btn_punish);
		txtDes = (TextView) findViewById(R.id.txtDes);
		punishment_button.setVisibility(View.INVISIBLE);
		uMengClick("game_click");
		click_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				click_times++;
				DisplayParameter(click_times);
				if(isOver){
					return;
				}

				if (click_times >= random_times) {
					SoundPlayer.faile();
					setFinish();
					click_button.setText("罚");
					isOver=true;
				} else {
					SoundPlayer.click();
					click_button.setText(""+click_times);
				}
				
			}
		});
		
		
		click_button.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(isOver){
					initStart();
					isOver=false;
				}
				return true;
			}
		});

//		restart_button.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				initStart();
//			}
//		});

		punishment_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(local_click.this, local_punish.class);
				startActivity(intentGo);
			}
		});
		initStart();
	}

	protected void initStart() {
		uMengClick("count_game_click");
		randomLimit = peopleCount * 3;
		click_times = 0;
		random_times = Math.abs(random.nextInt()) % randomLimit+2;
		click_button.setClickable(true);
//		restart_button.setVisibility(View.INVISIBLE);
		punishment_button.setVisibility(View.INVISIBLE);
		DisplayParameter(0);
		txtDes.setVisibility(View.GONE);
		SoundPlayer.start();
	}

	protected void setFinish() {
//		click_button.setClickable(false);
//		restart_button.setVisibility(View.VISIBLE);
		punishment_button.setVisibility(View.VISIBLE);
		txtDes.setVisibility(View.VISIBLE);
		setGameIsNew(ConstantControl.GAME_CLICK,false);
	}

	protected void DisplayParameter(int time) {
		if(time==0){
			click_button.setText("点");
		}else{
			float rate=0.5f+time/15.0f*0.5f;
			click_button.setAlpha(rate);
		}
	}

}
