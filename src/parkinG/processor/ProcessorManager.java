package parkinG.processor;

import java.io.InputStream;
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
		S.addCallback("parkingStream", new Consumer<Event[]>() {

			@Override
			public void accept(Event[] t) {
				System.out.print("[SiddhiThread] " + "parkingStream" + ": ");
				for(Event e : t)
					System.out.print(" " + e.getData(0) + " ");
			}
			
		});
	}
	
	@Override
	public void run() {
		M.start();
		S.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true) {
			try {
				sfp = ((SfpAvailability) M.getData(a -> { return SfparkUtil.unmarshalSFPData((InputStream) a); }));
				for(Avl a : sfp.getAvl()) {
					S.pushEvent("parkingInputStream", new Object[] {a.getName(), a.getOcc(), a.getOper(), a.typeIs()});
				}
			} catch (InterruptedException e) {
				System.err.println("[ProcessorManager] run(): " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}
