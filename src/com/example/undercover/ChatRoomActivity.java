package com.example.undercover;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Camera;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatRoomActivity extends Activity
{
    // 声明对象
    private Button mInButton, mSendButton;
    private EditText mEditText01, mEditText02;
    private static final String SERVERIP = "192.168.1.101";
    private static final int SERVERPORT = 6688;
    private Thread mThread = null;
    private Socket mSocket = null;
    private BufferedReader mBufferedReader = null;
    private PrintWriter mPrintWriter = null;
    private  String mStrMSG = "";
    private static String TAG = Camera.class.getSimpleName();
    // ////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        mInButton = (Button) findViewById(R.id.myinternet_tcpclient_BtnIn);
        mSendButton = (Button) findViewById(R.id.myinternet_tcpclient_BtnSend);
        mEditText01 = (EditText) findViewById(R.id.myinternet_tcpclient_EditText01);
        mEditText02 = (EditText) findViewById(R.id.myinternet_tcpclient_EditText02);
        // ////////////////////////////////////////////////////////////////////////////////////
// 登陆
//        mInButton.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (peopleCount > 3)
//				{
//					peopleCount--;
//				}
//				setPeople();
//			}
//		});
        
        mInButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    // ①Socket实例化，连接服务器
                    mSocket = new Socket(SERVERIP, SERVERPORT);
                    // ②获取Socket输入输出流进行读写操作
                    mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
                } catch (Exception e)
                {
                    // TODO: handle exception
                    Log.e(TAG, e.toString());
                }
            }
        });
        // ////////////////////////////////////////////////////////////////////////////////////
// 发送消息
        mSendButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    // 取得编辑框中我们输入的内容
                    String str = mEditText02.getText().toString() + "\n";
                    // 发送给服务器
                    mPrintWriter.print(str);
                    mPrintWriter.flush();
                } catch (Exception e)
                {
                    // TODO: handle exception
                    Log.e(TAG, e.toString());
                }
            }
        });
        mThread = new Thread(mRunnable);
        mThread.start();
    }
    // ////////////////////////////////////////////////////////////////////////////////////
// 线程:监听服务器发来的消息
    private Runnable mRunnable = new Runnable()
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    if ((mStrMSG = mBufferedReader.readLine()) != null)
                    {                        
                        mStrMSG += "\n";// 消息换行
                        mHandler.sendMessage(mHandler.obtainMessage());// 发送消息
                    }                    
                } catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        }
    };
    // ////////////////////////////////////////////////////////////////////////////////////
    Handler mHandler = new Handler()//更新界面的显示（不能直接在线程中更新视图，因为Android线程是安全的）
    {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            // 刷新
            try
            {                
                mEditText01.append(mStrMSG);// 将聊天记录添加进来
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
            }
        }
    };
    // ////////////////////////////////////////////////////////////////////////////////////
}