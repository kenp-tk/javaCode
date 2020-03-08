package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.pojo.EasyUITreeNode;
import com.e3mall.service.ItemCatService;

/**
 *   商品分类管理
 * 
 * */

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCat(@RequestParam(name = "id", defaultValue = "0") long parentId){
		//通过调用服务  获得分类列表
		return itemCatService.getItemCatList(parentId);
	}
	
}
