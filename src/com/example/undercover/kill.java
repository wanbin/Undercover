package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class kill extends BaseActivity {
	private String son;
	private String[] content;
	private TextView txtShenfen;
	private TextView textViewab;
	private Button imagePan;
	/** 按钮--记住了，传给下一位 */
	private Button btnOK;
	private ImageView imagebg;
	private Random random;
	private int nowIndex = 1;
	private boolean isShow;
	//
	private int peopleCount;
	private int killerCount;
	private int policeCount;

	private SharedPreferences gameInfo;
	private boolean canchangeword = true;
	private Animation animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kill);
		initBtnBack(R.id.btnback);
		//
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		btnOK = (Button) findViewById(R.id.btnOk);
		txtShenfen = (TextView) findViewById(R.id.txtShenfen);
		textViewab = (TextView) findViewById(R.id.textViewab);
		imagePan = (Button) findViewById(R.id.imagePan);
		imagebg = (ImageView) findViewById(R.id.imagebg);

		random = new Random();

		// 游戏一轮结束后，快速开始用。
		gameInfo = getSharedPreferences("gameInfo", 0);
		isShow = gameInfo.getBoolean("isShow", false);
		peopleCount = gameInfo.getInt("peopleCount", 4);
		killerCount = gameInfo.getInt("killerCount", 1);
		policeCount = gameInfo.getInt("policeCount", 1);

		animation = (AnimationSet) AnimationUtils.loadAnimation(this,
				R.anim.reflash);

		initFanpai();
		initBtnInfo(R.id.btninfo, "请记着自己的编号和身份，把手机交与下个玩家\n1.请避免被别的玩家看到\n");


		initPan(nowIndex);

		btnOK.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.playball();
				if (nowIndex <= content.length) {
					initPan(nowIndex);
				} else {
					Intent goMain = new Intent();
					goMain.setClass(kill.this, KillGuess.class);
					startActivity(goMain);
					uMengClick("click_kill_pai_last");
					finish();
				}
			}
		});

		btnOK.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_nextont_un);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_nextone);
				}
				return false;
			}
		});

		imagePan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nowIndex == 1) {
					uMengClick("click_undercover_pai_first");
				}
				v.setVisibility(View.INVISIBLE);
				setContentVis(true);
				// 在这里更新nowIndex，不至于呀恢复时错开一个
				nowIndex++;
				SoundPlayer.playball();
			}
		});

		imagePan.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					imagePan.setBackgroundResource(stringToId("btnun_"
							+ nowIndex,
							"drawable"));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					imagePan.setBackgroundResource(stringToId(
							"btn_"
							+ nowIndex,
							"drawable"));
				}
				return false;
			}
		});


	}

	// 重新翻牌
	protected void initFanpai() {
		content = getRandomString();
		nowIndex = 1;
		setContentVis(false);
		initPan(nowIndex);
	}

	protected void initPan(int index) {
		setContentVis(false);
		if (index > 12)
			index = 12;
		imagePan.setBackgroundResource(stringToId("btn_" + index, "drawable"));
		txtShenfen.setText(content[index - 1]);
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	protected void setContentVis(boolean show) {
		if (show) {
			btnOK.setVisibility(View.VISIBLE);
			txtShenfen.setVisibility(View.VISIBLE);
			textViewab.setVisibility(View.VISIBLE);
			imagePan.setVisibility(View.INVISIBLE);
			imagebg.setVisibility(View.INVISIBLE);
		} else {
			btnOK.setVisibility(View.INVISIBLE);
			imagebg.setVisibility(View.VISIBLE);
			txtShenfen.setVisibility(View.INVISIBLE);
			textViewab.setVisibility(View.INVISIBLE);
			imagePan.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 获取随机的一堆词条
	 * 
	 * @param contnettxt
	 * @return
	 */
	private String[] getRandomString() {
		String[] ret = new String[peopleCount];
		for (int n = 0; n < ret.length; n++) {
			ret[n] = "平民";
		}
		ret[Math.abs(random.nextInt()) % peopleCount] = "法官";
		for (int i = 0; i < policeCount; i++) {
			int tem;
			do {
				tem = Math.abs(random.nextInt()) % peopleCount;
			} while (!ret[tem].equals("平民"));
			ret[tem] = "警察";
		}
		for (int i = 0; i < killerCount; i++) {
			int tem;
			do {
				tem = Math.abs(random.nextInt()) % peopleCount;
			} while (!ret[tem].equals("平民"));
			ret[tem] = "杀手";
		}
		// 设置content
		setContent(ret);
		setSon(son);
		return ret;
	}

	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		onBackPressed();
	}
	
}
