package com.example.undercover;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;

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
	private ImageView btnreturn;
	private UMSocialService controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MobclickAgent.setDebugMode(false);
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

	protected void initShare(int id) {
		Button btnShare = (Button) findViewById(id);
		btnShare.setVisibility(View.VISIBLE);
		btnShare.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				openShareEdit("我发现了个超好玩的谁是卧底游戏，大家快来玩吧 http://zhushou.360.cn/detail/index/soft_id/706695");
			}
		});
	}


	protected void initBtnBack(int id) {
		Button btnBack = (Button) findViewById(id);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void initBtnInfo(int id, final String infostr) {
		Button btnInfo = (Button) findViewById(id);
		btnInfo.setVisibility(View.VISIBLE);
		btnInfo.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BaseActivity.this, infostr, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	private void initShare() {
		// 设置新浪SSO handler
		controller = UMServiceFactory.getUMSocialService("adfads",
				RequestType.SOCIAL);
		controller.getConfig().setSinaSsoHandler(new SinaSsoHandler());
	}

	protected void openShareEdit(String share) {
		UMServiceFactory.shareTo(BaseActivity.this, share, null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**
		 * 使用SSO必须添加，指定获取授权信息的回调页面，并传给SDK进行处理
		 */
		UMSsoHandler sinaSsoHandler = controller.getConfig()
				.getSinaSsoHandler();
		if (sinaSsoHandler != null
				&& requestCode == UMSsoHandler.DEFAULT_AUTH_ACTIVITY_CODE) {
			sinaSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onStart() {
		super.onResume();
	}

	protected void initBtnReturn() {
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				finish();
			}
		});
		btnreturn.setVisibility(View.VISIBLE);
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

	protected String lastGameType(){
		return gameInfo.getString("gametype", "");
	}

	protected void setGameType(String type) {
		gameInfo.edit().putString("gametype", type).commit();
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
		int id = stringToId(strid, "string");
		return getResources().getString(id);
	}

	protected int stringToId(String strid, String type) {
		return getResources().getIdentifier(
				"com.example.undercover:" + type + "/" + strid, null, null);
	}

	protected String GetVersion() throws NameNotFoundException {
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}

	// libary = getResources().getStringArray(getWords(word[num]));
	// int selectindex = Math.abs(random.nextInt()) % libary.length;
	// content = getRandomString(libary[selectindex]);
	// String[] children = new String[2];
	// children = contnettxt.split("_");
	// int sonindex = Math.abs(random.nextInt()) % 2;
	// son = children[sonindex];
	// String father = children[Math.abs(sonindex - 1)];
	// String[] ret = new String[peopleCount];
	// for (int n = 0; n < ret.length; n++) {
	// ret[n] = father;
	// }
	// for (int i = 0; i < underCount; i++) {
	// int tem;
	// do {
	// tem = Math.abs(random.nextInt()) % peopleCount;
	// } while (ret[tem].equals(son));
	// ret[tem] = son;
	// }
	// // 设置content
	// setContent(ret);
	// setSon(son);
	// return ret;

	protected Map<String, StringBuffer> map;

	protected void getMaoxian() {
		String[] zhenxin = getResources().getStringArray(R.array.damaoxian);
		map = new HashMap<String, StringBuffer>();
		for (int n = 0; n < zhenxin.length; n++) {
			String[] children = zhenxin[n].split("_");
			if (children.length == 2) {
				StringBuffer temstr = map.get("start");
				if (temstr == null) {
					temstr = new StringBuffer();
				}
				temstr.append(zhenxin[n] + "&");
				map.put("start", temstr);
			} else {
				StringBuffer temstr = map.get(children[0]);
				if (temstr == null) {
					temstr = new StringBuffer();
				}
				temstr.append(children[1] + "_" + children[2] + "&");
				map.put(children[0], temstr);
			}
		}
	}

	protected StringBuffer appendString(StringBuffer temstr, String stradd) {
		if (temstr == null) {
			temstr = new StringBuffer();
		}
		return temstr.append(stradd + ",");
	}

	protected String getRandomMaoxian(String strkey) {
		if (map == null) {
			getMaoxian();
		}
		StringBuffer temstr = map.get(strkey);
		if (temstr == null) {
			return "";
		}
		String[] children = temstr.toString().split("&");
		Random random = new Random();
		int randomindex = 0;
		if (children.length > 1) {
			randomindex = Math.abs(random.nextInt()) % (children.length - 1);
		}
		String temmaoxian = children[randomindex];
		String[] tem = temmaoxian.split("_");
		String strReturn = tem[0];
		if (tem.length <= 1) {
			return strReturn;
		}
		if (!tem[1].equals("end")) {
			strReturn = strReturn + getRandomMaoxian(tem[1]);
			// Log.v(strReturn, tem[1]);
		}
		return strReturn;
	}

	public String getDamaoxian() {
		return getRandomMaoxian("start");
	}

	protected Map<String, StringBuffer> mapWords;

	protected void initUndercoverWords() {
		// words 所有谁是卧底词汇,包括分类
		mapWords = new HashMap<String, StringBuffer>();
		String[] words = getResources().getStringArray(R.array.undercoverword);
		for (int n = 0; n < words.length; n++) {
			String[] children = words[n].split("_");
			StringBuffer temstr = mapWords.get(children[0]);
			if (temstr == null) {
				temstr = new StringBuffer();
			}
			temstr.append(children[1] + "_" + children[2] + ",");
			mapWords.put(children[0], temstr);
		}
		// 初始化网络更新词汇
		String[] underWordVersion = MobclickAgent.getConfigParams(this,
				"under_string_version").split("\n");
		for (int n = 0; n < underWordVersion.length; n++) {
			String[] children = underWordVersion[n].split("_");
			if (children.length != 3) {
				continue;
			}
			StringBuffer temstr = mapWords.get(children[0]);
			if (temstr == null) {
				temstr = new StringBuffer();
			}
			temstr.append(children[1] + "_" + children[2] + ",");
			mapWords.put(children[0], temstr);
		}
	}


	protected String[] getUnderWords(String wordstype) {
		initUndercoverWords();
		StringBuffer returnstr = mapWords.get(wordstype);
		Set<String> stringSet = null;
		if (returnstr == null) {
			returnstr = new StringBuffer();
			stringSet = mapWords.keySet();
			Iterator iterator = stringSet.iterator();
			while (iterator.hasNext()) {
				String temStr = (String) iterator.next();
				returnstr.append(mapWords.get(temStr).toString());
			}
		}
		return returnstr.toString()
				.substring(0, returnstr.toString().length() - 1).split(",");
	}

	protected String[] getUnderKind() {
		initUndercoverWords();
		StringBuffer returnstr = new StringBuffer();
		Set<String> stringSet = mapWords.keySet();
		Iterator iterator = stringSet.iterator();
		while (iterator.hasNext()) {
			returnstr.append((String) iterator.next() + "_");
		}
		returnstr.append("全部");
		return returnstr.toString().split("_");
	}


	protected Map jsonToMap(JSONObject data) {
		Iterator keyIter = data.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			try {
				value = data.get(key);
				valueMap.put(key, value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return valueMap;
	}

	protected int importUnderCoverWord(String words) {

		return 1;
	}
}

