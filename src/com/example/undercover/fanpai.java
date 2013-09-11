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
import android.widget.LinearLayout;
import android.widget.TextView;

public class fanpai extends BaseActivity {
	private String son;
	private String[] content;
	private String[] libary;
	private TextView txtShenfen;
	private TextView textViewab;
	private Button btnchangeword;
	private LinearLayout changeword;
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
	private String[] word;
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
		changeword = (LinearLayout) findViewById(R.id.changeword);
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
		word = gameInfo.getString("word", "eat").trim().split(",");

		animation = (AnimationSet) AnimationUtils.loadAnimation(this,
				R.anim.reflash);

		Log.i("fanpai",
				"***************获得的词组为："
						+ gameInfo.getString("word", "").trim());
		initFanpai();


		// Bundle bundle = this.getIntent().getExtras();
		// isBlank = bundle.getBoolean("isBlank");
		// isShow = bundle.getBoolean("isShow");
		// peopleCount = bundle.getInt("peopleCount");
		// underCount = bundle.getInt("underCount");

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

		initPan(nowIndex);

		btnOK.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.playball();
				if (nowIndex >= 1) {
					canchangeword = false;
					btnchangeword.setBackgroundResource(R.drawable.update);
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

		int num = Math.abs(random.nextInt()) % word.length;
		libary = getResources().getStringArray(getWords(word[num]));
		int selectindex = Math.abs(random.nextInt()) % libary.length;
		content = getRandomString(libary[selectindex]);
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
		// 设置content
		setContent(ret);
		setSon(son);
		return ret;
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean("isBlank", isBlank);
		savedInstanceState.putBoolean("isShow", isShow);
		savedInstanceState.putInt("peopleCount", peopleCount);
		savedInstanceState.putInt("underCount", underCount);
		savedInstanceState.putStringArray("content", content);
		savedInstanceState.putInt("nowIndex", nowIndex);
		Log.d("saved", "onSaveInstanceState");
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		onBackPressed();
	}
	
	private int getWords(String str){
		return getResources().getIdentifier("com.example.undercover:array/"+str, null, null);
	}
}
