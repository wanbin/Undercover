package cn.centurywar.undercover.view;

import http.PublishHandler;

import java.util.List;
import cn.centurywar.undercover.BaseActivity;
import cn.centurywar.undercover.R;
import cn.centurywar.undercover.net_punish_add;
import cn.centurywar.undercover.net_punish;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author chenzheng_java
 * @description 该类的部分实现模仿了SimpleAdapter
 */
public class MyAdapter extends BaseAdapter {
	private List<Publish> publishs;
	Context context;
	private String uid;
	private net_punish callBackActivity = null;
	private boolean isGM = false;

	/**
	 * 初始化Myadapter
	 * 
	 * @param context
	 *            回调函数，写上就行
	 * @param publishs
	 *            这个是返回的数据LIST
	 * @param uid
	 */
	public MyAdapter(Context context, List<Publish> publishs, String uid) {
		this.publishs = publishs;
		this.context = context;
		this.uid = uid;
	}

	public void setCallBack(net_punish v) {
		callBackActivity = v;
	}

	public void setGM(boolean gm) {
		isGM = gm;
	}

	public final class ViewHolder {
		public TextView content;
		public TextView txtLike;
		public TextView txtDislike;
		public Button likebtn;
		public Button dislikebtn;
		public Button btnshare;
		public RelativeLayout relativeView;
	}

	@Override
	public int getCount() {
		return (publishs == null) ? 0 : publishs.size();
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
			
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.txtContent);
			viewHolder.txtLike = (TextView) convertView
					.findViewById(R.id.txtLike);
			viewHolder.txtDislike = (TextView) convertView
					.findViewById(R.id.txtDislike);
			
			viewHolder.likebtn = (Button) convertView
					.findViewById(R.id.buttonLike);
			
			viewHolder.btnshare = (Button) convertView
					.findViewById(R.id.btnShare);
			
			viewHolder.dislikebtn = (Button) convertView
					.findViewById(R.id.buttonDislike);
			viewHolder.relativeView = (RelativeLayout) convertView
					.findViewById(R.id.relativeView);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 设置各控件的内容在这里
		viewHolder.content.setText(temPublish.content);
		
		

		viewHolder.txtLike.setText(String.format("%s", temPublish.like));
		viewHolder.txtDislike.setText(String.format("%s",
				temPublish.dislike));

		viewHolder.likebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temPublish.likeed = true;
				addcollect(temPublish, 1, viewHolder.likebtn);
//				if(temPublish.likeed){
				viewHolder.txtLike.setText(String.format("%d",
						++temPublish.like));
//			}
			}
		});
		viewHolder.dislikebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				temPublish.dislikeed = true;
				addcollect(temPublish, 2, viewHolder.dislikebtn);
//				if (temPublish.dislikeed) {
				viewHolder.txtDislike.setText(String.format("%d",
						++temPublish.dislike));
//				}
			}
		});
		
		viewHolder.relativeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBackActivity.punishAdd(temPublish.content);
			}
		});
		viewHolder.btnshare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBackActivity.share(temPublish.content);
			}
		});
		return convertView;
	}

	/**
	 * 用户收藏，用户点赞
	 */
	public void addcollect(Publish tempublish, int type, Button btn) {
		setDisableBtn(btn);
		((BaseActivity) this.context).addDamaoxian(tempublish.content);
	}
	public void reomvecollect(Publish tempublish, int type, Button btn) {
		setDisableBtn(btn);
		((BaseActivity) this.context).removeDamaoxian(tempublish.content);
	}

	/**
	 * 把按键状态设置为灰
	 * 
	 * @param btn
	 */
	public void setDisableBtn(Button btn) {
		btn.setClickable(false);
	}

	/**
	 * 管理员审核词条
	 * 
	 * @param id
	 * @param type
	 */
	public void shenhe(Publish tempublish, int type) {
		PublishHandler publishHandler = new PublishHandler(callBackActivity);
		publishHandler.setUid(uid);
		publishHandler.shenHe(tempublish.id, type);
	}

	/**
	 * @author wanhin 真心话大冒险类，需要在里面添加like,dislike内容
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
		public int type = 1;

		public Publish(int id, String name, String content, int like,
				int dislike, boolean likeed, boolean dislikeed, int type) {
			super();
			this.id = id;
			this.content = content;
			this.like = like;
			this.dislike = dislike;
			this.likeed = likeed;
			this.dislikeed = dislikeed;
			this.type = type;
		}
	}
}
