package parkinG.processor;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.query.api.ExecutionPlan;
import org.wso2.siddhi.query.api.annotation.Annotation;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.execution.query.Query;

public class SiddhiThread extends Thread {

	private HashMap<String, Consumer<Event[]>> streamCallbacks;
	// Arraylist of names of Siddhi query/stream to associate
	//  with streamCallbacks
	private StringBuilder executionPlan;			// StringBuilder to combine all queries and streamDefinitions
	private ExecutionPlanRuntime e;					// ExecutionPlanRuntime which will be running and executing
	//  the queries

	public SiddhiThread() {			// Initialize all the variables
		executionPlan = new StringBuilder();
		streamCallbacks = new HashMap<String,Consumer<Event[]>>();
	}

	/**
	 * Adds a stream definition 
	 * @param stream
	 */
	public void addStream(String stream) {
		executionPlan.append(stream);
	}

	/**
	 * Adds a default callback to streamName
	 * @param streamName
	 */
	public void addCallback(String streamName) {
		addCallback(streamName, new Consumer<Event[]>() {

			@Override
			public void accept(Event[] t) {		// Default consumer
				System.out.print("[SiddhiThread] " + streamName + ": ");
				EventPrinter.print(t);
			}

		});
	}

	/**
	 * Adds a custom callback to streamName
	 * @param streamName
	 * @param customCalback
	 */
	public void addCallback(String streamName, Consumer<Event[]> customCallback) {
		streamCallbacks.put(streamName, customCallback);
	}

	/**
	 * Adds a query to the ArrayList of queries
	 * ! DOES NOT VALIDATE FOR LANGUAGE VALIDITY - make sure your code is valid
	 * @param query
	 */
	public void addQuery(String query) {
		executionPlan.append(query);
	}
	
	/**
	 * Adds all queries to execution plan
	 * ! DOES NOT VALIDATE FOR LANGUAGE VALIDITY
	 * @param executionPlan
	 */
	public void addExecutionPlan(String executionPlan) {
		this.executionPlan.append(executionPlan);
	}

	/**
	 * Adds streamCallbacks for each of the streamCallbacks listed in
	 * the streamCallbacks ArrayList to the given ExecutionPlanRuntime
	 * @param e
	 */
	private void addCallbacks(ExecutionPlanRuntime e) {
		streamCallbacks.entrySet().parallelStream().forEach(a -> {
			System.out.println("[SiddhiThread] addCallbacks(): Adding callback for " + a.getKey());
			e.addCallback(a.getKey(), new StreamCallback() {

				@Override				
				public void receive(Event[] events) {
					a.getValue().accept(events);	
				}

			});
		});
	}

	/**
	 * Push array of Objects to given stream in ExecutionPlanRuntime
	 * ! DOES NOT VALIDATE INPUT - make sure object array is properly formatted
	 * @param streamName
	 * @param args
	 */
	public void pushEvent(String streamName, Object[] args) {
		InputHandler i = e.getInputHandler(streamName);
		try {
			i.send(args);
		} catch (InterruptedException e) {
			System.err.println("[SiddhiThread] pushEvent: ERROR - " + e.getMessage());
		}
	}

	/**
	 * Push array of events to given stream
	 * ! DOES NOT VALIDATE - make sure data is properly formatted
	 * @param streamName
	 * @param events
	 */
	public void pushEvents(String streamName, Object[][] events) {
		for(Object[] event : events)
			pushEvent(streamName, event);
	}

	/**
	 * Initializes SiddhiManager, creates ExecutionPlanRuntime with input, and starts...
	 */
	@Override
	public void run() {
		SiddhiManager s = new SiddhiManager();

		e = s.createExecutionPlanRuntime(executionPlan.toString());
		
		e.getStreamDefinitionMap().entrySet().parallelStream().forEach(new Consumer<Entry<String, AbstractDefinition>>() {

			public void accept(Entry<String, AbstractDefinition> t) {
				System.out.println(t.getKey() + ": ");
				for(Annotation a : t.getValue().getAnnotations())
					System.out.println("\t" + a.toString());
				for(Attribute a : t.getValue().getAttributeList()) {
					System.out.println("\t" + a.toString());
				}
				
			}
			
		});
		addCallbacks(e);

		e.start();

	}
}
