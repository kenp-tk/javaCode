package com.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.pojo.EasyUITreeNode;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import com.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 *   网页内容管理
 * 
 * */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper; 
	
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		//利用传过来的parentId  查询
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询语句
		List<TbContentCategory> catlist = tbContentCategoryMapper.selectByExample(example);
		
		//转换为  EasyUITreeNode
		List<EasyUITreeNode> nodelist = new ArrayList<EasyUITreeNode>();
		
		for (TbContentCategory tbContentCategory : catlist) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent() ? "closed":"open");
			node.setText(tbContentCategory.getName());
			nodelist.add(node);
		}
		
		return nodelist;
	}

	@Override
	public E3Result addEasyUITreeNode(Long parentId, String name) {
		//创建 tb_content_category表 对应的pojo对象 
		TbContentCategory tbContentCategory = new TbContentCategory(); 
		//设置 pojo 对象
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		//1(正常),2(删除)
		tbContentCategory.setStatus(1);
		//默认排序为  1
		tbContentCategory.setSortOrder(1);
		//添加 is_parentId 为叶子节点
		tbContentCategory.setIsParent(false);
		//设置时间
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		//插入 到tb_content_category表中数据
		tbContentCategoryMapper.insert(tbContentCategory);
		
		//判断  is_parentId  是不是true若不为true改为true
		//根据parentId查询父节点
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			//更新数据后  添加到数据库
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回成功信息
		
		return E3Result.ok(tbContentCategory);
	}
	
}
