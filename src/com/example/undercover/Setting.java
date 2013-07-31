package com.example.undercover;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends Activity {
	private Button btnAdd;
	private Button btnCost;
	private Button btnStart;
	private Button btnAddUnder;
	private Button btnCostUnder;
	private TextView people;
	private TextView under;
	private TextView title;
	// 说明
	private TextView tip;
	private int peopleCount = 3;
	private int underCount = 1;
	private String[] content;
	private Random random;
	private String son;
	private String father;

	// private int soncount = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAddUnder = (Button) findViewById(R.id.btnAddUnder);
		btnCost = (Button) findViewById(R.id.btnCost);
		btnCostUnder = (Button) findViewById(R.id.btnCostUnder);
		btnStart = (Button) findViewById(R.id.btnStart);
		Button startChatRoom =(Button) findViewById(R.id.startChatRoom);
		people = (TextView) findViewById(R.id.txtPeople);
		under = (TextView) findViewById(R.id.txtUnder);
		title = (TextView) findViewById(R.id.txtPeopleTitle);
		tip = (TextView) findViewById(R.id.txtTip);

		random = new Random();
		content = getResources().getStringArray(R.array.content);

		setPeople();
		setUnder();
		initTip();
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount < 20)
				peopleCount++;
				underCount = Math.max((int) Math.floor(peopleCount / 5), 1);
				setPeople();
				setUnder();
			}
		});

		btnCost.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount > 3)
				{
					peopleCount--;
				}
				setPeople();
			}
		});

		btnAddUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (underCount < Math.floor(peopleCount / 3))
					underCount++;
				setUnder();
			}
		});

		btnCostUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (underCount > 1) {
					underCount--;
				}
				setUnder();
			}
		});

		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				int selectindex = Math.abs(random.nextInt()) % content.length;
				String[] tem = getRandomString(content[selectindex]);
				Bundle bundle = new Bundle();
				bundle.putStringArray("content", tem);
				bundle.putString("son", son);
				bundle.putInt("sonCount", underCount);
				Intent goMain = new Intent();
				goMain.putExtras(bundle);
				goMain.setClass(Setting.this, fanpai.class);
				startActivity(goMain);
				finish();
			}
		});
		
		startChatRoom.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goChat = new Intent();
				goChat.setClass(Setting.this,ChatRoomActivity.class);
				startActivity(goChat);
			}
		});

	}

	private void setPeople() {
		people.setText(Integer.toString(peopleCount));
	}

	private void setUnder() {
		under.setText(Integer.toString(underCount));
	}

	private String[] getRandomString(String contnettxt)
	{
		// if (peopleCount > 15) {
		// soncount = 4;
		// } else if (peopleCount > 10) {
		// soncount = 3;
		// } else if (peopleCount > 5) {
		// soncount = 2;
		// }
		String[] children  =new String[2];
		children = contnettxt.split("_");
		int sonindex = Math.abs(random.nextInt()) % 2;
		son = children[sonindex];
		father = children[Math.abs(sonindex - 1)];
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
		return ret;
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	protected void initTip() {
		tip.setText("游戏规则：\n" + "\t 1.选择参与人数与卧底人数开始游戏 \n"
				+ "\t 2.每人记得自己的编号和身份 \n" + "\t 3.依次描述自己的身份 \n"
				+ "\t 4.每轮结束大家投票选出卧底 \n" + "\t 5.卧底出局后剩余玩家继续进行游戏 \n"
				+ "胜利条件； \n" + "\t 1.当卧底全部被指出则平民胜利 \n"
				+ "\t 2.当卧底数大于等于平民数则卧底胜利");
	}

}
