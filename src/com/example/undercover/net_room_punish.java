package com.example.undercover;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;

public class net_room_punish extends BaseActivity {
	JSONArray punish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			punish=new JSONArray(getIntent().getStringExtra("punish"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
