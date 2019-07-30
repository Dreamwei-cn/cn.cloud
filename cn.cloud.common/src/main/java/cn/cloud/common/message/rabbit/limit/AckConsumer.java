package cn.cloud.common.message.rabbit.limit;

import java.io.IOException;

import org.springframework.web.servlet.tags.EvalTag;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class AckConsumer extends DefaultConsumer {

	private Channel channel;
	public AckConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}


	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(envelope.getDeliveryTag() +" envelope.getDeliveryTag()  ");
		System.out.println(String.valueOf(properties.getHeaders().get("test")) + "  headers");
		if (String.valueOf(properties.getHeaders().get("test")) == null 
				||  "null".equals(String.valueOf(properties.getHeaders().get("test")))
				|| "ack".equals(String.valueOf(properties.getHeaders().get("test")) )) {
			/**
			 * 
			 *  
			 *  发送次数     是否批量   是否重回队列 basicNack(long deliveryTag, boolean multiple, boolean requeue)
			 *  
			 *  重回队列 尾端
			 */
			if (envelope.getDeliveryTag()<3) {
				System.out.println(" ack  重发");
				channel.basicNack(envelope.getDeliveryTag(), false, true);
			}else {
				System.out.println(" ack 消费发");
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
			
			
		}
		System.out.println(" ack1  消费" + new String(body));
		channel.basicAck(envelope.getDeliveryTag(), false);
	}

}
