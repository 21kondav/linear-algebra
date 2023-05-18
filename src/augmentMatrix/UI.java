package augmentMatrix;

abstract interface UI {

	abstract AugmentedArray createAugmentedArray();
	abstract void printMatrix(AugmentedArray aug);
	abstract AugmentedArray enterVariables(AugmentedArray aug);
	abstract AugmentedArray enterCoefficients(AugmentedArray aug);
	abstract AugmentedArray enterConstants(AugmentedArray aug);
	abstract AugmentedArray inputSwapRows(AugmentedArray aug);
	abstract AugmentedArray inputSwapColumns(AugmentedArray aug);
	abstract AugmentedArray inputMultiplyRow(AugmentedArray aug);
	abstract AugmentedArray inputMultiplyandAdd(AugmentedArray aug);
	abstract void printMessage(String message);
	abstract public Menu select();
	abstract public void run();

	public default void command(AugmentedArray aug) {
		Menu item = select();
		if(item.equals(Menu.exit)) {
			printMessage("Good Bye!");
			System.exit(0);
		}
		if(aug == null && !item.equals(Menu.create)) {
			printMessage("Create an augmented array first!");
			return;
		}
		switch (item) {
		case create:
			aug = createAugmentedArray();
			break;
		case enterVar:
			aug = enterVariables(aug);
			break;
		case enterCoeff:
			aug = enterCoefficients(aug);
			break;
		case enterConst:
			aug = enterConstants(aug);
			break;
		case printAug:
			//TODO: abstract for GUI implementation
			printMatrix(aug);
			break;
		case swapRows:
			aug = inputSwapRows(aug);
			break;
		case swapCol:
			aug = inputSwapColumns(aug);
			break;
		case multiplyRow:
			aug = inputMultiplyRow(aug);
			break;
		case addMultiple:
			aug = inputMultiplyandAdd(aug);
			break;

		default:
		}

	}
}

