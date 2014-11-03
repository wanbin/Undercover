package com.example.undercover;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class local_punish  extends BaseActivity {
	TextView txtPunish;
	Button btnNext;
	Button btnNet;
	Button btnLocal;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_punish);
		showShack = true;
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		txtPunish=(TextView)this.findViewById(R.id.txtPunish);
		btnNext=(Button)this.findViewById(R.id.btnNext);
		
		btnLocal=(Button)this.findViewById(R.id.btnLocal);
		btnNet=(Button)this.findViewById(R.id.btnNet);
		
		btnNext.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextPunish();
			}
		});		
		
		btnLocal.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
				intentGo.setClass(local_punish.this, local_punish_list.class);
				uMengClick("click_intenet");
				startActivity(intentGo);
			}
		});
		
		btnNet.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
				intentGo.setClass(local_punish.this, net_punish.class);
				uMengClick("click_intenet");
				startActivity(intentGo);
			}
		});		
	}
	@Override
	public void shackAction() {
		nextPunish();
	}
	private void nextPunish(){
		SoundPlayer.playclaps();
		txtPunish.setText(getRandomMaoxianFromLocate());
	}
}
