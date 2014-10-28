package com.example.undercover;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class net_home extends BaseActivity {
	Button btnJoin;
	Button btnCreate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_home);
		btnJoin = (Button) this.findViewById(R.id.btnJoin);
		btnJoin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				joinRoom(10001);
			}
		});
		btnCreate = (Button) this.findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				createRoom();
			}
		});
	}
	
	
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_CREATE)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				int i=1;
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if(cmd.equals(ConstantControl.ROOM_JOIN)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
			
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
}
