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

/*
 * A class to implement a fileParser
 * */
public class FileParser implements Runnable {
	private BlockingQueue<Shingle> queue;
	private String fileName;
	private int shingleSize, k;
	private Deque<String> buffer = new LinkedList<>();
	private int docId;

	// Constructor
	// public FileParser(BlockingQueue<Shingle> queue, String fileName, int
	// shingleSize, int k) {
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
				if (line.length() > 0) {//skip blank lines
					String uLine = line.toUpperCase();
					
					String[] words = uLine.split("\\s+"); // Can also take a
														// regexpression
					addWordsToBuffer(words);
					Shingle s = getNextShingle(words);
					queue.put(s); // Blocking method. Add is not a blocking
									// method
					for (String word : words) {
						System.out.print(word+" ");
					}
					System.out.print(words.toString() + " Shingle hash:" + s.getShingleHashCode()+"\n");
				}
			}
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

	private void addWordsToBuffer(String[] words) {
		for (String s : words) {
			buffer.add(s);
		}

	}// addWordsToBuffer

	private Shingle getNextShingle(String[] words) {
		StringBuffer sb = new StringBuffer();
		int counter = 0;

		while (counter < shingleSize) {
			if (buffer.peek() != null) {
				sb.append(buffer.poll());
				counter++;
			}
		}

		if (sb.length() > 0) {
			return (new Shingle(docId, sb.toString().hashCode()));
		} else {
			return (null);
		}
	} // Next shingle

	private void flushBuffer() throws InterruptedException {
		while (buffer.size() > 0) {
			Shingle s = getNextShingle(null);
			if (s != null) {
				queue.put(s);
			} else {
				queue.put(new Poisin(docId, 0));
			}
		}
	}

}
