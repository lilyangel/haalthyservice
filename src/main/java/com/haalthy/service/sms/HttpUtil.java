package com.haalthy.service.sms;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	
	/**
	 * http post请求
	 * @param url						地址
	 * @param postContent				post内容格式为param1=value&param2=value2&param3=value3
	 * @return
	 * @throws IOException
	 */
	public static String httpPostRequest(URL url, String postContent) throws Exception{
		OutputStream outputstream = null;
		BufferedReader in = null;
		try
		{
			URLConnection httpurlconnection = url.openConnection();
			httpurlconnection.setConnectTimeout(10 * 1000);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			OutputStreamWriter out = new OutputStreamWriter(httpurlconnection
					.getOutputStream(), "UTF-8");
			out.write(postContent);
			out.flush();
			
			StringBuffer result = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(httpurlconnection
					.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}
			return result.toString();
		}
		catch(Exception ex){
			logger.error("post请求异常：" + ex.getMessage());
			throw new Exception("post请求异常：" + ex.getMessage());
		}
		finally
		{
			if (outputstream != null)
			{
				try
				{
					outputstream.close();
				}
				catch (IOException e)
				{
					outputstream = null;
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					in = null;
				}
			}
		}	
	}	
}
