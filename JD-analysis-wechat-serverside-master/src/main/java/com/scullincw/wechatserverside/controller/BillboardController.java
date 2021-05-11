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
import com.scullincw.wechatserverside.mapper.BillboardMapper;
import com.scullincw.wechatserverside.pojo.Billboard;

@RequestMapping("/wechat")
@Controller
public class BillboardController {
	
	@Autowired
    private BillboardMapper billboardMapper;
	
	/**
     * 在“公告”界面获取公告
     */
    @PostMapping("/getBillboard")
    @ResponseBody
    public GlobalResult getBillboardList(@RequestParam(value = "openid", required = true) String openid,
    									 @RequestParam(value = "skey", required = true)String skey,
    									 @RequestParam(value = "currentPageNum", required = true)int currentPageNum,
    									 @RequestParam(value = "rowsPerPage", required = true)int rowsPerPage
    ) {
    	/*
    	 * TODO: 还没验证openid和skey
    	 */
    	
    	Page<Billboard> page = new Page<>(currentPageNum, rowsPerPage);
    	IPage<Billboard> lstBillboard = billboardMapper.selectPage(page, null);
    	List<Billboard> record = lstBillboard.getRecords();
    	Collections.reverse(record);
    	
    	return new GlobalResult(200,"查询完成", JSON.toJSON(record));
    }
    
    /*
     * 根据id删除公告
     */
    @PostMapping("/deleteBillboard")
    @ResponseBody
    public GlobalResult deleteBillboard(@RequestParam(value = "openid", required = true) String openid,
    									@RequestParam(value = "skey", required = true)String skey,
    									@RequestParam(value = "id", required = true)String id
    									) {
    	int deleteRow = billboardMapper.deleteById(id);
    	if(deleteRow == 1) {
    		Page<Billboard> page = new Page<>(1, 5);
        	IPage<Billboard> lstBillboard = billboardMapper.selectPage(page, null);
        	List<Billboard> record = lstBillboard.getRecords();
    		return new GlobalResult(200,"删除公告成功",record);
    	} else {
    		String errMsg = "删除行数：" + deleteRow;
    		return new GlobalResult(500,"删除公告失败", errMsg);
    	}
    	
    }
    
    /*
     * 添加公告
     */
    @PostMapping("/addBillboard")
    @ResponseBody
    public GlobalResult addBillboard(@RequestParam(value = "openid", required = true) String openid,
									 @RequestParam(value = "skey", required = true)String skey,
									 @RequestParam(value = "title", required = true)String title,
									 @RequestParam(value = "content", required = true)String content
    								) {
		Billboard billboard = new Billboard();
		billboard.setBillboardTitle(title);
		billboard.setBillboardContent(content);
		int insertNum = billboardMapper.insert(billboard);
    	return new GlobalResult(200,"增加了" + insertNum + "条公告", null);
    }
    
    
}
