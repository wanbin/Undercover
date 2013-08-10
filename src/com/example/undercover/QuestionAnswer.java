package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class QuestionAnswer extends BaseActivity {
	
	private TableLayout baseTable;
	// ready Go,下一题，开始惩罚，公用一个按钮
	private Button baseBtn;
	// 标志位，游戏是否开始，默认否
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
	private TextView textView;
	private Button restartBtn;
	
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
		baseBtn		= new Button(this);
		restartBtn	= new Button(this);
		textView	= (TextView)findViewById(R.id.questionTitle);
		baseTable	= (TableLayout)findViewById(R.id.questionTable);
		baseBtn.setText("Ready Go!");
		restartBtn.setText("重新开始");
		baseTable.addView(baseBtn);
		baseTable.addView(punish_0);
		baseTable.addView(punish_1);
		baseTable.addView(punish_2);
		baseTable.addView(punish_3);
		baseTable.addView(punish_4);
		baseTable.addView(punish_5);
		timeLimit	= MathUtil.getInstance().getRondom(3000, 12000);
//		timeLimit	= 300;
		updateTime();
		
		restartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isOver	= false;
				isBegin	= false;
				baseBtn.setText("Ready Go!");
				baseBtn.setClickable(true);
			}
		});
		
		// Ready Go,下一题，开始惩罚，button点击事件
		baseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 第一次进入游戏
				if (!isBegin) {
					isBegin	= true;
					Timer timer	= new Timer();
					timer.schedule(timetask, 0, 10);
					baseBtn.setText("下一题");
					getNextQuestion();
				}else{
					// 游戏未结束，继续下一题目,结束后的操作由时间控制，
					if(!isOver){
						getNextQuestion();
					}else{
						// 游戏结束后的，显示开始惩罚按钮
						showPunish();
						baseBtn.setClickable(false);
//						baseTable.addView(restartBtn);
					}
				}
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
	TimerTask timetask = new TimerTask() {
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
	}
	
	/**
	 * 计时加10毫秒
	 */
	private void addTenMMS(){
		timeLimit	-=1;
		if(timeLimit<=0){
			// 游戏结束后的操作
			isOver	= true;
			baseBtn.setBackgroundResource(R.drawable.ic_launcher);
			punish_0.setText("");
			isOver	= true;
			baseBtn.setText("开始惩罚");
		}
		updateTime();
	}
	
	private void updateTime(){
		// textView.setText("剩余时间:"+(double)timeLimit/100);
	}
	
	/**
	 * 进入下一题（每次取两道题目）
	 */
	private void  getNextQuestion(){
		int hardQuestion	= (int)Math.floor(Math.random()*45);
		punish_0.setText("1、" + PunishProps.getQestionHard(hardQuestion));
	}
	
}
