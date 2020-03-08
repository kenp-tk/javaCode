package com.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;

/**
 * 首页展示  
 * 
 * */

@Controller
public class IndexController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private long CONTENT_LUNBO_ID; 
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value = "/index")
	public String showIndex(Model model) {
		//查询内容列表
		List<TbContent> ad1list = contentService.getContent(CONTENT_LUNBO_ID);
		model.addAttribute("ad1List", ad1list);
		return "index";
	}
	
	
	
}
