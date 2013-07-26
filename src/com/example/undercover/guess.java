package com.example.undercover;
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
	private int soncount;
	private String son;
	private String[] content;
	private TextView txtTitle;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guess);
		contentTable = (TableLayout) findViewById(R.id.contentTable);
		txtTitle = (TextView) findViewById(R.id.txtTitle);

		Bundle bundle = this.getIntent().getExtras();
		son = bundle.getString("son");
		soncount = bundle.getInt("sonCount");
		content = bundle.getStringArray("content");

		int temindex = 0;
		for (int i = 0; i < Math.ceil((float) content.length / 3); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 3; m++) {
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
						Button tt=(Button)v;
						tt.setText("*");
					}
				});
				newrow.addView(btn, 100, 50);
			}
			contentTable.addView(newrow);
		}

	}

	protected void tapIndex(int tag) {
		if (content[tag - 1].equals(son)) {
			soncount--;
		}
		if (soncount <= 0) {
			Log("任务完成");
			txtTitle.setText("完成任务，卧底为" + son);
			Button btn = new Button(this);
			btn.setText("重新开始");
			btn.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent goMain = new Intent();
					goMain.setClass(guess.this, Setting.class);
					startActivity(goMain);
					finish();
				}
			});
			contentTable.addView(btn);
		} else {
			Log("还有" + soncount + "个");

		}
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

}
