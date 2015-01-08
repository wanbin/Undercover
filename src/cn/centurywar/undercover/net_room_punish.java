package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.centurywar.undercover.view.PunishAdapter;
import cn.centurywar.undercover.view.PunishAdapter.PublishUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class net_room_punish extends BaseActivity {
	JSONArray punish;
	ListView listView;
	Button btnPunish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_room_punish);
		listView=(ListView)this.findViewById(R.id.listPunishUser);
		btnPunish=(Button)this.findViewById(R.id.btnPunish);
		
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
		
		
		btnPunish.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(net_room_punish.this, local_punish.class);
				startActivity(intentGo);
			}
		});
		
		PunishAdapter adapter = new PunishAdapter(net_room_punish.this, temPubs,
				this.getUid());
		adapter.setCallBack(this);
		listView.setAdapter(adapter);
	}
	public void share(String name,String content){
		shareIt(net_room_punish.this,name+" 受到了 "+content+"(爱上聚会 http://www.centurywar.cn )");
	}

}
