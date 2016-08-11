package parkinG.retriever;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Abstract class Retriever outlines basic methods revolving around the InputStream data
 * @author joshuawu
 *
 * @param <T> - type of main retriever process - e.g. for HttpRetriever, it is HttpClient
 */
abstract class Retriever<T> extends Thread {
	
	protected final String dataSource;	// Datasource description URL, filename, etc.
	protected InputStream data;			// InputStream with data
	protected final T R;				// Retriever process e.g. HttpURLConnection, File, etc.
	protected final RetrieverManager M;	// RetrieverManager in charge of this retriever object
	
	protected Retriever(String dataSource, T r, RetrieverManager m) {
		this.dataSource = dataSource;
		R = r;
		M = m;
	}
	
	/**
	 * Update InputStream data in some form
	 */
	public abstract void run();
	
	/**
	 *  Closes data InputStream
	 */
	protected void closeDataStream() {
		try {
			data.close(); 
		} catch (IOException e) {
			System.err.println("[HttpRetriever] closeResponseStream(): ERROR - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns data InputStream
	 * ! FOR TESTING PURPOSES ONLY
	 * @return data InputStream
	 */
	protected InputStream getDataStream() {
		return data;
	}
	
	/**
	 * Returns String data from InputStream data
	 * ! FOR TESTING PURPOSES ONLY
	 * ! Will consume all data inside InputStream
	 * @return String response
	 */
	protected String getResponseString() {
		Scanner s = new Scanner(data);
		String result = s.useDelimiter("\\A").next().trim();
		s.close();
		closeDataStream();
		return result;
	}
}
