package cn.centurywar.undercover;

import java.util.HashMap;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.centurywar.util.Punish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
				SoundPlayer.click();
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
		SoundPlayer.shake();
	}
	private void nextPunish(){
		txtPunish.setText(getRandomMaoxianFromLocate(true));
	}

	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.PUNISH_RANDOMONE)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				JSONArray objarr=obj.getJSONArray("content");
				JSONObject random=objarr.getJSONObject(0);
				String punish=random.getString("content");
				txtPunish.setText(punish);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				txtPunish.setText("可以免除惩罚");
			}
		}
	}
	
	@Override
	public void MessageCallBackWrong(String cmd) {
		super.MessageCallBackWrong(cmd);
		if(cmd.equals(ConstantControl.PUNISH_RANDOMONE))
		{
			try{
				txtPunish.setText(getRandomMaoxianFromLocate(false));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
