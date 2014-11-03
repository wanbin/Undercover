package com.example.undercover.view;

import http.PublishHandler;

import java.util.List;
import com.example.undercover.BaseActivity;
import com.example.undercover.R;

import android.content.Context;
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
public class PunishAdapter extends BaseAdapter {
	private List<PublishUser> publishs; 
	Context context;  
    private BaseActivity callBackActivity=null;
    
    /**
     * 初始化Myadapter
     * @param context 回调函数，写上就行
     * @param publishs 这个是返回的数据LIST
     * @param uid
     */
    public PunishAdapter(Context context,List<PublishUser> publishs,String uid){  
        this.publishs = publishs;  
        this.context = context;  
    }  
    
    
	
    public final class ViewHolder {  
        public ImageView imageUser;
        public TextView txtName;  
        public TextView txtPunish;  
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
		final PublishUser temPublish = (PublishUser) getItem(position);
		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.net_room_punish_list, null);
			viewHolder = new ViewHolder();
			viewHolder.imageUser = (ImageView) convertView
					.findViewById(R.id.imgPhoto);
			viewHolder.txtName = (TextView) convertView
					.findViewById(R.id.txtName);
			viewHolder.txtPunish = (TextView) convertView
					.findViewById(R.id.txtPunish);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//设置各控件的内容在这里
		viewHolder.txtName.setText(temPublish.name);  
        viewHolder.txtPunish.setText(temPublish.punish);
		return convertView;
	}  
	
	
	
	/**
	 * @author wanhin
	 *真心话大冒险类，需要在里面添加like,dislike内容
	 */
	public static class PublishUser {
		public int id;
		public String name;
		public String punish;
		public String photo;

		/**
		 * @param id
		 * @param name
		 * @param content
		 * @param photo
		 */
		public PublishUser(int id, String name, String content,String photo) {
			super();
			this.id = id;
			this.name = name;
			this.punish=content;
			this.photo=photo;
		} 
	}  
}

