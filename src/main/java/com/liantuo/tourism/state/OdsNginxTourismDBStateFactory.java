package com.liantuo.tourism.state;

import java.util.Map;

import storm.trident.state.State;
import storm.trident.state.StateFactory;
import backtype.storm.task.IMetricsContext;

import com.liantuo.tourism.db.OdsNginxTourismDBProcessor;

public class OdsNginxTourismDBStateFactory implements StateFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2716219110861333525L;

	public State makeState(@SuppressWarnings("rawtypes") Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
		return new OdsNginxTourismDBState(new OdsNginxTourismDBProcessor());
	}
}