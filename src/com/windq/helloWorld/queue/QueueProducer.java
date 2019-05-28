package com.windq.helloWorld.queue;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


public class QueueProducer {
	public static void main(String[] args) throws JMSException {
		// ①创建连接工厂connectionFactory。
		// 参数表示使用tcp传输连接器(activeMQ支持的传输连接器在activemq.xml文件的<transportConnectors>元素中指定)
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		// ②使用工厂connectionFactory创建一个连接connection
		Connection connection = connectionFactory.createConnection();
		// ③开启connection连接
		connection.start();
		// ④利用connection创建一个session会话。
		// 第一个参数：是否开启事务；true为开启，则发送失败消息重新发送；false为关闭，则发送失败不重发
		// 第二个参数：响应模式；开启事务情况下，参数无作用；否则可以设置AUTO_ACKNOWLEDGE(自动确认),CLIENT_ACKNOWLEDGE(客户端手动确认),DUPS_OK_ACKNOWLEDGE(自动批量确认)和SESSION_TRANSACTED(事务提交并确认)四种模式。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// ⑤利用session会话创建一个名为testQueue目的地,PS:此处为队列，也可以为主题
		//Queue destination = session.createQueue("testQueue");
		Topic destination = session.createTopic("testTopic");
		// ⑥利用session会话创建一个生产者，参数是目的地队列
		MessageProducer producer = session.createProducer(destination);
		// ⑦发送100条消息
		Message message = null;
		for (int i = 0; i < 100; i++) {
			message = session.createTextMessage("hello world everyboy " + i );
			producer.send(message);
		}
		// ⑧关闭所有的资源连接
		producer.close();
		session.close();
		connection.close();
	}
}
