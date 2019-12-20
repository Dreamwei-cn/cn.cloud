package cn.cloud.shop.tickets_api.tickets.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class DataCallback implements Callback {
	
	private final long startTime;
	private final String message;

	public DataCallback(long startTime, String message) {
		this.startTime = startTime;
		this.message = message;
	}
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {

		if (metadata != null) {
			long endTime = System.currentTimeMillis() - startTime;
			System.out.println("callback success, message(" + message + ") send to partition("
                    + metadata.partition() + ")," + "offset(" + metadata.offset() + ") in" + endTime);

		} else {
			exception.printStackTrace();
		}
	}

}
