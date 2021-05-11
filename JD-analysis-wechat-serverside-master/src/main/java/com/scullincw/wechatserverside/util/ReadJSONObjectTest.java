package com.scullincw.wechatserverside.util;

import java.io.BufferedReader;
import java.io.FileReader;

import com.alibaba.fastjson.JSONObject;

public class ReadJSONObjectTest {

	public static void main(String[] args) {
		JSONObject obj1 = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/python-spider/jd_info.txt"));
			String line = null;
			if((line = br.readLine()) != null) {
				obj1.put("itemName", line);
			}
			br.close();
			System.out.println(obj1.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject obj2 = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/python-spider/average_sentiment.txt"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			obj2 = JSONObject.parseObject(sb.toString());
			System.out.println(obj2.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		obj1.putAll(obj2);
		System.out.println("\n" + obj1.toString());
		
	}

}
