package cn.cloud.common.message.activeMQ.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class MadeMes implements MessageCreator {

	private String message;
	@Override
	public Message createMessage(Session session) throws JMSException {
		TextMessage textMessage = session.createTextMessage(message);
		return textMessage;
	}

}
