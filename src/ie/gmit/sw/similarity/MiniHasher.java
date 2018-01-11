package ie.gmit.sw.similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ie.gmit.sw.objects.Poisin;
import ie.gmit.sw.Services;
import ie.gmit.sw.objects.Shingle;

/**
 * The Type MiniHasher. A MiniHasher is a{@link Runnable} implementation.
 * 
 * The <Strong>MiniHasher</Strong> class takes values from a BlockingQueue and
 * hashes them <code>k</code> times to compute the min hash value. The minimum
 * values are then stored in a map.
 * 
 * @see Runnable
 * 
 * @author Kevin Barry
 */
public class MiniHasher implements Runnable {

	private BlockingQueue<Shingle> q;
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<Integer, List<Integer>>();
	private ExecutorService pool;
	private Set<Integer> minHashes;
	private int amtMinHashes;

	/**
	 * Constructor that creates a new <code>MiniHasher</code> object.
	 * 
	 * @param q
	 *            a BlockingQueue of Shingle Objects
	 * @param k
	 *            the amount of minHashes the size of the thread pool
	 * 
	 */
	public MiniHasher(BlockingQueue<Shingle> q, int k) {
		this.q = q;
		this.amtMinHashes = k;
		pool = Executors.newFixedThreadPool(k);
		init();
	}

	/**
	 * Creates a <code>TreeSet</code> of size k
	 */
	public void init() {
		minHashes = new TreeSet<Integer>();

		// random number for the minhashing
		Random randInt = new Random();

		for (int i = 0; i < amtMinHashes; i++) {
			minHashes.add(randInt.nextInt());
		}
	}

	/**
	 * Takes in a {@link Shingle} and computes the lowest hash value of the hash
	 * code.
	 * 
	 * @param s
	 *            a {@link Shingle} object
	 * @return minValue The minimum hashed value of the {@link Shingle}
	 * 
	 */
	public synchronized int hasher(Shingle s) {
		int minValue = Integer.MAX_VALUE;
		for (Integer hash : minHashes) {

			// XOR the Shingle hashcode with the generated minhash methods
			int minHashed = s.getShingleHashCode() ^ hash;
			if (minHashed < minValue) {
				minValue = minHashed;
			}
		}
		return minValue;
	}

	@Override
	public void run() {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		List<Integer> voidList = new ArrayList<>();
		int docCount = 2;

		while (docCount > 0) {
			try {
				Shingle s = q.take();

				// A Poisin is added to the BlockingQueue to indicate EOF or a
				// null Shingle
				if (s instanceof Poisin == false) {
					pool.execute(new Runnable() {

						@Override
						public void run() {

							if (s.getDocumentId() == 1) {
								list1.add(hasher(s));
							} else if (s.getDocumentId() == 2) {
								list2.add(hasher(s));
							} else {
								voidList.add(hasher(s));
							}
						}
					});// Runnable
				} else {
					docCount--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Interuppted exception " + e);
			}
		}

		// Safely shut down the ExecutorService
		shutdownAndAwaitTermination(pool);

		int k1 = list1.size();
		int k2 = list2.size();

		// put list1,list2 to map at specified index
		map.put(1, list1);
		map.put(2, list2);

		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));

		// compute jaccard index
		SimilarityIndex sim = new JaccardIndex(intersection.size(), k1, k2);
		Services.showJaccardResult(sim);
	}

	/**
	 * Safely shut down the ExecutorService. This will ensure that there are no
	 * new task submitted and will wait for at most 60 seconds to finish all
	 * tasks.
	 * 
	 * @see <a
	 *      href="https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html</a>
	 * 
	 *      This method was adapted from
	 */
	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
