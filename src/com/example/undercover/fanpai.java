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
		blank = getResources().getString(R.string.blank);

		random = new Random();

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

		initBtnInfo(R.id.btninfo,
				"请记着自己的编号和身份，把手机交与下个玩家\n1.请避免被别的玩家看到\n2.第一个翻牌的玩家有机会更新词汇");

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

		setBtnGreen(imagePan);
		setBtnBlue(btnOK);

		// 刷新换词
		btnchangeword.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// v.setVisibility(View.INVISIBLE);
				if (!canchangeword) {
					siampleTitle("第一位玩家才可以换词呀~");
				} else {
					initFanpai();
					btnchangeword.startAnimation(animation);
				}

				// 添加变化动画
			}
		});

	}

	// 重新翻牌
	protected void initFanpai() {
		isChecked = false;
		libary = getUnderWords(word);
		int selectindex = Math.abs(random.nextInt()) % libary.length;
		content = getRandomString(libary[selectindex]);
		// 设置空白词
		int blandStr = Math.abs(new Random().nextInt());
		for (int i = 0, len = peopleCount; i < len; i++) {
			if (isBlank) {
				if (!isChecked) {
					if (!content[(i + blandStr) % len].equals(son)) {
						isChecked = true;
						content[(i + blandStr) % len] = blank;
					}
				}
			}
			Log(content[i]);
		}
		// 设置content
		setContent(content);
		nowIndex = 1;

		setContentVis(false);
		initPan(nowIndex);
	}

	protected void initPan(int index) {
		setContentVis(false);
		// if (index > 12)
		// index = 12;
//		 imagePan.setBackgroundResource(stringToId("btn_" + index,
		// "drawable"));
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
	private String[] getRandomString(String contnettxt) {
		String[] children = new String[2];
		children = contnettxt.split("_");
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
