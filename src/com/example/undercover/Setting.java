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
	private TextView people;
	private TextView title;
	private int peopleCount=3;
	private String[] content;
	private Random random;
	private String son;
	private String father;
	private int soncount = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnCost = (Button) findViewById(R.id.btnCost);
		btnStart = (Button) findViewById(R.id.btnStart);
		people = (TextView) findViewById(R.id.txtPeople);
		title = (TextView) findViewById(R.id.txtPeopleTitle);
		random = new Random();
		content = getResources().getStringArray(R.array.content);

		setPeople();
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount < 20)
				peopleCount++;
				setPeople();
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
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				int selectindex = Math.abs(random.nextInt()) % content.length;
				String[] tem = getRandomString(content[selectindex]);
				Bundle bundle = new Bundle();
				bundle.putStringArray("content", tem);
				bundle.putString("son", son);
				bundle.putInt("sonCount", soncount);
				Intent goMain = new Intent();
				goMain.putExtras(bundle);
				goMain.setClass(Setting.this, fanpai.class);
				startActivity(goMain);
				finish();
			}
		});

	}

	private void setPeople() {
		people.setText(Integer.toString(peopleCount));
	}

	private String[] getRandomString(String contnettxt)
	{
		if (peopleCount > 12) {
			soncount = 4;
		} else if (peopleCount > 8) {
			soncount = 3;
		} else if (peopleCount > 4) {
			soncount = 2;
		}
		String[] children  =new String[2];
		children = contnettxt.split("_");
		int sonindex = Math.abs(random.nextInt()) % 2;
		son = children[sonindex];
		father = children[Math.abs(sonindex - 1)];
		String[] ret = new String[peopleCount];
		for (int n = 0; n < ret.length; n++) {
			ret[n] = father;
		}
		for (int i = 0; i < soncount; i++) {
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

}
