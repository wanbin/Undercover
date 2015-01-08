package cn.centurywar.undercover.view;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GameBaseAdapter extends BaseAdapter {
	Context context;  

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void ImageFromUrl(ImageView imageView,String url,int defaultphoto){
		//第一次调用初始化
		if(ImageLoader.getInstance()==null){
			ImageLoader.getInstance().destroy();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
			ImageLoader.getInstance().init(config);
		}
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(defaultphoto) //设置图片在下载期间显示的图片  
		 .showImageForEmptyUri(defaultphoto)//设置图片Uri为空或是错误的时候显示的图片  
		 .cacheInMemory(true)
         .cacheOnDisk(true)
		.build();//构建完成  
		ImageLoader.getInstance().displayImage(url, imageView,options);
	}

}
