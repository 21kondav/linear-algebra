package workAugmMatrix;

import java.util.InputMismatchException;
import java.util.Scanner;

//import javax.management.monitor.GaugeMonitor;

public class CLI {

	public static Scanner keyboard = new Scanner(System.in);

	public static void printMenu() {
		
		System.out.println("\t1\tCreate Augmented Array");
		System.out.println("\t2\tEnter Variables");
		System.out.println("\t3\tEnter Coefficients");
		System.out.println("\t4\tEnter Constants");
		System.out.println("\t5\tPrint Augmented Array");
		System.out.println("\t6\tSwitch two Rows");
		System.out.println("\t7\tSwitch two Columns");
		System.out.println("\t8\tMultiply all entries of a row by a nonzero number");
		System.out.println("\t9\tAdd a multiple of one row into another row");
		System.out.println("\t10\tTo exit");
		System.out.print("\t\tChoice: ");
	}

	public static void main(String[] args) {

		AugmentedArray aug = null;

		boolean done = false;
		do {
			printMenu();
			int response = -1;
			try {
				response = keyboard.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Incorrect input! Retry...");
				if (keyboard.hasNext())
					keyboard.next();
				continue;
			}
			switch (response) {
			case 1:
				aug = createAugmentedArray();
				break;
			case 2:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = enterVariables(aug);
				}
				break;
			case 3:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = enterCoefficients(aug);
				}
				break;
			case 4:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = enterConstants(aug);
				}
				break;
			case 5:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					System.out.println(aug);
				}
				break;
			case 6:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = inputSwapRows(aug);
				}
				break;
			case 7:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = inputSwapColumns(aug);
				}
				break;
			case 8:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = inputMultiplyRow(aug);
				}
				break;
			case 9:
				if (aug == null) {
					System.out.println("Create augmented array first!");
				} else {
					aug = inputMultiplyandAdd(aug);
				}
				break;
			case 10:
				System.out.println("Goodbye!");
				done = true;

			default:
			}
		} while (!done);

	}

	private static AugmentedArray createAugmentedArray() {
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

	private static AugmentedArray enterVariables(AugmentedArray aug) {
		System.out.println("Setting up variable names...");

		for (int i = 0; i < aug.getNumberOfVariables(); i++) {
			System.out.print("Enter " + (i + 1) + " variable name: ");
			String input = keyboard.next();
			aug.initializeVariable(input, i);
		}
		return aug;
	}

	private static AugmentedArray enterCoefficients(AugmentedArray aug) {
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

	private static AugmentedArray enterConstants(AugmentedArray aug) {

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

	private static AugmentedArray inputSwapRows(AugmentedArray aug) {
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

	private static AugmentedArray inputSwapColumns(AugmentedArray aug) {
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

	private static AugmentedArray inputMultiplyRow(AugmentedArray aug) {
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

	private static AugmentedArray inputMultiplyandAdd(AugmentedArray aug) {
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