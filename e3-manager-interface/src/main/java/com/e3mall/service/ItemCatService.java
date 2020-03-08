package com.e3mall.service;

import java.util.List;

import com.e3mall.conmon.pojo.EasyUITreeNode;

public interface ItemCatService {

	public List<EasyUITreeNode> getItemCatList(long parentId);
}
