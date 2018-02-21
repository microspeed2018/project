package com.zjzmjr.core.common.biz.weixin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * 发送http请求工具类
 * @author Administrator
 *
 */
public class HttpsRequestUtil {
	
	/**
	 * 基于https协议post/get请求 post请求发送字符串
	 */
	public static String request(String urlStr,String method,String outputStr){
		BufferedReader bufferedReader=null;
		String result="";
		try {
			SSLContext context=SSLContext.getInstance("SSL");
			context.init(null,new TrustManager[]{new MyTrustManager()},new SecureRandom());
			//打开https连接
			URL url=new URL(urlStr);
			HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
			//设置https相关属性
			connection.setSSLSocketFactory(connection.getSSLSocketFactory());
			connection.setHostnameVerifier(new MyHostnameVerifier());
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			//设置请求方式
			connection.setRequestMethod(method);
			//建立实际的连接
			connection.connect();
			//POST方式响应输出
			if(outputStr!=null&&method.equals("POST")){
				OutputStream outputStream=connection.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
            //定义 BufferedReader输入流来读取URL的响应
            bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String temp=null;
            while((temp=bufferedReader.readLine())!=null){
            	result+=temp;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 *https或者http的post请求实现多文件上传
	 */
	public static String multiplyPartRequest(String urlStr,Map<String, File> fileParams){
		String result=null;
		try {
			if(urlStr.contains("https")){
				SSLContext context=SSLContext.getInstance("SSL");
				context.init(null,new TrustManager[]{new MyTrustManager()},new SecureRandom());
				//打开https连接
				URL url=new URL(urlStr);
				HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
				//设置https相关属性
				connection.setSSLSocketFactory(connection.getSSLSocketFactory());
				connection.setHostnameVerifier(new MyHostnameVerifier());
				//设置请求方式，POST方式响应输出
				connection.setRequestMethod("POST");
				result=sendData(connection,fileParams);
			}else {
				//打开http连接
				URL url=new URL(urlStr);
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				//设置请求方式，POST方式响应输出
				connection.setRequestMethod("POST");
				result=sendData(connection, fileParams);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 *发送报文数据
	 **/
	public static String sendData(URLConnection connection,Map<String, File> fileParams){
		String result="";
		BufferedReader bufferedReader=null;
		//设置通用的请求属性
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestProperty("accept", "*/*");
		//构造文件上传报文格式
		//定义边界：不能随便定义，会有问题
		String boundry= "---------" + System.currentTimeMillis();
		//设置Content-Type
		connection.setRequestProperty("Content-Type", "multipart/form-data;boundry="+boundry);
		try {
			//建立实际的连接
			connection.connect();
			OutputStream outputStream=connection.getOutputStream();
			for(Map.Entry<String, File> fileEntry:fileParams.entrySet()){
				String key=fileEntry.getKey();
				File file=fileEntry.getValue();
				String filename=file.getName();
				Long filelength=file.length();
				//设置头报文
				StringBuilder sb = new StringBuilder();
				sb.append("--"+boundry+"\r\n");
				sb.append("Content-Disposition:form-data;name=\""+key+"\";filename=\""+filename+"\";filelength="+filelength+"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				outputStream.write(sb.toString().getBytes("UTF-8"));
				//二进制输出文件
				outputStream.write(getFileBytes(file));
				outputStream.write(("\r\n--" + boundry +"\r\n").getBytes("utf-8"));
			}
			outputStream.flush();
			outputStream.close();
	        //定义 BufferedReader输入流来读取URL的响应
	        bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
	        String temp=null;
	        while((temp=bufferedReader.readLine())!=null){
	        	result+=temp;
	        }
		}catch (Exception e) {
				e.printStackTrace();
		}finally{
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 将file类型转化为byte[]
	 * @param file
	 * @return
	 */
	public static byte[] getFileBytes(File file){
		FileInputStream fileInputStream=null;
		try {
			byte[] b=new byte[(int)file.length()];
			fileInputStream=new FileInputStream(file);
			if(fileInputStream.read(b)!=-1){
				return b;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileInputStream!=null)
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
