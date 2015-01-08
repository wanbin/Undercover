package cn.centurywar.undercover.view;

import java.util.List;

import cn.centurywar.undercover.R;
import cn.centurywar.undercover.local_punish_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GuessHistoryAdapter extends GameBaseAdapter {
	private List<GuessString> arrays = null;

	public GuessHistoryAdapter(Context mContext, List<GuessString> arrays) {
		this.context = mContext;
		this.arrays = arrays;
	}

	public int getCount() {
		return this.arrays.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.guess_history, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvTitle.setText(arrays.get(position).content);
		if(arrays.get(position).right){
			viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.gamegreen2));
		}else{
			viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.gamered));
		}
		return view;
		
	}

	final static class ViewHolder {
		TextView tvTitle;
		Button btnDel;
	}
	public static class GuessString {
		public String content;
		public boolean right;

		/**
		 * @param id
		 * @param name
		 * @param content
		 * @param photo
		 */
		public GuessString(String content, boolean right) {
			this.content=content;
			this.right=right;
		} 
		
	} 
}