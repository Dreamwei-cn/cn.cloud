package cn.cloud.shop.tickets_api.tickets.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MyKafkaProducer {
	
	private final KafkaProducer<String, String> producer;
	public final static String TOPIC = "itsm-test111";
	
	public MyKafkaProducer() {
		 // 初始化producer相关配置
        Properties prop = new Properties();
        // 消费者获取消息元信息(topics, partitions and replicas)的地址,配置格式是：host1:port1,host2:port2，也可以在外面设置一个vip
        // metadata.broker.list传入boker和分区的静态信息,这里有两台server
        prop.put("bootstrap.servers", "127.0.0.1:9092");
        // key的序列化方式
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
     
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 设置消息确认模式
        // 0:不保证消息的到达确认，只管发送，低延迟但是会出现消息的丢失，在某个server失败的情况下，有点像TCP
        // 1:发送消息，并会等待leader 收到确认后，一定的可靠性
        // 2:发送消息，等待leader收到确认，并进行复制操作后，才返回，最高的可靠性
        prop.put("request.required.acks", 1);
       
        this.producer =   new KafkaProducer<>(prop);
	}
	
	public void produce() {
		int messageNo = 100;
		int count = 150;
		while (messageNo < count) {
			String key = String.valueOf(messageNo);
			String data = " Hello  kafka  message  " + key;
			long startTime = System.currentTimeMillis();
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, key, data);
			
			producer.send(record, new DataCallback(startTime, data));
			System.out.println(" 消息发送          >>> " + key );
			messageNo++;
			
		}
	}
	
	public static void main(String[] args) {
		new MyKafkaProducer().produce();
	}
	

	

}
