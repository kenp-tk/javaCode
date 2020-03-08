package com.e3mall.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class ActiveMqTest {
	
	
	/**
	 *   点到点测试发送消息
	 * (ActiveMQ5.11.1 +JDK7环境没有问题  jdk8会出现问题发送的消息看不到)
	 * */
	
	@Test
	public void testQueueProducer() throws Exception{
		//创建一个ConnectionFactory 连接工厂对象  指定需要的服务ip和端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		//使用 ConnectionFactory 对象创建Connection对象
		Connection connection = connectionFactory.createConnection();
		//调用Connection  对象的  start方法 创建连接
		connection.start();
		//创建Session  对象
		//第一个参数表示是否开启事务 .如果开启事务 第二个参数无意义(true). 一般不开启事务(false)
		//第二个参数表示应答模式. 一般是自动应答和手动应答(自己写代码相应).  多选自动应答.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用Session对象  创建Destination对象  有两种形式  queue(点到点)和 topic,此处应用queue
		Queue queue = session.createQueue("test-queue");//创建队列
		//使用Session对象  创建Producer对象
		MessageProducer producer = session.createProducer(queue);
		//创建一个Message对象  可以使用TextMessage
		/*TextMessage message = new ActiveMQTextMessage();
		message.setText("Hello ActiveMQ!");*/
		TextMessage textMessage = session.createTextMessage("Hello ActiveMQ!");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();

	}
	
	/**
	 * 	当消费者接收到消息后资源关闭
	 * 
	 * */
	@Test
	public void testQueueConsumer() throws Exception {
		//创建一个ConnectionFactory对象连接MQ服务器
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		//创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用Connection对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个Destination对象。queue对象
		Queue queue = session.createQueue("spring-queue");//接受消息传入需要接受的队列名称
		//使用Session对象创建一个消费者对象。
		MessageConsumer consumer = session.createConsumer(queue);
		//接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//打印结果
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		//等待接收消息
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 	利用广播的形式(发布和订阅模式)
	 * 
	 * */
	
	@Test
	public void testTopicProducer() throws Exception{
				//创建一个ConnectionFactory 连接工厂对象  指定需要的服务ip和端口
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
				//使用 ConnectionFactory 对象创建Connection对象
				Connection connection = connectionFactory.createConnection();
				//调用Connection  对象的  start方法 创建连接
				connection.start();
				//创建Session  对象
				//第一个参数表示是否开启事务 .如果开启事务 第二个参数无意义(true). 一般不开启事务(false)
				//第二个参数表示应答模式. 一般是自动应答和手动应答(自己写代码相应).  多选自动应答.
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				//使用Session对象  创建Destination对象  有两种形式  queue(点到点)和 topic(发布和订阅),此处应用topic
				Topic topic = session.createTopic("test-topic");//创建广播频道
				//使用Session对象  创建Producer对象
				MessageProducer producer = session.createProducer(topic);
				//创建一个Message对象  可以使用TextMessage
				/*TextMessage message = new ActiveMQTextMessage();
				message.setText("Hello ActiveMQ!");*/
				TextMessage textMessage = session.createTextMessage("Hello Topic ActiveMQ!");
				//发送消息
				producer.send(textMessage);
				//关闭资源
				producer.close();
				session.close();
				connection.close();
		
	}
	
	/**
	 *		使用topic  消费者模块
	 * 
	 * */
	
	@Test
	public void testTopicConsumer() throws Exception{
				//创建一个ConnectionFactory对象连接MQ服务器
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
				//创建一个连接对象
				Connection connection = connectionFactory.createConnection();
				//开启连接
				connection.start();
				//使用Connection对象创建一个Session对象
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				//创建一个Destination对象。queue对象
				Topic topic = session.createTopic("test-topic");//接受消息传入需要接受的队列名称
				//使用Session对象创建一个消费者对象。
				MessageConsumer consumer = session.createConsumer(topic);
				//接收消息
				consumer.setMessageListener(new MessageListener() {
					
					@Override
					public void onMessage(Message message) {
						//打印结果
						TextMessage textMessage = (TextMessage) message;
						String text;
						try {
							text = textMessage.getText();
							System.out.println(text);
						} catch (JMSException e) {
							e.printStackTrace();
						}
						
					}
				});
				
				System.out.println("已启动消费者3...");
				//等待接收消息
				System.in.read();
				//关闭资源
				consumer.close();
				session.close();
				connection.close();
	}
	
	
}
