package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class KillGuess extends BaseActivity {
	private TableLayout contentTable;

	// 卧底的词语
	private String son;
	// 0-n人数的词语数组
	private String[] content;
	private TextView txtTitle;
	private Button punishBtn;
	private Button startBtn;
	private Button quickStartBtn;
	private Button btnShare;
	private int killerCount;
	private int policeCount;
	// 平民
	private int otherCount;
	

	private int totalcount;
	private boolean isOver;
	private boolean flag;
	private boolean isGetRestart;
	private boolean isShow;
	private int temindex;
	private Random random = new Random();
	private TextView txtLong;
	// 还有人员分配
	private TextView txtRemain;
	/**
	 * 判断游戏是否结束
	 */
	private boolean gamefinish = false;
	
	private String[] overString;
	private String gusswhoisspy;
	private String undercover;
	private String blank;
	private String aggrieved;
	private String taplong;
	private String gameOver;
	private String gameoverspy;
	private String hy;
	private String ge;

	private String shibaizhe;
	private String fayan;
	private String hao;

	private boolean[] hasClicked;
	private boolean[] hasAliave;// 玩家是否还存活

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_killguess);
		initBtnBack(R.id.btnback);
		initShareBtn();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		//string_start
		overString = getResources().getStringArray(R.array.killstring);
		gusswhoisspy=getResources().getString(R.string.gusswhoisspy); 
		undercover=getResources().getString(R.string.undercover);
		blank=getResources().getString(R.string.blank);                
		aggrieved=getResources().getString(R.string.aggrieved);        
		taplong=getResources().getString(R.string.taplong);            
		gameOver=getResources().getString(R.string.gameOver);          
		gameoverspy=getResources().getString(R.string.gameoverspy);    
		shibaizhe = getResources().getString(R.string.shibaizhe);
		fayan = getResources().getString(R.string.fayan);
		hao = getResources().getString(R.string.hao);
		hy=getResources().getString(R.string.hy);                      
		ge=getResources().getString(R.string.ge);
		killerCount = gameInfo.getInt("killerCount", 1);
		policeCount = gameInfo.getInt("policeCount", 1);
		initBtnInfo(R.id.btninfo, strFromId("txtKillerSucces"));
		//string_end
		
		isOver = false;
		flag = false;
		isGetRestart = false;
		contentTable = (TableLayout) findViewById(R.id.tableContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
//		punishBtn = new Button(this);
//		startBtn = new Button(this);
		punishBtn = (Button) findViewById(R.id.btn_punish);
		startBtn = (Button) findViewById(R.id.btn_restart);
		quickStartBtn = (Button) findViewById(R.id.btn_quickstart);

		// 中断回退的时候使用
		setGameType("kill");

		LinearLayout btn_wrapper = (LinearLayout) findViewById(R.id.an);
		btn_wrapper.setVisibility(View.INVISIBLE);

//		Bundle bundle = this.getIntent().getExtras();
//		isShow	= bundle.getBoolean("isShow");
//		son = bundle.getString("son");
//		soncount = bundle.getInt("underCount");
//		content = bundle.getStringArray("content");
		
		content = getGuessContent();
		isOver = gameInfo.getBoolean("isBlank", false);
		son = gameInfo.getString("son", "");
		isShow = gameInfo.getBoolean("isShow", false);
		isShow = true;

		hasClicked = getClickedContent();

		if (hasClicked.length < 4) {
			hasClicked = new boolean[content.length];
			for (int i = 0; i < content.length; i++) {
				if (content[i].equals(faguan)) {
					hasClicked[i] = true;
				} else {
					hasClicked[i] = false;
				}
			}
		}

		otherCount = content.length - policeCount - killerCount - 1;

		for (int i = 0; i < hasClicked.length; i++) {
			if (hasClicked[i] == true) {
				if (content[i].equals(nomalpeople)) {
					otherCount--;
				} else if (content[i].equals(police)) {
					policeCount--;
				} else if (content[i].equals(killer)) {
					killerCount--;
				}
			}
		}
		


		totalcount = content.length;
		txtTitle.setText(taplong);
		temindex = 0;
		for (int i = 0; i < Math.ceil((float) content.length / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				
				if (temindex >= content.length) {
					break;
				}
				FrameLayout fl = new FrameLayout(this);
				ImageView select = new ImageView(this);
				final TextView text	= new TextView(this);
				// text.setText(String.valueOf(temindex+1));
				// text.setGravity(Gravity.CENTER);
				// text.setTextSize(30);

				final TextView shenfen = new TextView(this);
				shenfen.setText(content[temindex]);
				shenfen.setGravity(Gravity.CENTER);
				shenfen.setTextSize(13);
				shenfen.setTextColor(getResources().getColor(R.color.WRITE));
				shenfen.setTag(999);
				shenfen.setVisibility(View.INVISIBLE);

				final ImageView shenfenimage = new ImageView(this);
				shenfenimage.setBackgroundResource(R.drawable.wodi);
				shenfenimage.setVisibility(View.INVISIBLE);

				// ImageView mohu = new ImageView(this);
				// mohu.setBackgroundResource(R.drawable.mohu);
				// mohu.setVisibility(View.INVISIBLE);
				// mohu.setTag(998);

				select.setTag(temindex);
				if (hasClicked[temindex]) {
					// 身份进行确认
					select.setBackgroundResource(stringToId("btnun_"
							+ (temindex + 1), "drawable"));
					select.setClickable(false);
					shenfen.setVisibility(View.VISIBLE);
					// 接着上局玩里面
					if (isShow) {
						initShenfen(shenfenimage, temindex, false);
					}
				}else{
					select.setBackgroundResource(stringToId("btn_"
							+ (temindex + 1), "drawable"));
					select.setOnLongClickListener(new Button.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							if (hasClicked[(Integer) v.getTag()] == true) {
								return true;
							}
							tapIndex((Integer) v.getTag());
							hasClicked[(Integer)v.getTag()] = true;
							v.setClickable(false);
							ImageView tt = (ImageView) v;
							tt.setBackgroundResource(stringToId("btnun_"
									+ ((Integer) v.getTag() + 1), "drawable"));
							SoundPlayer.playball();
							if (isShow) {
								initShenfen(shenfenimage, (Integer) v.getTag(),
										true);
							} else {
								SoundPlayer.playChuiShao();
							}
							// tt.setText("*");
							return true;
						}
					});
				}
				temindex ++;
				fl.addView(select);
				fl.addView(text);
				fl.addView(shenfenimage);
				// fl.addView(mohu);
				fl.addView(shenfen);
				newrow.addView(fl, disWidth / 4, disWidth / 7);
			}
			contentTable.addView(newrow);
		}
		txtLong = new TextView(this);
		txtLong.setText(updateSaySeq());
		txtLong.setTag(100099);
		contentTable.addView(txtLong);
		
		txtRemain = new TextView(this);
		contentTable.addView(txtRemain);
		// 如果不亮明身份，则不显示
		if (!isShow) {
			txtRemain.setVisibility(View.INVISIBLE);
		}
		checkGameOver();
	}

	private void initShenfen(ImageView shenfenimage, int index,
			boolean playsound) {
		// if (content[index].equals(son)) {
		// SoundPlayer.playChuiShao();
		// shenfenimage.setBackgroundResource(R.drawable.wodi);
		// } else {
		// SoundPlayer.playA();
		// shenfenimage.setBackgroundResource(R.drawable.yuan);
		// }
		// shenfenimage.setVisibility(View.VISIBLE);
	}

	protected void setAllButton(boolean useable) {
		for (int i = 0; i < content.length; i++) {
			ImageView tem = (ImageView) contentTable.findViewWithTag(i);
			tem.setClickable(useable);
		}
	}
	protected void tapIndex(int tag) {
		// 记录点状态
		hasClicked[tag] = true;
		updateClicked(hasClicked);
		if (otherCount + policeCount + 1 + killerCount == totalcount) {
			uMengClick("game_kill_guessfirst");
		}
		if (content[tag].equals(nomalpeople)) {
			otherCount--;
		} else if (content[tag].equals(police)) {
			policeCount--;
		} else {
			killerCount--;
		}
		checkGameOver();
		// txtLong.setVisibility(View.INVISIBLE);
	}
	
	protected String updateSaySeq() {
		int seq = Math.abs(random.nextInt()) % content.length;
		String strSeq = fayan;
		for (int i = 0; i < content.length; i++) {
			int temindex = seq % content.length + 1;
			if (!hasClicked[temindex - 1]) {
				String tem = String.format(hao, temindex);
				strSeq += tem;
			}
			seq++;
		}
		return strSeq;
	}

	/**
	 * 返回剩余人员配置
	 * 
	 * @return
	 */
	protected String getRemain() {
		// return "剩余卧底:";
		return "剩余警察:" + policeCount + "个  剩余杀手:" + killerCount + "个  剩余平民:"
				+ otherCount + "个";
	}

	protected void checkGameOver() {
		Log("CheeckGameOver");
		// if (!isOver) {
		if (killerCount <= 0) {
			txtTitle.setText(strFromId("txtKillerPoliceSucces"));
			isOver = true;
			if (!gamefinish) {
				uMengClick("game_kill_guesslast");
				gamefinish = true;
			}
			SoundPlayer.playHighSoure();
			refash();
			setAllButton(false);
			txtLong.setText(getPoliceWinStr());
			cleanStatus();
			txtRemain.setVisibility(View.INVISIBLE);
		} else if (policeCount <= 0 || otherCount <= 0) {
			SoundPlayer.playNormalSoure();
			txtTitle.setText(strFromId("txtKillerKillerSucces"));
			isOver = true;
			if (!gamefinish) {
				uMengClick("game_kill_guesslast");
				gamefinish = true;
			}
			refash();
			setAllButton(false);
			txtLong.setText(getKillerWinStr());
			txtRemain.setVisibility(View.INVISIBLE);
			cleanStatus();
		} else {
			int stringcount = overString.length;
			int stringindex = Math.abs(random.nextInt()) % stringcount;
			txtTitle.setText(overString[stringindex]);
			txtLong.setText(updateSaySeq() + "(" + taplong + ")");
			txtRemain.setText(getRemain());
		}
		// }else{
		//
		// }
	}

	protected String getPoliceWinStr() {
		String str = shibaizhe;
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(killer)) {
				int temhao = i + 1;
				String tem = String.format(hao, temhao);
				str += tem;
			}
		}
		return str;
	}

	protected String getKillerWinStr() {
		String str = shibaizhe;
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(killer) || content[i].equals(faguan)) {
				continue;
			}
			int temhao = i + 1;
			String tem = String.format(hao, temhao);
			str += tem;
		}
		return str;
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	private void refash() {
		// 所有身份亮明
		//Button punishBtn = new Button(this);
//		punishBtn.setVisibility(View.VISIBLE);
//		startBtn.setVisibility(View.VISIBLE);
//		quickStartBtn.setVisibility(View.VISIBLE);
		LinearLayout btn_wrapper = (LinearLayout) findViewById(R.id.an);
		btn_wrapper.setVisibility(View.VISIBLE);

		for (int i = 0; i < contentTable.getChildCount(); i++) {
			if (contentTable.getChildAt(i) instanceof TableRow) {
				TableRow v = (TableRow) contentTable.getChildAt(i);
				for (int m = 0; m < v.getChildCount(); m++) {
					if (v.getChildAt(m) instanceof FrameLayout) {
						FrameLayout imageTem = (FrameLayout) v.getChildAt(m);
						TextView temtext = (TextView) imageTem
								.findViewWithTag(999);
						temtext.setVisibility(View.VISIBLE);
						// ImageView mohu = (ImageView) imageTem
						// .findViewWithTag(998);
						// mohu.setVisibility(View.VISIBLE);
					}
				}
			}

		}
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SoundPlayer.playball();
				 Intent goMain = new Intent();
				 goMain.setClass(KillGuess.this, PunishActivity.class);
				uMengClick("game_kill_punish");
				 startActivity(goMain);
			}
		});
		punishBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_punish2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_punish);
				}
				return false;
			}
		});

//		contentTable.addView(punishBtn);
		// startBtn.setText("重新开始");
		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SoundPlayer.playball();
				Intent goMain = new Intent();
				goMain.setClass(KillGuess.this, KillSetting.class);
				startActivity(goMain);
				uMengClick("game_kill_resert");
				finish();
			}
		});
		
		startBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_restart2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_restart);
				}
				return false;
			}
		});
		 
		quickStartBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent goMain = new Intent();
				SoundPlayer.playball();
				goMain.setClass(KillGuess.this, kill.class);
				startActivity(goMain);
				uMengClick("game_kill_quickstart");
				finish();
			}
		});
		quickStartBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_startquick2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_startquick);
				}
				return false;
			}
		});
//		contentTable.addView(startBtn);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// game_guess_finish
		uMengClick("game_kill_finish");
		super.finish();
	}
	
}
