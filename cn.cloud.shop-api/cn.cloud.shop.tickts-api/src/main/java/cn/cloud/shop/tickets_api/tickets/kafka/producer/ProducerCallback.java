package cn.cloud.shop.tickets_api.tickets.kafka.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.alibaba.fastjson.JSON;

import cn.cloud.shop.tickets_api.tickets.kafka.common.MessageEntity;



public class ProducerCallback implements ListenableFutureCallback<SendResult<String, MessageEntity>> {
	private final Long startTime;
	
	private final String key;
	
	private final MessageEntity messageEntity;

	
	private static final Logger log = LoggerFactory.getLogger(ProducerCallback.class);

	public ProducerCallback(Long startTime, String key, MessageEntity messageEntity) {
		this.startTime = startTime;
		this.key = key;
		this.messageEntity = messageEntity;
	}

	@Override
	public void onFailure(Throwable ex) {
		ex.printStackTrace();
		
	}

	@Override
	public void onSuccess(@Nullable SendResult<String, MessageEntity> result) {
		if (result == null) {
			return;
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		RecordMetadata metadata = result.getRecordMetadata();
		if (metadata != null) {
			StringBuilder record = new StringBuilder();
			record.append("message (")
				.append(" key = ").append(key).append(",")
				.append(" mesage = ").append(JSON.toJSON(messageEntity)).append(")")
				.append(" with offset( ").append(metadata.offset()).append(")")
				.append(" in ").append(elapsedTime).append(" ms");
			log.info(record.toString());

		}
	}

}
