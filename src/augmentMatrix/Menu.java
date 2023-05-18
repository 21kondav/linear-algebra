package augmentMatrix;
public enum Menu {
	create("Create Augmented Array"),
	enterVar("Enter Variables"),
	enterCoeff("Enter Coefficients"),
	enterConst("Enter Constants"),
	printAug("Print Augmented Array"),
	swapRows("Switch two rows"),
	swapCol("Switch two Columns"),
	multiplyRow("Multiply row by a nonzero number"),
	addMultiple("Add a multiple of a row into another row"),
	exit("Exit");
	
	private final String output;
	Menu(String output){
		this.output = output;
	}
	@Override
	public String toString() {
		return output;
	}
	public static void printMenu() {
		
		for(int i = 0; i < Menu.values().length; i++) System.out.printf("\t%d.\t%s\n", i+1, Menu.values()[i]);
		System.out.println("\tChoose:");
	}
	public static Menu[] getMenuArray(){
		return Menu.values();
	}

}




	
