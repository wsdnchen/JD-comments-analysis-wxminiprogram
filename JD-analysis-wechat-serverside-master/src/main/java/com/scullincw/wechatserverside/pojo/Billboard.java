package com.scullincw.wechatserverside.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


/*
 * 公告实体类
 */
@TableName("billboard")
@Data
public class Billboard {
	/*
	 * 公告ID
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
	 * 公告标题
	 */
	@TableField(value = "billboard_title")
	private String billboardTitle;
	/*
	 * 公告内容
	 */
	@TableField(value = "billboard_content")
	private String billboardContent;
	
}
