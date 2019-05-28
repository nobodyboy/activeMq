package com.windq.helloWorld.topic;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TopicProducer {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(session.createTopic("testTopic"));
		Message message = null;
		for (int i = 0; i < 100; i++) {
			message = session.createTextMessage("hello world everyboy - topic " + i );
			producer.send(message);
		}
		producer.close();
		session.close();
		connection.close();
	}
}
