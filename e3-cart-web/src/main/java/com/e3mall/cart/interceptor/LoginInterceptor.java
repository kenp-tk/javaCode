package com.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.conmon.utils.CookieUtils;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;

/**
 * 	用户登录处理拦截器
 * 
 * */


public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private TokenService tokenService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 前处理 执行Handler之前执行这个方法  返回 true 放行 返回 false拦截
		
		//1.从cookie中取出token
		String token = CookieUtils.getCookieValue(request, "token");
		
		//2.如果没有 token  用户未登录  直接放行
		if(StringUtils.isBlank(token)) {
			return true;
		}
		//3.取到token  需要调用sso系统的 服务   根据token取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		//4.没有取到用户信息  登录失效  直接放行
		if(e3Result.getStatus() != 200) {
			return true;	
		}
		//5.取到用户信息 登录状态
		TbUser tbUser = (TbUser) e3Result.getData();
		//6.把用户信息放到request中  只需要在Controller中判断是否包含User信息  不管用户有没有登录都需要  放行
		request.setAttribute("user", tbUser);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后  返回 ModelAndView  之前生效
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 完成处理 返回 ModelAndView 之后  
		// 可以在此处理异常
		
	}

}
