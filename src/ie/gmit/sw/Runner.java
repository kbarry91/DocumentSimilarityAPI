package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ie.gmit.sw.UserInterface;
import ie.gmit.sw.similarity.MiniHasher;
import ie.gmit.sw.objects.Shingle;
import ie.gmit.sw.parser.FileParser;
/**
 * @author Kevin Barry
 * 
 * Starts Document Similarity Program
 * */
public class Runner {

	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<>();
		UserInterface ui = new UserInterface();
		
		ui.show();

		Thread t1 = new Thread(new FileParser(q, ui.getFileNameA(), ui.getShingleSize(),1), "A");
		Thread t2 = new Thread(new FileParser(q, ui.getFileNameB(), ui.getShingleSize(),2), "B");
		
		t1.start();
		t2.start();
		
		// wait for t1 and t2 to die before t3.start()
		t1.join();
		t2.join();
		
		Thread t3 = new Thread(new MiniHasher(q,ui.getShingleSize()),"C");
		t3.start();
		t3.join();
	}

}
