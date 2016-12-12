package com.sinovoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

public class HttpUtil {
	/**
	 * 发送HTTP请求
	 * @param address	请求的URL地址
	 * @param text		发送的文本字符串
	 * @param listener	http回调监听
	 */
	public static void ttsSendRequest(String address, String text, HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpClient client = new HttpClient();
				PostMethod myPost = new PostMethod(address);
				
				//发送的HTTP头信息，详细参数可以参考开发手册
				myPost.setRequestHeader("Content-Type", "text/xml");
				myPost.setRequestHeader("charset", "utf-8");
				myPost.setRequestHeader("x-app-key", ConfigUtil.APP_KEY);
				myPost.setRequestHeader("x-sdk-version", "3.6");
				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				myPost.setRequestHeader("x-request-date", date);
				myPost.setRequestHeader("x-task-config", "capkey=" + ConfigUtil.TTS_CAP_KEY + ",audioformat=pcm16k16bit,pitch=5,volume=5,speed=5");
				String str = date + ConfigUtil.DEVELOPER_KEY;
				myPost.setRequestHeader("x-session-key", Md5.getMD5(str.getBytes()));
				myPost.setRequestHeader("x-udid", "101:123456789");
				
				//设置连接超时时间
				client.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
				//设置读取超时时间
				client.getHttpConnectionManager().getParams().setSoTimeout(15000);
				RequestEntity entity;
				try {
					entity = new StringRequestEntity(text, "text/html", "utf-8");
					myPost.setRequestEntity(entity);
					int statusCode = client.executeMethod(myPost);
					if (statusCode == HttpStatus.SC_OK) {				
						if (listener != null) {
							InputStream inputStream = myPost.getResponseBodyAsStream();
							String result = IOUtils.toString(inputStream, "iso-8859-1");
							result = result.substring(result.indexOf("</ResponseInfo>") + 15);
							listener.onFinish(result);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onError(e);
					}
				}
			}
		}).start();
	}

	/**
	 * 发送asr的音频到服务器
	 * @param address	服务器的地址
	 * @param filePath	音频文件所在路径
	 * @param listener	回调监听
	 */
	public static void asrSendRequest(String address, String filePath, HttpCallbackListener listener){
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
				HttpClient client = new HttpClient();
				PostMethod myPost = new PostMethod(address);
				
				//发送的HTTP头信息，详细参数可以参考开发手册
				myPost.setRequestHeader("Content-Type", "text/xml");
				myPost.setRequestHeader("charset", "utf-8");
				myPost.setRequestHeader("x-app-key", ConfigUtil.APP_KEY);
				myPost.setRequestHeader("x-sdk-version", "3.6");
				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				myPost.setRequestHeader("x-request-date", date);
				myPost.setRequestHeader("x-task-config", "capkey=" + ConfigUtil.ASR_CAP_KEY + ",audioformat=pcm16k16bit");
				String str = date + ConfigUtil.DEVELOPER_KEY;
				myPost.setRequestHeader("x-session-key", Md5.getMD5(str.getBytes()));
				myPost.setRequestHeader("x-udid", "101:123456789");
				
				//设置连接超时时间
				client.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
				//设置读取超时时间
				client.getHttpConnectionManager().getParams().setSoTimeout(15000);
				RequestEntity entity;
				try {
					File fileSrc = new File(filePath);
					if (!fileSrc.exists()){
						System.out.println("文件不存在。");
						return;
					}
					byte [] content = null;
					FileInputStream fis = new FileInputStream(fileSrc);
					content = new byte[fis.available()];
					fis.read(content);
					
					entity = new StringRequestEntity(new String(content, "iso-8859-1"), "text/html", "iso-8859-1");
					myPost.setRequestEntity(entity);
					int statusCode = client.executeMethod(myPost);
					if (statusCode == HttpStatus.SC_OK) {
						if (listener != null) {
							listener.onFinish(myPost.getResponseBodyAsString());
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onError(e);
					}
				}
//			}
//		}).start();
	}
}
