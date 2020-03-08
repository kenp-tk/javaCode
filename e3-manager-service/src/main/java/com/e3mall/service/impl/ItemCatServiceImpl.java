package com.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.pojo.EasyUITreeNode;
import com.e3mall.mapper.TbItemCatMapper;
import com.e3mall.pojo.TbItemCat;
import com.e3mall.pojo.TbItemCatExample;
import com.e3mall.pojo.TbItemCatExample.Criteria;
import com.e3mall.service.ItemCatService;
/**
 *   商品的分类管理 
 * 
 * */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//设置条件进行查询
		TbItemCatExample example = new TbItemCatExample(); 
		Criteria criteria = example.createCriteria();
		//条件为值是 parentId
		criteria.andParentIdEqualTo(parentId);
		//执行查询条件
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//创建一个空List<EasyUITreeNode> 用来存值
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		//转化为 EasyUITreeNode 格式
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			//设置属性
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到返回的结果列表
			resultList.add(node);
		}
		
		return resultList;
	}

}
