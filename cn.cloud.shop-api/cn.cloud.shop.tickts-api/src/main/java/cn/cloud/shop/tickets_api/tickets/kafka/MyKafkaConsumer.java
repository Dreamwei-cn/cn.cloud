package cn.cloud.shop.tickets_api.tickets.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class MyKafkaConsumer {
	
	private  Consumer<String, String> consumer;
	
	public MyKafkaConsumer(){
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "127.0.0.1:9092");
		properties.put("group.id", "group1");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(MyKafkaProducer.TOPIC));
	}
	
	public  void Consume(){
		int i = 0 ;
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
			if (records.count() > 0) {
				for (ConsumerRecord<String, String> consumerRecord : records) {
					String mesage = consumerRecord.value();
					System.out.println("  consumer<<<<<<<<<  接收到消息 " + mesage);
					
				}
			}
			

			try {
				Thread.sleep(1);
				System.out.println( " 读取  消息   " + i++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new MyKafkaConsumer().Consume();
	}

}
