package com.example.undercover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Setting extends BaseActivity {
	private Button btnAdd;
	private Button btnCost;
	private Button btnAddUnder;
	private Button btnCostUnder;
	private Button btnStart;
	private TextView people;
	private TextView under;
	private TextView wordText;
	private int maxPeople = 12;
	// 说明
	private int peopleCount = 4;
	private int underCount = 1;
	private CheckBox afterShow;
	// 是否添加 冤死 提示，在投票后
	private boolean isShow = true;
	// 是否添加空白词
	private boolean isBlank = false;
	// 分类词组
	// private StringBuffer word=new StringBuffer();
	// 共享的参与和卧底数
	private SharedPreferences gameInfo;
	// 长按触发菜单的按钮
	private Button popoBtn, popoword;
	// private int itemChecked;
	private String wordStr;
	// 词汇分类
	private String[] UnderKind;
	private boolean beginSetting = false;
	private LinearLayout undercoverCount, kindSetting;
	private CheckBox blank;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
//		initBtnInfo(R.id.btninfo, strFromId("txtSettingHelp"));
		UnderKind = getUnderKind();
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAddUnder = (Button) findViewById(R.id.btnAddUnder);
		btnCost = (Button) findViewById(R.id.btnCost);
		btnCostUnder = (Button) findViewById(R.id.btnCostUnder);
		btnStart = (Button) findViewById(R.id.btnStart);
		people = (TextView) findViewById(R.id.txtPeople);
		under = (TextView) findViewById(R.id.txtUnder);
		wordText = (TextView) findViewById(R.id.wordText);
		gameInfo = getSharedPreferences("gameInfo", 0);
		peopleCount = gameInfo.getInt("peopleCount", 4);
		// 如果是认理卧底,最小人数6人
		if (lastGameType().equals("kill")) {
			peopleCount = Math.max(6, peopleCount);
		} 
		undercoverCount = (LinearLayout) findViewById(R.id.settingUnderCover);
		kindSetting = (LinearLayout) findViewById(R.id.settingKind);
		wordText.setText(strFromId("setting_word_new")
				+ strFromId("setting_word_all"));
		afterShow = (CheckBox) findViewById(R.id.afterShow);
		popoBtn = (Button) findViewById(R.id.popo_button);
		popoword = (Button) findViewById(R.id.popo_wordsetting);
		blank = (CheckBox) findViewById(R.id.isBlank);
		// 共享数据

		// 如果是杀人游戏，界面显示去掉一些东西
		if (lastGameType().equals("kill")) {
			initKill();
		} else {
			initUnderCover();
		}
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (peopleCount < maxPeople) {
					SoundPlayer.playball();
					peopleCount++;
					underCount = Math.max((int) Math.floor(peopleCount / 3), 1);
				} else {
					siampleTitle(strFromId("txtTooMuchPeople"));
				}
				setPeople();
				setUnder();
			}
		});
		btnCost.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (peopleCount > 4) {
					SoundPlayer.playball();
					peopleCount--;
					underCount = Math.min(
							Math.max((int) Math.floor(peopleCount / 3), 1),
							underCount);
				} else {
					siampleTitle(strFromId("txtTooLittlePeople"));
				}
				setPeople();
				setUnder();
			}
		});
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameInfo.edit().putInt("peopleCount", peopleCount).commit();
				gameInfo.edit().putInt("underCount", underCount).commit();
				gameInfo.edit().putBoolean("isShow", isShow).commit();
				gameInfo.edit().putBoolean("isBlank", isBlank).commit();
				gameInfo.edit().putString("word", wordStr).commit();
				Intent goMain = new Intent();
				// goMain.putExtras(bundle);
				goMain.setClass(Setting.this, fanpai.class);
				startActivity(goMain);
				uMengClick("game_undercover_start");
				SoundPlayer.playball();
				finish();
			}
		});
		setBtnGreen(btnStart);
		setBtnPinkCer(btnAdd);
		setBtnPinkCer(btnCost);


		setPeople();
	}

	// 初始化杀人游戏
	private void initKill() {
		// 杀人游戏最少要5人参加
		peopleCount = Math.max(peopleCount, 5);
		undercoverCount.setVisibility(View.GONE);
		wordText.setVisibility(View.GONE);
		kindSetting.setVisibility(View.GONE);
		popoword.setVisibility(View.GONE);
		blank.setVisibility(View.GONE);
		afterShow.setVisibility(View.GONE);
	}

	private void initUnderCover() {
		btnAddUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (underCount < 4) {
					SoundPlayer.playball();
					underCount++;
					peopleCount = Math.min(
							Math.max(underCount * 3, peopleCount), maxPeople);
				} else {
					siampleTitle(strFromId("txtTooMuchUnderCover"));
				}
				setPeople();
				setUnder();
			}
		});

		btnCostUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (underCount > 1) {
					SoundPlayer.playball();
					underCount--;
				} else {
					siampleTitle(strFromId("txtTooLitterUnderCover"));
				}
				setUnder();
				setPeople();
			}
		});
		popoword.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent goMain = new Intent();
				goMain.setClass(Setting.this, WordSetting.class);
				startActivity(goMain);
				uMengClick("word_setting");
			}
		});
		afterShow
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							isShow = true;
						} else {
							isShow = false;
						}
					}
				});
		// 添加 空白词 按钮
		blank.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					isBlank = true;
				} else {
					isBlank = false;
				}
			}
		});
		setUnder();
		setBtnGreen(popoBtn);
		setBtnGreen(popoword);
		setBtnPinkCer(btnAddUnder);
		setBtnPinkCer(btnCostUnder);
		registerForContextMenu(popoBtn);
	}

	private void setPeople() {
		if (peopleCount >= maxPeople) {
			// btnAdd.setBackgroundResource(R.drawable.cergray01);
			// btnAdd.setClickable(false);
		} else {
			// btnAdd.setBackgroundResource(R.drawable.cerpink01);
			// btnAdd.setClickable(true);
		}
		if (peopleCount <= 4) {
			// btnCost.setBackgroundResource(R.drawable.cergray01);
			// btnCost.setClickable(false);
		} else {
			// btnCost.setBackgroundResource(R.drawable.cerpink01);
			// btnCost.setClickable(true);
		}
		people.setText(Integer.toString(peopleCount));

	}

	private void setUnder() {
		if (underCount >= 4) {
			// btnAddUnder.setBackgroundResource(R.drawable.cergray01);
			// btnAddUnder.setClickable(false);
		} else {
			// btnAddUnder.setBackgroundResource(R.drawable.cerpink01);
			// btnAddUnder.setClickable(true);
		}
		if (underCount <= 1) {
			// btnCostUnder.setBackgroundResource(R.drawable.cergray01);
			// btnCostUnder.setClickable(false);
		} else {
			// btnCostUnder.setBackgroundResource(R.drawable.cerpink01);
			// btnCostUnder.setClickable(true);
		}
		under.setText(Integer.toString(underCount));
	}


	private static final int ITEM1 = Menu.FIRST;
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.popo_menu, menu);
		menu.setHeaderTitle(strFromId("txtSettingWords"));
		for (int i = 0; i < UnderKind.length; i++) {
			menu.add(0, ITEM1 + i, 0, UnderKind[i]);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		item.setChecked(true);
		int temi = item.getItemId() - ITEM1;
		wordStr = UnderKind[temi];
		wordText.setText(strFromId("setting_word_new") + wordStr);
		return super.onContextItemSelected(item);
	}

	public void onResume() {
		super.onResume();
		UnderKind = getUnderKind();
	}
}
