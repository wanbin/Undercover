package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WordSetting extends BaseActivity {
	private Button btnAdd, btnClear;
	// 是否添加 冤死 提示，在投票后
	// 是否添加空白词
	// 分类词组
	// private StringBuffer word=new StringBuffer();
	// 共享的参与和卧底数

	private EditText text1, text2;

	// 词汇分类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordsetting);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnClear = (Button) findViewById(R.id.btnClear);
		text1 = (EditText) findViewById(R.id.edit1);
		text2 = (EditText) findViewById(R.id.edit2);

		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (text1.getText().toString()
						.equals(text2.getText().toString())) {
					siampleTitle(strFromId("txtWordsCantSame"));
					return;
				}
				if (text1.getText().toString().indexOf("_") > 0
						|| text1.getText().toString().indexOf(",") > 0
						|| text2.getText().toString().indexOf("_") > 0
						|| text2.getText().toString().indexOf(",") > 0) {
					siampleTitle(strFromId("txtWordsCantSpecial"));
					return;
				}
				
				String strtem = gameInfo.getString("user_setting", "");
				if (strtem.length() > 0) {
					strtem = strtem + "," + strFromId("txtWordsPeopleInput")
							+ text1.getText().toString()
							+ "_" + text2.getText().toString();
				} else {
					strtem = strtem + strFromId("txtWordsPeopleInput")
							+ text1.getText().toString() + "_"
							+ text2.getText().toString();
				}
				strtem = strtem.trim();
				gameInfo.edit().putString("user_setting", strtem).commit();
				siampleTitle(strFromId("txtWordsAddSuccess"));
				text1.setText("");
				text2.setText("");
			}
		});
		btnClear.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameInfo.edit().putString("user_setting", "").commit();
			}
		});

	}


}
