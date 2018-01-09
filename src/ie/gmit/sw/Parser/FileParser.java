package ie.gmit.sw.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import javax.management.relation.RelationServiceNotRegisteredException;

import ie.gmit.sw.Poisin;
import ie.gmit.sw.Shingle;

/**
 * The Type FileParser. A FileParser is a{@link Runnable} implementation
 */
public class FileParser implements Runnable {
	private BlockingQueue<Shingle> queue;
	private String fileName;
	private int shingleSize, k;
	private Deque<String> buffer = new LinkedList<>();
	private int docId;

	// private StringBuffer sb = new StringBuffer();
	// Constructor
	// public FileParser(BlockingQueue<Shingle> queue, String fileName, int
	// shingleSize, int k) {
	/**
	 * Instantiates a new FileParser
	 * 
	 * @param queue
	 *            a BlockingQueue of Shingle Objects
	 * @param fileName
	 *            the name of the file to be parsed
	 * @param shingleSize
	 *            the specified size of the shingles
	 */
	public FileParser(BlockingQueue<Shingle> queue, String fileName, int shingleSize) {
		super();
		this.queue = queue;
		this.fileName = fileName;
		this.shingleSize = shingleSize;
		// this.k = k;
	}

	@Override
	public void run() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("File " + fileName + " not found");
		}
		String line = "";

		try {
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {// skip blank lines
					String uLine = line.toUpperCase();

					// Separate words by blank space (adjust to remover ,.!)
					String[] words = uLine.split("\\s+");

					// Add array of words to buffer
					addWordsToBuffer(words);

					// Shingle s = getNextShingle();
					// queue.put(s); // Blocking method. Add is not a blocking
					// method
					// for (String word : words) {
					// System.out.print(word+" ");
					// }
					// System.out.print(words.toString() + " Shingle hash:" +
					// s.getShingleHashCode()+"\n");
				}
			} // while
				// while(shCounter!=buffer.size()){

			/*
			 * Iterate through buffer until buffer is emptied (buffer.size>0)
			 * With every iteration a Shingle s is created and added to queue
			 */
			for (int i = 0; buffer.size() > 0; i++) {
				// String bufferStr= ((LinkedList<String>) buffer).get(i)+"
				// "+((LinkedList<String>) buffer).get(i+1)+"
				// "+((LinkedList<String>) buffer).get(i+2);
				Shingle s = getNextShingle();
				queue.put(s); // Blocking method. Add is not a blocking
				// System.out.println( bufferStr+" Shingle hash:" +
				// s.getShingleHashCode()+" buffer size:"+buffer.size()+"
				// i="+i+"\n");
				System.out.print("\tShingle hash:" + s.getShingleHashCode() + "\tbuffer size:" + buffer.size() + " i="
						+ i + "\n"); // used for debugging

			}
			// }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			flushBuffer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// run

	/**
	 * Adds every word in Array to buffer
	 * 
	 * @param words
	 *            contains a string array of words
	 */
	private void addWordsToBuffer(String[] words) {
		for (String s : words) {
			buffer.add(s);
		}

	}

	/**
	 * Retrieves and removes the head of the buffer queue and appends the string
	 * to StringBuffer sb This is done until the value shingleSize is met The
	 * values are then removed from the StringBuffer
	 * 
	 * @return a new Shingle object
	 */
	private Shingle getNextShingle() {
		StringBuffer sb = new StringBuffer();
		int counter = 0;

		while (counter < shingleSize) {
			if (buffer.peek() != null) {
				System.out.print(buffer.peek() + " "); // Used for debugging
				sb.append(buffer.poll());
				counter++;
			}
		}

		if (sb.length() > 0) {
			if (sb.length() == shingleSize) {
				sb.delete(0, sb.length() - 1);
			}
			return (new Shingle(docId, sb.toString().hashCode()));
		} else {
			return (null);
		}
	}

	/**
	 * Any values left in the buffer are added to a final Shingle s and placed
	 * on the queue
	 * 
	 * @throws InterruptedException
	 *             when a thread is waiting, sleeping, or otherwise occupied
	 */
	private void flushBuffer() throws InterruptedException {
		while (buffer.size() > 0) {
			Shingle s = getNextShingle();
			if (s != null) {
				queue.put(s);
			} else {
				queue.put(new Poisin(docId, 0));
			}
		}
	}

}
