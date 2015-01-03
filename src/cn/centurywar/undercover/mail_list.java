package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.centurywar.undercover.view.MailAdapter;
import cn.centurywar.undercover.view.MailAdapter.MailUser;
import cn.centurywar.undercover.view.MyAdapter;
import cn.centurywar.undercover.view.MyAdapter.Publish;

import http.PublishHandler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class mail_list extends BaseActivity {

	ListView listView;
	List<String> data;
	MailAdapter adapter ;
	JSONArray maillist;
	TextView txtTitle;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_list);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		listView=(ListView)this.findViewById(R.id.listview);
		txtTitle=(TextView)this.findViewById(R.id.txtTitle);
		maillist=new JSONArray();
		getAllMail(1);
		txtTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getAllMail(1);
			}
		});
	}
	
	/**
	 * 取得所有新闻
	 */
	protected void getAllMail(int page) {
		txtTitle.setText("正在获取信件");
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getAllMail(page);
	}
	
	@Override
	public void MessageCallBack(JSONObject jsonobj,String cmd) {
		super.MessageCallBack(jsonobj,cmd);
		if(cmd.equals(ConstantControl.MAIL_LIST))
		{
			try{
				String temstr=jsonobj.getString("data");
//				temstr=String.
				maillist=new JSONArray(temstr);
				updateMessage();
			}catch(Exception e){
				txtTitle.setText("数据异常[点击刷新]");
				e.printStackTrace();
			}
		}
		else if (cmd.equals(ConstantControl.MAIL_DEL)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
//				int mailid=obj.getInt("mailid");
//				boolean status=obj.getBoolean("status");
//				if(status){
//					//delItSuccess(mailid);
//				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void MessageCallBackWrong(String cmd) {
		super.MessageCallBackWrong(cmd);
		if(cmd.equals(ConstantControl.MAIL_LIST))
		{
			try{
				txtTitle.setText("数据异常[点击刷新]");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	protected void delItSuccess(int mailid){
		JSONArray newlist=new JSONArray();
		for(int i=0;i<maillist.length();i++){
			try {
				JSONObject temobj =maillist.getJSONObject(i);
				if(temobj.getInt("_id")!=mailid){
					//newlist.put(temobj);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		maillist=newlist;
		adapter.notifyDataSetChanged();
	}
	
	protected void updateMessage() {
		txtTitle.setText("获取到"+maillist.length()+"条信件[点击刷新]");
		List<MailUser> temPubs = new ArrayList<MailUser>();
		for (int i = 0; i < maillist.length(); i++) {
			try {
				JSONObject temobj =maillist.getJSONObject(i);
				//在这里把从网络传回来的参数给初始化为publish实例，并加到list里面
				temPubs.add(new MailUser(temobj.getInt("_id"), temobj
						.getString("content"), temobj.getString("username"),temobj.getString("photo"), temobj.getString("time"),
						temobj.getBoolean("read")));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		adapter= new MailAdapter(mail_list.this, temPubs,
				this.getUid());
		adapter.setCallBack(this);
		listView.setAdapter(adapter);
	}
	
	public void removeMail(int id){
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.delMail(id);
	}
	

	
}
