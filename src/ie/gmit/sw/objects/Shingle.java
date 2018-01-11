package ie.gmit.sw.objects;

/**
 * 
 * Represents an Array of Strings converted to a hash code.
 * 
 * @author Kevin Barry
 */

public class Shingle {
	private int documentId;
	private int shingleHashCode;

	/**
	 * Constructor that creates a new <code>Shingle</code> object.
	 * 
	 * @param documentId
	 *            the unique identification number of the document
	 * 
	 * @param shingleHashCode
	 *            a hash code representation of an array of Strings
	 * 
	 */
	public Shingle(int documentId, int shingleHashCode) {
		super();
		this.documentId = documentId;
		this.shingleHashCode = shingleHashCode;
	}

	public int getDocumentId() {
		return this.documentId;
	}

	protected void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getShingleHashCode() {
		return shingleHashCode;
	}

	protected void setShingleHashCode(int shingleHashCode) {
		this.shingleHashCode = shingleHashCode;
	}

	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}

