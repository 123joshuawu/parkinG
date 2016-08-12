package parkinG;

import parkinG.processor.ProcessorManager;
import parkinG.retriever.RetrieverManager;
import parkinG.sfpark.templates.SfpAvailability;

public class Testing {
	public static void main(String[] args) {
		RetrieverManager<SfpAvailability> rm = new RetrieverManager<SfpAvailability>();
		rm.setHttpRetriever("http://api.sfpark.org/sfpark/rest/availabilityservice?lat=37.792275&long=-122.397089&radius=1.0&uom=mile&response=xml");
		ProcessorManager pm = new ProcessorManager(rm);
		pm.start();
	}
}
