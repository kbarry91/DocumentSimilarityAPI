package ie.gmit.sw.similarity;

/**
 * A abstract class that represents the similarities between 2 comparisons
 * 
 * @author Kevin Barry
 */
public abstract class SimilarityIndex {
	private final int intersection;
	private final int itemASize;
	private final int itemBSize;

	/**
	 * Constructor that creates a new <code>Similarity</code> object.
	 * 
	 * @param intersection
	 *            the Integer amount of similarities between 2 objects
	 * 
	 * @param itemASize
	 *            The size of the first object
	 * 
	 * @param itemBSize
	 *            The size of the 2nd object
	 * 
	 */
	public SimilarityIndex(int intersection, int itemASize, int itemBSize) {

		this.intersection = intersection;
		this.itemASize = itemASize;
		this.itemBSize = itemBSize;
	}

	/**
	 * An abstract/deferred method
	 * 
	 * @return float
	 */
	public abstract float calculateIndex();

	public int getIntersection() {
		return intersection;
	}

	public int getItemASize() {
		return itemASize;
	}

	public int getItemBSize() {
		return itemBSize;
	}

}
