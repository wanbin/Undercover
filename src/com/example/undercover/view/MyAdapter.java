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
        public ImageView likebtn;  
        public ImageView dislikebtn;
        public ImageView collect;
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
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_sub_usercontribute, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.txtName);
			viewHolder.info = (TextView) convertView
					.findViewById(R.id.txtContent);
			viewHolder.likebtn = (ImageView) convertView
					.findViewById(R.id.buttonLike);
			viewHolder.dislikebtn = (ImageView) convertView
					.findViewById(R.id.buttonDislike);
			viewHolder.collect = (ImageView) convertView
					.findViewById(R.id.buttonCollect);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//设置各控件的内容在这里
		viewHolder.title.setText(temPublish.name);  
        viewHolder.info.setText(temPublish.content);  
        
		if (isGM==1) {
//			viewHolder.likebtn.setText("通过");
			viewHolder.likebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish.id, 1);
				}
			});
//			viewHolder.dislikebtn.setText("未通过");
			viewHolder.dislikebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish.id, 2);
				}
			});
//			viewHolder.collect.setText("直接删除");
			viewHolder.collect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					shenhe(temPublish.id, 3);
				}
			});
		}
        else{
        	 viewHolder.likebtn.setOnClickListener(new OnClickListener(){  
                 @Override  
                 public void onClick(View v) {  
                 	addcollect(temPublish.id,1);
                 }  
             }); 
             viewHolder.dislikebtn.setOnClickListener(new OnClickListener(){  
                 @Override  
                 public void onClick(View v) {  
                 	addcollect(temPublish.id,2);
                 }  
             });  
     		viewHolder.collect.setOnClickListener(new OnClickListener() {
     			@Override
     			public void onClick(View v) {
     				addcollect(temPublish.id, -1);
     			}
     		});
        }
		return convertView;
	}  
	
	/**
	 * 用户收藏，用户点赞
	 */
	public void addcollect(int id,int type){
		PublishHandler publishHandler = new PublishHandler(callBackActivity);
		publishHandler.setUid(uid);
		publishHandler.addCollect(id,type);
	}
	
	
	public void shenhe(int id,int type){
		PublishHandler publishHandler = new PublishHandler(callBackActivity);
		publishHandler.setUid(uid);
		publishHandler.shenHe(id,type);
	}
	
	/**
	 * @author wanhin
	 *真心话大冒险类，需要在里面添加like,dislike内容
	 */
	public static class Publish {
		public int id;
		public String name;
		public String content;

		public Publish(int id, String name, String content) {
			super();
			this.id = id;
			this.name = name;
			this.content = content;
		} 
	}  
}

