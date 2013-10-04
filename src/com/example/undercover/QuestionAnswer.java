package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class QuestionAnswer extends BaseActivity {

	// 标志位，游戏是否开始，默认否
	private boolean isBegin = false;
	// 标志位，游戏是否结束，默认否
	private boolean isOver = false;
	private TextView punish_0, questionTitle;
	private int timeLimit;
	private Button imageNext;
	private ProgressBar proBar;
	/** 是否需要显示进度条 */
	private boolean isShowBar;
	/** 是否加载过TimeTask */
	/** 惩罚页面跳转按钮 */
	private Button intentPunish;
	private int startSec = 2000;
	private int endSec = 12000;
	private String nextQuestion;
	private String GameOver;
	private int peopleCount;
	// 问答页面倒计时时间3000毫秒
	private final static int TIME = 5000;

	private Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		peopleCount = gameInfo.getInt("peopleCount", 4);
		endSec = Math.min(12000, startSec + peopleCount * 1000);

		initBtnBack(R.id.btnback);
		initShareBtn();

		punish_0 = (TextView) findViewById(R.id.question);
		questionTitle = (TextView) findViewById(R.id.questionTitle);
		intentPunish = (Button) findViewById(R.id.intent_punish);
		timeLimit = MathUtil.getInstance().getRondom(startSec, endSec);

		// timeLimit = 1000;
		imageNext = (Button) findViewById(R.id.question_image);
		proBar = (ProgressBar) findViewById(R.id.question_proar);
		proBar.setMax(TIME);
		proBar.setVisibility(View.INVISIBLE);
		initBtnInfo(R.id.btninfo, strFromId("question_rule"));

		// imageView（下一题按钮所在ImageView）点击事件
		imageNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 第一次进入游戏
				if (!isBegin) {
					restartActivity();
					SoundPlayer.playJishi();
					isBegin = true;
					if (timer == null) {
						timer = new Timer();
						timer.schedule(timetask, 0, 10);
					}
					getNextQuestion();
					SoundPlayer.playball();
					proBar.setVisibility(View.VISIBLE);
					proBar.incrementProgressBy(TIME);
					isShowBar = true;
					intentPunish.setVisibility(View.GONE);
				} else {
					// 游戏未结束，继续下一题目,结束后的操作由时间控制，
					if (!isOver) {
						getNextQuestion();
						proBar.setVisibility(View.VISIBLE);
						proBar.incrementProgressBy(TIME);
						isShowBar = true;
					} else {
						// 游戏结束后的，显示开始惩罚按钮
						// showPunish();
						restartActivity();
					}
				}
				imageNext.setText("下一题");
				imageNext.setClickable(false);
				setBtnGray(imageNext);
			}
		});

		// 惩罚页面跳转
		intentPunish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goChat = new Intent();
				goChat.setClass(QuestionAnswer.this, PunishActivity.class);
				startActivity(goChat);
			}
		});
	}

	// 接受时间
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
				addTenMMS();
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
	private void addTenMMS() {
		if (timeLimit > 0) {
			timeLimit -= 1;
		}

		// 控制游戏
		if (timeLimit <= 0) {
			// 游戏结束后的操作
			isOver = true;
			intentPunish.setVisibility(View.VISIBLE);
			proBar.setVisibility(View.INVISIBLE);
			imageNext.setClickable(true);
			imageNext.setText("重新开始");
			punish_0.setText("");
			SoundPlayer.stopJishi();
			isBegin = false;
			setBtnGreen(imageNext);
		}

		// 控制进度条
		if (isShowBar) {
			if (proBar.getProgress() <= 0) {
				isShowBar = false;
				proBar.setVisibility(View.VISIBLE);
				imageNext.setClickable(true);
				imageNext.setText("下一题");
				setBtnGreen(imageNext);
			}
			proBar.incrementProgressBy(-10);
		}
	}

	/**
	 * 进入下一题（每次取两道题目）
	 */
	private void getNextQuestion() {
		questionTitle.setText(strFromId("txtQuictAsk"));
		punish_0.setText(PunishProps.getRaoKouLing());
	}

	private void restartActivity() {
		isOver = false;
		isBegin = false;
		isShowBar = true;
		timeLimit = MathUtil.getInstance().getRondom(startSec, endSec);
		imageNext.setClickable(true);
		proBar.setVisibility(View.INVISIBLE);
		punish_0.setText("");
		proBar.incrementProgressBy(-(proBar.getProgress()));
		intentPunish.setVisibility(View.GONE);
	}

	public void onResume() {
		super.onResume();
		restartActivity();
	}

	public void onPause() {
		super.onPause();
		SoundPlayer.stopJishi();
	}
}
