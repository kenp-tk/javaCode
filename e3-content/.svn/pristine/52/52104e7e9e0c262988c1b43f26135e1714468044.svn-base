package com.e3mall.content.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.jedis.JedisClient;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;

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

}
