package parkinG.retriever;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import parkinG.sfparktemplate.SfpAvailability;

public class Testing {
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		RetrieverManager m = new RetrieverManager();
		HttpRetriever r = new HttpRetriever("http://api.sfpark.org/sfpark/rest/availabilityservice?lat=37.792275&long=-122.397089&radius=1.0&uom=mile&response=xml", m);
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					SfpAvailability sfp = m.getSfp();
					if(!sfp.isDataClean())
						sfp.cleanParkingData();
					System.out.println("[Testing] sfp updatedAt: " + sfp.getUpdatedAt());

					try {
						Thread.sleep(TimeUnit.MINUTES.toMillis(1));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		r.start();
		TimeUnit.SECONDS.sleep(30);
		t1.start();

	}
}
