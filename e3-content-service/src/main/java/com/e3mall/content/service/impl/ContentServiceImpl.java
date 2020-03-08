package com.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.jedis.JedisClient;
import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST; 
	
	@Override
	public E3Result addContent(TbContent content) {
		//将 TbContent对象 补充完整
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//将对象添加到 数据库
		tbContentMapper.insert(content);
		//缓存同步 ,将数据库中的对应内容删除
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}


	@Override
	public List<TbContent> getContent(Long cid) {
		//查询缓存中是否有需要的内容
		try {
			//缓存中有就直接返回相应
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//没有就查询数据
		//设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		//将结果添加到缓存中
		try {
			jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}


	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		
		//设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		//执行查询语句
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//设置返回结果的值
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(list);
		
		//获取分页结果
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		//获取总记录数
		long total = pageInfo.getTotal();
		easyUIDataGridResult.setTotal(total);
		
		return easyUIDataGridResult;
	
	}


	@Override
	public E3Result getContentById(Long cid) {
		// 用来查询内容表中的单个数据信息 
		TbContent content = tbContentMapper.selectByPrimaryKey(cid);
		return E3Result.ok(content);
	}


	@Override
	public E3Result updateContent(TbContent content) {
		// 填充 TbContent 对象的属性
		content.setUpdated(new Date());
		// 执行更新操作
		tbContentMapper.updateByPrimaryKeyWithBLOBs(content);
		// 返回更新结果
		return E3Result.ok();
	}


	@Override
	public E3Result deleteContent(String ids) {
		// 根据 id  进行删除
		String[] idList = ids.split(",");
		for (String id : idList) {
			//删除内容
			tbContentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		//返回结果
		return E3Result.ok();
	}

}
