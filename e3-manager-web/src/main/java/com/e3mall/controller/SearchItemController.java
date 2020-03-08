package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.search.service.SearchItemService;

/**
 * 	导入商品数据到索引库
 * 
 * */

@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result addItemList() {
		E3Result result = searchItemService.addAllItems();
		return result;
	}
	
}
