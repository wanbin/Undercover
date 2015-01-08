package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.centurywar.undercover.view.GuessHistoryAdapter;
import cn.centurywar.undercover.view.GuessHistoryAdapter.GuessString;
import cn.centurywar.undercover.view.ItemAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class local_cai extends BaseActivity {
	int click_times;
	
	// 参与人数，默认为 6
	Random random = new Random();
	TextView txtDes;
	TextView txtContent;
	TextView txtTime;

	TextView txtWrong;
	TextView txtRight;
	
	Button butStart;
	ListView listView;
	RelativeLayout bg;

	List<GuessString> historyData;
	GuessHistoryAdapter historyAdapter;
	
	private SensorManager sensorMgr;
	Sensor sensor;
	private float x, y, z;
	Timer timer;
	boolean gamestart=false;
	//一局一分钟
	int onesTime=60;
	int remaintime=0;
	int willStartTime=0;
	int wordIndex=0;
	//恢复到初始状态才可显示
	boolean isHuifu=false;
	
	
	
	JSONArray gameArray=new JSONArray();

	List<String> rightArray=new ArrayList<String>();
	List<String> wrongArray=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_cai);
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		txtDes=(TextView)this.findViewById(R.id.txtDes);
		txtContent=(TextView)this.findViewById(R.id.txtContent);
		txtTime=(TextView)this.findViewById(R.id.txtTime);
		
		txtRight=(TextView)this.findViewById(R.id.txtRight);
		txtWrong=(TextView)this.findViewById(R.id.txtWrong);
		
		butStart=(Button)this.findViewById(R.id.butStart);
		bg=(RelativeLayout)this.findViewById(R.id.bg);
		listView=(ListView)this.findViewById(R.id.listView);
		
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		SensorEventListener lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				x = e.values[SensorManager.DATA_X];
				y = e.values[SensorManager.DATA_Y];
				z = e.values[SensorManager.DATA_Z];
				
				if((int)z>=8){
					guessWrong();
				}else if((int)z<=-8){
					guessRight();
				}else if((int)z>-2&&(int)z<2){
					isHuifu=true;
					gameReset();
				}
//				txtDes.setText((int)z+"向上翻屏表示跳过，向下翻屏表示正确");
			}
			public void onAccuracyChanged(Sensor s, int accuracy) {

			}

		};

		// 注册listener，第三个参数是检测的精确度
		sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
		
		
		timer = new Timer();
		timer.schedule(timetask, 0, 1000);
		
		txtDes.setText("向上翻屏表示跳过，向下翻屏表示正确");
		txtContent.setText("疯狂猜词");
		butStart.setText("开始游戏");
		txtTime.setVisibility(View.GONE);
		
		butStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameWillStart();
			}
		});
		uMengClick("game_guess");
		
		
		historyData=new ArrayList<GuessString>();
		historyAdapter= new GuessHistoryAdapter(this,historyData);  
        listView.setAdapter(historyAdapter); 
	}
	
	
	
	
	//将要开始，有倒计时
	private void gameWillStart(){
		wrongArray.clear();
		rightArray.clear();
		wordIndex=0;
		txtContent.setText("将要开始");
		willStartTime=4;
		remaintime=onesTime;
		txtTime.setText("3");
		GuessRandomOne();
		
		historyData.clear();
		historyAdapter.notifyDataSetChanged();
		
		uMengClick("game_guess_click");
		
		updateContent();
		butStart.setVisibility(View.GONE);
	}
	
	private void gameReset(){
		bg.setBackgroundColor(Color.WHITE);
		updateContent();
		txtContent.setTextColor(Color.BLACK);
		txtTime.setTextColor(Color.BLACK);
	}
	
	private void updateContent(){
		if(gamestart){
			if(wordIndex>=gameArray.length()){
				gameEnd();
			}else{
				JSONObject temobj;
				try {
					temobj = gameArray.getJSONObject(wordIndex);
					String tem=temobj.getString("content");
					String des=temobj.getString("key");
					txtContent.setText(tem);
					txtDes.setText(des);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					txtContent.setText("取词错误");
				}
				
			}
		}
		txtRight.setText(rightArray.size()+"");
		txtWrong.setText(wrongArray.size()+"");
	}
	private void gameEnd(){
		gamestart=false;
		remaintime=0;
		txtContent.setText("游戏结束");
		butStart.setClickable(true);
		butStart.setVisibility(View.VISIBLE);
		txtTime.setVisibility(View.GONE);
	}
	
	private void guessRight(){
		if(gamestart&&isHuifu){
			bg.setBackgroundColor(getResources().getColor(R.color.gamegreen2));
			try {
				JSONObject temobj;
				temobj = gameArray.getJSONObject(wordIndex);
				String tem=temobj.getString("content");
				rightArray.add(tem);
				historyData.add(new GuessString(tem,true));
				historyAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wordIndex++;
			txtContent.setText("正确");
			txtContent.setTextColor(Color.WHITE);
			txtTime.setTextColor(Color.WHITE);
			isHuifu=false;
		}
	}
	private void guessWrong(){
		if(gamestart&&isHuifu){
			bg.setBackgroundColor(getResources().getColor(R.color.gamered));
			try {
				JSONObject temobj;
				temobj = gameArray.getJSONObject(wordIndex);
				String tem=temobj.getString("content");
				wrongArray.add(tem);
				historyData.add(new GuessString(tem,false));
				historyAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wordIndex++;
			txtContent.setText("错误");
			txtContent.setTextColor(Color.WHITE);
			txtTime.setTextColor(Color.WHITE);
			butStart.setVisibility(View.GONE);
			isHuifu=false;
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mincost();
			super.handleMessage(msg);
		}
	};
	
	// 传递时间
	private TimerTask timetask = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	
	/**
	 * 这个方法为了3秒取一次惩罚
	 */
	private void mincost(){
		if(willStartTime>0){
			willStartTime--;
			txtContent.setText(willStartTime+"");
		}else if(remaintime>0){
			if(!gamestart){
				gamestart=true;
				updateContent();
			}
			txtTime.setVisibility(View.VISIBLE);
			remaintime--;
			txtTime.setText(remaintime+"");
		}else if(gamestart){
			gameEnd();
		}
		
	}
	
	
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.GUESS_RANDOMONE)) {
			try {
				JSONArray obj = new JSONArray(jsonobj.getString("data"));
				gameArray=obj;
//				txtLast.setText(getLastString());
//				setLastString("上一条："+des+"[的/地]"+action);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				txtAction.setText("可以免除");
				txtDes.setText("可以免除");
			}
		}
	}
	@Override
	public void MessageCallBackWrong(String cmd) {
		super.MessageCallBackWrong(cmd);
		if(cmd.equals(ConstantControl.GUESS_RANDOMONE))
		{
			try{
//				txtAction.setText("服务器错误");
				txtDes.setText("服务器错误");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
