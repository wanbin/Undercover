package com.example.undercover;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author wanbin
 * 设置界面
 */
public class setting  extends BaseActivity {
	TextView txtVersion;
	TextView txtUsername;
	LinearLayout LinearSound;
	TextView txtSound;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		txtVersion=(TextView)this.findViewById(R.id.txtVersion);
		txtSound=(TextView)this.findViewById(R.id.txtSound);
		txtUsername=(TextView)this.findViewById(R.id.txtUsername);
		
		LinearSound=(LinearLayout)this.findViewById(R.id.LinearSound);
		txtVersion.setText(getVersion());
		
		txtUsername.setText(getFromObject("username"));
		
		String soundOn = getFromObject("sound");
		if (soundOn.equals("")||soundOn.equals("on")) {
			setSwithSound(true);
		}else{
			setSwithSound(false);
		}
		
		
		LinearSound.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				setSwithSound(!SoundPlayer.getSoundSt());
			}
		});
	}

	private void setSwithSound(boolean isChecked) {
		if (isChecked) {
			SoundPlayer.setSoundSt(true);
			txtSound.setText("开");
			setToObject("sound", "on");
		} else {
			SoundPlayer.setSoundSt(false);
			txtSound.setText("关");
			setToObject("sound", "off");
		}
	}
	
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "3.0";
		}
	}
}
