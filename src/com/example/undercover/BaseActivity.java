package com.example.undercover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
	int disWidth;
	int disHeight;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	protected boolean showShack = false;

	private static final String TAG = "TestSensorActivity";
	private static final int SENSOR_SHAKE = 10;
	// 共同存储单元
	protected SharedPreferences gameInfo;
	protected float initTime, duration, curTime, lastTime = 0, shake,
			totalShake, last_x = 0.0f, last_y = 0.0f, last_z = 0.0f;
	
	protected String VersionName = "1.00";
	protected int versionType = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MobclickAgent.setDebugMode(true);
		MobclickAgent.onError(this);
		try {
			VersionName = GetVersion();
			// 初始化版本号信息,在替换界面元素时候使用
			versionType = VersionName.charAt(0) - 48;
		} catch (Exception e) {
		}
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		disWidth = (int) dm.widthPixels;
		disHeight = (int) dm.heightPixels;

		// 共享数据
		gameInfo = getSharedPreferences("gameInfo", 0);

		// MobclickAgent.setSessionContinueMillis(60000);
		SoundPlayer.init(this);
		SoundPlayer.pushSound(R.raw.ball);
		SoundPlayer.pushSound(R.raw.claps3);
		SoundPlayer.pushSound(R.raw.bottle);
		SoundPlayer.pushSound(R.raw.jishi);
		SoundPlayer.pushSound(R.raw.failshout);
		SoundPlayer.pushSound(R.raw.hiscore02);
		SoundPlayer.pushSound(R.raw.whistle);
		SoundPlayer.pushSound(R.raw.normalscore);
		SoundPlayer.pushSound(R.raw.rolling);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// 保持屏幕常亮，仅此一句
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		
		if (sensorManager != null && showShack) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager
							.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void uMengClick(String clickname) {
		MobclickAgent.onEvent(this, clickname);
	}


	@Override
	protected void onStop() {
		super.onStop();
		if (sensorManager != null) {// 取消监听器
			sensorManager.unregisterListener(sensorEventListener);
		}
	}

	/**
	 * 重力感应监听
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			// Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度"
			// + z);
			// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
					|| Math.abs(z) > medumValue) {
				vibrator.vibrate(200);
				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	/**
	 * 动作执行
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				shackAction();
				break;
			}
		}

	};

	public void shackAction() {
		siampleTitle("检测到摇晃，执行操作！");
		Log.i(TAG, "检测到摇晃，执行操作！");
	}

	public void siampleTitle(String title) {
		Toast.makeText(BaseActivity.this, title, Toast.LENGTH_SHORT).show();
	}


	// 以下为存取中断时状态信息的地方
	protected String[] getGuessContent() {
		String temContent = gameInfo.getString("contentStr", "");
		return temContent.split("_");
	}

	protected boolean[] getClickedContent() {
		String temContent = gameInfo.getString("clickedStr", "");
		String[] tem = temContent.split("_");
		Log.v("boolean str", temContent);
		boolean[] cliceked = new boolean[tem.length];
		for (int i = 0; i < tem.length; i++) {
			boolean temboolean = false;
			if (tem[i].equals("1")) {
				temboolean = true;
			}
			cliceked[i] = temboolean;
		}

		return cliceked;
	}

	protected void setContent(String[] content) {
		String contentStr = "";
		for (int i = 0; i < content.length; i++) {
			contentStr += content[i] + "_";
		}
		contentStr = contentStr.substring(0, contentStr.length() - 1);
		gameInfo.edit().putString("contentStr", contentStr).commit();
	}

	protected void updateClicked(boolean[] boolClick) {
		String clickedStr = "";
		for (int i = 0; i < boolClick.length; i++) {
			int tem = 0;
			if (boolClick[i]) {
				tem = 1;
			}
			clickedStr += tem + "_";
		}
		clickedStr = clickedStr.substring(0, clickedStr.length() - 1);
		gameInfo.edit().putString("clickedStr", clickedStr).commit();
	}

	protected void setSon(String son) {
		gameInfo.edit().putString("son", son).commit();
	}

	protected boolean getStatus() {
		return getClickedContent().length > 3;
	}

	protected void cleanStatus() {
		gameInfo.edit().putString("clickedStr", "").commit();
	}

	protected String strFromId(String strid) {
		int id = getResources().getIdentifier(
				"com.example.undercover:string/" + strid, null, null);
		return getResources().getString(id);
	}

	protected String GetVersion() throws NameNotFoundException {
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}
}

