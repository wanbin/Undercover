package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.undercover.view.PunishAdapter;
import com.example.undercover.view.PunishAdapter.PublishUser;

import android.os.Bundle;
import android.widget.ListView;

public class net_room_punish extends BaseActivity {
	JSONArray punish;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netroom_punish);
		listView=(ListView)this.findViewById(R.id.listPunishUser);
		
		try {
//			punish=new JSONArray("[{\"username\":\"NO.3\",\"photo\":\"\",\"content\":\"\\u60e9\\u7f5a\\uff1a\\u76ee\\u524d\\u6700\\u5927\\u7684\\u613f\\u671b\\uff1f\",\"gameuid\":\"-3\"},{\"username\":\"NO.3\",\"photo\":\"\",\"content\":\"\\u60e9\\u7f5a\\uff1a\\u76ee\\u524d\\u6700\\u5927\\u7684\\u613f\\u671b\\uff1f\",\"gameuid\":\"-3\"},{\"username\":\"NO.3\",\"photo\":\"\",\"content\":\"\\u60e9\\u7f5a\\uff1a\\u76ee\\u524d\\u6700\\u5927\\u7684\\u613f\\u671b\\uff1f\",\"gameuid\":\"-3\"}]");
			punish=new JSONArray(getIntent().getStringExtra("punish"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<PublishUser> temPubs=new ArrayList<PublishUser>();
		for(int i=0;i<punish.length();i++){
			JSONObject temObject;
			try {
				temObject = punish.getJSONObject(i);
				PublishUser tem=new PublishUser(temObject.getInt("gameuid"),temObject.getString("username"),temObject.getString("content"),temObject.getString("photo"));
				temPubs.add(tem);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		PunishAdapter adapter = new PunishAdapter(net_room_punish.this, temPubs,
				this.getUid());
		listView.setAdapter(adapter);
	}
}