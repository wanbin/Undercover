package com.example.undercover.view;

import http.PublishHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.undercover.BaseActivity;
import com.example.undercover.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author chenzheng_java
 * @description 该类的部分实现模仿了SimpleAdapter
 */
public class MyAdapter extends BaseAdapter {
	private List<Publish> publishs; 
	Context context;  
    private String uid;
    private BaseActivity callBackActivity=null;
    private int isGM=0;
    
    /**
     * 初始化Myadapter
     * @param context 回调函数，写上就行
     * @param publishs 这个是返回的数据LIST
     * @param uid
     */
    public MyAdapter(Context context,List<Publish> publishs,String uid){  
        this.publishs = publishs;  
        this.context = context;  
        this.uid=uid;
    }  
    
    public void setCallBack(BaseActivity v){
    	callBackActivity=v;
    }
    
	public void setGM(int gm) {
		isGM = gm;
	}
	
    public final class ViewHolder {  
        public TextView title;  
        public TextView info;  
        public TextView sendtime;  
        public Button likebtn;  
        public Button dislikebtn;
        public Button collect;
        public ImageView imageUser;
    }
	@Override
	public int getCount() {
		 return (publishs==null)?0:publishs.size();  
	}
	@Override
	public Object getItem(int position) {
		return publishs.get(position);  
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Publish temPublish = (Publish) getItem(position);
		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_sub_usercontribute, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.txtName);
			viewHolder.sendtime = (TextView) convertView
					.findViewById(R.id.txtTime);
			viewHolder.info = (TextView) convertView
					.findViewById(R.id.txtContent);
			viewHolder.likebtn = (Button) convertView
					.findViewById(R.id.buttonLike);
			viewHolder.dislikebtn = (Button) convertView
					.findViewById(R.id.buttonDislike);
			viewHolder.collect = (Button) convertView
					.findViewById(R.id.buttonCollect);
			viewHolder.imageUser = (ImageView) convertView
					.findViewById(R.id.imageUser);
			
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		if (temPublish.type == 1) {
			viewHolder.imageUser.setImageResource(R.drawable.cerblue01);
		} else if (temPublish.type == 2) {
			viewHolder.imageUser.setImageResource(R.drawable.cerbrown01);
		} else if (temPublish.type == 3) {
			viewHolder.imageUser.setImageResource(R.drawable.ceryellow01);
		} else if (temPublish.type == 4) {
			viewHolder.imageUser.setImageResource(R.drawable.cerpink01);
		} else if (temPublish.type == 5) {
			viewHolder.imageUser.setImageResource(R.drawable.cerpurple01);
		} else if (temPublish.type == 6) {
			viewHolder.imageUser.setImageResource(R.drawable.cergray01);
		}
			
		
		//设置各控件的内容在这里
		viewHolder.title.setText(temPublish.name);  
        viewHolder.info.setText(temPublish.content);
        viewHolder.sendtime.setText(temPublish.sendtime);
		if (isGM==1) {
//			viewHolder.likebtn.setText("通过");
			viewHolder.likebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish, 1);
				}
			});
//			viewHolder.dislikebtn.setText("未通过");
			viewHolder.dislikebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish, 2);
				}
			});
//			viewHolder.collect.setText("直接删除");
			viewHolder.collect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish, 3);
				}
			});
		}
        else{
			if (temPublish.likeed) {
				setDisableBtn(viewHolder.likebtn);
			} else {
				((BaseActivity) this.context).setBtnBrown(viewHolder.likebtn);
			}
			if (temPublish.dislikeed) {
				setDisableBtn(viewHolder.dislikebtn);
			} else {
				((BaseActivity) this.context).setBtnBrown(viewHolder.dislikebtn);
			}
			if (temPublish.collented) {
				setDisableBtn(viewHolder.collect);
			} else {
				((BaseActivity) this.context).setBtnBrown(viewHolder.collect);
			}
        	
			
        	viewHolder.likebtn.setText(String.format("喜欢(%s)", temPublish.like));
        	viewHolder.dislikebtn.setText(String.format("不喜欢(%s)", temPublish.dislike));
        	viewHolder.collect.setText("导入大冒险");
        	
        	
        	 viewHolder.likebtn.setOnClickListener(new OnClickListener(){  
                 @Override  
                 public void onClick(View v) {  
					temPublish.likeed = true;
                 	addcollect(temPublish,1,viewHolder.likebtn);
                 	viewHolder.likebtn.setText(String.format("喜欢(%d)", ++temPublish.like));
                 }  
             }); 
             viewHolder.dislikebtn.setOnClickListener(new OnClickListener(){  
                 @Override  
                 public void onClick(View v) {  
 					temPublish.dislikeed = true;
					addcollect(temPublish, 2,viewHolder.dislikebtn);
                 	viewHolder.dislikebtn.setText(String.format("不喜欢(%d)", ++temPublish.dislike));
                 }  
             });  
     		viewHolder.collect.setOnClickListener(new OnClickListener() {
     			@Override
     			public void onClick(View v) {
					temPublish.collented = true;
     				addcollect(temPublish,3,viewHolder.collect);
     			}
     		});
        }
		return convertView;
	}  
	
	
	/**
	 * 用户收藏，用户点赞
	 */
	public void addcollect(Publish tempublish,int type,Button btn){
		setDisableBtn(btn);
		PublishHandler publishHandler = new PublishHandler(callBackActivity);
		publishHandler.setUid(uid);
		publishHandler.addCollect(tempublish.id,type);
		if(type==3){
			((BaseActivity) this.context).addDamaoxian(tempublish.content);
		}
	}
	
	/**
	 * 把按键状态设置为灰
	 * @param btn
	 */
	public void setDisableBtn(Button btn){
		btn.setClickable(false);
		((BaseActivity) this.context).setBtnPink(btn);
	}
	
	/**
	 * 管理员审核词条
	 * @param id
	 * @param type
	 */
	public void shenhe(Publish tempublish,int type){
		PublishHandler publishHandler = new PublishHandler(callBackActivity);
		publishHandler.setUid(uid);
		publishHandler.shenHe(tempublish.id,type);
	}
	
	/**
	 * @author wanhin
	 *真心话大冒险类，需要在里面添加like,dislike内容
	 */
	public static class Publish {
		public int id;
		public String name;
		public String content;
		public int like;
		public int dislike;

		public boolean likeed = false;
		public boolean dislikeed = false;
		public boolean collented = false;
		public String sendtime="";
		public int type=1;

		public Publish(int id, String name, String content,int like,int dislike,boolean likeed,boolean dislikeed,boolean collented,String sendtime,int type) {
			super();
			this.id = id;
			this.name = name;
			this.content = content;
			this.like = like ;
			this.dislike = dislike;
			this.likeed = likeed;
			this.dislikeed = dislikeed;
			this.collented = collented;
			this.sendtime = sendtime;
			this.type=type;
		} 
	}  
}

