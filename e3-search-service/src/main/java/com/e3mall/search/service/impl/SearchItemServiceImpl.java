package com.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.pojo.SearchItem;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.SearchItemService;

/**
 *   索引库服务
 * */


@Service
public class SearchItemServiceImpl implements SearchItemService{
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public E3Result addAllItems() {
		try {
			//查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();
			//遍历商品列表
			for(SearchItem item : itemList) {
				//创建一个文档对象
				SolrInputDocument document = new SolrInputDocument();
				//将文档商品对象添加到域中
				document.addField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				//将文档对象写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "导入商品数据失败,发生异常");
		}
		
	}

}
