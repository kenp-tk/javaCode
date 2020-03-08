package com.e3mall.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrj {
	
	@Test
	public void addDocument() throws Exception{
		//创建一个SolrServer对象 ,建立连接.  参数为solr的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建  SolrInputDocument 文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域  文档中必须包含一个id域,所有的域必须在schema.xml中定义(添加一个文档就相当于更新文档,添加的文档id一致时会覆盖信息)
		document.addField("id", "doc1");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//删除文档
		solrServer.deleteById("doc1");//根据id删除数据
		//solrServer.deleteByQuery("item_title:测试商品01");//根据查询语法删除数据
		//提交
		solrServer.commit();
	}
	
	@Test
	public void queryIndex() throws Exception {
		//创建一个SolrServer对象 ,建立连接.  参数为solr的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建  SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		//query.setQuery("*:*");  //查询所有的数据  *:*
		query.set("q", "*:*");
		//执行查询  QueryResponse
		QueryResponse queryResponse = solrServer.query(query);
		//获取文档列表(SolrDocumentList)  , 取得查询结果的总记录数
		SolrDocumentList results = queryResponse.getResults();
		System.out.println("总记录数:"+results.getNumFound());
		//遍历列表  取出域 列表内容
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
		
	}
	
	@Test
	public void queryComplex() throws Exception {
		//创建一个SolrServer对象 ,建立连接.  参数为solr的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建  SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("手机");//设置你要搜索的名称
		query.setStart(0);//分页 从零 开始内容
		query.setRows(20);//分页 到 20结束内容
		query.set("df","item_title");//默认搜索域  (要搜索的内容对应的域)
		query.setHighlight(true);// 设置高亮显示开关
		query.addHighlightField("item_title");//设置高亮显示内容
		query.setHighlightSimplePre("<em>");//设置高亮显示标签的开始部分
		query.setHighlightSimplePost("</em>");
		
		//执行查询  QueryResponse
			QueryResponse queryResponse = solrServer.query(query);
			//获取文档列表(SolrDocumentList)  , 取得查询结果的总记录数
			SolrDocumentList results = queryResponse.getResults();
			System.out.println("总记录数:"+results.getNumFound());
			//遍历列表  取出域 列表内容
			for (SolrDocument solrDocument : results) {
				System.out.println(solrDocument.get("id"));
				
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
				String title = "";
				if(list != null && list.size() > 0) {
					title = list.get(0);
				}else {
					title = (String) solrDocument.get("item_title");
				}
				System.out.println(title);
				System.out.println(solrDocument.get("item_sell_point"));
				System.out.println(solrDocument.get("item_price"));
				System.out.println(solrDocument.get("item_image"));
				System.out.println(solrDocument.get("item_category_name"));
			}
	}
	
	
	
	
}
