package com.e3mall.order.service;

import java.util.List;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.pojo.TbOrder;
import com.e3mall.pojo.TbOrderItem;

public interface OrderService {
	
	public E3Result createOrder(OrderInfo orderInfo);
	public List<TbOrder> getListByUserId(Long uid);
	public List<TbOrderItem> getListByOrderId(String oid);
}
