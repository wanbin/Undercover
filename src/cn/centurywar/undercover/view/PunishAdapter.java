package cn.centurywar.undercover.view;

import http.PublishHandler;

import java.util.List;
import cn.centurywar.undercover.BaseActivity;
import cn.centurywar.undercover.R;
import cn.centurywar.undercover.net_punish;
import cn.centurywar.undercover.net_room_punish;

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
public class PunishAdapter extends BaseAdapter {
	private List<PublishUser> publishs; 
	Context context;  
    private net_room_punish callBackActivity=null;
    
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
    
	public void setCallBack(net_room_punish v) {
		callBackActivity = v;
	}
	
    public final class ViewHolder {  
        public ImageView imageUser;
        public TextView txtName;  
        public TextView txtPunish;  
        public Button btnShare;  
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
			viewHolder.btnShare = (Button) convertView
					.findViewById(R.id.btnShare);
			convertView.setTag(viewHolder);
			//设置各控件的内容在这里
			viewHolder.txtName.setText(temPublish.name);  
	        viewHolder.txtPunish.setText(temPublish.punish);
	        if(temPublish.photo.length()>0){
	        	ImageFromUrl(viewHolder.imageUser,temPublish.photo,R.drawable.default_photo);
	        }
	        
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBackActivity.share(temPublish.name,temPublish.punish);
			}
		});
		return convertView;
	}  

	public void ImageFromUrl(ImageView imageView,String url,int defaultphoto){
		//第一次调用初始化
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
		ImageLoader.getInstance().init(config);
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(defaultphoto) //设置图片在下载期间显示的图片  
		 .showImageForEmptyUri(defaultphoto)//设置图片Uri为空或是错误的时候显示的图片  
		.build();//构建完成  
		ImageLoader.getInstance().displayImage(url, imageView,options);
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

