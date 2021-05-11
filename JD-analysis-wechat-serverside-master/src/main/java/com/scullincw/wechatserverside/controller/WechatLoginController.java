package com.scullincw.wechatserverside.controller;

import com.scullincw.wechatserverside.common.*;
import com.scullincw.wechatserverside.mapper.UserMapper;
import com.scullincw.wechatserverside.pojo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.UUID;

@RequestMapping("/wechat")
@Controller
public class WechatLoginController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信用户登录详情
     */
    @PostMapping("/weLogin")
    @ResponseBody
    public GlobalResult user_login(@RequestParam(value = "code", required = true) String code
                                   ) {
        /* @description: 用户非敏感信息：rawData, 小程序端通过wx.getUserInfo获取
         * 签名：signature
         * @author: scullincw
         * @deprecated: 2020.3.7，用户非敏感数据已使用open-type获取，不再支持wx.getUserInfo
         * 详见 https://developers.weixin.qq.com/community/develop/doc/0000a26e1aca6012e896a517556c01
         */
        //JSONObject rawDataJson = JSON.parseObject(rawData);
        
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appid + appsecret + code , 换取 openid, session_key, unionid .
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
//        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
//        if (!signature.equals(signature2)) {
//            return GlobalResult.build(500, "签名校验失败", null);
//        }
        
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
        User user = this.userMapper.selectById(openid);		//openid是用户数据表的主键，即可以唯一确定用户的标识
        
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        //String skey = UUID.randomUUID().toString();
        
        if (user == null) {
            // 用户信息入库
//            String nickName = rawDataJson.getString("nickName");
//            String avatarUrl = rawDataJson.getString("avatarUrl");
//            String gender = rawDataJson.getString("gender");
//            String city = rawDataJson.getString("city");
//            String country = rawDataJson.getString("country");
//            String province = rawDataJson.getString("province");

            user = new User();
            user.setOpenId(openid);
//            user.setSkey(skey);
            user.setCreateTime(new Date());
            user.setLastVisitTime(new Date());
            user.setSessionKey(sessionKey);
//            user.setCity(city);
//            user.setProvince(province);
//            user.setCountry(country);
//            user.setAvatarUrl(avatarUrl);
//            user.setGender(Integer.parseInt(gender));
//            user.setNickName(nickName);

            this.userMapper.insert(user);
        } else {
            // 已存在，更新用户登录时间
            user.setLastVisitTime(new Date());
            // 重新设置sessionKey
            user.setSessionKey(sessionKey);
            this.userMapper.updateById(user);
        }
        
        //6. 把新的sessionKey返回给小程序
        JSONObject respondData = new JSONObject();
        respondData.put("openid", user.getOpenId());
        respondData.put("sessionKey", sessionKey);
        GlobalResult result = new GlobalResult(200, "获取openid, sessionKey成功", respondData);
        return result;
    }
}
