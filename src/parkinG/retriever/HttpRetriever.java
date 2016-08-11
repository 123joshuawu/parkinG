package parkinG.retriever;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

class HttpRetriever extends Retriever<CloseableHttpClient> {

	private final long requestInterval;	// Amount of time between requests

	protected HttpRetriever(String url, RetrieverManager m) {
		super(url, HttpClients.createDefault(), m);
		requestInterval = TimeUnit.MINUTES.toMillis(1);	// Set timeInterval to 1 minute
	}

	/**
	 * Handy Dandy tool for testing - prints some header field data
	 * @throws IOException 
	 */
	private void printHeaderData(HttpResponse response) throws IOException {
		System.out.println("\t\tResponse Code: " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
		System.out.println("\t\tProtocol Version: " + response.getStatusLine().getProtocolVersion());
		for(Header h : response.getAllHeaders()) {
			System.out.println("\t\t" + h.getName() + ": " + h.getValue());
		}
	}

	/**
	 * Gets data from server, sleeps for requestInterval, repeat....
	 */
	@Override
	public void run() {
		while(true) {
			try {

				HttpGet httpget = new HttpGet(dataSource);
				System.out.println("[HttpRetriever] run(): Created GET request");
				HttpResponse response = R.execute(httpget);
				System.out.println("[HttpRetriever] run(): Request sent and response received");
				printHeaderData(response);
				data = response.getEntity().getContent();
				M.updateSfp(XmlUtil.unmarshalSFPData(data));	// Update RetrieverManager's SfpAvailability object
				closeDataStream();
				
			} catch (IOException e) {
				System.out.println("[HttpRetriever] run(): ERROR - " + e.getMessage());
			}
			try {
				System.out.println("[HttpRetriever] run(): Going to sleep for 1 minute");
				Thread.sleep(requestInterval);
				System.out.println("[HttpRetriever] run(): Sleep complete");
			} catch (InterruptedException e) {
				System.err.println("[HttpRetriever] run(): ERROR - " + e.getMessage());
			} 
		}
	}
}
