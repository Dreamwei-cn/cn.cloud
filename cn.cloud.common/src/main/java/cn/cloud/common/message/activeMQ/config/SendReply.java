package cn.cloud.common.message.activeMQ.config;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.MessagePostProcessor;

public class SendReply implements MessagePostProcessor  {

	@Override
	public Message postProcessMessage(Message message) throws JMSException {
		message.setStringProperty("requiresReply", "no");
		return null;
	}





}
