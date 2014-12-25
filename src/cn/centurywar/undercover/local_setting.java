package cn.centurywar.undercover;

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
	TextView peopleCount;
	TextView undercoverCount;
	TextView txtName;
	Button btnStart;
	Button btn1;
	Button btn2;
	String gameType;
	LinearLayout linearUndercover;
	int basePeople=4;
	int undercovercount=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_setting);
		linearUndercover=(LinearLayout)this.findViewById(R.id.tem2);
		
		seekPeople=(SeekBar)this.findViewById(R.id.seekPeople);
		peopleCount=(TextView)this.findViewById(R.id.labPeople);
		undercoverCount=(TextView)this.findViewById(R.id.labUndercover);
		txtName=(TextView)this.findViewById(R.id.txtName);
		btnStart=(Button)this.findViewById(R.id.btnStart);
		btn1=(Button)this.findViewById(R.id.btn1);
		btn2=(Button)this.findViewById(R.id.btn2);
		
		gameType=lastGameType();
		if (gameType.equals("game_undercover")) {
			seekPeople.setMax(8);
			basePeople=4;
			txtName.setText("谁是卧底");
			uMengClick("game_undercover");
		} else if (gameType.equals("game_killer")) {
			seekPeople.setMax(10);
			linearUndercover.setVisibility(View.GONE);
			basePeople=6;
			txtName.setText("杀人游戏");
			uMengClick("game_kill_select");
		}
		
		seekPeople.setProgress(0);
		peopleCount.setText(String.valueOf (basePeople));
		undercoverCount.setText("1");
		
		
		
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameInfo.edit().putInt("peopleCount", basePeople+seekPeople.getProgress()).commit();
				gameInfo.edit().putInt("underCount", undercovercount).commit();

				gameInfo.edit().putBoolean("isShow", false).commit();
				gameInfo.edit().putBoolean("isBlank", false).commit();
				gameInfo.edit().putString("word", "全部分类").commit();
				Intent goMain = new Intent();
				goMain.setClass(local_setting.this, local_fanpai.class);
				startActivity(goMain);
			}
		});
		
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				int now=seekPeople.getProgress();
				if(now>0){
					seekPeople.setProgress(now-1);
				}
			}
		});
		btn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				int now=seekPeople.getProgress();
				if(now<seekPeople.getMax()){
					seekPeople.setProgress(now+1);
				}
			}
		});
		
		
		
		
		
		
		seekPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
					peopleCount.setText(String.valueOf (basePeople+progress));
					 undercovercount=(4+progress)/3;
					 undercoverCount.setText(undercovercount+"");
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
