package com.example.undercover;

import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private String dangerrate;
	private String click;
	private String ci;
	
	Random random = new Random();
	int random_times = Math.abs(random.nextInt()) % 50;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_50);
		dangerrate = getResources().getString(R.string.dangerrate);
		click = getResources().getString(R.string.click);
		ci = getResources().getString(R.string.ci);
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
				SoundPlayer.playball();
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
				SoundPlayer.playball();
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
		Probability.setText(dangerrate + Math.min((time + 1) * 2, 100) +click 
				+ time + ci);
	}
//退出确认
//	public void onBackPressed() {  
//    	new AlertDialog.Builder(this).setTitle("确认退出吗？")  
//    	    .setIcon(android.R.drawable.ic_dialog_info)  
//    	    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
//    	  
//    	        @Override  
//    	        public void onClick(DialogInterface dialog, int which) {  
//    	        // 点击“确认”后的操作  
//    	        random_50.this.finish();  
//    	  
//    	        }  
//    	    })  
//    	    .setNegativeButton("返回", new DialogInterface.OnClickListener() {  
//    	  
//    	        @Override  
//    	        public void onClick(DialogInterface dialog, int which) {  
//    	        // 点击“返回”后的操作,这里不设置没有任何操作  
//    	        }  
//    	    }).show();  
//    	// super.onBackPressed();  
//	}  
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		onBackPressed();
	}
}
