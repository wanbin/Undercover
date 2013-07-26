package com.example.undercover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class fanpai extends Activity {
	private int soncount;
	private String son;
	private String[] content;
	private TextView txtShenfen;
	private TextView txtIndex;
	private ImageView imagePan;
	private Button btnOK;
	private int nowIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pai);
		//
		btnOK = (Button) findViewById(R.id.btnOk);
		txtIndex = (TextView) findViewById(R.id.txtIndex);
		txtShenfen = (TextView) findViewById(R.id.txtShenfen);
		imagePan = (ImageView) findViewById(R.id.imagePan);

		Bundle bundle = this.getIntent().getExtras();
		son = bundle.getString("son");
		soncount = bundle.getInt("soncount");
		content = bundle.getStringArray("content");
		for (int i = 0; i < content.length; i++) {
			Log(content[i]);
		}

		initPan(nowIndex);

		btnOK.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nowIndex < content.length) {
					nowIndex++;
					initPan(nowIndex);
				} else {
					Bundle bundle = new Bundle();
					bundle.putStringArray("content", content);
					bundle.putString("son", son);
					bundle.putInt("sonCount", soncount);
					Intent goMain = new Intent();
					goMain.putExtras(bundle);
					goMain.setClass(fanpai.this, guess.class);
					startActivity(goMain);
					finish();
				}
			}
		});

		imagePan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				txtIndex.setVisibility(View.INVISIBLE);

			}
		});

	}

	protected void initPan(int index) {
		imagePan.setVisibility(View.VISIBLE);
		txtIndex.setVisibility(View.VISIBLE);
		txtIndex.setText("" + index);
		txtShenfen.setText(content[index - 1]);
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

}
