package cn.cloud.common.message.rabbit.limit;

import java.io.IOException;

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
		if (String.valueOf(properties.getHeaders().get("test")) == null 
				||  "null".equals(String.valueOf(properties.getHeaders().get("test")))
				|| "ack".equals(String.valueOf(properties.getHeaders().get("test")) )) {
			/**
			 *  发送次数     是否批量   是否重回队列 basicNack(long deliveryTag, boolean multiple, boolean requeue)
			 *  重回队列 尾端
			 */
			if (envelope.getDeliveryTag()<3) {
				System.out.println(" ack  重发 +++++++" + new String(body));
				channel.basicNack(envelope.getDeliveryTag(), false, true);
			}else {
				System.out.println(" ack 重新发发 后消费    ");
				channel.basicAck(envelope.getDeliveryTag(), false);
			}	
			
		}else {
			System.out.println(" ack1消费     " + new String(body));
			channel.basicAck(envelope.getDeliveryTag(), false);

		}
		
	}

}
