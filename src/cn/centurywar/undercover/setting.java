package cn.centurywar.undercover;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
	LinearLayout LinearFeedback;
	LinearLayout LinearVersion;
	LinearLayout LinearHelp;
	LinearLayout LinearInfo;
	LinearLayout LinearMail;
	TextView txtSound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		txtVersion=(TextView)this.findViewById(R.id.txtVersion);
		txtSound=(TextView)this.findViewById(R.id.txtSound);
		txtUsername=(TextView)this.findViewById(R.id.txtUsername);
		
		LinearSound=(LinearLayout)this.findViewById(R.id.LinearSound);
		LinearFeedback=(LinearLayout)this.findViewById(R.id.LinearFeedback);
		LinearVersion=(LinearLayout)this.findViewById(R.id.LinearVersion);
		LinearInfo=(LinearLayout)this.findViewById(R.id.LinearInfo);
		LinearMail=(LinearLayout)this.findViewById(R.id.LinearMail);
		LinearHelp=(LinearLayout)this.findViewById(R.id.LinearHelp);
		
		txtVersion.setText(getVersion());
		
		txtUsername.setText(getFromObject("username"));
		
		String soundOn = getFromObject("sound");
		if (soundOn.equals("")||soundOn.equals("on")) {
			setSwithSound(true);
		}else{
			setSwithSound(false);
		}
		
		usernameList.add(txtUsername);
		
		LinearSound.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				setSwithSound(!SoundPlayer.getSoundSt());
			}
		});
		LinearFeedback.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				 FeedbackAgent agent = new FeedbackAgent(setting.this);
				 agent.startFeedbackActivity();
			}
		});
		LinearHelp.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
				intentGo.setClass(setting.this, game_list.class);
				intentGo.putExtra("showtype", 2);
				startActivity(intentGo);
			}
		});
		LinearVersion.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				showHelp("VERSION");
			}
		});
		
		LinearInfo.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
				intentGo.setClass(setting.this, setting_username.class);
				startActivity(intentGo);
			}
		});
		LinearMail.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
				intentGo.setClass(setting.this, mail_list.class);
				startActivity(intentGo);
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 20:
			txtUsername.setText(getFromObject("username"));
			break;
		}
	}
//	@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // 获取当前活动的Activity实例
//        Activity subActivity = getLocalActivityManager().getCurrentActivity();
//        //判断是否实现返回值接口
//        if (subActivity instanceof OnTabActivityResultListener) {
//            //获取返回值接口实例
//            OnTabActivityResultListener listener = (OnTabActivityResultListener) subActivity;
//            //转发请求到子Activity
//            listener.onTabActivityResult(requestCode, resultCode, data);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
	
}
