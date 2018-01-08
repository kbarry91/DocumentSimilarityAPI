package ie.gmit.sw;

//import java.util.Scanner;
//import ie.gmit.sw.*;
public class UserInterface {
	private String fileNameA;
	private String fileNameB;
	private int shingleSize;
	private int option;
	
	
	public UserInterface() {
		super();
	}
	/*
	 * function to launch a menu to the user menu will allow user to enter 2
	 * filenames and shingle size
	 */
	public UserInterface show() {

	//	do {
			option = Services.getInputInt(Services.showMainMenu);
			if (option==1){
				this.fileNameA=Services.getInputString("Enter file name 1 : ");
				this.fileNameB=Services.getInputString("Enter file name 2 : ");
				this.shingleSize = Services.getInputInt("Enter Shingle Size :");
				System.out.println("fileNameA"+this.fileNameA+"fileNameB"+fileNameB+ "SS;"+shingleSize);
			}
	//	}while(option!=2);
		
		
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
