package com.scullincw.wechatserverside.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//正则表达式测试
public class RegTest {

	public static void main(String[] args) {
		String URL = "https://item.paipai.jd.com/100000205012.html#none";
		//验证URL
		String regex = "(?<httpitem>https:(\\/)(\\/)item(\\.))(\\D*)(?<jdcomhtml>jd(\\.)com(\\/)(\\d+)\\.html)(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(URL);
		matcher.find();
		System.out.println(matcher.group("httpitem") + matcher.group("jdcomhtml"));
	}

}
