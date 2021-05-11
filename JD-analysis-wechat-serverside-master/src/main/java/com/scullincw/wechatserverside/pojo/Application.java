package com.scullincw.wechatserverside.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/*
 * 申请实体类
 */
@TableName("application")
@Data
public class Application {
	/*
	 * 申请ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private String id;
	/*
	 * 创建时间
	 */
	@TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
	/*
	 * 申请类型
	 */
	@TableField("app_type")
	private int appType;
	/*
	 * 申请人姓名
	 */
	@TableField("applicant")
	private String applicant;
	/*
	 * 申请理由
	 */
	@TableField("app_content")
	private String appContent;
	/*
	 * 附加事项，如果是报销则是金额，如果是会议室申请则是block和room
	 */
	@TableField("additional_content")
	private String additionalContent;
	/*
	 * 申请人微信账号的openid
	 */
	@TableField("applicant_openid")
	private String applicantOpenid;
	/*
	 * 审核人微信账号的openid
	 */
	@TableField("reviewer_openid")
	private String reviewerOpenid;
	/*
	 * 审核时间
	 */
	@TableField("review_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reviewTime;
	
}
