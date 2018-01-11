package ie.gmit.sw.objects;

import ie.gmit.sw.objects.Shingle;

/**
 * 
 * Represents A string of words converted to a hash code.
 * 
 * A <code>Poisin</code> is used to differentiate as a separate object to a
 * <code>Shingle</code>.
 * 
 * @see Shingle
 * 
 * @author Kevin Barry
 */
public class Poisin extends Shingle {

	/**
	 * Constructor that creates a new <code>Poisin</code> object.
	 * 
	 * @param documentId
	 *            the unique identification number of the document
	 * 
	 * @param shingleHashCode
	 *            a hash code representation of an array of Strings
	 * 
	 */
	public Poisin(int documentId, int shingleHashCode) {
		super(documentId, shingleHashCode);
	}

}
