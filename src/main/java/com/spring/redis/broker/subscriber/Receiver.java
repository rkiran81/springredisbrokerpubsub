package com.spring.redis.broker.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Receiver implements MessageListener {
	
	Logger logger = LoggerFactory.getLogger(Receiver.class);
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		logger.info("Redis Event Consumed - {}",message);
	}

}
