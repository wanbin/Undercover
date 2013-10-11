package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class fanpai extends BaseActivity {
	private String son;
	private String[] content;
	private String[] libary;
	private TextView txtShenfen;
	private TextView textViewab;
	private Button btnchangeword;
	private Button imagePan;
	/** 按钮--记住了，传给下一位 */
	private Button btnOK;
	private ImageView imagebg;
	private Random random;
	private int nowIndex = 1;
	private boolean isShow;
	private boolean isBlank;
	private boolean isChecked;
	//
	private int peopleCount;
	private int underCount;

	private SharedPreferences gameInfo;
	private String blank;
	private boolean canchangeword = true;
	private String word;
	private Animation animation;
	private LinearLayout linChangeword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pai);
		initBtnBack(R.id.btnback);
		//
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		isChecked = false;
		btnOK = (Button) findViewById(R.id.btnOk);
		btnchangeword = (Button) findViewById(R.id.btnchangeword);
		txtShenfen = (TextView) findViewById(R.id.txtShenfen);
		textViewab = (TextView) findViewById(R.id.textViewab);
		imagePan = (Button) findViewById(R.id.imagePan);
		imagebg = (ImageView) findViewById(R.id.imagebg);
		linChangeword = (LinearLayout) findViewById(R.id.changewordlin);
		blank = getResources().getString(R.string.blank);
		random = new Random();

		if (lastGameType().equals("kill")) {
			linChangeword.setVisibility(View.INVISIBLE);
		}

		// 游戏一轮结束后，快速开始用。
		gameInfo = getSharedPreferences("gameInfo", 0);
		isBlank = gameInfo.getBoolean("isBlank", false);
		isShow = gameInfo.getBoolean("isShow", false);
		peopleCount = gameInfo.getInt("peopleCount", 4);
		underCount = gameInfo.getInt("underCount", 1);
		word = gameInfo.getString("word", "全部");

		animation = (AnimationSet) AnimationUtils.loadAnimation(this,
				R.anim.reflash);

		Log.i("fanpai",
				"***************获得的词组为："
						+ gameInfo.getString("word", "").trim());
		initFanpai();

		initBtnInfo(R.id.btninfo, strFromId("txtPaiHelp"));

		initPan(nowIndex);

		btnOK.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.playball();
				if (nowIndex >= 1) {
					canchangeword = false;
					btnchangeword.setBackgroundResource(R.drawable.update2);
					// changeword.setVisibility(View.INVISIBLE);
				}
				if (nowIndex <= content.length) {
					initPan(nowIndex);
				} else {
					Bundle bundle = new Bundle();
					// bundle.putInt("peopleCount", peopleCount);
					bundle.putStringArray("content", content);
					bundle.putString("son", son);
					bundle.putInt("underCount", underCount);
					bundle.putBoolean("isShow", isShow);
					Intent goMain = new Intent();
					goMain.putExtras(bundle);
					goMain.setClass(fanpai.this, guess.class);
					startActivity(goMain);
					uMengClick("click_undercover_pai_last");
					finish();
				}
			}
		});

		imagePan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				tapPai(v);
			}
		});

		imagebg.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				tapPai(v);
			}
		});


		setBtnGreen(imagePan);
		setBtnBlue(btnOK);

		// 刷新换词
		btnchangeword.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// v.setVisibility(View.INVISIBLE);
				if (!canchangeword) {
					siampleTitle(strFromId("txtPaiFirstPeople"));
				} else {
					initFanpai();
					btnchangeword.startAnimation(animation);
				}

				// 添加变化动画
			}
		});

	}

	protected void tapPai(View v) {
		if (nowIndex == 1) {
			uMengClick("click_undercover_pai_first");
		}
		v.setVisibility(View.INVISIBLE);
		setContentVis(true);
		// 在这里更新nowIndex，不至于呀恢复时错开一个
		nowIndex++;
		SoundPlayer.playball();
	}

	// 重新翻牌
	protected void initFanpai() {
		// 如果是杀人游戏，界面显示去掉一些东西
		if (lastGameType().equals("kill")) {
			content = getRandomString();
		} else {
			content = getRandomStringUnderCover();
		}
		// 设置content
		setContent(content);
		nowIndex = 1;
		setContentVis(false);
		initPan(nowIndex);
	}

	protected void initPan(int index) {
		setContentVis(false);
		imagePan.setText("" + index);
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
	private String[] getRandomStringUnderCover() {
		libary = getUnderWords(word);
		int selectindex = Math.abs(random.nextInt()) % libary.length;
		String[] children = new String[2];
		children = libary[selectindex].split("_");

		setHasGuessed(children[0] + "_" + children[1]);
		int sonindex = Math.abs(random.nextInt()) % 2;
		son = children[sonindex];
		String father = children[Math.abs(sonindex - 1)];
		String[] ret = new String[peopleCount];
		for (int n = 0; n < ret.length; n++) {
			ret[n] = father;
		}
		for (int i = 0; i < underCount; i++) {
			int tem;
			do {
				tem = Math.abs(random.nextInt()) % peopleCount;
			} while (ret[tem].equals(son));
			ret[tem] = son;
		}

		if (isBlank) {
			for (int i = 0; i < underCount; i++) {
				int tem;
				do {
					tem = Math.abs(random.nextInt()) % peopleCount;
				} while (ret[tem].equals(son));
				ret[tem] = blank;
			}
		}
		setSon(son);
		return ret;
	}

	
	/**
	 * 获取随机的一堆词条
	 * 
	 * @param contnettxt
	 * @return
	 */
	private String[] getRandomString() {
		peopleCount = gameInfo.getInt("peopleCount", 4);
		int policeCount = Math.max((int) Math.floor(peopleCount / 4), 1);
		int killerCount = Math.max((int) Math.floor(peopleCount / 4), 1);
		String[] ret = new String[peopleCount];
		for (int n = 0; n < ret.length; n++) {
			ret[n] = nomalpeople;
		}
		ret[Math.abs(random.nextInt()) % peopleCount] = faguan;
		for (int i = 0; i < policeCount; i++) {
			int tem;
			do {
				tem = Math.abs(random.nextInt()) % peopleCount;
			} while (!ret[tem].equals(nomalpeople));
			ret[tem] = police;
		}
		for (int i = 0; i < killerCount; i++) {
			int tem;
			do {
				tem = Math.abs(random.nextInt()) % peopleCount;
			} while (!ret[tem].equals(nomalpeople));
			ret[tem] = killer;
		}
		// 设置content
		setContent(ret);
		setSon(son);
		return ret;
	}

}
