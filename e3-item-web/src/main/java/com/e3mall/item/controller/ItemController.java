package com.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.item.pojo.Item;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.service.ItemService;

/**
 * 		商品静态详情页面展示控制Controller
 * 
 * */

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId, Model model) {
		//获取商品的基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		//获取商品描述信息
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		//将信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		//返回逻辑视图
		return "item";
	}
	
}
