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
	private Deque<String> buffer = new LinkedList<>();

	private String fileName;

	private int shingleSize;
	private int numMinhashes;
	private int docId ;

	/**
	 * Constructor that instantiates a new FileParser
	 * 
	 * @param queue
	 *            a BlockingQueue of Shingle Objects
	 * @param fileName
	 *            the name of the file to be parsed
	 * @param shingleSize
	 *            the specified size of the shingles
	 * @param docId
	 *            the unique identification number of the document
	 */
	public FileParser(BlockingQueue<Shingle> queue, String fileName, int shingleSize, int numMinhashes, int docId) {
		super();
		this.queue = queue;
		this.fileName = fileName;
		this.shingleSize = shingleSize;
		this.numMinhashes = numMinhashes;

		this.docId = docId;
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
			int testerInt=0;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {// skip blank lines
					String uLine = line.toUpperCase();

					// Separate words by blank space (adjust to remover ,.!)
					String[] words = uLine.split("\\s+");

					// Add array of words to buffer
					addWordsToBuffer(words);
					Shingle s = getNextShingle();
					queue.put(s); 
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
				// for debugging
				// String bufferStr= ((LinkedList<String>) buffer).get(i)+"
				// "+((LinkedList<String>) buffer).get(i+1)+"
				// "+((LinkedList<String>) buffer).get(i+2);

				Shingle s = getNextShingle();///////////////////////////////////////////////
				queue.put(s); // Blocking method. Add is not a blocking//////////////////////////

				// for debugging
				// System.out.println( bufferStr+" Shingle hash:" +
				// s.getShingleHashCode()+" buffer size:"+buffer.size()+"
				// i="+i+"\n");
				//System.out.print("\tShingle hash:" + s.getShingleHashCode() + "\tbuffer size:" + buffer.size() + " i="
					//	+ i + " Docid:" + docId + "\n"); // used for debugging
				testerInt++;
				System.out.println("in buuffer  for>>>>>>>testerInt" + testerInt);

			}

			// }
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
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
		System.out.println("FINISHED RUN in reader");

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

		while (counter < shingleSize-1) {
			if (buffer.peek() != null) {
				System.out.print(buffer.peek() + "<"+docId+">"); // Used for debugging
				sb.append(buffer.poll());
				counter++;
			}
		}
System.out.print("\t hashode:"+sb.toString().hashCode()+ " docID"+ docId+"\n");
		if (sb.length() > 0) {
			if (sb.length() == shingleSize) {
				sb.delete(0, sb.length() - 1);
			}
			return (new Shingle(docId, sb.toString().hashCode()));
		} else {
			   sb.delete(0, sb.length());
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
			} //else {
			//	queue.put(new Poisin(docId, 0));
			//}
		}
		queue.put(new Poisin(0, 0));
	}

}
