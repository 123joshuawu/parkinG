package parkinG.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

final class SiddhiExecutionPlansReader {
	
	private static final String FILEPATH = "SiddhiExecutionPlans";		// Filepath to files with streams/queries
	private static SiddhiThread s;				// SiddhiThread to add streams and queries
	
	/**
	 * Looks in SiddhiExecutionPlans - reads and adds to given SiddhiThread the execution plans provided
	 * @param s
	 */
	protected static final void addSiddhiDefinitions(SiddhiThread s) {
		SiddhiExecutionPlansReader.s = s;
		init();
		SiddhiExecutionPlansReader.s = null;		// Reset s
		System.out.println("[SiddhiExecutionPlansReader] addSiddhiDefinitions(): add complete");
	}
	
	/**
	 * Checks if SiddhiExecutionPlans exists
	 * then calls readExecutionPlans
	 */
	private static final void init() {
		final File f = new File(FILEPATH);
		if(f.exists()) {
			try {
				readExecutionPlans(f);
			} catch (IOException e) {
				System.err.println("[SiddhiExecutionPlansReader] init(): " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.err.println("[SiddhiExecutionPlansReader] init(): SiddhiExecutionPlans folder MISSING!!!");
		}
	}
	
	/**
	 * Calls readFile on every file in SiddhiExecutionPlans except read-me.txt
	 * @param f
	 * @throws IOException
	 */
	private static final void readExecutionPlans(final File f) throws IOException {
		Files.walk(Paths.get(f.getAbsolutePath())).filter(a -> { return !a.endsWith("read-me.txt") && Files.isRegularFile(a); }).forEach(filePath -> {
			try {
				readFile(filePath.toFile());
			} catch (FileNotFoundException e) {
				System.err.println("[SiddhiExecutionPlansReader] init(): interesting, FileNotFoundException even though I SPECIFICALLY TESTED FOR IT - " + e.getMessage());
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Reads the file line by line and calls addToSiddhiThread skipping those that start with '#'
	 * @param f
	 * @throws FileNotFoundException
	 */
	private static final void readFile(final File f) throws FileNotFoundException {
		final String execPlanName = f.getName();
		final Scanner scanner = new Scanner(f);
		String input;
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			if(input.trim().equals("") || input.charAt(0) == '#')
				continue;
			
//			if(input.trim().split(" ")[2].equals("inputStream"))
			if(input.trim().split(" ")[2].toLowerCase().contains("outputstream"))
				s.addCallback(input.trim().split(" ")[2]);	// If output stream definition detected: add callback
			
			s.addExecutionPlan(input);	// revise for multi execution plans
		}
		
		scanner.close();
	}
}
