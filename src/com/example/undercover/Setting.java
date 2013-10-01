package com.example.undercover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Setting extends BaseActivity {
	private ImageView btnAdd;
	private ImageView btnCost;
	private ImageView btnAddUnder;
	private ImageView btnCostUnder;
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
//	private StringBuffer word=new StringBuffer();
	// 共享的参与和卧底数
	private SharedPreferences gameInfo;
	// 长按触发菜单的按钮
	private Button popoBtn, popoword;
//	private int itemChecked;
	private String wordStr;
	// 词汇分类
	private String[] UnderKind;
	private boolean beginSetting = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		UnderKind = getUnderKind();
		btnAdd = (ImageView) findViewById(R.id.btnAdd);
		btnAddUnder = (ImageView) findViewById(R.id.btnAddUnder);
		btnCost = (ImageView) findViewById(R.id.btnCost);
		btnCostUnder = (ImageView) findViewById(R.id.btnCostUnder);
		btnStart = (Button) findViewById(R.id.btnStart);
		//Button startChatRoom =(Button) findViewById(R.id.startChatRoom);
		people = (TextView) findViewById(R.id.txtPeople);
		under = (TextView) findViewById(R.id.txtUnder);
		wordText = (TextView) findViewById(R.id.wordText);

		wordText.setText(strFromId("setting_word_new")
				+ strFromId("setting_word_all"));
		// 添加 冤死 提示按钮
		afterShow	= (CheckBox)findViewById(R.id.afterShow);
		popoBtn	= (Button)findViewById(R.id.popo_button);
		popoword = (Button) findViewById(R.id.popo_wordsetting);

		popoword.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent goMain = new Intent();
				goMain.setClass(Setting.this, WordSetting.class);
				startActivity(goMain);
			}
		});

		initBtnInfo(R.id.btninfo, strFromId("txtSettingHelp"));

		popoBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_popo2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_popo);
				}
				return false;
			}
		});

		registerForContextMenu(popoBtn);
		// 共享数据
		gameInfo = getSharedPreferences("gameInfo", 0);
		
		afterShow.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					isShow	= true;
				}else{
					isShow	= false;
				}
			}
		});
		// 添加 空白词 按钮
		CheckBox blank	= (CheckBox)findViewById(R.id.isBlank);
		blank.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					isBlank	= true;
				}else{
					isBlank	= false;
				}
			}
		});
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (peopleCount < maxPeople) {
					SoundPlayer.playball();
					peopleCount++;
					underCount = Math.max((int) Math.floor(peopleCount / 3), 1);
				}
 else {
					siampleTitle(strFromId("txtTooMuchPeople"));
				}
				setPeople();
				setUnder();
			}
		});
		setBtnPinkCer(btnAdd);
		setBtnPinkCer(btnAddUnder);
		setBtnPinkCer(btnCostUnder);
		setBtnPinkCer(btnCost);
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
				}
 else {
					siampleTitle(strFromId("txtTooLittlePeople"));
				}
				setPeople();
				setUnder();
			}
		});

		btnAddUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginSetting = true;
				if (underCount < 4) {
					SoundPlayer.playball();
					underCount++;
					peopleCount = Math.min(
							Math.max(underCount * 3, peopleCount), maxPeople);
				}
 else {
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
				}
 else {
					siampleTitle(strFromId("txtTooLitterUnderCover"));
				}
				setUnder();
				setPeople();
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
//				goMain.putExtras(bundle);
				goMain.setClass(Setting.this, fanpai.class);
				startActivity(goMain);
				uMengClick("game_undercover_start");
				SoundPlayer.playball();
				finish();
			}
		});
		setBtnGreen(btnStart);
		setBtnGreen(popoBtn);
		setBtnGreen(popoword);
		setUnder();
		setPeople();
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


	protected void Log(String string) {
		Log.v("tag", string);
	}

	// protected void initTip() {
	// tip.setText("游戏规则：\n" + "\t 1.选择参与人数与卧底人数开始游戏 \n"
	// + "\t 2.每人记得自己的编号和身份 \n" + "\t 3.依次描述自己的身份 \n"
	// + "\t 4.每轮结束大家投票选出卧底 \n" + "\t 5.卧底出局后剩余玩家继续进行游戏 \n"
	// + "胜利条件； \n" + "\t 1.当卧底全部被指出则平民胜利 \n"
	// + "\t 2.当卧底数大于等于平民数则卧底胜利");
	// }
	private static final int ITEM1 = Menu.FIRST;
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.popo_menu, menu);
		menu.setHeaderTitle(strFromId("txtSettingWords"));
		for (int i = 0; i < UnderKind.length; i++) {
			menu.add(0, ITEM1 + i, 0, UnderKind[i]);
		}
//		menu.getItem(itemChecked).geti;
//		menu.setGroupCheckable(R.id.popo_word_group, true, false);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		item.setChecked(true);
		// itemChecked = item.getItemId(); //记录被选中的菜单项的ID
		int temi=item.getItemId()-ITEM1;
		wordStr = UnderKind[temi];
		wordText.setText(strFromId("setting_word_new") + wordStr);
		return super.onContextItemSelected(item);
	}
}
