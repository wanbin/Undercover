package com.example.undercover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class local_guess extends BaseActivity {
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
	private Button punishBtn;
	private Button btn_restart;

	private int temindex;
	private Random random = new Random();
//	private TextView txtLong;
	// 还有人员分配
	/**
	 * 判断游戏是否结束
	 */
	private boolean gamefinish = false;
	private boolean[] hasClicked;
	
	/**
	 * 用来注册所有的按键，用户来停用或可用
	 */
	private List<Button> regButton=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_guess);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		regButton=new ArrayList<Button>();
		contentTable = (TableLayout) findViewById(R.id.tableContent);
		punishBtn = (Button) findViewById(R.id.btn_punish);
		btn_restart = (Button) findViewById(R.id.btn_restart);
		btn_restart.setVisibility(View.GONE);
		content = getGuessContent();

		
		btn_restart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		hasClicked = new boolean[content.length];
		for (int i = 0; i < content.length; i++) {
				hasClicked[i] = false;
		}
		
		if (lastGameType().equals("game_killer")) {
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
				select.setTag(temindex);
				select.setText(String.valueOf(temindex + 1));
				select.setTextSize(20);
				select.setGravity(Gravity.CENTER|Gravity.BOTTOM);
				
				if (lastGameType().equals("game_killer")&&content[temindex].equals("法官")) {
					select.setText("法官");
					select.setClickable(false);
					select.setEnabled(false);
					hasClicked[temindex]=true;
				}
				
				if (hasClicked[temindex]) {
					// 身份进行确认
					select.setClickable(false);
					select.setEnabled(false);
					// 接着上局玩里面
					
				} else {
					select.setOnLongClickListener(new Button.OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							if (hasClicked[(Integer) v.getTag()] == true) {
								return true;
							}
							// 如果是杀人游戏，界面显示去掉一些东西
							if (lastGameType().equals("game_killer")) {
								tapIndexKiller((Integer) v.getTag());
							} else {
								tapIndex((Integer) v.getTag());
							}
							hasClicked[(Integer) v.getTag()] = true;
							v.setEnabled(false);
							v.setClickable(false);
							return true;
						}
					});
				}
				temindex++;
				regButton.add(select);
				fl.addView(select);
				fl.setPadding(4, 4, 4, 4);
				newrow.addView(fl, disWidth / 4, disWidth / 4);
			}
			contentTable.addView(newrow);
		}
		punishBtn.setText(saySeqString());
	}

	private void initKill() {
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
		son = gameInfo.getString("son", "");
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


	protected void setAllButton(boolean useable) {
		for (int i = 0; i < regButton.size(); i++) {
			Button tem = regButton.get(i);
//			tem.setClickable(false);
			tem.setOnLongClickListener(new Button.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					return true;
				}
			});
		}
	}

	protected void tapIndex(int tag) {
		// 记录点状态
		hasClicked[tag] = true;
//		updateClicked(hasClicked);
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
//		updateClicked(hasClicked);
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
//			txtTitle.setText(strFromId("txtKillerPoliceSucces"));
			if (!gamefinish) {
				uMengClick("game_kill_guesslast");
				gamefinish = true;
			}
			SoundPlayer.playHighSoure();
			showAllWord();
			setAllButton(false);
			initControlBtn();
			punishBtn.setText("杀手接受惩罚");
			cleanStatus();
		} else if (policeCount <= 0 || otherCount <= 0) {
			SoundPlayer.playNormalSoure();
//			txtTitle.setText(strFromId("txtKillerKillerSucces"));
			if (!gamefinish) {
				uMengClick("game_kill_guesslast");
				gamefinish = true;
			}
			showAllWord();
			setAllButton(false);
			initControlBtn();
			punishBtn.setText("平民和警官接受惩罚");
			cleanStatus();
		} else {
			punishBtn.setText(saySeqString());
		}
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

	protected String saySeqString() {
		List<Integer> hasNotClick=new ArrayList<Integer>();
		for (int i = 0; i < content.length; i++) {
			if (!hasClicked[i]) {
				hasNotClick.add(i);
			} 
		}
		int seq = Math.abs(random.nextInt()) % hasNotClick.size();
		return (hasNotClick.get(seq)+1)+"号用户开始描述";
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
		if (soncount <= 0) {
			if (!gamefinish) {
				uMengClick("click_guess_last");
				gamefinish = true;
			}
			SoundPlayer.playHighSoure();
			showAllWord();
			initControlBtn();
			setAllButton(false);
			punishBtn.setText(getSonStr());
			cleanStatus();
		} else if (fathercount <= soncount) {
			SoundPlayer.playNormalSoure();
			if (!gamefinish) {
				uMengClick("click_guess_last");
				gamefinish = true;
			}
			showAllWord();
			initControlBtn();
			setAllButton(false);
			punishBtn.setText(getFatherStr());
			cleanStatus();
		} else {
			punishBtn.setText(saySeqString());
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

	private void showAllWord() {
		// 所有身份亮明
		for (int i = 0; i < regButton.size(); i++) {
			Button tembtn=regButton.get(i);
			tembtn.setText(content[i]);
		}
	}

	private void initControlBtn() {
		punishBtn.setBackgroundResource(R.drawable.btn_fang_purple);
		btn_restart.setVisibility(View.VISIBLE);
		punishBtn.setTextColor(getResources().getColor(R.color.WRITE));
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.playball();
				Intent goMain = new Intent();
				goMain.setClass(local_guess.this, local_punish.class);
				uMengClick("game_undercover_punish");
				startActivity(goMain);
			}
		});
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		// game_guess_finish
		uMengClick("game_guess_finish");
		super.finish();
	}
}
