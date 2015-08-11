package com.liantuo.tourism.main;

import nl.minvenj.nfi.storm.kafka.KafkaSpout;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Sum;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;

import com.liantuo.tourism.filter.TeamdbsFilter;
import com.liantuo.tourism.function.ExtractFields;
import com.liantuo.tourism.state.OdsNginxTourismDBStateFactory;
import com.liantuo.tourism.state.OdsNginxTourismDBUpdater;
import com.liantuo.tourism.utils.TeamdbsUtils;

/**
 * 统计customer应用的 总访问量
 *
 * @author Administrator
 */
public class OdsNginxTeamdbsMain {
	/**
	 * topic名称
	 */
	public static final String TOPIC_NAME = "tourism";
	/**
	 * 提取的字段
	 */
	public static final Fields EXTRACTED_FIELDS = new Fields("appName", "serverIP", "reqUrlPath", "httpStatus", "reqTime", "reqDate", "respTime", "size", "reqUrlType");
	/**
	 * group by 的字段
	 */
	public static final Fields GROUP_FIELDS = new Fields("appName", "serverIP", "reqUrlPath", "httpStatus", "reqTime", "reqDate", "reqUrlType");
	/**
	 * 结果字段
	 */
	public static final Fields RESULT_FIELDS = new Fields("appName", "serverIP", "reqUrlPath", "httpStatus", "reqTime", "reqDate", "counts", "respTimeSum", "sizeSum", "reqUrlType");

	public static StormTopology buildTopology() {

		TridentTopology topology = new TridentTopology();
		topology.newStream("kafkaSpout", new KafkaSpout(TOPIC_NAME))
		// 并发读取：与topic分区相同或者稍多点
				.parallelismHint(1)
				// 过滤无用数据（少字段，空，null）
				.each(new Fields("bytes"), new TeamdbsFilter())
				// 并发
				// .parallelismHint(4)
				// 提取访问时间字段
				.each(new Fields("bytes"), new ExtractFields(), EXTRACTED_FIELDS)
				// 并发
				// .parallelismHint(4)
				// 按字段分区
				.groupBy(GROUP_FIELDS)
				// 多聚合开始
				.chainedAgg()
				// 计数聚合
				.aggregate(new Count(), new Fields("counts"))
				// 耗时聚合
				.aggregate(new Fields("respTime"), new Sum(), new Fields("respTimeSum"))
				// 访问量聚合
				.aggregate(new Fields("size"), new Sum(), new Fields("sizeSum"))
				// 多聚合 结束
				.chainEnd()
				// 持久化
				.partitionPersist(new OdsNginxTourismDBStateFactory(), RESULT_FIELDS, new OdsNginxTourismDBUpdater(), new Fields(""));
		// 并发
		// .parallelismHint(4);
		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		Config conf = TeamdbsUtils.getKafkaConfig("OdsNginxTeamdbsMain1");
		if (args != null && args.length > 0) {
			try {
				StormSubmitter.submitTopology(args[0], conf, buildTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("b2b-count", conf, buildTopology());
			Thread.sleep(60 * 1000);
			cluster.shutdown();
		}
	}

}
