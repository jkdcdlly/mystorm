package com.liantuo.travel.customer.filter;

import org.apache.commons.lang.StringUtils;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * 过滤无效日志
 */
public class TeamdbsFilter extends BaseFilter {

	private static final long serialVersionUID = -3404468132243757157L;
	private String appName = "";

	public TeamdbsFilter(String appName) {
		this.appName = appName;
	}

	public boolean isKeep(TridentTuple tuple) {
		byte[] bytes = tuple.getBinaryByField("bytes");
		// 过滤 空
		if (null == bytes || bytes.length == 0) {
			return false;
		}
		String str = new String(bytes);
		String[] arrays = str.split(" ");
		// 过滤字段数小于10
		if (arrays.length < 10) {
			return false;
		}
		// 过滤应用名不符的日志
//		if (StringUtils.isNotBlank(appName) && !appName.equals(arrays[0])) {
//			return false;
//		}

		return true;
	}
}
