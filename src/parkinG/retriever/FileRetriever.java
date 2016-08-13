package parkinG.retriever;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class FileRetriever extends Retriever<File> {

	public FileRetriever(String filepath, RetrieverManager m) {
		super(filepath, new File(filepath), m);
	}

	@Override
	public void run() {
		while(true) {
			try {

				data = new DataInputStream(new BufferedInputStream(new FileInputStream(R)));
				M.updateData(data);

				TimeUnit.SECONDS.sleep(10);

			} catch (FileNotFoundException e) {
				System.err.println("[FileRetriever] run(): ERROR - " + e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
