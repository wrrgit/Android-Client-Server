package com.wqx.test.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Message;

public class HttpUtils {

	private static final int GETVALUE = 1001;
	HttpUtils() {
	}
	public static URL url = null;
	public static HttpURLConnection huc = null;
    public static String hostname = "http://xxx.xxx.197.1:8080";//修改为自己电脑的ip地址
	public static String sendGetRequestLogin(String user, String pwd) {
		String result = null;
		String path = hostname + "/test/servlet/loginservlet";
		try {
			StringBuilder requestUrl = new StringBuilder(path);
			requestUrl.append("?").append("userName=").append(user).append("&")
					.append("passWord=").append(pwd);
			String urlPath = requestUrl.toString();
			System.getProperty("file.encoding");
			android.util.Log.i("wqx", "urlPath="+urlPath);
			url = new URL(urlPath);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestProperty("charset", "UTF-8");  
			huc.setRequestMethod("GET");
			huc.setConnectTimeout(15000);
			huc.setReadTimeout(5000);
			huc.setDoInput(true);
			if (huc != null) {
				if (huc.getResponseCode() == 200) {
					/*请求成功不代表登录成功：举个例子，你问小王吃饭没，小王回答你了，就代表请求成功。小王没理你，就代表请求没成功，有可能是他没听到你说话，或者他不想理你。
					 * 小王回答你说吃过了，代表登录成功。小王回答你说没吃，代表登录失败。*/
					android.util.Log.i("wqx", "请求登录!");
					//inputStreamReader字节流读取转为字符流,可以一个个字符读也可以读到一个buffer  
                    //getInputStream是真正去连接网络获取数据
					InputStream in = huc.getInputStream(); 
					//使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
                    StringBuilder s = new StringBuilder();  
                    String line;
                    while ((line = reader.readLine()) != null) {  
                        s.append(line);  
                    }  
                    reader.close();  
                    //Message message = Message.obtain();  
                    //message.what = GETVALUE;  
                    //message.obj = s.toString();  
                    //handler.sendMessage(message); 
					result = s.toString();
					android.util.Log.i("wqx", "result="+result);
				} else {
					android.util.Log.i("wqx", "请求登录Fail!");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			android.util.Log.i("wqx", "e="+e);
			e.printStackTrace();
		}
        finally{
        	if(huc != null)
        		huc.disconnect();
        }
		return result;
	}
	public static String sendGetRequestRegister(String user, String pwd) {
		String result = null;
		String path = hostname + "/test/servlet/registerservlet";
		try {
			StringBuilder requestUrl = new StringBuilder(path);
			requestUrl.append("?").append("userName=").append(user).append("&")
					.append("passWord=").append(pwd);
			String urlPath = requestUrl.toString();
			android.util.Log.i("wqx", "urlPath="+urlPath);
			url = new URL(urlPath);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("GET");
			huc.setConnectTimeout(15000);
			huc.setReadTimeout(5000);
			huc.setRequestProperty("charset", "UTF-8");  
			huc.setDoInput(true);  
			if (huc != null) {
				if (huc.getResponseCode() == 200) {
					 android.util.Log.i("wqx", "请求注册!");
					 InputStream in = huc.getInputStream();  
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
                     StringBuilder s = new StringBuilder();  
                     String line;
                     while ((line = reader.readLine()) != null) {  
                         s.append(line);  
                     }  
                     reader.close();
					 result = s.toString();
					 android.util.Log.i("wqx", "result="+result);
				} else {
					android.util.Log.i("wqx", "请求注册Fail!");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			android.util.Log.i("wqx", "e="+e);
			e.printStackTrace();
		}
        finally{
        	if(huc != null)
        		huc.disconnect();
        }
		return result;
	}
}
