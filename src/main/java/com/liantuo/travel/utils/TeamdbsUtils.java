package com.liantuo.travel.utils;

import org.apache.commons.lang.StringUtils;

import backtype.storm.Config;

public class TeamdbsUtils {

	public static Config getKafkaConfig(String groupId) throws Exception {
		if (StringUtils.isBlank(groupId)) {
			throw new Exception("groupId is required");
		}
		Config config = new Config();
		config.setNumWorkers(6);
		config.put("kafka.group.id", groupId);
		config.put("kafka.zookeeper.connect", "slaver4:2181,slaver5:2181,slaver6:2181");
		config.put("kafka.consumer.timeout.ms", 100);
		config.put("kafka.auto.offset.reset", "smallest");

		config.put("kafka.zookeeper.session.timeout.ms", "4000");
		config.put("kafka.zookeeper.sync.time.ms", "200");
		config.put("kafka.auto.commit.interval.ms", "1000");
		config.put("kafka.spout.buffer.size.max", "1000");
		// 序列化类
		config.put("kafka.serializer.class", "kafka.serializer.StringEncoder");
		return config;
	}
}
