package parkinGv3.retriever;

import java.io.InputStream;

import parkinGv3.retriever.sfparktemplate.SfpAvailability;

public class RetrieverManager extends Thread {
	
	private SfpAvailability sfp;
	
	@Override
	public void run() {
		
	}
	
	public synchronized void updateSfp(SfpAvailability sfp) {
		this.sfp = sfp;
	}
	
	public synchronized SfpAvailability getSfp() {
		return sfp;
	}
	
}
