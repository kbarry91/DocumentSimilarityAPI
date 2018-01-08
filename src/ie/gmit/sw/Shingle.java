package ie.gmit.sw;

public class Shingle {
	private int documentId;// call them doc 0 and doc 1
	private int shingleHashCode;

	public Shingle(int documentId, int shingleHashCode) {
		super();
		this.documentId = documentId;
		this.shingleHashCode = shingleHashCode;
	}
	
	
	
// get and set
	protected int getDocumentId() {
		return documentId;
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

	// must override hashcode and equals
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/// int hash = t.hashCode();

}
