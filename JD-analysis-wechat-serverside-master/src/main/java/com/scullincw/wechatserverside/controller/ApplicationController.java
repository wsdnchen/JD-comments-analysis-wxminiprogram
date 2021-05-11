package com.scullincw.wechatserverside.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scullincw.wechatserverside.common.GlobalResult;
import com.scullincw.wechatserverside.mapper.ApplicationMapper;
import com.scullincw.wechatserverside.pojo.Application;

@RequestMapping("/wechat")
@Controller
public class ApplicationController {
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	/*
	 * 在“审批”页面查看审批
	 */
	@PostMapping("/getApplication")
	@ResponseBody
	public GlobalResult getApplication(@RequestParam(value = "openid", required = true) String openid,
									   @RequestParam(value = "skey", required = true)String skey
									   ) {
		Page<Application> page = new Page<>(1, 10);
		IPage<Application> lstApplication = applicationMapper.selectPage(page, null);
		List<Application> record = lstApplication.getRecords();
		Collections.reverse(record);
		return new GlobalResult(200, "查询完成", JSON.toJSON(record));
	}
	
	/*
	 * 添加一个审批
	 */
	@PostMapping("/addApplication")
	@ResponseBody
	public GlobalResult addApplication(@RequestParam(value = "openid", required = true) String openid,
			   						   @RequestParam(value = "skey", required = true)String skey,
			   						   @RequestParam(value = "appType", required = true)int appType,
			   						   @RequestParam(value = "applicant", required = true)String applicant,
			   						   @RequestParam(value = "appContent", required = true)String appContent,
			   						   @RequestParam(value = "additionalContent", required = false)String additionalContent
	) {
		Application app = new Application();
		app.setAppType(appType);
		app.setApplicant(applicant);
		app.setAppContent(appContent);
		app.setAdditionalContent(additionalContent);
		app.setApplicantOpenid(openid);
		int insertNum = applicationMapper.insert(app);
		return new GlobalResult(200,"增加了" + insertNum + "条公告", null);
	}
}
