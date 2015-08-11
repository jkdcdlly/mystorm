package com.liantuo.tourism.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.guava.collect.Maps;

public class Global {
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	private static Properties loader = new Properties();
	static {
		try {
			loader.load(Global.class.getResourceAsStream("/topology.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置
	 * 
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {

		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	public static void main(String[] args) throws IOException {
		loader.load(Global.class.getResourceAsStream("/topology.properties"));
		System.out.println(loader.getProperty("jdbc.url"));
		;
	}
}
