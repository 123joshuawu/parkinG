package parkinG.retriever;

import parkinG.retriever.sfparktemplate.SfpAvailability;

public class RetrieverManager extends Thread {
	
	private SfpAvailability sfp;
	
	@Override
	public void run() {
		
	}
	
	/**
	 * HttpRetriever will use this to update SfpAvailability object
	 * @param sfp
	 */
	protected synchronized void updateSfp(SfpAvailability sfp) {
		this.sfp = sfp;
	}
	
	/**
	 * Processor layer will use this to request SfpAvailability object
	 * @return SfpAvailability sfp
	 */
	public synchronized SfpAvailability getSfp() {
		return sfp;
	}
	
}
