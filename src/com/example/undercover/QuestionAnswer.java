package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class QuestionAnswer extends BaseActivity {
	
	// 标志位，游戏是否开始，默认否
	private boolean isBegin = false;
	// 标志位，游戏是否结束，默认否
	private boolean isOver	= false;
	private TextView punish_0;
	private int timeLimit;
	private ImageView imageNext;
	private TextView questionNext;
	private ProgressBar proBar;
	/** 是否需要显示进度条 */
	private boolean isShowBar;
	/** 是否加载过TimeTask */
	private boolean isTimeRun;
	/** 惩罚页面跳转按钮 */
	private Button intentPunish;
	private int startSec = 2000;
	private int endSec = 6000;
	private String nextQuestion;
	private String GameOver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		nextQuestion = getResources().getString(R.string.nextQuestion);
		GameOver = getResources().getString(R.string.GameOver);
		punish_0	= (TextView)findViewById(R.id.question);
		punish_0.setPadding(10, 0, 0, 0);
		intentPunish	= (Button)findViewById(R.id.intent_punish);
		intentPunish.setVisibility(View.INVISIBLE);
		
		timeLimit = MathUtil.getInstance().getRondom(startSec, endSec);
//		timeLimit	= 1000;
		final FrameLayout frame	= (FrameLayout)findViewById(R.id.question_frame);
		imageNext	= (ImageView)findViewById(R.id.question_image);
		imageNext.setBackground(getResources().getDrawable(R.drawable.popo152));
		questionNext	= (TextView)findViewById(R.id.questionNext);
		questionNext.setText("Ready Go !");
		
		proBar	= (ProgressBar)findViewById(R.id.question_proar);
		proBar.setMax(3000);
		proBar.setVisibility(View.INVISIBLE);
		
		// 动画效果
		final AnimationSet aniSet = new AnimationSet(true);
		final ScaleAnimation scaleAni = new ScaleAnimation(1.0f, 1.02f, 1.0f,1.02f);
		final ScaleAnimation scaleAn = new ScaleAnimation(1.02f, 1f, 1.02f, 1f);
		scaleAni.setDuration(10);
		scaleAn.setDuration(10);
		aniSet.addAnimation(scaleAni);
		aniSet.addAnimation(scaleAn);
		
		// imageView（下一题按钮所在ImageView）点击事件
		imageNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 第一次进入游戏
				if (!isBegin) {
					isBegin	= true;
					if(!isTimeRun){
						Timer timer	= new Timer();
						timer.schedule(timetask, 0, 10);
						isTimeRun = true;
					}
					questionNext.setText(nextQuestion);
					getNextQuestion();
					SoundPlayer.playball();
					proBar.setVisibility(View.VISIBLE);
					proBar.incrementProgressBy(3000);
					isShowBar = true;
					imageNext.setClickable(false);
					imageNext.setBackgroundResource(R.drawable.popogray152);
					frame.startAnimation(aniSet);
				}else{
					// 游戏未结束，继续下一题目,结束后的操作由时间控制，
					if(!isOver){
						getNextQuestion();
						
						proBar.setVisibility(View.VISIBLE);
						proBar.incrementProgressBy(3000);
						isShowBar = true;
						imageNext.setClickable(false);
						imageNext.setBackgroundResource(R.drawable.popogray152);
						frame.startAnimation(aniSet);
					}else{
						// 游戏结束后的，显示开始惩罚按钮
						// showPunish();
						imageNext.setBackgroundResource(R.drawable.popogray152);
//						restartActivity();
					}
				}
			}
		});
		
		// 惩罚页面跳转
		intentPunish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpPunish();
				restartActivity();
			}
		});
		
		// 返回按钮
		ImageView backView	= (ImageView)findViewById(R.id.question_forBack);
		backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				finish();
			}
		});
	}

	// 接受时间
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(!isOver){
				addTenMMS();
			}
			super.handleMessage(msg);
		}
	};
	// 传递时间
	private TimerTask timetask = new TimerTask() {
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	/**
	 * 计时加10毫秒
	 */
	private void addTenMMS(){
		timeLimit	-=1;
		
		// 控制游戏
		if(timeLimit<=0){
			// 游戏结束后的操作
			isOver	= true;
			isShowBar	= false;
			intentPunish.setVisibility(View.VISIBLE);
			imageNext.setBackgroundResource(R.drawable.popogray152);
			imageNext.setClickable(false);
			punish_0.setText("");
			questionNext.setText(GameOver);
			
		}
		
		// 控制进度条
		if (isShowBar) {
			if(proBar.getProgress()<=0){
				isShowBar	= false;
				proBar.setVisibility(View.INVISIBLE);
				imageNext.setClickable(true);
				imageNext.setBackgroundResource(R.drawable.popo152);
				
			}
			proBar.incrementProgressBy(-10);
		}
	}
	
	/**
	 * 进入下一题（每次取两道题目）
	 */
	private void  getNextQuestion(){
		int hardQuestion	= (int)Math.floor(Math.random()*45);
		punish_0.setText("1、" + PunishProps.getQestionHard(hardQuestion));
	}
	
	private void restartActivity(){
		isOver	= false;
		isBegin	= false;
		isShowBar	= false;
		timeLimit = MathUtil.getInstance().getRondom(startSec, endSec);
//		timeLimit	= 1000;
		imageNext.setClickable(true);
		questionNext.setText("Ready Go !");
		imageNext.setBackgroundResource(R.drawable.popo152);
//		proBar.incrementProgressBy(-3000);
		proBar.setVisibility(View.INVISIBLE);
		punish_0.setText("");
		proBar.incrementProgressBy(-(proBar.getProgress()));
		intentPunish.setVisibility(View.INVISIBLE);
//		timetask.cancel();
	}
	
	// 跳转至开始惩罚页面
	private void jumpPunish() {
		Intent goChat = new Intent();
		goChat.setClass(QuestionAnswer.this, PunishActivity.class);
		startActivity(goChat);
	}
}
