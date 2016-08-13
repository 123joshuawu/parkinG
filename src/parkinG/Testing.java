package parkinG;

import java.util.concurrent.TimeUnit;

import parkinG.processor.ProcessorManager;
import parkinG.retriever.RetrieverManager;
import parkinG.sfpark.templates.SfpAvailability;

public class Testing {
	public static void main(String[] args) {
		// 1st testing of inter-layer coordination and communication between Retriever layer and Processor layer
		Thread time = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 0;
				while(true) {
					System.out.println("[Time] run(): " + i / 4 + " minutes " + (i++ * 15) % 60 + " seconds");
					try {
						TimeUnit.SECONDS.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		RetrieverManager<SfpAvailability> rm = new RetrieverManager<SfpAvailability>();
		rm.setHttpRetriever("http://api.sfpark.org/sfpark/rest/availabilityservice?lat=37.792275&long=-122.397089&radius=1.0&uom=mile&response=xml");
		//rm.setFileRetriever("SFPTestCase1.xml");
		ProcessorManager pm = new ProcessorManager(rm);
		time.start();
		pm.start();
	}
}
