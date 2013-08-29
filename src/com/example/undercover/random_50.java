package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class random_50 extends BaseActivity {
	// int random_times;
	int click_times;
	private ImageView click_button;
	private ImageView btnreturn;
	private Button restart_button;

	private Button punishment_button;
	private Button back_button;
	// 添加和减少人数按钮
	private Button addPeopleButton, subPeopleButton;

	// 开始游戏按钮
	private Button startClickme;
	
	// 设置人数的 层
	private RelativeLayout setPeople;
	
	private TextView Probability;
	private TextView clicktimes;
	private TextView preference;
	
	// 显示人数的textView
	private TextView countTextView;
	
	private TextView lose;
	private String dangerrate;
	private String click;
	private String ci;
	
	// 参与人数，默认为 6
	private int peopleCount = 6;
	private static int randomLimit;
	
	Random random = new Random();
	int random_times;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_50);
		dangerrate = getResources().getString(R.string.dangerrate);
		click = getResources().getString(R.string.click);
		ci = getResources().getString(R.string.ci);
		click_button = (ImageView) findViewById(R.id.imageBtnMain);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		restart_button = (Button) findViewById(R.id.button2);
		
		peopleCount = gameInfo.getInt("peopleCount", 4);

		// 设置人数的相关按钮和层
		addPeopleButton = (Button) findViewById(R.id.addPeople);
		subPeopleButton = (Button) findViewById(R.id.subPeople);
		startClickme = (Button) findViewById(R.id.startClickme);
		countTextView = (TextView) findViewById(R.id.peopleCount);
		setPeople = (RelativeLayout) findViewById(R.id.setPeopleCount);
		countTextView.setText(Integer.toString(peopleCount));
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

		// 设置添加和减少按钮的监听
		addPeopleButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				SoundPlayer.playball();
				if(peopleCount == 2){
					subPeopleButton.setBackgroundResource(R.drawable.popo72);
					subPeopleButton.setClickable(true);
				}
				peopleCount ++;
				countTextView.setText(Integer.toString(peopleCount));
			}
		});
		
		subPeopleButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				SoundPlayer.playball();
				if(peopleCount > 2){
					peopleCount--;
				}else{
					v.setBackgroundResource(R.drawable.popogray72);
					v.setClickable(false);
				}
				countTextView.setText(Integer.toString(peopleCount));
			}
		});
		
		startClickme.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				SoundPlayer.playball();
				setPeople.setVisibility(View.INVISIBLE);
				click_button.setVisibility(View.VISIBLE);
				randomLimit = peopleCount * 5;
				click_times = randomLimit;
				random_times = Math.abs(random.nextInt()) % randomLimit;
				DisplayParameter(0);
				preference.setVisibility(View.INVISIBLE);
				Log.d("Tag",String.valueOf(randomLimit));
				Log.d("Tag",String.valueOf(random_times));
			}
		});
		
		
		lose.setVisibility(View.INVISIBLE);
		click_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				click_times--;
				DisplayParameter(randomLimit - click_times);
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
				random_times = Math.abs(random.nextInt()) % randomLimit;
				click_times = randomLimit;
				click_button.setClickable(true);
				click_button.setBackgroundResource(R.drawable.click);
				lose.setVisibility(View.INVISIBLE);
				restart_button.setVisibility(View.INVISIBLE);
				punishment_button.setVisibility(View.INVISIBLE);
				Log.d("Tag",String.valueOf(randomLimit));
				Log.d("Tag",String.valueOf(random_times));
				DisplayParameter(randomLimit - click_times);
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
		Probability.setText(dangerrate + Math.floor(((float)time/(float)randomLimit)*100) +click 
				+ time + ci);
	}

	// 退出确认
//	public void onBackPressed() {  
	// new AlertDialog.Builder(this).setTitle("确认退出吗？")
//    	    .setIcon(android.R.drawable.ic_dialog_info)  
	// .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//    	  
//    	        @Override  
//    	        public void onClick(DialogInterface dialog, int which) {  
	// // 点击“确认”后的操作
//    	        random_50.this.finish();  
//    	  
//    	        }  
//    	    })  
	// .setNegativeButton("返回", new DialogInterface.OnClickListener() {
//    	  
//    	        @Override  
//    	        public void onClick(DialogInterface dialog, int which) {  
	// // 点击“返回”后的操作,这里不设置没有任何操作
	// }
//    	    }).show();  
//    	// super.onBackPressed();  
//	}  
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		onBackPressed();
	}
}
