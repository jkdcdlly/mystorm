package com.liantuo.travel.customer.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

import com.liantuo.travel.customer.vo.OdsNginxTeamdbsVo;

public class ExtractFields extends BaseFunction {

	/**
	 * 提取需要使用的字段
	 */
	private static final long serialVersionUID = -782900013517185366L;
	private final Logger logger = LoggerFactory.getLogger(ExtractFields.class);

	public void execute(TridentTuple tuple, TridentCollector collector) {
		logger.debug("提取需要使用的字段：开始 ");

		String str = new String(tuple.getBinaryByField("bytes"));
		String[] arrs = str.split(" ");
		if (10 == arrs.length) {
			OdsNginxTeamdbsVo vo = new OdsNginxTeamdbsVo(arrs);
			try {
				collector.emit(new Values(
				// 应用名称
						vo.getAppName(),
						// 服务器IP
						vo.getServerIP(),
						// 访问URL不带参数
						vo.getReqUrlPath(),
						// 状态码
						vo.getHttpStatus(),
						// 访问时间
						vo.getReqTime(),
						// 访问日期
						vo.getReqTime().substring(0, 10),
						// 耗时
						vo.getRespTime(),
						// 流量
						vo.getSize()));
			} catch (Exception e) {
				vo.getReqTime();
			}
		}

		logger.debug("提取需要使用的字段:完成 ");
	}

}
