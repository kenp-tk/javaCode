package com.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.e3mall.conmon.pojo.SearchItem;
import com.e3mall.search.mapper.ItemMapper;

/**
 * 		监听商品添加消息  ,接收到消息后,将对应的商品信息同步到索引库.
 * 
 * */


public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer; 
	
	@Override
	public void onMessage(Message message) {
		try {
			//从传输的消息中取出商品的id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(300);
			//根据商品的id查询商品信息
			SearchItem item = itemMapper.getItemById(itemId);
			//创建一个文档对象
			SolrInputDocument document =  new SolrInputDocument();
			//将文档商品对象添加到域item中
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			//将文档对象添加到索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
