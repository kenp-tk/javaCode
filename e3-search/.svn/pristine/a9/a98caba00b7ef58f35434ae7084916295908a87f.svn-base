package com.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	
	@Test
	public void addSolrCloud() throws Exception{
		//创建一个集群链接    使用CloudSolrServer连接
		CloudSolrServer cloudServer = new CloudSolrServer("192.168.25.132:2181,192.168.25.132:2182,192.168.25.132:2183");
		//设置DefaultCollection属性
		cloudServer.setDefaultCollection("collection2");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "solrcloud01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文件添加到索引库
		cloudServer.add(document);
		//提交
		cloudServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception {
		//创建一个CloudSolrServer对象
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.132:2181,192.168.25.132:2182,192.168.25.132:2183");
		//设置默认的Collection
		cloudSolrServer.setDefaultCollection("collection2");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		//执行查询
		QueryResponse queryResponse = cloudSolrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("总记录数：" + solrDocumentList.getNumFound());
		//打印
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("title"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	} 
	
}
