package parkinG.processor;

import java.io.InputStream;

import parkinG.retriever.RetrieverManager;
import parkinG.sfpark.SfparkUtil;
import parkinG.sfpark.templates.SfpAvailability;

public class ProcessorManager extends Thread {
	
	private final RetrieverManager M;
	
	public ProcessorManager(RetrieverManager m) {
		M = m;
	}
	
	@Override
	public void run() {
		M.start();
		while(true) {
			try {
				SfparkUtil.printSfpAvailabilityObject(((SfpAvailability) M.getData(a -> { return SfparkUtil.unmarshalSFPData((InputStream) a); })));
			} catch (InterruptedException e) {
				System.err.println("[ProcessorManager] run(): " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}
