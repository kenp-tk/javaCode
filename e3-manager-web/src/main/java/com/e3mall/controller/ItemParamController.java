package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.service.ItemParamService;

@Controller
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamList(Integer page,Integer rows) {
		//查询得到  商品规格参数的信息
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	
	@RequestMapping("/item/param/query/itemcatid/{cid}")
	@ResponseBody
	public E3Result getItemParamByCatId(@PathVariable Long cid) {
//		System.out.println("Controller" + cid);
		//根据  id查询对应的商品类目
		E3Result result = itemParamService.getItemParamByCatId(cid);
		return result;
	}
	
	@RequestMapping(value="/item/param/save/{cid}", method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItemParam(String paramData,@PathVariable Long cid) {
		// 添加一个商品类目的 规格参数模板
		E3Result result = itemParamService.addItemParam(paramData, cid);
		return result;
	}
}
