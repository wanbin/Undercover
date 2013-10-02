package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class guess extends BaseActivity {
	private TableLayout contentTable;

	// 卧底人数
	private int soncount;
	// 平民人数
	private int fathercount;
	// 卧底的词语
	private String son;
	// 0-n人数的词语数组
	private String[] content;
	private TextView txtTitle;
	private Button punishBtn;
	private Button startBtn;
	private Button quickStartBtn;
	private Button btnShare;
	

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

	protected void onCreate(Bundle savedInstanceState) {
		gusswhoisspy=getResources().getString(R.string.gusswhoisspy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guess);
		initBtnBack(R.id.btnback);
		initShareBtn();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		overString = getResources().getStringArray(R.array.overstring);
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
		
		initBtnInfo(R.id.btninfo, strFromId("txtGuessHelp"));
		isOver = false;
		flag = false;
		isGetRestart = false;
		contentTable = (TableLayout) findViewById(R.id.tableContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		punishBtn = (Button) findViewById(R.id.btn_punish);
		startBtn = (Button) findViewById(R.id.btn_restart);
		quickStartBtn = (Button) findViewById(R.id.btn_quickstart);

		setGameType("spy");

		LinearLayout btn_wrapper = (LinearLayout) findViewById(R.id.an);
		btn_wrapper.setVisibility(View.INVISIBLE);

		content = getGuessContent();
		isOver = gameInfo.getBoolean("isBlank", false);
		son = gameInfo.getString("son", "");
		isShow = gameInfo.getBoolean("isShow", false);
		soncount = gameInfo.getInt("underCount", 1);

		hasClicked = getClickedContent();

		if (hasClicked.length < 4) {
			hasClicked = new boolean[content.length];
			for (int i = 0; i < content.length; i++) {
				hasClicked[i] = false;
			}
		}

		fathercount = content.length - soncount;
		for (int i = 0; i < hasClicked.length; i++) {
			if (hasClicked[i] == true) {
				if (content[i].equals(son)) {
					soncount--;
				} else {
					fathercount--;
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
				Button select = new Button(this);
				final TextView text	= new TextView(this);
				final TextView shenfen = new TextView(this);
				int temshenf = temindex + 1;
				shenfen.setText(temshenf + "." + content[temindex]);
				shenfen.setGravity(Gravity.CENTER);
				shenfen.setTextSize(13);
				shenfen.setTextColor(getResources().getColor(R.color.WRITE));
				shenfen.setTag(999);
				shenfen.setVisibility(View.INVISIBLE);

				final ImageView shenfenimage = new ImageView(this);
				shenfenimage.setBackgroundResource(R.drawable.tag_new);
				shenfenimage.setVisibility(View.INVISIBLE);


				setBtnBlue(shenfenimage);
				select.setTag(temindex);
				int te = temindex + 1;
				select.setText("" + te);
				select.setTextColor(getResources().getColor(R.color.Writegray));
				select.setTextSize(20);
				setBtnBlueColor(select);

				if (hasClicked[temindex]) {
					// 身份进行确认
					select.setClickable(false);
					// 接着上局玩里面
					if (isShow) {
						initShenfen(shenfenimage, temindex, false);
					}
				}else{
					select.setOnLongClickListener(new Button.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							if (hasClicked[(Integer) v.getTag()] == true) {
								return true;
							}
							tapIndex((Integer) v.getTag());
							hasClicked[(Integer)v.getTag()] = true;
							v.setClickable(false);
							SoundPlayer.playball();
							if(isShow){
								initShenfen(shenfenimage, (Integer) v.getTag(),
										true);
							} else {
								SoundPlayer.playChuiShao();
							}
							return true;
						}
					});
				}
				temindex ++;
				fl.addView(select);
				fl.addView(text);
				fl.addView(shenfenimage, disWidth / 12, disWidth / 12);
				fl.addView(shenfen);
				fl.setPadding(4, 4, 4, 4);
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
		if (content[index].equals(son)) {
			SoundPlayer.playChuiShao();
			shenfenimage.setBackgroundResource(R.drawable.tag_right);
		} else {
			SoundPlayer.playA();
			shenfenimage.setBackgroundResource(R.drawable.tag_error);
		}
		shenfenimage.setVisibility(View.VISIBLE);
	}

	protected void setAllButton(boolean useable) {
		for (int i = 0; i < content.length; i++) {
			Button tem = (Button) contentTable.findViewWithTag(i);
			tem.setClickable(useable);
		}
	}
	protected void tapIndex(int tag) {
		// 记录点状态
		hasClicked[tag] = true;
		updateClicked(hasClicked);
		if (soncount + fathercount == totalcount) {
			uMengClick("click_guess_first");
		}
		if (content[tag].equals(son)) {
			soncount--;
		} else {
			fathercount--;
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
		return "剩余卧底:" + soncount + "个  剩余平民:" + fathercount + "个";
	}

	protected void checkGameOver() {
		Log("CheeckGameOver");
		// if (!isOver) {
		if (soncount <= 0) {
			Log("任务完成");
			txtTitle.setText(gameOver + "【" + son + "】");
			isOver = true;
			if (!gamefinish) {
				uMengClick("click_guess_last");
				gamefinish = true;
			}
			SoundPlayer.playHighSoure();
			refash();
			setAllButton(false);
			txtLong.setText(getSonStr());
			cleanStatus();
			txtRemain.setVisibility(View.INVISIBLE);
		} else if (fathercount <= soncount) {
			Log("卧底胜利");
			SoundPlayer.playNormalSoure();
			txtTitle.setText(gameoverspy + "【" + son + "】");
			isOver = true;
			if (!gamefinish) {
				uMengClick("click_guess_last");
				gamefinish = true;
			}
			refash();
			setAllButton(false);
			txtLong.setText(getFatherStr());
			txtRemain.setVisibility(View.INVISIBLE);
			cleanStatus();
		} else {
			int stringcount = overString.length;
			int stringindex = Math.abs(random.nextInt()) % stringcount;
			txtTitle.setText(overString[stringindex]);
			Log(hy + soncount + ge);
			txtLong.setText(updateSaySeq() + "(" + taplong + ")");
			txtRemain.setText(getRemain());
		}
	}

	protected String getSonStr() {
		String str = shibaizhe;
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(son)) {
				int temhao = i + 1;
				String tem = String.format(hao, temhao);
				str += tem;
			}
		}
		return str;
	}

	protected String getFatherStr() {
		String str = shibaizhe;
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(son)) {
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
						Button btnbg = (Button) imageTem.getChildAt(0);
						btnbg.setClickable(false);
						btnbg.setText("");
					}
				}
			}

		}
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SoundPlayer.playball();
				 Intent goMain = new Intent();
				 goMain.setClass(guess.this, PunishActivity.class);
				uMengClick("game_undercover_punish");
				 startActivity(goMain);
			}
		});
		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SoundPlayer.playball();
				Intent goMain = new Intent();
				goMain.setClass(guess.this, Setting.class);
				startActivity(goMain);
				uMengClick("game_undercover_resert");
				finish();
			}
		});
		setBtnGreen(startBtn);
		setBtnGreen(quickStartBtn);
		setBtnGreen(punishBtn);
		 
		quickStartBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent goMain = new Intent();
				SoundPlayer.playball();
				goMain.setClass(guess.this, fanpai.class);
				startActivity(goMain);
				uMengClick("game_undercover_quickresert");
				finish();
			}
		});
//		contentTable.addView(startBtn);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// game_guess_finish
		uMengClick("game_guess_finish");
		super.finish();
	}
}
