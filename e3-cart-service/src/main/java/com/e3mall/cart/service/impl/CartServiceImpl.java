package com.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.cart.service.CartService;
import com.e3mall.conmon.jedis.JedisClient;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;

/**
 *   	购物车处理服务
 * @author Administator
 *
 */


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper; 
	
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	
	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		// 向redis中添加购物车的商品信息
		// 数据类型是hash  key:用户id  field:商品id value:商品信息
		
		//判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
		//如果存在数量相加
		if(hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
			//把json转换成TbItem对象
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			//更新商品数量
			tbItem.setNum(tbItem.getNum() + num);
			//将更新的数据添加到  redis
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		//若是不存在根据商品id获取商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		//设置购物车数量
		item.setNum(num);
		//取得一张图片
		String image = item.getImage();
		//判断image是否为空
		if(StringUtils.isNotBlank(image)) {
			item.setImage(image.split(",")[0]);
		}
		//将商品信息添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		//返回成功
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		// 遍历商品列表
		// 把列表添加到购物车中
		// 判断购物车中是不是有这个商品
		// 如果有该商品 数量相加
		// 如果没有添加一个新的商品
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		// 返回成功
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// 根据用户id查询购物车列表的信息
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList<TbItem>();
		for (String string : jsonList) {
			//创建一个Tbitem对象
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			//添加到 itemList  列表中
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		// 从redis中获取商品信息
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
		// 更新商品数量
		TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
		item.setNum(num);
		// 写入redis
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		//返回成功
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		// 删除对应商品
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
		return E3Result.ok();
	}

	@Override
	public E3Result ClearCartItem(long userId) {
		// 删除购物车信息
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return E3Result.ok();
	}

}
