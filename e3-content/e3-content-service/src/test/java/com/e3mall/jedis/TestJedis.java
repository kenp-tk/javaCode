package com.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	//使用jedis  连接  
	@Test
	public void testJedis() throws Exception {
		//创建一个连接对象  参数  host , port
		 Jedis jedis = new Jedis("192.168.25.129",6379);
		//使用jedis  操作redis 所有的jedis命令都对应一个方法
		 jedis.set("test1","my first Jedis test");
		 String string = jedis.get("test1");
		 System.out.println(string);
		//关闭连接
		 jedis.close();
	}
	
	
	//使用连接池连接单机版
	@Test
	public void testJedisPool() throws Exception{
		//创建一个连接池对象 参数:host , port
		JedisPool jedisPool = new JedisPool("192.168.25.129",6379);
		//从连接池中得到 Jedis对象
		Jedis jedis = jedisPool.getResource();
		//使用jedis  操作redis
		String string = jedis.get("test1");
		System.out.println(string);
		//使用完毕后  关闭连接  连接池回收资源
		jedis.close();
		//关闭连接池
		jedisPool.close();
	}
	
	//连接  redis 集群  
	@Test
	public void testJedisCluster() throws Exception{
		//创建一个JedisCluster对象,有一个对象 是nodes (set类型  set中包含HostAndPort对象)
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.25.129", 7001));
		nodes.add(new HostAndPort("192.168.25.129", 7002));
		nodes.add(new HostAndPort("192.168.25.129", 7003));
		nodes.add(new HostAndPort("192.168.25.129", 7004));
		nodes.add(new HostAndPort("192.168.25.129", 7005));
		nodes.add(new HostAndPort("192.168.25.129", 7006));
		
		JedisCluster jedisCluster = new JedisCluster(nodes);
		//直接用 JedisCluster 对象  操作redis
		jedisCluster.set("test", "123");
		String string = jedisCluster.get("test");
		System.out.println(string);
		//关闭  JedisCluster  对象
		jedisCluster.close();
	}
	
	
}
