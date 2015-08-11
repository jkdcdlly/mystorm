package com.liantuo.tourism.filter;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * 过滤无效日志
 */
public class TeamdbsFilter extends BaseFilter {

	private static final long serialVersionUID = -3404468132243757157L;


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

		return true;
	}
}
