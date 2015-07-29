package com.liantuo.travel.customer.state;

import java.util.Map;

import storm.trident.state.State;
import storm.trident.state.StateFactory;
import backtype.storm.task.IMetricsContext;

import com.liantuo.travel.customer.db.OdsNginxTourismDBProcessor2;

public class OdsNginxTourismDBStateFactory implements StateFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8176406959946236376L;

	public State makeState(@SuppressWarnings("rawtypes") Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
		return new OdsNginxTourismDBState(new OdsNginxTourismDBProcessor2());
	}
}