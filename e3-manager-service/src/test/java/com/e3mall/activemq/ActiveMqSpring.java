package com.e3mall.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpring {
	
	@Test
	public void sendMessage() throws Exception{
		//初始化spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中获得JmsTemplate对象
		JmsTemplate template = context.getBean(JmsTemplate.class);
		//从容器中获得Destination对象
		Destination destination = (Destination) context.getBean("queueDestination");
		//发送消息
		
		template.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("send activemq message");
			}
		});
	}
	
	
}
