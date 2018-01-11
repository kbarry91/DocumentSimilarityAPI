package ie.gmit.sw.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.objects.Poisin;
import ie.gmit.sw.objects.Shingle;

/**
 * 
 * The Type FileParser. A FileParser is a{@link Runnable} implementation.
 * 
 * @see Runnable
 * 
 * @author Kevin Barry
 */
public class FileParser implements Runnable {
	private BlockingQueue<Shingle> queue;
	private Deque<String> buffer = new LinkedList<>();
	private String fileName;
	private int shingleSize;
	private int docId;

	/**
	 * Constructor that creates a new <code>FileParser</code> object.
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
	public FileParser(BlockingQueue<Shingle> queue, String fileName, int shingleSize, int docId) {
		super();
		this.queue = queue;
		this.fileName = fileName;
		this.shingleSize = shingleSize;
		this.docId = docId;
	}

	@Override
	public void run() {

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		} catch (FileNotFoundException e1) {
			System.out.println("The specified file was not found, ensure correct path and try again");
		}
		String line = "";

		try {
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) { // skip blank lines
					String uLine = line.toUpperCase();

					// Separate words by blank space (adjust to remove ,.!)
					String[] words = uLine.split("\\s+");

					// Add array of words to buffer
					addWordsToBuffer(words);
				}

			} // while

			/*
			 * Iterate through buffer until buffer is emptied (buffer.size<0)
			 * With every iteration a Shingle s is created and added to queue
			 */
			while (buffer.size() != 0) {
				Shingle s = getNextShingle();
				if (!(s == null)) {
					queue.put(s);
				}
			}

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
	}// run

	/**
	 * Adds every separate String <code>s</code> in a String Array to
	 * <code>buffer</code>.
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
	 * to a StringBuffer This is done until the value shingleSize is met The
	 * values are then removed from the StringBuffer.
	 * 
	 * @return Shingle a new Shingle object
	 */
	private Shingle getNextShingle() {
		StringBuffer sb = new StringBuffer();
		int counter = 0;

		while (counter < shingleSize) {
			if (buffer.peek() != null) {
				sb.append(buffer.poll());
				counter++;
			} else {
				counter = shingleSize;
			}
		}

		if (sb.length() > 0) {
			return (new Shingle(docId, sb.toString().hashCode()));
		} else {
			return (null);
		}
	}

	/**
	 * Any values left in the buffer are added to a final Shingle s and placed
	 * on the <strong>BlockingQueue</strong> as a <code>Poisin</code> object.
	 * 
	 * @throws InterruptedException
	 *             when a thread is waiting, sleeping, or otherwise occupied
	 */
	private void flushBuffer() throws InterruptedException {
		while (buffer.size() > 0) {
			Shingle s = getNextShingle();
			if (s != null) {
				queue.put(s);
			}
		}
		queue.put(new Poisin(0, 0));
	}

}
