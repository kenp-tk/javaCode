package com.e3mall.order.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.e3mall.cart.service.CartService;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;

/**
 * 订单管理controller
 * @author Administator
 *
 */

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		//获取用户信息 中的 id
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user);
		// 根据用户id获取收货地址列表
		// 使用静态数据
		// 获取支付方式列表
		// 使用静态数据
		
		
		// 根据用户id  获取用户购物车的列表
		List<TbItem> cartList = cartService.getCartList(user.getId());
		// 将查询到的用户信息传递给  jsp
		request.setAttribute("cartList", cartList);
		// 返回页面
		return "order-cart";
	}
	
	@RequestMapping(value = "/order/create",method = RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request) {
		//获取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//把 用户信息 添加到 OrderInfo 对象中
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务生成订单
		E3Result e3Result = orderService.createOrder(orderInfo);
		//如果订单生成 成功 需要删除  购物车信息  
		if(e3Result.getStatus() == 200) {
			//清空购物车
			cartService.ClearCartItem(user.getId());
		}
		//把单号传递给页面
		request.setAttribute("orderId", e3Result.getData());
		//传递 应付金额 给页面
		request.setAttribute("payment", orderInfo.getPayment());
		//返回页面
		return "success";
	}
	
	
	
	
}
