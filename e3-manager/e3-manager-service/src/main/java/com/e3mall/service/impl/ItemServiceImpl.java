package com.e3mall.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.pojo.TbItemExample.Criteria;
import com.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		//  tbItemMapper.selectByPrimaryKey(itemId);  根据id主键直接查询
		
		//  设置条件查询信息
		
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		
		//设置根据值为itemId的条件查询
		criteria.andIdEqualTo(itemId);
		 
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
}
