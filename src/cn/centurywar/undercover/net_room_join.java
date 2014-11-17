package cn.centurywar.undercover;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class net_room_join extends BaseActivity {
	Button btnReflash;
	TextView txtTitle;
	TextView txtContent;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_room_join);
		btnReflash=(Button)this.findViewById(R.id.btnReflash);
		txtTitle=(TextView)this.findViewById(R.id.txtTitle);
		txtContent=(TextView)this.findViewById(R.id.txtContent);
		
		btnReflash.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				roomGetContent();
			}
		});
		roomGetContent();
	}
	
	
	private void reflash(String title,String content){
		if(title.length()==0){
			txtTitle.setText("游戏名称");
		}
		if(content.length()==0){
			txtContent.setText("暂未得到最新身份/指示");
		}
		txtTitle.setText(title);
		txtContent.setText(content);
	}
	@Override
	protected void roomGetContent(){
		super.roomGetContent();
		txtContent.setText("正在获取");
	}
	
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_GET_CONTENT)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				JSONObject roominfo=obj.getJSONObject("roominfo");
				reflash(roominfo.getString("name"),obj.getString("content"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				reflash("","好像出了点问题");
			}
			
		}
	}
}
