package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;

/**
 * 
 * 网页内容控制处理
 * 
 * */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content) {
		//将传输过来的值添加到服务中
		return contentService.addContent(content);
	}
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
		//获取查询到的内容信息
		EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	@RequestMapping("/content/getContent")
	@ResponseBody
	public E3Result getContentById(Long id) {
		//获取查询到的单个内容表信息
		E3Result result = contentService.getContentById(id);
		return result; 
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateContent(TbContent content){
		E3Result result = contentService.updateContent(content);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(String ids){
		E3Result result = contentService.deleteContent(ids);
		return result;
	}

	
	
}
