package cn.centurywar.undercover;

import http.BehaveHandler;
import http.UserHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

/**
 * @author wanhin
 *
 */
public class BaseActivity extends FragmentActivity  implements httpCallBack{
	//这是服务器的地址
	protected String serverUrl = "http://www.centurywar.cn/CenturyServer/Entry.php";
//	protected String serverUrl = "http://192.168.1.31/Entry.php";
	int disWidth;
	int disHeight;
	protected SensorManager sensorManager;
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
	protected String faguan;
	protected String police;
	protected String killer;
	protected String nomalpeople;

	protected static int gameuid = 0;
	protected static String uid = "";
	protected UMSocialService mController;
	protected static BehaveHandler behaveHandler =null;
	
	
	protected static List<TextView> usernameList;
	
	boolean isneedshack=false;
	
	/**
	 * 用户是否为GM管理员
	 */
	protected static int isGm=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MobclickAgent.setDebugMode(false);
		MobclickAgent.onError(this);
		faguan = strFromId("txtFaGuan");
		police = strFromId("txtPolice");
		killer = strFromId("txtKiller");
		nomalpeople = strFromId("txtNormal");
		if (usernameList == null) {
			usernameList = new ArrayList<TextView>();
		}
		try {
			VersionName = GetVersion();
			// 初始化版本号信息,在替换界面元素时候使用
			versionType = VersionName.charAt(0) - 48;
		} catch (Exception e) {
		}
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		disWidth = dm.widthPixels;
		disHeight = dm.heightPixels;

		// 共享数据
		gameInfo = getSharedPreferences("gameInfo", 0);

		// MobclickAgent.setSessionContinueMillis(60000);
		SoundPlayer.init(this);
		SoundPlayer.pushSound(R.raw.failshout);
		SoundPlayer.pushSound(R.raw.hiscore02);
		SoundPlayer.pushSound(R.raw.whistle);
		SoundPlayer.pushSound(R.raw.normalscore);
		SoundPlayer.pushSound(R.raw.uncover);
		SoundPlayer.pushSound(R.raw.shake_sound);
		SoundPlayer.pushSound(R.raw.vote_popu);
		SoundPlayer.pushSound(R.raw.cancel);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// 保持屏幕常亮，仅此一句
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initJPUSH();
	}
	protected void updateUsername(String username){
		for(int i=0;i<usernameList.size();i++){
			TextView tem=usernameList.get(i);
			if(tem!=null){
				tem.setText(username);
			}
		}
	}

	/**
	 * 初始化JPUSH推送
	 */
	protected void initJPUSH() {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		JPushInterface.setAlias(this, getUid(), null);
	}
	
	/**
	 * 从友盟取得全局配置
	 * @param configname
	 * @return
	 */
	protected String getConfigFromIntent(String configname) {
		return MobclickAgent.getConfigParams(this, configname);
	}


	protected void initTitle(String title) {
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setVisibility(View.VISIBLE);
	}



	protected void openShareEdit(String share) {
		mController.openShare(this, false);
		// UMServiceFactory.shareTo(BaseActivity.this, share, null);
	}


	@Override
	protected void onStart() {
		super.onResume();
	}

	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		
		if (sensorManager != null && showShack) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 用户友盟点击，发送到服务器后台
	 * @param clickname
	 */
	public void uMengClick(String clickname) {
		MobclickAgent.onEvent(this, clickname);
		if (behaveHandler == null) {
			behaveHandler = new BehaveHandler(this);
		}
		behaveHandler.addUserBehave(clickname, "");
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
				isneedshack=true;
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

	protected void setGameType(String type) {
		gameInfo.edit().putString("gametype", type).commit();
	}

	protected String lastGameType() {
		return gameInfo.getString("gametype", "");
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
				"cn.centurywar.undercover:" + type + "/" + strid, null, null);
	}

	protected String GetVersion() throws NameNotFoundException {
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}


	




	protected Map<String, StringBuffer> mapWords;

	protected void initUndercoverWords() {

		// 判断词语是否已经出现过，如果剩余词语小于20个，那个重新加载
		String hasGuess = gameInfo.getString("user_guessed", "");

		// words 所有谁是卧底词汇,包括分类
		mapWords = new HashMap<String, StringBuffer>();
		String[] words = getResources().getStringArray(R.array.undercoverword);
		int wordCount = 0;
		boolean reInit = false;
		for (int n = 0; n < words.length; n++) {
			String[] children = words[n].split("_");
			StringBuffer temstr = mapWords.get(children[0]);
			if (temstr == null) {
				temstr = new StringBuffer();
			}
			if (hasGuess.lastIndexOf(children[1] + "_" + children[2]) > 0) {
				int a = 1;
				continue;
			}
			temstr.append(children[1] + "_" + children[2] + ",");
			mapWords.put(children[0], temstr);
			wordCount++;
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
			if (hasGuess.lastIndexOf(children[1] + "_" + children[2]) > 0) {
				continue;
			}
			temstr.append(children[1] + "_" + children[2] + ",");
			mapWords.put(children[0], temstr);
			wordCount++;
		}

		// 初始化用户自定义词汇

		String[] userSetting = gameInfo.getString("user_setting", "")
				.split(",");
		for (int n = 0; n < userSetting.length; n++) {
			String[] children = userSetting[n].split("_");
			if (children.length != 3) {
				continue;
			}
			if (hasGuess.lastIndexOf(children[1] + "_" + children[2]) > 0) {
				continue;
			}
			StringBuffer temstr = mapWords.get(children[0]);
			if (temstr == null) {
				temstr = new StringBuffer();
			}
			temstr.append(children[1] + "_" + children[2] + ",");
			mapWords.put(children[0], temstr);
			wordCount++;
		}
		// 如果词汇小于20个，那个重新
		if (wordCount < 20 && reInit == false) {
			reInit = true;
			gameInfo.edit().putString("user_guessed", "").commit();
			initUndercoverWords();
		}
	}

	protected void setHasGuessed(String content) {
		String hasguess = gameInfo.getString("user_guessed", "");
		gameInfo.edit().putString("user_guessed", hasguess + "," + content)
				.commit();
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


	protected void setBtnGrayColor(Button btn) {
		setTouchActionFactory(btn, R.color.graybtn, R.color.graybtn);
	}


	protected void setBtnBlueColor(Button btn) {
		setTouchActionFactory(btn, R.color.bluebtn1, R.color.bluebtn2);
	}


	protected void setTouchActionFactory(Button btn, final int id1,
			final int id2) {
		btn.setBackgroundResource(id1);
		btn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(id2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(id1);
				}

				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					v.setBackgroundResource(id1);
				}

				return false;
			}
		});
	}

	private void setTouchActionFactory(ImageView btn, final int id1,
			final int id2) {
		btn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(id2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(id1);
				}

				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					v.setBackgroundResource(id1);
				}

				return false;
			}
		});
	}

	public void addShortcut(Context context) {

		String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
		// 快捷方式要启动的包
		Intent intent = new Intent(context, context.getClass());

		// 设置快捷方式的参数
		Intent shortcutIntent = new Intent(ACTION_INSTALL_SHORTCUT);
		// 设置名称
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context
				.getResources().getString(R.string.app_name)); // 设置启动 Intent
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 设置图标
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context,
						R.drawable.ic_launcher));
		// 只创建一次快捷方式
		shortcutIntent.putExtra("duplicate", false);
		// 创建
		context.sendBroadcast(shortcutIntent);
	}
	

/**
 * 友盟提供取得设备号的方法
 * @param context
 * @return
 */
public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return device_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
}

	
	/* 如果需要返回值的话，在这里面进行处理
	 * 
	 * @param jsonobj
	 * @throws Exception
	 */
	@Override
	public  void MessageCallBack(JSONObject jsonobj,String cmd){
		try {
			int code=jsonobj.getInt("code");
			SayWithCode(code);
			CallBackPublicCommand(jsonobj,cmd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public  void MessageCallBackWrong(String cmd){
		try {
			CallBackPublicCommandWrong(cmd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 回调的公共处理类方法
	 * @param jsonobj
	 * @param cmd
	 */
	public void CallBackPublicCommand(JSONObject jsonobj,String cmd)
	{
		
		
	}
	
	/**
	 * 回调的公共处理类方法
	 * @param jsonobj
	 * @param cmd
	 */
	public void CallBackPublicCommandWrong(String cmd)
	{
		
		
	}
	
	
	/**
	 * 设置用户基本信息
	 * @param o
	 */
	protected void setUserInfo(JSONObject o) {
		try {
//			isGm=o.getInt("isgm");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		gameInfo.edit().putString("userinfo", o.toString()).commit();
	}

	
	//设置持久化存储
	protected boolean setToObject(String name,String value){
		return gameInfo.edit().putString(name, value).commit();
	}
	
	
	//获取持久化存储
	protected String getFromObject(String key){
		return gameInfo.getString(key, "");
	}
	
	//设置持久化存储
	protected boolean setToObjectInt(String name,int value){
		return gameInfo.edit().putInt(name, value).commit();
	}
	
	//获取持久化存储
	protected int getFromObjectInt(String key){
		return gameInfo.getInt(key, 0);
	}
	
	/**
	 * 从本地取得用户基本信息
	 * @return
	 */
	protected JSONObject getUserInfoFromLocal() {
		JSONObject obj = null;
		try {
			obj = new JSONObject(gameInfo.getString("userinfo", ""));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
	
	/**
	 * 服务器返回的编码解释
	 * @param code
	 */
	protected   void SayWithCode(int code) {
		if (code == ConstantCode.SUCCESS) {
//			Toast.makeText(BaseActivity.this, "成功!!!", Toast.LENGTH_LONG).show();
		}else if (code == ConstantCode.FAILE) {
			Toast.makeText(BaseActivity.this, "失败!!!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public String getUid() {
		if (uid.equals("")) {
			uid = BaseActivity.getDeviceInfo(this);
		}
		return uid;
	}

	
	/**
	 * 判断当前网络是否可用的功能
	 * @param act
	 * @return
	 */
	public static boolean detect(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 发送Toast提示
	 * 
	 * @param message
	 */
	@Override
	public void ToastMessage(String message) {
		Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	protected void ToastMessageLong(String message) {
		Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 取得用户信息，可以判断用户当前的身份，是否显示待审核的词汇
	 */
	protected void getUserInfo() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.getUserInfo(getUid());
	}
	
	
	protected void joinRoom(int roomid) {
		UserHandler userHandler = new UserHandler(this);
		userHandler.joinRoom(roomid, getUid());
	}
	protected void createRoom() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.createRoom(getUid());
	}
	protected void getRoomInfo() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.getRoomInfo();
	}
	protected void roomGetContent() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.RoomGetContent();
	}
	
	protected void RoomStartGame(int type,int addPeople) {
		UserHandler userHandler = new UserHandler(this);
		userHandler.RoomStartGame(type,addPeople);
	}
	
	protected void RoomPunish(String gameuidstr) {
		UserHandler userHandler = new UserHandler(this);
		userHandler.RoomPunish(gameuidstr);
	}
	
	protected void MailSend(String content,int gameuid) {
		UserHandler userHandler = new UserHandler(this);
		userHandler.MailSend(content,gameuid);
	}
	
	protected void NameChange(String username,String photo) {
		UserHandler userHandler = new UserHandler(this);
		userHandler.NameChange(username,photo);
	}
	protected void PublishRandomOne() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.PublishRandomOne();
	}
	
	protected void ActionRandomOne() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.ActionRandomOne();
	}
	
	protected void UndercoverWordRandomOne() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.UndercoverWordRandomOne();
	}
	
	
	protected void GuessRandomOne() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.GuessRandomOne();
	}
	
	/**
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	*/
	public static int dip2px(Context context, float dpValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (dpValue * scale + 0.5f);
	}

	/**
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	*/
	public static int px2dip(Context context, float pxValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (pxValue / scale + 0.5f);
	}
	
	public void clickGame(int gameid) {
		
	}
	/**
	 * 异步加载图片
	 * @param imageView
	 * @param url
	 * @param defaultphoto
	 */
	public void ImageFromUrl(ImageView imageView,String url,int defaultphoto){
		//第一次调用初始化
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(defaultphoto) //设置图片在下载期间显示的图片  
		 .showImageForEmptyUri(defaultphoto)//设置图片Uri为空或是错误的时候显示的图片  
		.build();//构建完成  
		ImageLoader.getInstance().displayImage(url, imageView,options);
	}
	public Bitmap BitmapFromUrl(String url,int defaultphoto){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		DisplayImageOptions options;  
		options = new DisplayImageOptions.Builder()  
		 .showImageOnLoading(defaultphoto) //设置图片在下载期间显示的图片  
		 .showImageForEmptyUri(defaultphoto)//设置图片Uri为空或是错误的时候显示的图片  
		.build();//构建完成  
		return ImageLoader.getInstance().loadImageSync(url);
	}
	protected void ImageFromLocal(ImageView imageView,String url){
		//第一次调用初始化
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(url, imageView);
	}
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "3.0";
		}
	}
	
	
	
	
	protected Map<String, StringBuffer> map;

	protected StringBuffer appendString(StringBuffer temstr, String stradd) {
		if (temstr == null) {
			temstr = new StringBuffer();
		}
		return temstr.append(stradd + ",");
	}
	/**
	 * 取到本地的真心话
	 * @return
	 */
	public String getTurns() {
		String[] zhenxin = getResources().getStringArray(R.array.zhenxinhua);
		Random random = new Random();
		int index = Math.abs(random.nextInt()) % (zhenxin.length);
		return zhenxin[index];
	}
	
	public boolean addDamaoxian(String damaoxian) {
		JSONArray jsonarray = getLocateDamaoxian();
		for (int i = 0; i < jsonarray.length(); i++) {
			try {
				JSONObject tem = jsonarray.getJSONObject(i);
				String str = tem.getString("data");
				if (str.equals(damaoxian)) {
					return false;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject newtem = new JSONObject();
		try {
			newtem.put("data", damaoxian);
			jsonarray.put(newtem);
			setLocateDamaoxian(jsonarray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	
	public boolean removeDamaoxian(String damaoxian) {
		JSONArray jsonarray = getLocateDamaoxian();
		JSONArray jsonarray2=new JSONArray();
		for (int i = 0; i < jsonarray.length(); i++) {
			try {
				JSONObject tem = jsonarray.getJSONObject(i);
				String str = tem.getString("data");
				if (!str.equals(damaoxian)) {
					jsonarray2.put(tem);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setLocateDamaoxian(jsonarray2);
		return true;
	}
	
	/**
	 * 取到网络的真心话
	 * @return
	 */
	protected JSONArray getLocateDamaoxian() {
		JSONArray json = new JSONArray();
		try {
			json = new JSONArray(gameInfo.getString("damaoxian", ""));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return json;
	}
	/**
	 * 取得一条网络随机的大冒险，优先先网络，如果没有网络，那么取本地
	 * 
	 * @return
	 */
	public String getRandomMaoxianFromLocate(boolean trynet) {
		JSONArray jsonarray = getLocateDamaoxian();
		Random a = new Random();
		// 这里有个BUG
		if (jsonarray.length() < 20) {
			// 防止词汇较少一直取相同的词汇情况
			if (Math.abs(a.nextInt()) % 10 < 8) {
				return getRandomMaoxian("start",trynet);
			}
		}
		try {
			if (jsonarray.length() == 0) {
				return getRandomMaoxian("start",trynet);
			}
			int index = Math.abs(a.nextInt()) % jsonarray.length();
			return jsonarray.getJSONObject(index).getString("data").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getRandomMaoxian("start",trynet);
	}
	
	/**
	 * 把网络取到的真心话保存到本地
	 * @param objarray
	 */
	protected void setLocateDamaoxian(JSONArray objarray) {
		gameInfo.edit().putString("damaoxian", objarray.toString()).commit();
	}
	protected String getRandomMaoxian(String strkey,boolean fromnet) {
		//判断用户下在获取词汇
		if(isNetworkAvailable(this)&&fromnet){
			PublishRandomOne();
			return  "正在获取";
		}
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
			strReturn = strReturn + getRandomMaoxian(tem[1],fromnet);
		}
		return strReturn;
	}


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
		
	/**
	 * 判断是否联网
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {
        	return false;
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    } 
                }
            }
        }
        return false;
	}
	
	/**
	 * 判断是否开GPS
	 * @param context
	 * @return
	 */
	public static boolean isGpsEnabled(Context context) {   
        LocationManager lm = ((LocationManager) context   
                .getSystemService(Context.LOCATION_SERVICE));   
        List<String> accessibleProviders = lm.getProviders(true);   
        return accessibleProviders != null && accessibleProviders.size() > 0;   
    } 
	
	/**
	 * 判断是否wifi
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {   
        ConnectivityManager mgrConn = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        TelephonyManager mgrTel = (TelephonyManager) context   
                .getSystemService(Context.TELEPHONY_SERVICE);   
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn   
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel   
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);   
    } 
	  /**
	   * 判断是否3G
	 * @param context
	 * @return
	 */
	public static boolean is3rd(Context context) {   
	        ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
	        if (networkINfo != null   
	                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {   
	            return true;   
	        }   
	        return false;   
	    }
	
	
	public void shareIt(Activity activ,String content){
		shareItImg(activ,content,"");
	}
	
	
	public void shareItImg(Activity activ,String content,String img){
		final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 设置分享内容
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activ, "1103446374",
                "34exCuoYFu7mlL9l");
		qZoneSsoHandler.addToSocialSDK();
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		mController.setShareContent(content);
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activ, "1103446374",
                "34exCuoYFu7mlL9l");
		
		qqSsoHandler.addToSocialSDK();  
		// 设置分享图片, 参数2为图片的url地址
		if(img.length()>0){
			mController.setShareMedia(new UMImage(this, img));
		}
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		mController.openShare(activ, false);
	}
	
	
	
	
	public boolean checkGameIsNew(int gameid){
		return gameInfo.getBoolean("gamenew_"+getVersion()+"_"+gameid,true);
	}
	public void setGameIsNew(int gameid,boolean isnew){
		gameInfo.edit().putBoolean("gamenew_"+getVersion()+"_"+gameid, isnew).commit();
	}
	
	public boolean checkNetGameIsNew(int gameid){
		return gameInfo.getBoolean("netgamenew_"+getVersion()+"_"+gameid,true);
	}
	public void setNetGameIsNew(int gameid,boolean isnew){
		gameInfo.edit().putBoolean("netgamenew_"+getVersion()+"_"+gameid, isnew).commit();
	}
	
	/**
	 * 添加缩略图形式，节省流量
	 * @param url
	 */
	public String getImgUrlSmall(String url){
		if (disWidth <= 320) {
			return url + "!KUANx320";
		} else if (disWidth <= 480) {
			return url + "!KUANx480";
		} else {
			return url + "!KUANx600";
		}
	}
	public String getImgBanner(String url){
		return url+"!480X120";
	}

}

