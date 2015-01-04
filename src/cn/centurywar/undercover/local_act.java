package cn.centurywar.undercover;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import cn.centurywar.util.Punish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class local_act  extends BaseActivity {
	TextView txtAction;
	TextView txtDes;
	TextView txtLast;
	Button btnNext;
	Button btnShare;
	ImageView imgBg;
	Timer timer;
	int remainSec=0;
	int onesSec=300;
	
	ProgressBar proBar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_act);
		showShack = true;
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		txtAction=(TextView)this.findViewById(R.id.txtAction);
		txtDes=(TextView)this.findViewById(R.id.txtDes);
		txtLast=(TextView)this.findViewById(R.id.txtLast);
		btnNext=(Button)this.findViewById(R.id.btnNext);
		btnShare=(Button)this.findViewById(R.id.btnShare);
		
		proBar=(ProgressBar)this.findViewById(R.id.proBar);
		
		imgBg=(ImageView)this.findViewById(R.id.imgBg);
		
		btnNext.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.click();
				nextPunish();
			}
		});		
		btnShare.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				shareIt(local_act.this,"发现了个好玩的聚会惩罚："+txtDes.getText().toString()+"的/地"+txtAction.getText().toString()+"(爱上聚会 http://www.centurywar.cn )");
			}
		});		
		
		imgShake();
		timer = new Timer();
		timer.schedule(timetask, 0, 10);
		proBar.setMax(onesSec);
		txtLast.setText(getLastString());
		uMengClick("game_action");
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mincost();
			super.handleMessage(msg);
		}
	};
	
	// 传递时间
	private TimerTask timetask = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	
	/**
	 * 这个方法为了3秒取一次惩罚
	 */
	private void mincost(){
		if(remainSec>0){
			remainSec--;
			proBar.setProgress(remainSec);
		}
		if(remainSec<=0){
			btnNext.setEnabled(true);
		}
	}
	
	

	/**
	 * 背景图摇晃
	 */
	public void imgShake(){
		RotateAnimation rt = new RotateAnimation(-10,10,
				dip2px(this,151),dip2px(this,120));
		rt.setDuration(1000);
		rt.setFillAfter(true);
		imgBg.startAnimation(rt);
		rt.setRepeatCount(-1);	
        rt.setRepeatMode(Animation.REVERSE);
	}
	
	@Override
	public void shackAction() {
		if(nextPunish()){
			SoundPlayer.shake();
		}
	}
	private boolean nextPunish(){
		if (remainSec <= 0) {
			btnNext.setEnabled(false);
			remainSec = onesSec;
		} else {
			return false;
		}
		if(isNetworkAvailable(this)){
			ActionRandomOne();
			txtAction.setText("正在获取");
			txtDes.setText("正在获取");
			uMengClick("game_action_count");
		}
		setGameIsNew(ConstantControl.GAME_ACTION,false);
		return true;
	}

	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ACTION_RANDOMONE)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				JSONObject objobj=obj.getJSONObject("content");
				String action=objobj.getString("action");
				String des=objobj.getString("des");
				txtAction.setText(action);
				txtDes.setText(des);
				txtLast.setText(getLastString());
				setLastString("上一条："+des+"[的/地]"+action);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				txtAction.setText("可以免除");
				txtDes.setText("可以免除");
			}
		}
	}
	@Override
	public void MessageCallBackWrong(String cmd) {
		super.MessageCallBackWrong(cmd);
		if(cmd.equals(ConstantControl.ACTION_RANDOMONE))
		{
			try{
				txtAction.setText("服务器错误");
				txtDes.setText("服务器错误");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void setLastString(String str){
		setToObject("ACT_LAST",str);
	}
	public String getLastString(){
		return getFromObject("ACT_LAST");
	}
	

}
