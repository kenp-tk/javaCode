package com.e3mall.content.service;

import java.util.List;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.pojo.TbContent;

public interface ContentService {
	
	//向内容表中添加信息
	public E3Result addContent(TbContent content);
	public List<TbContent> getContent(Long cid);
	//查询内容表中单个数据信息
	public E3Result getContentById(Long cid);
	//修改内容表中的信息
	public E3Result updateContent(TbContent content);
	//删除内容表数据
	public E3Result deleteContent(String ids);
	//查询显示 内容表的信息
	public EasyUIDataGridResult getContentList(Long categoryId,int page, int rows);
	
}
