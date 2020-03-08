package com.e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.jedis.JedisClient;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.mapper.TbOrderItemMapper;
import com.e3mall.mapper.TbOrderMapper;
import com.e3mall.mapper.TbOrderShippingMapper;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbOrder;
import com.e3mall.pojo.TbOrderExample;
import com.e3mall.pojo.TbOrderExample.Criteria;
import com.e3mall.pojo.TbOrderItem;
import com.e3mall.pojo.TbOrderItemExample;
import com.e3mall.pojo.TbOrderShipping;

/**
 * 订单处理服务
 * @author Administator
 *
 */

@Service
public class OrderServiceImpl implements OrderService{

	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETAIL_ID_GEN_KEY;
	
	
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		// 生成订单号  使用redis的incr生成
		
		//判断 默认订单号是否有初始值  没有就添加初始值
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		// 补全 orderInfo 的属性
		orderInfo.setOrderId(orderId);
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		// 插入订单表
		orderMapper.insert(orderInfo);
		// 向订单明细表中插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//生成明细Id
			String odId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
			//补全 订单明细表 pojo 属性
			tbOrderItem.setId(odId);
			tbOrderItem.setOrderId(orderId);
			//向明细表中插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		//向物流表中插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		//补全  物流表 对应的pojo属性
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		//向物流表中插入  补全数据
		orderShippingMapper.insert(orderShipping);
		//返回生成的订单号
		return E3Result.ok(orderId);
	}



	@Override
	public List<TbOrder> getListByUserId(Long uid) {
		// 设置条件查询  
		TbOrderExample example = new TbOrderExample();
		Criteria criteria = example.createCriteria();
		// 条件是根据  UserId查询
		criteria.andUserIdEqualTo(uid);
		
		List<TbOrder> list = orderMapper.selectByExample(example);
		return list;
	}



	@Override
	public List<TbOrderItem> getListByOrderId(String oid) {
		// 设置条件查询  
		TbOrderItemExample example = new TbOrderItemExample();
		com.e3mall.pojo.TbOrderItemExample.Criteria criteria = example.createCriteria(); 
		//设置条件根据  OrderId查询
		criteria.andOrderIdEqualTo(oid);
		
		//执行查询语句
		List<TbOrderItem> list = orderItemMapper.selectByExample(example);

		return list;
	}

}
