package com.example.undercover;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class guess extends Activity {
	private TableLayout contentTable;
	private int soncount;
	private String son;
	private String[] content;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pai);
		contentTable = (TableLayout) findViewById(R.id.contentTable);

		Bundle bundle = this.getIntent().getExtras();
		son = bundle.getString("son");
		soncount = bundle.getInt("soncount");
		content = bundle.getStringArray("content");

		int temindex = 0;
		for (int i = 0; i < content.length / 3; i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 3; m++) {
				temindex++;
				Button btn = new Button(this);
				btn.setText("" + temindex);
				btn.setTag(temindex);
				btn.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						tapIndex((Integer) v.getTag());
						v.setClickable(false);
					}
				});
				newrow.addView(btn);
			}
			// contentTable.addView(newrow);
		}

	}

	protected void tapIndex(int tag) {
		if (content[tag].equals(son)) {
			soncount--;
		}
		if (soncount <= 0) {
			Log("任务完成");
		} else {
			Log("还有" + soncount + "个");
		}
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

}
