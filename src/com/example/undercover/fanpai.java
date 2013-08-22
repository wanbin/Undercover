package com.example.undercover;

import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class fanpai extends BaseActivity {
	private String son;
	private String[] content;
	private String[] libary;
	private TextView txtShenfen;
	private TextView txtIndex;
	private TextView textViewab;
	private ImageView imagePan;
	private Button btnOK;
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
	private String[] word;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pai);
		//
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		isChecked = false;
		btnOK = (Button) findViewById(R.id.btnOk);
		txtIndex = (TextView) findViewById(R.id.txtIndex);
		txtShenfen = (TextView) findViewById(R.id.txtShenfen);
		textViewab = (TextView) findViewById(R.id.textViewab);
		imagePan = (ImageView) findViewById(R.id.imagePan);
		blank = getResources().getString(R.string.blank);

		random = new Random();
		if (savedInstanceState == null) {
			gameInfo = getSharedPreferences("gameInfo", 0);
			isBlank = gameInfo.getBoolean("isBlank", false);
			isShow = gameInfo.getBoolean("isShow", false);
			peopleCount = gameInfo.getInt("peopleCount", 4);
			underCount = gameInfo.getInt("underCount", 1);
			word	= gameInfo.getString("word", "").trim().split(",");
			if(word.length==1){
				if(word[0].equals("")){
					//默认为 吃货选项
					libary = getResources().getStringArray(R.array.eat);
				}else{
					
					libary = getResources().getStringArray(getWords(word[0]));
				}
			}else{
				//word.length>1
				int num	= new Random().nextInt()%word.length;
				libary	= getResources().getStringArray(getWords(word[num]));
			}
			
			
			int selectindex = Math.abs(random.nextInt()) % libary.length;
			content = getRandomString(libary[selectindex]);

		} else {
			isBlank = savedInstanceState.getBoolean("isBlank");
			isShow = savedInstanceState.getBoolean("isShow");
			peopleCount = savedInstanceState.getInt("peopleCount");
			underCount = savedInstanceState.getInt("underCount");
			content = savedInstanceState.getStringArray("content");
			nowIndex = savedInstanceState.getInt("nowIndex");
		}

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
				if (nowIndex <= content.length) {
					if (nowIndex == 1) {
						uMengClick("click_undercover_pai_first");
					}

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
				v.setVisibility(View.INVISIBLE);
				txtIndex.setVisibility(View.INVISIBLE);
				setContentVis(true);
				// 在这里更新nowIndex，不至于呀恢复时错开一个
				nowIndex++;
				SoundPlayer.playball();
			}
		});

	}

	protected void initPan(int index) {

		imagePan.setVisibility(View.VISIBLE);
		txtIndex.setVisibility(View.VISIBLE);
		txtIndex.setText("" + index);
		txtShenfen.setText(content[index - 1]);
		setContentVis(false);
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	protected void setContentVis(boolean show) {
		if (show) {
			btnOK.setVisibility(View.VISIBLE);
			txtIndex.setVisibility(View.INVISIBLE);
			txtShenfen.setVisibility(View.VISIBLE);
			textViewab.setVisibility(View.VISIBLE);

		} else {
			btnOK.setVisibility(View.INVISIBLE);
			txtIndex.setVisibility(View.VISIBLE);
			txtShenfen.setVisibility(View.INVISIBLE);
			textViewab.setVisibility(View.INVISIBLE);
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
