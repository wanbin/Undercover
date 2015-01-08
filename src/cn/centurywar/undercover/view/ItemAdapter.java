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

public class ItemAdapter extends GameBaseAdapter {
	private List<String> arrays = null;
	private Button curDel_btn;
	private TextView curDel_text;
	private float x, ux;
	private local_punish_list callBackClass;

	public ItemAdapter(Context mContext,local_punish_list call, List<String> arrays) {
		this.context = mContext;
		this.arrays = arrays;
		callBackClass=call;
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
			view = LayoutInflater.from(context).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.btnDel = (Button) view.findViewById(R.id.del);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// 为每一个view项设置触控监听
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag();
				// 当按下时处理
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 设置背景为选中状态
					v.setBackgroundResource(R.color.light_gray);
					// 获取按下时的x轴坐标
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
				// 设置背景为未选中正常状态
					v.setBackgroundResource(R.color.WRITE);
					if(holder.btnDel.getVisibility()==View.VISIBLE){
						holder.btnDel.setVisibility(View.GONE);
					}
					else{
						if(curDel_btn!=null){
							curDel_btn.setVisibility(View.GONE);
						}
						holder.btnDel.setVisibility(View.VISIBLE);
						curDel_btn=holder.btnDel;
					}
					
				}  else {// 其他模式
				// 设置背景为未选中正常状态
					v.setBackgroundResource(R.color.WRITE);
				}
				return true;
			}
		});
		final String showTxt=this.arrays.get(position);
		viewHolder.tvTitle.setText(showTxt);
		// 为删除按钮添加监听事件，实现点击删除按钮时删除该项
		viewHolder.btnDel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (curDel_btn != null)
					curDel_btn.setVisibility(View.GONE);
				arrays.remove(position);
				callBackClass.removeString(showTxt);
				notifyDataSetChanged();
			}
		});
		return view;
	}

	final static class ViewHolder {
		TextView tvTitle;
		Button btnDel;
	}
}