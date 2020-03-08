package com.e3mall.content.service;

import java.util.List;

import com.e3mall.conmon.pojo.EasyUITreeNode;
import com.e3mall.conmon.utils.E3Result;

public interface ContentCategoryService {
	
	public List<EasyUITreeNode> getContentCatList(Long parentId);
	public E3Result  addEasyUITreeNode(Long parentId, String name);
}
