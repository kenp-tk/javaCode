package com.e3mall.order.service;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.order.pojo.OrderInfo;

public interface OrderService {
	
	public E3Result createOrder(OrderInfo orderInfo);
	
}
