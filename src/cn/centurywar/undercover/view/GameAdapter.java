package cn.centurywar.undercover.view;

import http.PublishHandler;

import java.util.List;
import cn.centurywar.undercover.BaseActivity;
import cn.centurywar.undercover.R;
import cn.centurywar.undercover.homegame;
import cn.centurywar.undercover.mail_list;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
public class GameAdapter extends GameBaseAdapter {
	private List<GameContent> publishs; 
    private BaseActivity callBackActivity=null;
    
    /**
     * 初始化Myadapter
     * @param context 回调函数，写上就行
     * @param publishs 这个是返回的数据LIST
     * @param uid
     */
    public GameAdapter(Context context,List<GameContent> publishs,String uid){  
        this.publishs = publishs;  
        this.context = context;  
    } 
    
    public void setCallBack(BaseActivity view){
    	this.callBackActivity=view;
    }
    
	
    public final class ViewHolder {  
        public ImageView imageUser;
        public ImageView imgHelp;
        public TextView txtName;  
        public TextView txtTime;  
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final GameContent temPublish = (GameContent) getItem(position);
		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.game_list_content, null);
			viewHolder = new ViewHolder();
			viewHolder.imageUser = (ImageView) convertView
					.findViewById(R.id.imgPhoto);
			
			viewHolder.imgHelp = (ImageView) convertView
					.findViewById(R.id.imgHelp);
			
			viewHolder.txtName = (TextView) convertView
					.findViewById(R.id.txtName);
			viewHolder.txtTime = (TextView) convertView
					.findViewById(R.id.txtTime);
			convertView.setTag(viewHolder);
	        
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//设置各控件的内容在这里
		viewHolder.txtName.setText(temPublish.name);  
        viewHolder.txtTime.setText(temPublish.people);

//        if(viewHolder.imageUser.getBackground()==callBackActivity.getResources().getDrawable(R.drawable.fang_pink_pressed)){
        if(temPublish.img.length()>0){
        	ImageFromUrl(viewHolder.imageUser,temPublish.img+"!50X50",R.drawable.fang_write_pressed);
        }else{
        	viewHolder.imageUser.setBackgroundResource(R.drawable.fang_write_pressed);
        }
        
        if(temPublish.helpid.length()>0){
        	viewHolder.imgHelp.setVisibility(View.VISIBLE);
        }else{
        	viewHolder.imgHelp.setVisibility(View.GONE);
        }
//        }
        
        
        convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBackActivity.clickGame(temPublish.id);
			}
		});
        
        viewHolder.imgHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBackActivity.showHelp(temPublish.helpid);
			}
		});
        
      
		
		return convertView;
	}  

	
	/**
	 * @author wanhin
	 *真心话大冒险类，需要在里面添加like,dislike内容
	 */
	public static class GameContent {
		public int id;
		public String img;
		public String name;
		public String people;
		public String des;
		public String helpid;

		/**
		 * @param id
		 * @param name
		 * @param content
		 * @param photo
		 */
		public GameContent(int id,String img, String name, String people,String des,String helpid) {
			super();
			this.id = id;
			this.img=img;
			this.name = name;
			this.people=people;
			this.des=des;
			this.helpid=helpid;
		} 
		
	}  
}

