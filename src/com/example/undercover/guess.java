package com.example.undercover;

import java.util.Random;


import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

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
	private int policeCount;
	private int killerCount;
	private int otherCount;
	// 卧底的词语
	private String son;
	// 0-n人数的词语数组
	private String[] content;
	private TextView txtTitle;
	private Button punishBtn;
	private Button startBtn;
	private Button quickStartBtn;

	private boolean isOver;
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
	private boolean[] hasClicked;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guess);
		initBtnBack(R.id.btnback);
		initShareBtn();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		overString = getResources().getStringArray(R.array.overstring);

		  
		  
		initBtnInfo(R.id.btninfo, strFromId("txtGuessHelp"));
		contentTable = (TableLayout) findViewById(R.id.tableContent);
		txtTitle = (TextView) findViewById(R.id.txtTitleFaile);
		punishBtn = (Button) findViewById(R.id.btn_punish);
		startBtn = (Button) findViewById(R.id.btn_restart);
		quickStartBtn = (Button) findViewById(R.id.btn_quickstart);
		LinearLayout btn_wrapper = (LinearLayout) findViewById(R.id.an);
		btn_wrapper.setVisibility(View.INVISIBLE);
		content = getGuessContent();
		hasClicked = getClickedContent();

		if (lastGameType().equals("kill")) {
			initKill();
		} else {
			initUnderCover();
		}

		temindex = 0;
		for (int i = 0; i < Math.ceil((float) content.length / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {

				if (temindex >= content.length) {
					break;
				}
				FrameLayout fl = new FrameLayout(this);
				Button select = new Button(this);
				final TextView text = new TextView(this);
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
					if (lastGameType().equals("kill")) {
						shenfen.setVisibility(View.VISIBLE);
						select.setText("");
					}
					if (isShow) {
						initShenfen(shenfenimage, temindex, false);
					}
					setBtnGrayColor(select);
				} else {
					select.setOnLongClickListener(new Button.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							if (hasClicked[(Integer) v.getTag()] == true) {
								return true;
							}
							// 如果是杀人游戏，界面显示去掉一些东西
							if (lastGameType().equals("kill")) {
								tapIndexKiller((Integer) v.getTag());
							} else {
								tapIndex((Integer) v.getTag());
							}
							setBtnGrayColor((Button) v);
							hasClicked[(Integer) v.getTag()] = true;
							v.setClickable(false);
							SoundPlayer.playball();
							if (isShow) {
								initShenfen(shenfenimage, (Integer) v.getTag(),
										true);
							} else {
								SoundPlayer.playChuiShao();
							}
							return true;
						}
					});
				}
				temindex++;
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
		if (lastGameType().equals("kill")) {
			checkGameOverKiller();
		} else {
			checkGameOver();
		}
		
		if (AdManage.showad) {
			showAd();
		}

	}

	private void showAd() {
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.ad);
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		adLayout.addView(adView);
		adView.setAdListener(new AdViewListener() {
			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiSample", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiSample", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiSample", "请求广告失败");
			}
		});
	}

	private void initKill() {
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
		policeCount = Math.max((int) Math.floor(content.length / 4), 1);
		killerCount = Math.max((int) Math.floor(content.length / 4), 1);
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
	}

	private void initUnderCover() {
		if (hasClicked.length < 4) {
			hasClicked = new boolean[content.length];
			for (int i = 0; i < content.length; i++) {
				hasClicked[i] = false;
			}
		}
		isOver = gameInfo.getBoolean("isBlank", false);
		son = gameInfo.getString("son", "");
		isShow = gameInfo.getBoolean("isShow", false);
		soncount = gameInfo.getInt("underCount", 1);
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
	}

	private void initShenfen(ImageView shenfenimage, int index,
			boolean playsound) {
		if (lastGameType().equals("kill")) {
			if (content[index].equals(killer)) {
				SoundPlayer.playChuiShao();
				shenfenimage.setBackgroundResource(R.drawable.tag_right);
			} else {
				SoundPlayer.playA();
				shenfenimage.setBackgroundResource(R.drawable.tag_error);
			}
		} else {

			if (content[index].equals(son)) {
				SoundPlayer.playChuiShao();
				shenfenimage.setBackgroundResource(R.drawable.tag_right);
			} else {
				SoundPlayer.playA();
				shenfenimage.setBackgroundResource(R.drawable.tag_error);
			}
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
		if (soncount + fathercount == content.length) {
			uMengClick("click_guess_first");
		}
		if (content[tag].equals(son)) {
			soncount--;
		} else {
			fathercount--;
		}

		checkGameOver();
	}

	protected void tapIndexKiller(int tag) {
		// 记录点状态
		hasClicked[tag] = true;
		updateClicked(hasClicked);
		if (otherCount + policeCount + 1 + killerCount == content.length) {
			uMengClick("game_kill_guessfirst");
		}
		if (content[tag].equals(nomalpeople)) {
			otherCount--;
		} else if (content[tag].equals(police)) {
			policeCount--;
		} else {
			killerCount--;
		}
		checkGameOverKiller();
	}

	protected void checkGameOverKiller() {
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
			txtLong.setText(updateSaySeq() + "(" + strFromId("taplong") + ")");
			txtRemain.setText(getKillerCoverRemain());
		}
		// }else{
		//
		// }
	}

	protected String getPoliceWinStr() {
		String str = strFromId("shibaizhe");
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(killer)) {
				int temhao = i + 1;
				String tem = String.format(strFromId("hao"), temhao);
				str += tem;
			}
		}
		return str;
	}

	protected String getKillerWinStr() {
		String str = strFromId("shibaizhe");
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(killer) || content[i].equals(faguan)) {
				continue;
			}
			int temhao = i + 1;
			String tem = String.format(strFromId("hao"), temhao);
			str += tem;
		}
		return str;
	}

	protected String updateSaySeq() {
		int seq = Math.abs(random.nextInt()) % content.length;
		String strSeq = strFromId("fayan");
		boolean firstsay = true;
		String temStr = "";
		for (int i = 0; i < content.length; i++) {
			int temindex = seq % content.length + 1;
			if (!hasClicked[temindex - 1]) {
				String tem = String.format(strFromId("hao"), temindex);
				// 如果随机的第一个玩家为空白，则不第一个发言
				if (firstsay
						&& content[temindex - 1].equals(strFromId("blank"))) {
					temStr = tem;
				} else {
					strSeq += tem;
				}
				firstsay = false;
			} else {
				// 如果有人被投死了，那么就不用再考虑是不是空白了
				firstsay = false;
			}
			seq++;
		}
		return strSeq + temStr;
	}

	/**
	 * 返回剩余人员配置
	 * 
	 * @return
	 */
	protected String getUnderCoverRemain() {
		return strFromId("aliveNUM") + soncount + strFromId("citizenNUM") + fathercount + strFromId("unitGE");
	}

	protected String getKillerCoverRemain() {
		return strFromId("aliveNUM") + soncount + strFromId("citizenNUM") + fathercount + strFromId("unitGE");
	}

	protected void checkGameOver() {
		Log("CheeckGameOver");
		// if (!isOver) {
		if (soncount <= 0) {
			txtTitle.setText(strFromId("gameOver") + "【" + son + "】");
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
			SoundPlayer.playNormalSoure();
			txtTitle.setText(strFromId("gameoverspy") + "【" + son + "】");
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
			Log(strFromId("hao") + soncount + strFromId("ge"));
			txtLong.setText(updateSaySeq() + "(" + strFromId("taplong") + ")");
			if (lastGameType().equals("kill")) {
				txtRemain.setText(getKillerCoverRemain());
			} else {
				txtRemain.setText(getUnderCoverRemain());
			}
		}
	}

	protected String getSonStr() {
		String str = strFromId("shibaizhe");
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(son)) {
				int temhao = i + 1;
				String tem = String.format(strFromId("hao"), temhao);
				str += tem;
			}
		}
		return str;
	}

	protected String getFatherStr() {
		String str = strFromId("shibaizhe");
		for (int i = 0; i < content.length; i++) {
			if (content[i].equals(son)) {
				continue;
			}
			int temhao = i + 1;
			String tem = String.format(strFromId("hao"), temhao);
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
		initControlBtn();
	}

	private void initControlBtn() {
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
		setBtnGreen(startBtn);
		setBtnGreen(quickStartBtn);
		setBtnGreen(punishBtn);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// game_guess_finish
		uMengClick("game_guess_finish");
		super.finish();
	}
}
