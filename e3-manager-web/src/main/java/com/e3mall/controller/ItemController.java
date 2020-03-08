package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.service.ItemService;

/**
 *     商品信息管理
 * 
 * */

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable("itemId") long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	
	@RequestMapping(value = "/item/save",method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc, String itemParams) {
		E3Result result = itemService.addItem(item, desc, itemParams);
		return result;
	}
	
	@RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
	@ResponseBody
	public E3Result deleteItem(@RequestParam("ids") List<Long> ids) {	
		return itemService.deleteItem(ids);
	}
	
	@RequestMapping(value = "/rest/item/update",method = RequestMethod.POST)
	@ResponseBody
	public E3Result updateItem(TbItem item, String desc) {
		E3Result result = itemService.updateItem(item, desc);
		return result;
	}
	
	@RequestMapping("/item/{itemDescId}")
	@ResponseBody
	public TbItemDesc getItemDescById(@PathVariable("itemDescId") long itemDescId) {
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemDescId);
		return tbItemDesc;
	}
	
	

	
}
