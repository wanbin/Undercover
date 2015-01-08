package cn.centurywar.undercover.view;

import http.PublishHandler;

import java.util.List;
import cn.centurywar.undercover.BaseActivity;
import cn.centurywar.undercover.R;
import cn.centurywar.undercover.mail_list;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.util.Log;
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
public class MailAdapter extends GameBaseAdapter {
	private Button hasPress;
	private List<MailUser> publishs; 
    private mail_list callBackActivity=null;
    
    /**
     * 初始化Myadapter
     * @param context 回调函数，写上就行
     * @param publishs 这个是返回的数据LIST
     * @param uid
     */
    public MailAdapter(Context context,List<MailUser> publishs,String uid){  
        this.publishs = publishs;  
        this.context = context;  
        
    }  
    
    public void setCallBack(mail_list view){
    	this.callBackActivity=view;
    }
    
	
    public final class ViewHolder {  
        public ImageView imageUser;
        public TextView txtName;  
        public TextView txtPunish;  
        public TextView txtTime;  
        public Button btnDel;
    }
	@Override
	public int getCount() {
		 return publishs.size();  
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MailUser temPublish = this.publishs.get(position);
		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.mail_list_content, null);
			viewHolder = new ViewHolder();
			viewHolder.imageUser = (ImageView) convertView
					.findViewById(R.id.imgPhoto);
			viewHolder.txtName = (TextView) convertView
					.findViewById(R.id.txtName);
			viewHolder.txtTime = (TextView) convertView
					.findViewById(R.id.txtTime);
			viewHolder.txtPunish = (TextView) convertView
					.findViewById(R.id.txtPunish);
			viewHolder.btnDel = (Button) convertView
					.findViewById(R.id.btnDel);
			convertView.setTag(viewHolder);
			
			//设置各控件的内容在这里
			viewHolder.txtName.setText(temPublish.fromname);  
	        viewHolder.txtPunish.setText(temPublish.content);
	        viewHolder.txtTime.setText(temPublish.time);

	        if(temPublish.photo.length()>0){
	        	ImageFromUrl(viewHolder.imageUser,temPublish.photo,R.drawable.default_photo);
	        }
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
        convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(hasPress!=null){
					hasPress.setVisibility(View.GONE);
				}
				viewHolder.btnDel.setVisibility(View.VISIBLE);
				hasPress=viewHolder.btnDel;
			}
		});
        
        viewHolder.btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				publishs.remove(position);
				notifyDataSetChanged();
				callBackActivity.removeMail(temPublish.id);
			}
		});
      
		
		return convertView;
	}  
	
	/**
	 * @author wanhin
	 *真心话大冒险类，需要在里面添加like,dislike内容
	 */
	public static class MailUser {
		public int id;
		public String content;
		public String fromname;
		public String time;
		public String photo;
		public boolean isread;

		/**
		 * @param id
		 * @param name
		 * @param content
		 * @param photo
		 */
		public MailUser(int id, String content, String fromname,String photo,String time,boolean isread) {
			super();
			this.id = id;
			this.content = content;
			this.fromname=fromname;
			this.time=time;
			this.isread=isread;
			this.photo=photo;
		} 
		
	}  
}

