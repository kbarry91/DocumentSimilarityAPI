package ie.gmit.sw;

/**
 * UserInterface is an object that provides a UI to allow console inputs
 */
public class UserInterface {
	private String fileNameA;
	private String fileNameB;
	private int shingleSize;
	private int option;

	/**
	 * Constructor that creates a new <code>UserInterface</code> object.
	 */
	public UserInterface() {
		super();
	}

	/**
	 * show() is a function to launch a menu to the user menu will allow user to
	 * enter 2 filenames and shingle size
	 * 
	 * @return null
	 */
	public UserInterface show() {
		option = Services.getInputInt(Services.showMainMenu);

		if (option == 1) {
			this.fileNameA = Services.getInputString("Enter file name 1 : ");
			this.fileNameB = Services.getInputString("Enter file name 2 : ");
			this.shingleSize = Services.getInputInt("Enter Shingle Size :");
		}
		return null;
	}// show()

	public String getFileNameA() {
		return fileNameA;
	}

	protected void setFileNameA(String fileNameA) {
		this.fileNameA = fileNameA;
	}

	public String getFileNameB() {
		return fileNameB;
	}

	protected void setFileNameB(String fileNameB) {
		this.fileNameB = fileNameB;
	}

	public int getShingleSize() {
		return shingleSize;
	}

	protected void setShingleSize(int shingleSize) {
		this.shingleSize = shingleSize;
	}

}
