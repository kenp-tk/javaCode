package com.e3mall.cart.service;

import java.util.List;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.pojo.TbItem;

public interface CartService {
	public E3Result addCart(long userId, long itemId, int num);
	public E3Result mergeCart(long userId, List<TbItem> itemList);
	public List<TbItem> getCartList(long userId);
	public E3Result updateCartNum(long userId, long itemId, int num);
	public E3Result deleteCartItem(long userId, long itemId);
	public E3Result ClearCartItem(long userId);
}
