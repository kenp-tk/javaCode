package com.e3mall.item.listener;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.e3mall.item.pojo.Item;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 		监听商品添加消息,生成对应的静态页面
 * 
 * */


public class HtmlGenListener implements MessageListener {
 
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	
	@Override
	public void onMessage(Message message) {
		try {
			//从消息中获取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new  Long(text);
			//等待商品提交
			Thread.sleep(300);
			//根据商品id查询商品信息(商品基本信息和商品描述)
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			//获取商品描述
			TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
			//将商品信息的数据封装到数据集中
			Map data = new HashMap();
			data.put("item", item);
			data.put("itemDesc", tbItemDesc);
			//加载模板对象
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			//创建一个输出流,指定文件的输出路径
			Writer out = new OutputStreamWriter(new FileOutputStream(HTML_GEN_PATH + itemId +".html"),"UTF-8");
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
