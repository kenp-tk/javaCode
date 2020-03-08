package com.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.cart.service.CartService;
import com.e3mall.conmon.utils.CookieUtils;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;

/**
 * 用户登录拦截器
 * @author Administator
 *
 */

public class LoginInterceptor implements HandlerInterceptor {

	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService  cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中 取出 token
		String token= CookieUtils.getCookieValue(request, "token");
		//判断  token  是否存在
		if(StringUtils.isBlank(token)) {
			//如果token 不存在  用户是未登录状态  跳转到  sso登录页面.登陆成功后跳转到请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			//拦截  
			return false;
		}
		//如果token 存在 需要调用  sso系统服务  根据token获取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		//如果没有取到  表示用户信息已经过期  需要重新登录
		if(e3Result.getStatus() != 200) {
			//如果token 不存在  用户是未登录状态  跳转到  sso登录页面.登陆成功后跳转到请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			//拦截  
			return false;
		}
		//如果取到用户信息  登录状态  需要把用户信息写入request
		TbUser user = (TbUser) e3Result.getData();
		//将user 对象存入request
		request.setAttribute("user", user);
		//判断  cookie中是否有  购物车数据  有就要合并到服务端
		String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
		//判断传过来的json数据是不是为空  不为空合并购物车
		if(StringUtils.isNoneBlank(jsonCartList)) {
			//合并购物车
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		//放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
