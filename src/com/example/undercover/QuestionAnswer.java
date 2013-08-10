package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class QuestionAnswer extends BaseActivity {
	
	private TableLayout baseTable;
	//标志位，游戏是否开始，默认否
	private boolean isBegin = false;
	// 标志位，游戏是否结束，默认否
	private boolean isOver	= false;
	private TextView punish_0;
	private TextView punish_1;
	private TextView punish_2;
	private TextView punish_3;
	private TextView punish_4;
	private TextView punish_5;
	private int timeLimit;
	private Button restartBtn;
	private ImageView imageNext;
	private TextView questionNext;
	private ProgressBar proBar;
	/** 是否需要显示进度条 */
	private boolean isShowBar;
	/** 是否加载过TimeTask */
	private boolean isTimeRun;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		punish_0	= new TextView(this);
		punish_1	= new TextView(this);
		punish_2	= new TextView(this);
		punish_3	= new TextView(this);
		punish_4	= new TextView(this);
		punish_5	= new TextView(this);
		restartBtn	= new Button(this);
		baseTable	= (TableLayout)findViewById(R.id.questionTable);
		restartBtn.setText("重新开始");
		restartBtn.setVisibility(View.INVISIBLE);
		LinearLayout restartLayout	= new LinearLayout(this);
		restartLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		restartLayout.addView(restartBtn);
		baseTable.addView(punish_0);
		baseTable.addView(punish_1);
		baseTable.addView(punish_2);
		baseTable.addView(punish_3);
		baseTable.addView(punish_4);
		baseTable.addView(punish_5);
		baseTable.addView(restartLayout);
//		timeLimit	= MathUtil.getInstance().getRondom(3000, 12000);
		timeLimit	= 1000;
		final FrameLayout frame	= (FrameLayout)findViewById(R.id.question_frame);
		imageNext	= (ImageView)findViewById(R.id.question_image);
		imageNext.setBackground(getResources().getDrawable(R.drawable.popo152));
		questionNext	= (TextView)findViewById(R.id.questionNext);
		questionNext.setText("Ready Go !");
		
		proBar	= (ProgressBar)findViewById(R.id.question_proar);
		proBar.setMax(3000);
		proBar.setVisibility(View.INVISIBLE);
		restartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isOver	= false;
				isBegin	= false;
				isShowBar	= false;
//				timeLimit	= MathUtil.getInstance().getRondom(3000, 12000);
				timeLimit	= 1000;
				imageNext.setClickable(true);
				questionNext.setText("Ready Go !");
				imageNext.setBackgroundResource(R.drawable.popo152);
				proBar.setVisibility(View.INVISIBLE);
				punish_0.setText("");
				punish_1.setText("");
				punish_2.setText("");
				punish_3.setText("");
				punish_4.setText("");
				punish_5.setText("");
				restartBtn.setVisibility(View.INVISIBLE);
//				timetask.cancel();
				
			}
		});
		//动画效果
		final AnimationSet aniSet = new AnimationSet(true);
		final ScaleAnimation scaleAni = new ScaleAnimation(1.0f, 1.02f, 1.0f,1.02f);
		final ScaleAnimation scaleAn = new ScaleAnimation(1.02f, 1f, 1.02f, 1f);
		final Drawable popoGray	= getResources().getDrawable(R.drawable.popogray);
		scaleAni.setDuration(10);
		scaleAn.setDuration(10);
//		scaleAn.setStartOffset(10);
		aniSet.addAnimation(scaleAni);
		aniSet.addAnimation(scaleAn);
		//imageView（下一题按钮所在ImageView）点击事件
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
					questionNext.setText("下一题");
					getNextQuestion();
				}else{
					// 游戏未结束，继续下一题目,结束后的操作由时间控制，
					if(!isOver){
						getNextQuestion();
					}else{
						// 游戏结束后的，显示开始惩罚按钮
						showPunish();
						questionNext.setText("接受惩罚");
						imageNext.setBackgroundResource(R.drawable.popogray152);
						imageNext.setClickable(false);
					}
				}
				proBar.setVisibility(View.VISIBLE);
				proBar.incrementProgressBy(3000);
				isShowBar = true;
				imageNext.setClickable(false);
				frame.startAnimation(aniSet);
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
	//传递时间
	private TimerTask timetask = new TimerTask() {
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	/**
	 * 游戏结束，显示惩罚操作
	 */
	private void showPunish(){
		// 0默认可见，1不可见但是在页面上留有位置，2移除
		
		int[] num	= MathUtil.getInstance().check(73, 6);
		String[] str	= new String[6];
		for(int i=0;i<num.length;i++){
			str[i]	=PunishProps.getPunish(num[i]);
		}
		punish_0.setText("1"+str[0]);
		punish_1.setText("2"+str[1]);
		punish_2.setText("3"+str[2]);
		punish_3.setText("4"+str[3]);
		punish_4.setText("5"+str[4]);
		punish_5.setText("6"+str[5]);
		restartBtn.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 计时加10毫秒
	 */
	private void addTenMMS(){
		timeLimit	-=1;
		//控制游戏
		if(timeLimit<=0){
			// 游戏结束后的操作
			isOver	= true;
			showPunish();
			questionNext.setText("接受惩罚");
			imageNext.setBackgroundResource(R.drawable.popogray152);
			imageNext.setClickable(false);
		}
		
		//控制进度条
		if(isShowBar){
			if(proBar.getProgress()<=0){
				isShowBar	= false;
				proBar.setVisibility(View.INVISIBLE);
				imageNext.setClickable(true);
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
	
}
