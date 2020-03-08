package com.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.cart.service.CartService;
import com.e3mall.conmon.utils.CookieUtils;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.service.ItemService;

/**
 * 		购物车处理Controller
 * 
 * */


@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartService cartService;
	
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("user");
		//如果用户是登录状态 
			if(tbUser != null) {
				// 保存的服务端( 购物车数据写入redis中)
				cartService.addCart(tbUser.getId(), itemId, num);
			}
		//用户未登录  执行以下代码
		//获取cookie中购物车的列表
		List<TbItem> cartlist = getCartListFromCookie(request);
		//判断商品在商品列表中是否存在
		boolean flag = false;
		for (TbItem tbItem : cartlist) {
			//查找商品存在数量相加
			if(tbItem.getId() == itemId.longValue()) {
				flag = true;
				//查找到商品数量相加
				tbItem.setNum(tbItem.getNum()+num); 
				//跳出循环
				break;
			}
		}
		
		//若未查找到商品  
		if(!flag) {
			//根据商品id查询商品信息  获得TbItem对象
			TbItem tbItem = itemService.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//设置存储一张图片
			String image = tbItem.getImage();
			if(StringUtils.isNotBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			//将商品添加到商品列表
			cartlist.add(tbItem);
		}
		//将商品写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartlist), COOKIE_CART_EXPIRE, true);
		//返回成功页面
		return "cartSuccess";
	}
	
	
	/**
	 * 		从cookie获取购物车中的商品列表
	 * 
	 * */
	public List<TbItem> getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//商品列表json为空时  传一个空list集合
		if(StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		//不为空时  将json 转换成商品列表
		  List<TbItem> jsonList = JsonUtils.jsonToList(json, TbItem.class);
		  return jsonList;
	}
	
	/**
	 * 		展示购物车列表
	 * 
	 * */
	
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {
		//获取cookie中购物车的列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("user");
		//如果用户是登录状态 
		if(tbUser != null) {
			//如果有商品  把cookie中的商品和服务端的购物车商品合并
			cartService.mergeCart(tbUser.getId(), cartList);
			//把cookie中的购物车商品删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端获取购物车列表
			cartList = cartService.getCartList(tbUser.getId());
		}
		
		//未登录状态
		
		//将购物车列表传递给页面
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "cart";
	}
	
	/**
	 * 	更新购物车商品数量
	 * */
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否是登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		//登录状态
		if(user != null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		
		
		//从cookie中取出购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历购物车列表中找到对应的商品
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId) {
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//将商品写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回成功数据
		return E3Result.ok();
	}
 
	
	/**
	 * 	删除购物车商品
	 * 	
	 * */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否是登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		//登录状态
		if(user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		
		
		//从cookie中取出购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历购物车列表中找到对应的商品  进行删除
		
		/*只能删除一个商品*/
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId) {
				//删除商品
				cartList.remove(tbItem);
				//跳出循环  不跳出会抛出异常
				break;
			}
		}
		//将商品写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回成功数据
		return "redirect:/cart/cart.html";
	}
	
	
	
	
	
	
	
}
