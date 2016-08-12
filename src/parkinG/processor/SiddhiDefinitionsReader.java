package parkinG.processor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class SiddhiDefinitionsReader {
	
	private static final String FILEPATH = "SiddhiDefinitions.txt";		// Filepath to files with streams/queries
	private static SiddhiThread s;				// SiddhiThread to add streams and queries
	
	/**
	 * Looks in SiddhiDefinitions.txt - reads and adds to given SiddhiThread the streams and queries provided
	 * @param s
	 */
	protected static final void addSiddhiDefinitions(SiddhiThread s) {
		SiddhiDefinitionsReader.s = s;
		init();
		SiddhiDefinitionsReader.s = null;		// Reset s
		System.out.println("[SiddhiDefinitionsReader] addSiddhiDefinitions(): add complete");
	}
	
	/**
	 * Checks if SiddhiDefinitions.txt exists
	 * If it does -> proceed to read the file
	 * else -> Create the file with the information comments on top then exit
	 */
	private static final void init() {
		final File f = new File(FILEPATH);
		if(f.exists()) {
			try {
				readFile(f);
			} catch (FileNotFoundException e) {
				System.err.println("[SiddhiDefinitionsReader] init(): interesting, FileNotFoundException even though I SPECIFICALLY TESTED FOR IT - " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				f.createNewFile();
				final BufferedWriter w = new BufferedWriter(new FileWriter(f));
				w.write("#\n#	DO NOT MOVE, RENAME, OR DELETE THIS FILE\n#\n#	All lines beginning with # will be ignored\n#	Query/stream definitions are read from top down in order\n#	Validity of query/stream definitions are not checked\n#	 so MAKE SURE YOUR CODE IS RIGHT\n# No annotations allowed\n# Callbacks cannot be added here\n#");
				w.close();
			} catch (IOException e) {
				System.err.println("[SiddhiDefinitionsReader] init(): ERROR - " + e.getMessage());
				e.printStackTrace();
			}
			System.err.println("[SiddhiDefinitionsReader] init(): ERROR - DO NOT MOVE, RENAME, OR DELETE SIDDHIDEFINITIONS.TXT");
			System.err.println("\t\tNew blank SiddhiDefinitions.txt created");
		}
	}
	
	/**
	 * Reads the file line by line and calls addToSiddhiThread skipping those that start with '#'
	 * @param f
	 * @throws FileNotFoundException
	 */
	private static final void readFile(File f) throws FileNotFoundException {
		final Scanner scanner = new Scanner(f);
		String input;
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine().trim();
			if(input.charAt(0) == '#')
				continue;
			else 
				addToSiddhiThread(input);
		}
		scanner.close();
	}
	
	/**
	 * Adds streams and queries to SiddhiThread
	 * @param in
	 */
	private static final void addToSiddhiThread(String in) {
		final String[] words = in.split(" ");
		
		switch(words[0]) {
		case "define":
			s.addStream(in);
			break;
		case "from":
			s.addQuery(in);
			break;
		default:
			System.err.println("[SiddhiDefinitionsReader] addToSiddhiThread(): ERROR - unkown statement:");
			System.err.println(in);
			break;
		}
	}
}
