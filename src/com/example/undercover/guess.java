package com.example.undercover;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class guess extends Activity {
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
	private boolean isOver;
	private boolean flag;
//	private int temindex = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guess);
		isOver = false;
		flag = false;
		contentTable = (TableLayout) findViewById(R.id.contentTable);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		punishBtn = new Button(this);
		Bundle bundle = this.getIntent().getExtras();
		son = bundle.getString("son");
		soncount = bundle.getInt("sonCount");
		content = bundle.getStringArray("content");
		fathercount = content.length - soncount;
		int temindex = 0;
		for (int i = 0; i < Math.ceil((float) content.length / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				temindex++;
				if (temindex > content.length) {
					break;
				}
				Button btn = new Button(this);
				btn.setText("" + temindex);
				btn.setTag(temindex);
				btn.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						tapIndex((Integer) v.getTag());
						v.setClickable(false);
						Button tt = (Button) v;
						tt.setText("*");
					}
				});
				newrow.addView(btn, 120, 70);
			}
			contentTable.addView(newrow);
		}

	}

	protected void tapIndex(int tag) {
		if (content[tag - 1].equals(son)) {
			soncount--;
		} else {
			fathercount--;
		}
		if (!isOver) {
			if (soncount <= 0) {
				Log("任务完成");
				txtTitle.setText("完成任务，卧底为" + son);
				isOver = true;
				refash();
			} else if (fathercount <= soncount) {
				Log("卧底胜利");
				txtTitle.setText("卧底胜利，卧底为" + son);
				isOver = true;
				refash();
			} else {
				Log("还有" + soncount + "个");
			}
		}
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	private void refash() {
		//Button punishBtn = new Button(this);
		punishBtn.setText("开始惩罚");
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getPunish();
				getRestartButton();
				// Intent goMain = new Intent();
				// goMain.setClass(guess.this, Setting.class);
				// startActivity(goMain);
				// finish();
			}
		});
		contentTable.addView(punishBtn);
	}

	/**
	 * 获取惩罚内容数组
	 * 
	 * @return
	 */
	private void getPunish() {
		if (!flag) {
			flag 			= true;
			int arr[]		= MathUtil.getInstance().check(200, 6);
			TextView text 	= null;
			String temp 	= null;
			for (int i = 0; i < 6; i++) {
				text 		= new TextView(this);
				temp 		= PunishProps.getPunish(arr[i]);
				if (null == temp) {
					temp 	= "请执行第一条";
				}
				text.setText(temp);
				contentTable.addView(text);
			}
		}
	}
	
	private void getRestartButton(){
		Button restartBtn = new Button(this);
		restartBtn.setText("重新开始");
		restartBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent goMain = new Intent();
				 goMain.setClass(guess.this, Setting.class);
				 startActivity(goMain);
				 finish();
			}
		});
		contentTable.addView(restartBtn);
	}
//	private void frozenBtn()
//	{
//		for(int i = 1 ;i < temindex+1 ; i++)
//		{
//			Button btn = (Button)contentTable.findViewWithTag(i);
//			if(btn == null)
//			{
//			    continue ;	
//			}
//			
//			btn.setClickable(false);
//		}
//	}

}
