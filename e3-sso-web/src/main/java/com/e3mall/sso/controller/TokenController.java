package com.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * 		根据Token查询用户信息Controller
 * 
 * */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.sso.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	/*@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE"application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);
		//响应结果之前, 判断是否为jsonp请求
		 if(StringUtils.isNotBlank(callback)) {
			 //把结果封装成一个js语句响应
			 return callback + "(" + JsonUtils.objectToJson(e3Result) + ");";
		 }
		
		return JsonUtils.objectToJson(e3Result);
	}*/
	
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);
		//响应结果之前, 判断是否为jsonp请求
		 if(StringUtils.isNotBlank(callback)) {
			 //把结果封装成一个js语句响应
			 MappingJacksonValue jacksonValue = new MappingJacksonValue(e3Result);
			 jacksonValue.setJsonpFunction(callback);
			 return jacksonValue;
		 }
		
		return e3Result;
	}
	
	
	
}
