package com.e3mall.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.e3mall.conmon.jedis.JedisClient;

public class TestJedisClient {
	
	@Test
	public void testJedisClient() throws Exception {
		//初始化一个spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//获得 JedisClient对象
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("mytest", "my jedisClient test");
		//获取存储的对象
		String string = jedisClient.get("mytest");
		System.out.println(string);
	}
}
