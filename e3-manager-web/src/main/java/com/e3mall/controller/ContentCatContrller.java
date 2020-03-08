package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.pojo.EasyUITreeNode;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.content.service.ContentCategoryService;

/**
 *  网页内容控制
 * 
 * */

@Controller
public class ContentCatContrller {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(
			@RequestParam(name="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	/**
	 *   添加内容分类文件
	 * 
	 * */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addContentCategory(Long parentId, String name) {
		return contentCategoryService.addEasyUITreeNode(parentId, name);
	}
	
}
