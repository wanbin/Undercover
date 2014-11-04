package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

public class local_setting extends BaseActivity {
	SeekBar seekPeople;
	SeekBar seekUndercover;
	TextView peopleCount;
	TextView undercoverCount;
	Button btnStart;
	String gameType;
	LinearLayout linearUndercover;
	int basePeople=4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_setting);
		linearUndercover=(LinearLayout)this.findViewById(R.id.tem2);
		
		seekPeople=(SeekBar)this.findViewById(R.id.seekPeople);
		seekUndercover=(SeekBar)this.findViewById(R.id.seekUndercover);
		peopleCount=(TextView)this.findViewById(R.id.labPeople);
		undercoverCount=(TextView)this.findViewById(R.id.labUndercover);
		btnStart=(Button)this.findViewById(R.id.btnStart);
		
		gameType=lastGameType();
		if (gameType.equals("game_undercover")) {
			seekPeople.setMax(8);
			seekUndercover.setMax(3);
			basePeople=4;
		} else if (gameType.equals("game_killer")) {
			seekPeople.setMax(10);
			linearUndercover.setVisibility(View.GONE);
			basePeople=6;
		}
		seekPeople.setProgress(0);
		seekUndercover.setProgress(0);
		peopleCount.setText(String.valueOf (basePeople));
		undercoverCount.setText("1");
		
		
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameInfo.edit().putInt("peopleCount", basePeople+seekPeople.getProgress()).commit();
				gameInfo.edit().putInt("underCount", 1+seekUndercover.getProgress()).commit();
				gameInfo.edit().putBoolean("isShow", false).commit();
				gameInfo.edit().putBoolean("isBlank", false).commit();
				gameInfo.edit().putString("word", "全部分类").commit();
				Intent goMain = new Intent();
				goMain.setClass(local_setting.this, local_fanpai.class);
				startActivity(goMain);
			}
		});
		
		
		
		
		seekPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
					peopleCount.setText(String.valueOf (basePeople+progress));
					
					int undercover=1+seekUndercover.getProgress();
					if(basePeople+progress<undercover*3&&seekUndercover.getProgress()>0){
						seekUndercover.setProgress(seekUndercover.getProgress()-1);
					}
//					addPeople=progress;
//	                description.setText("当前进度："+progress+"%");
            }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
		
		seekUndercover.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
					undercoverCount.setText(String.valueOf (1+progress));
					int people=basePeople+seekPeople.getProgress();
					if(people<(1+progress)*3){
						seekPeople.setProgress(Math.min((1+progress)*3, 12)-basePeople);
					}
//	                description.setText("当前进度："+progress+"%");
            }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
