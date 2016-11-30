package com.wqx.test;


import com.wqx.test.util.HttpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ProgressBar pb;
	private Button loginBt;
	private Button registerBt;
	private EditText userName;
	private EditText passWord;
	private Handler mHandler;
	private static final int LOGIN_SUCCESS_FLAG = 1;
	private static final int REGISTER_SUCCESS_FLAG = 2;
	private static final int LOGIN_FAIL_FLAG = 3;
	private static final int REGISTER_FAIL_FLAG = 4;
	private static final int USERNAME_EXIST = 5;
	private static final int UNKNOWN_FLAG = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pb = (ProgressBar) findViewById(R.id.proBar);
		userName = (EditText) findViewById(R.id.userName);
		passWord = (EditText) findViewById(R.id.passWord);
		loginBt = (Button) findViewById(R.id.loginBt);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				android.util.Log.i("wqx", "msg.what=" + msg.what);
				switch (msg.what) {
				case LOGIN_SUCCESS_FLAG:
					Intent intent = new Intent(MainActivity.this,
							LoginSuccessActivity.class);
					startActivity(intent);
					break;
				case REGISTER_SUCCESS_FLAG:
					startActivity(new Intent(MainActivity.this,
							RegisterSuccessActivity.class));
					break;
				case LOGIN_FAIL_FLAG:
				case REGISTER_FAIL_FLAG:
				case USERNAME_EXIST:
					startActivity(new Intent(MainActivity.this,
							ErrorActivity.class));
					break;
				default:
					startActivity(new Intent(MainActivity.this,
							UnknownActivity.class));
					break;
				}
			}
		};
		loginBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.util.Log.i("wqx", "beforeaaaaaaaaaaaaaa");
				new Thread() {
					public void run() {
						Message msg = Message.obtain();
						String result = HttpUtils.sendGetRequestLogin(userName
								.getText().toString(), passWord.getText()
								.toString());
						if (result != null) {
							if (result.equals("login_success")) {
								msg.what = LOGIN_SUCCESS_FLAG;
								mHandler.sendMessage(msg);
							} else if (result.equals("login_fail")) {
								msg.what = LOGIN_FAIL_FLAG;
								mHandler.sendMessage(msg);
							}
						} else {
							msg.what = UNKNOWN_FLAG;
							mHandler.sendMessage(msg);
						}
					};
				}.start();
				android.util.Log.i("wqx", "after^^^^^^^^");
			}
		});
		registerBt = (Button) findViewById(R.id.registerBt);
		registerBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.util.Log.i("wqx", "beforeaaaaaaaaaaaaaa");
				new Thread() {
					public void run() {
						Message msg = Message.obtain();
						String result = HttpUtils.sendGetRequestRegister(
								userName.getText().toString(), passWord
										.getText().toString());
						if (result != null) {
							if (result.equals("register_success")) {
								msg.what = REGISTER_SUCCESS_FLAG;
								mHandler.sendMessage(msg);
							} else if (result.equals("register_fail")) {
								msg.what = REGISTER_FAIL_FLAG;
								mHandler.sendMessage(msg);
							} else if (result.equals("userName_exist")) {
								msg.what = USERNAME_EXIST;
								mHandler.sendMessage(msg);
							} else{
								msg.what = UNKNOWN_FLAG;
								mHandler.sendMessage(msg);
							}
						} else {
							msg.what = UNKNOWN_FLAG;
							mHandler.sendMessage(msg);
						}
					};
				}.start();
				android.util.Log.i("wqx", "after^^^^^^^^");
			}
		});

	}

}
