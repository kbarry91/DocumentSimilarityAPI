package ie.gmit.sw.similarity;

/**
 * The Type JaccardIndex. A {@link JaccardIndex} extends the Abstract class
 * {@link SimilarityIndex}.
 * 
 * The <Strong>JaccardIndex</Strong> class represents the similarities between 2
 * comparison by computing the jaccard index.
 * 
 * @author Kevin Barry
 * 
 */
public class JaccardIndex extends SimilarityIndex {
	private final int intersection;
	private final int itemASize;
	private final int itemBSize;
	private float jaccard;

	/**
	 * Constructor that creates a new <code>JaccardIndex</code> object.
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
	public JaccardIndex(int intersection, int itemASize, int itemBSize) {
		super(intersection, itemASize, itemBSize);
		this.intersection = intersection;
		this.itemASize = itemASize;
		this.itemBSize = itemBSize;
	}

	/**
	 * Computes the Jaccard index 0-1 relating to how similar the comparison has
	 * been
	 * 
	 * @return jaccard The Jaccard index
	 */
	@Override
	public float calculateIndex() {
		jaccard = (float) intersection / (((float) itemASize + (float) itemBSize) - (float) intersection);
		return jaccard;
	}

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
