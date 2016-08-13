package parkinG.retriever;

import java.io.InputStream;
import java.util.function.Function;

/**
 * Public class RetrieverManager is in charge of relaying data from its dataRetriever(s) to 
 * the Processor layer.
 * @author joshuawu
 *
 * @param <T> - Type of object data should be returned as
 */
public class RetrieverManager<T> extends Thread {
						
	private Retriever dataRetriever;		// Retriever that will get the data
											// For now only one - expansion is a possibility
	private InputStream data;				// InputStream with server data
	private boolean updated = false;		// Used for coordination between getData and updateData methods
	
	@Override
	public void run() {
		dataRetriever.start();
	}
	
	/**
	 * Set dataRetriever to new HttpRetriever
	 * @param url
	 */
	public void setHttpRetriever(String url) {
		dataRetriever = new HttpRetriever(url, this);
	}
	
	public void setFileRetriever(String filepath) {
		dataRetriever = new FileRetriever(filepath, this);
	}
	
	/**
	 * HttpRetriever will use this to update this InputStream
	 * @param in
	 * @throws InterruptedException 
	 */
	protected synchronized void updateData(InputStream in) throws InterruptedException {
		data = in;			// Get data from dataRetriever
		updated = true;		// Toggle updated to allow getData()
		System.out.println("[RetrieverManager] updateData(): data updated ");
		notify();
		System.out.println("[RetrieverManager] updateData(): notify() complete");
	}
	
	/**
	 * Processor layer will use this to get data in object format
	 * @param f - Function to convert InputStream data into object of type T
	 * @return T dataObject
	 * @throws InterruptedException
	 */
	public synchronized T getData(Function<InputStream, T> f) throws InterruptedException {
		System.out.println("[RetrieverManager] getSfp(): beginning wait()");
		while(!updated)		// Wait for dataRetriever to update data
			wait();
		System.out.println("[RetrieverManager] getSfp(): wait() over");
		T dataObject = f.apply(data);
		dataRetriever.closeDataStream();		// Clean up resources
		updated = false;						// Reset updated
		return dataObject;
	}
	
}
