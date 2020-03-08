package com.e3mall.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PageHelperTset {
	
	private ApplicationContext applicationContext;

	@Test
	public void testPageHelper() throws Exception{
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//从容器中获得Mapper代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行sql语句之前设置分页信息  调用PageHelper 的  startPage方法
		PageHelper.startPage(1, 10);
		//执行查询语句
		TbItemExample example = new TbItemExample();
		List<TbItem> list =  itemMapper.selectByExample(example);
		//提取分页信息  从 PageInfo 中  可以获得 总记录数 总页数 当前页码  等信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(list.size());
		
		
	}
	
}
