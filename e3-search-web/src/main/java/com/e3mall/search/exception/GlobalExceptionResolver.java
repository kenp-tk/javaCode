package com.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 		全局异常处理器
 * 
 * */


public class GlobalExceptionResolver implements HandlerExceptionResolver{

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//打印控制台
		//ex.printStackTrace();
		//编写错误日志
		logger.debug("打印日志信息.........");
		logger.info("系统发生异常........");
		logger.error("系统发生异常", ex);
		//发送邮件、短信
		//使用jmail工具包。发送短信使用 第三方的Webservice
		//显示错误页面
		ModelAndView view = new ModelAndView();
		view.setViewName("error/exception");
		return view;
	}

}
