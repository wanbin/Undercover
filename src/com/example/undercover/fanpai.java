package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class fanpai extends BaseActivity {
	private int soncount;
	private String son;
	private String[] content;
	private TextView txtShenfen;
	private TextView txtIndex;
	private TextView textViewab;
	private ImageView imagePan;
	private Button btnOK;
	private int nowIndex = 1;
	private boolean isShow;
	private boolean isBlank;
	private boolean isChecked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pai);
		//
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		isChecked	= false;
		btnOK = (Button) findViewById(R.id.btnOk);
		txtIndex = (TextView) findViewById(R.id.txtIndex);
		txtShenfen = (TextView) findViewById(R.id.txtShenfen);
		textViewab = (TextView) findViewById(R.id.textViewab);
		imagePan = (ImageView) findViewById(R.id.imagePan);

		Bundle bundle = this.getIntent().getExtras();
		isBlank	= bundle.getBoolean("isBlank");
		isShow	= bundle.getBoolean("isShow");
		son = bundle.getString("son");
		soncount = bundle.getInt("sonCount");
		content = bundle.getStringArray("content");
		int blandStr	= Math.abs(new Random().nextInt()); 
		for (int i = 0, len	=content.length; i < len; i++) {
			if(isBlank){
				
				if(!isChecked){
					if(!content[(i+blandStr)%len].equals(son)){
						isChecked	= true;
						content[(i + blandStr) % len] = "空白";
					}
				}
			}
			Log(content[i]);
		}

		initPan(nowIndex);

		btnOK.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nowIndex < content.length) {
					if (nowIndex == 1) {
						uMengClick("click_undercover_pai_first");
					}
					nowIndex++;
					initPan(nowIndex);
				} else {
					Bundle bundle = new Bundle();
					bundle.putStringArray("content", content);
					bundle.putString("son", son);
					bundle.putInt("sonCount", soncount);
					bundle.putBoolean("isShow", isShow);
					Intent goMain = new Intent();
					goMain.putExtras(bundle);
					goMain.setClass(fanpai.this, guess.class);
					startActivity(goMain);
					uMengClick("click_undercover_pai_last");
					finish();
				}
			}
		});

		imagePan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				txtIndex.setVisibility(View.INVISIBLE);
				setContentVis(true);
				SoundPlayer.playball();
			}
		});

	}

	protected void initPan(int index) {

		imagePan.setVisibility(View.VISIBLE);
		txtIndex.setVisibility(View.VISIBLE);
		txtIndex.setText("" + index);
		txtShenfen.setText(content[index - 1]);
		setContentVis(false);
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	protected void setContentVis(boolean show) {
		if (show) {
			btnOK.setVisibility(View.VISIBLE);
			txtIndex.setVisibility(View.INVISIBLE);
			txtShenfen.setVisibility(View.VISIBLE);
			textViewab.setVisibility(View.VISIBLE);

		} else {
			btnOK.setVisibility(View.INVISIBLE);
			txtIndex.setVisibility(View.VISIBLE);
			txtShenfen.setVisibility(View.INVISIBLE);
			textViewab.setVisibility(View.INVISIBLE);
		}
	}
}
