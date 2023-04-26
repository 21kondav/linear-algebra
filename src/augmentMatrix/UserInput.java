package augmentMatrix;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class UserInput {

	abstract protected int getUserInput();
	abstract protected AugmentedArray createAugmentedArray();
	abstract protected void printMatrix(AugmentedArray aug);
	abstract protected AugmentedArray enterVariables(AugmentedArray aug);
	abstract protected AugmentedArray enterCoefficients(AugmentedArray aug);
	abstract protected AugmentedArray enterConstants(AugmentedArray aug);
	abstract protected AugmentedArray inputSwapRows(AugmentedArray aug);
	abstract protected AugmentedArray inputSwapColumns(AugmentedArray aug);
	abstract protected AugmentedArray inputMultiplyRow(AugmentedArray aug);
	abstract protected AugmentedArray inputMultiplyandAdd(AugmentedArray aug);
	abstract protected void printMessage(String message);
	abstract public Menu select();

	public void command(AugmentedArray aug) {
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
			System.out.println(aug); 
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

	//TODO: Build a command method that takes a string response and does some functions using regex


class CLI extends UserInput{
	private Scanner keyboard;
	public CLI() {
		keyboard = new Scanner(System.in);
	}
	public void run() {
		AugmentedArray aug = null;
		do {
			Menu.printMenu();
			this.command(aug);

		} while (true);
	}
	@Override
	public int getUserInput() {
		Scanner keyboard = new Scanner(System.in);
		int input = keyboard.nextInt();
		keyboard.close();
		return input;
	}
	@Override
	protected void printMessage(String message) {
		System.out.println(message);
	}
	@Override
	public Menu select() {
		int response = -1;
		try {
			response = keyboard.nextInt();
		} catch (InputMismatchException NoSuchElementException) {
			System.err.println("Invalid input! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		
		return Menu.values()[response -1];
	}
	@Override
	protected AugmentedArray createAugmentedArray() {
		try {
			System.out.print("Enter the number of equations: ");
			int equations = keyboard.nextInt();
			System.out.print("Enter the number of variables: ");
			int variables = keyboard.nextInt();
			return new AugmentedArray(equations, variables);
		} catch (InputMismatchException e) {
			System.err.println("Input incorrect! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		return null;
	}
	protected AugmentedArray enterVariables(AugmentedArray aug) {
		System.out.println("Setting up variable names...");

		for (int i = 0; i < aug.getNumberOfVariables(); i++) {
			System.out.print("Enter " + (i + 1) + " variable name: ");
			String input = keyboard.next();
			aug.initializeVariable(input, i);
		}
		return aug;
	}
	protected AugmentedArray enterCoefficients(AugmentedArray aug) {
		AugmentedArray temp = new AugmentedArray(aug);
		System.out.println("Setting up coefficients...");
		for (int i = 0; i < temp.getNumberOfEquations(); i++) {
			System.out.println("Enter coefficients for equation #" + (i + 1) + ":");
			for (int j = 0; j < temp.getNumberOfVariables(); j++) {
				System.out.print("Enter coefficient for [" + (i + 1) + ", " + (j + 1) + "]: ");
				try {
					double value = keyboard.nextDouble();
					temp.initializeCoefficients(value, i, j);
				} catch (InputMismatchException e) {
					if (keyboard.hasNext())
						keyboard.nextLine();	
					System.err.print("Input error! Return to menu (y/n)? ");
					String resp = keyboard.nextLine();
					if (resp.equalsIgnoreCase("y"))
						return aug;
					j--;
				}
			}
		}
		return temp;
	}
	@Override
	protected void printMatrix(AugmentedArray aug) {
		System.out.println(aug);
	}
	protected AugmentedArray enterConstants(AugmentedArray aug) {

		System.out.println("Setting up constants...");
		for (int i = 0; i < aug.getNumberOfEquations(); i++) {
			System.out.print("Enter row " + (i + 1) + " constant: ");
			try {
				double value = keyboard.nextDouble();
				aug.initializeConstants(value, i);
			} catch (InputMismatchException e) {
				System.err.println("Input error! Retry...");
				i--;
				if (keyboard.hasNext())
					keyboard.next();
			}
		}
		return aug;
	}

	protected AugmentedArray inputSwapRows(AugmentedArray aug) {
		System.out.print("Enter rows you will like to swap " );
		try {
			int value1 = keyboard.nextInt();
			int value2 = keyboard.nextInt();
			if (value1 < 1 || value1 > aug.getNumberOfEquations() 
					|| value2 < 1 || value2 > aug.getNumberOfEquations()) {
				System.err.println("Invalid input! Retry...");
				return aug;
			}
			return aug.swapRows(value1, value2);
		} catch (InputMismatchException e) {
			System.err.println("Input incorrect! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		return aug;
	}

	protected AugmentedArray inputSwapColumns(AugmentedArray aug) {
		System.out.print("Enter columns you will like to swap ");
		try {
			int value1 = keyboard.nextInt();
			int value2 = keyboard.nextInt();
			if (value1 < 1 || value1 > aug.getNumberOfVariables() 
					|| value2 < 1 || value2 > aug.getNumberOfVariables()) {
				System.err.println("Invalid input! Retry...");
				return aug;
			}
			return aug.swapColumns(value1, value2);
		} catch (InputMismatchException e) {
			System.err.println("Input incorrect! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		return aug;
	}

	protected AugmentedArray inputMultiplyRow(AugmentedArray aug) {
		try {
			System.out.print("What row would you like to multiply?");
			int row = keyboard.nextInt();
			System.out.print("What non-zero number would you like to multiply row " + row + " by?");
			double multiplier = keyboard.nextDouble();
			if (row < 1 || row > aug.getNumberOfEquations() 
					||  multiplier == 0) {
				System.err.println("Invalid input! Retry...");
				return aug;
			}
			return aug.multiplyToArray(multiplier, row);
		} catch (InputMismatchException e) {
			System.err.println("Input incorrect! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		return aug;
	}
	@Override
	protected AugmentedArray inputMultiplyandAdd(AugmentedArray aug) {
		try {
			System.out.print("What is your source row? ");
			int srow = keyboard.nextInt();
			System.out.print("What non-zero number would you like to multiply by ");
			double multiplier = keyboard.nextDouble();
			System.out.println("What is you destination row? ");
			int drow = keyboard.nextInt();
			if (srow < 1 || srow > aug.getNumberOfEquations() 
					|| drow < 1 || drow > aug.getNumberOfEquations()
					||  multiplier == 0) {
				System.err.println("Invalid input! Retry...");
				return aug;
			}

			return aug.multiplyAndAddToArray(multiplier, srow, drow); // chain calling
		} catch (InputMismatchException e) {
			System.err.println("Input incorrect! Retry...");
			if (keyboard.hasNext())
				keyboard.next();
		}
		return aug;
	}
}