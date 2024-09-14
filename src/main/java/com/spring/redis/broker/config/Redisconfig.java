package com.spring.redis.broker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spring.redis.broker.subscriber.Receiver;

@Configuration
public class Redisconfig {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
		conf.setHostName("localhost");
		conf.setPort(6379);
		return new JedisConnectionFactory(conf);
	}
	
	@Bean(name = "MyRedis")
	public RedisTemplate<String, Object> template(){
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
	
	@Bean
	public ChannelTopic topic() {
		return new ChannelTopic("pubsub:javatechie-channel");
	}
	
	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new Receiver());
	}
	
	@Bean
	public RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.addMessageListener(messageListenerAdapter(), topic());
		return container;
	}
}
