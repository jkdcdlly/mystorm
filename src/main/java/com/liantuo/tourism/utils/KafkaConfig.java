package com.liantuo.tourism.utils;

import org.apache.commons.lang.StringUtils;

import com.liantuo.tourism.config.Global;

import backtype.storm.Config;

public class KafkaConfig {
	/**
	 * kafka 配置
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public static Config getKafkaConfig(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			throw new Exception("groupId is required");
		}
		Config config = new Config();
		config.setNumWorkers(6);
		config.put("kafka.group.id", groupId);
		config.put("kafka.zookeeper.connect", Global.getConfig("kafka.zookeeper.connect"));
		config.put("kafka.consumer.timeout.ms", Global.getConfig("kafka.consumer.timeout.ms"));
		config.put("kafka.auto.offset.reset", Global.getConfig("kafka.auto.offset.reset"));

		config.put("kafka.zookeeper.session.timeout.ms", Global.getConfig("kafka.zookeeper.session.timeout.ms"));
		config.put("kafka.zookeeper.sync.time.ms", Global.getConfig("kafka.zookeeper.sync.time.ms"));
		config.put("kafka.auto.commit.interval.ms", Global.getConfig("kafka.auto.commit.interval.ms"));
		config.put("kafka.spout.buffer.size.max", Global.getConfig("kafka.spout.buffer.size.max"));
		// 序列化类
		config.put("kafka.serializer.class", Global.getConfig("kafka.serializer.class"));
		return config;
	}
}
