package parkinG.processor;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.wso2.siddhi.core.event.Event;

import parkinG.retriever.RetrieverManager;
import parkinG.sfpark.SfparkUtil;
import parkinG.sfpark.templates.Avl;
import parkinG.sfpark.templates.SfpAvailability;

public class ProcessorManager extends Thread {
	
	private final RetrieverManager M;
	private final SiddhiThread S;
	private SfpAvailability sfp;
	
	public ProcessorManager(RetrieverManager m) {
		M = m;
		S = new SiddhiThread();
		init();
	}
	
	private void init() {
		SiddhiDefinitionsReader.addSiddhiDefinitions(S);

		S.addCallback("parkingInputStream");
		S.addCallback("parkingStream");
		S.addCallback("agregateInfoStream");
		
	}
	
	@Override
	public void run() {

		M.start();
		S.start();
	
		while(true) {
			try {
				sfp = ((SfpAvailability) M.getData(a -> { return SfparkUtil.unmarshalSFPData((InputStream) a); }));
				Object[][] objs = new Object[sfp.getAvl().size()][5];
				int i = 0;
				for(Avl a : sfp.getAvl()) {
					objs[i++] = new Object[] {a.getName(), a.getOcc(), a.getOper(), a.typeIs(), sfp.retrieveUpdatedAt().getTime()};
				}
				S.pushEvents("parkingInputStream", objs);
			} catch (InterruptedException e) {
				System.err.println("[ProcessorManager] run(): " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}
