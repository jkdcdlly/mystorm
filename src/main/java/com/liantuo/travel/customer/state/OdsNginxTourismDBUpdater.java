package com.liantuo.travel.customer.state;

import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;

public class OdsNginxTourismDBUpdater extends BaseStateUpdater<OdsNginxTourismDBState> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336659070675499471L;

	public void updateState(OdsNginxTourismDBState state, List<TridentTuple> tuples, TridentCollector collector) {
		synchronized (this) {
			state.update(tuples, collector);
		}
	}

}
