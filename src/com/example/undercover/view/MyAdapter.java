package com.example.undercover.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author chenzheng_java
 * @description 该类的部分实现模仿了SimpleAdapter
 */
public class MyAdapter extends BaseAdapter {
	private List<Publish> publishs; 
	Context context;  
    
    public MyAdapter(Context context,List<Publish> publishs){  
        this.publishs = publishs;  
        this.context = context;  
    }  
	
    public final class ViewHolder {  
        public TextView title;  
        public TextView info;  
        public Button likebtn;  
        public Button dislikebtn;
        public Button collect;
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
			viewHolder.likebtn = (Button) convertView
					.findViewById(R.id.buttonLike);
			viewHolder.dislikebtn = (Button) convertView
					.findViewById(R.id.buttonDislike);
			viewHolder.collect = (Button) convertView
					.findViewById(R.id.buttonCollect);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.title.setText(temPublish.name);  
        viewHolder.info.setText(temPublish.content);  
        
        viewHolder.likebtn.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Toast.makeText(context,   
                        "[textViewItem01.setOnClickListener]点击了"+temPublish.name,   
                        Toast.LENGTH_SHORT).show();  
            }  
        });  
		return convertView;
	}  
	
	
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

