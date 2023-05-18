package augmentMatrix;

import java.util.InputMismatchException;
import java.util.Scanner;

class CLI implements UI {
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


	public int getUserInput() {
		Scanner keyboard = new Scanner(System.in);
		int input = keyboard.nextInt();
		keyboard.close();
		return input;
	}

	@Override
	public void printMessage(String message) {
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

		return Menu.values()[response - 1];
	}

	@Override
	public AugmentedArray createAugmentedArray() {
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

	public AugmentedArray enterVariables(AugmentedArray aug) {
		System.out.println("Setting up variable names...");

		for (int i = 0; i < aug.getNumberOfVariables(); i++) {
			System.out.print("Enter " + (i + 1) + " variable name: ");
			String input = keyboard.next();
			aug.initializeVariable(input, i);
		}
		return aug;
	}

	public AugmentedArray enterCoefficients(AugmentedArray aug) {
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
	public void printMatrix(AugmentedArray aug) {
		System.out.println(aug);
	}

	public AugmentedArray enterConstants(AugmentedArray aug) {

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

	public AugmentedArray inputSwapRows(AugmentedArray aug) {
		System.out.print("Enter rows you will like to swap ");
		try {
			int value1 = keyboard.nextInt();
			int value2 = keyboard.nextInt();
			if (value1 < 1 || value1 > aug.getNumberOfEquations() || value2 < 1
					|| value2 > aug.getNumberOfEquations()) {
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

	public AugmentedArray inputSwapColumns(AugmentedArray aug) {
		System.out.print("Enter columns you will like to swap ");
		try {
			int value1 = keyboard.nextInt();
			int value2 = keyboard.nextInt();
			if (value1 < 1 || value1 > aug.getNumberOfVariables() || value2 < 1
					|| value2 > aug.getNumberOfVariables()) {
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

	public AugmentedArray inputMultiplyRow(AugmentedArray aug) {
		try {
			System.out.print("What row would you like to multiply?");
			int row = keyboard.nextInt();
			System.out.print("What non-zero number would you like to multiply row " + row + " by?");
			double multiplier = keyboard.nextDouble();
			if (row < 1 || row > aug.getNumberOfEquations() || multiplier == 0) {
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
		public AugmentedArray inputMultiplyandAdd(AugmentedArray aug) {
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