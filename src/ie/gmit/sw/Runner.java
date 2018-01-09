package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ie.gmit.sw.UserInterface;
import ie.gmit.sw.Parser.FileParser;

public class Runner {

	public static void main(String[] args) {
	UserInterface ui = new UserInterface();
	ui.show();
		//UserInterface ui = new UserInterface().show();
		//new UserInterface().show();
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<>();
		int shing = 4;
		String name= "tester.txt";
		// threadPoolSize
		//Thread t1 = new Thread(new FileParser(q, name, shing));
		//System.out.println(ui.getFileNameA()+ ui.getShingleSize());
		Thread t1 = new Thread(new FileParser(q, ui.getFileNameA(), ui.getShingleSize()),"A");
		Thread t2 = new Thread(new FileParser(q, ui.getFileNameB(), ui.getShingleSize()),"B");
		
		// Thread t1 = new Thread(new FileParser(q,
		// ui.getFileNameA(),ui.getShingleSize(), k));
		
		
		//Thread t1 = new Thread(new FileParser(q, name, shing));

		t1.start();
		t2.start();
	}

}
