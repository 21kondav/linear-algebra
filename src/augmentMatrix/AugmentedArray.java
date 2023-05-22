package augmentMatrix;

import java.util.Arrays;

public class AugmentedArray {


	private String[] coefficients = null;
	private double[] constants = null;
	private String[] variables = null;

	/**
	 * Constructor taking an argument that defines the number of variables and the
	 * number of independent equations.
	 * 
	 * @param numvar {@link #initializeVariable(String, int)}
	 * @see Au
	 */
	public AugmentedArray(int numeqn, int numvar) {
		//builds arrays for the coefficients, variables and constants
		String row = "";
		for(int i = 0; i < numvar; i++) row+="* ";
		coefficients = new String[numeqn];
		Arrays.fill(coefficients, row);
		
		variables = new String[numvar];
		constants = new double[numeqn];
	}
//Copy constructor
	public AugmentedArray(AugmentedArray other) {
		coefficients = other.coefficients.clone();
		variables = other.variables.clone();
		constants = other.constants.clone();
	}

	public void initializeCoefficients(double value, int row, int col) {
		if (row < 0 || row >= getNumberOfEquations() || col < 0 || col >= getNumberOfVariables()) {
			System.err.println("Index out of bounds!");
			return;
		}
		//new implementation
		String[] theRow = coefficients[row].split(" ");
		theRow[col] = String.valueOf(value);
		coefficients[row] = String.join(" ", theRow);
	}
	public void initializeVariable(String variable, int col) {
		if (col < 0 || col >= getNumberOfVariables()) {
			System.err.println("Index out of bounds!");
			return;
		}
		variables[col] = variable;
	}

	public void initializeConstants(double constant, int row) {
		if (row < 0 || row >= getNumberOfEquations()) {
			System.err.println("Index out of bounds!");
			return;
		}
		constants[row] = constant;
	}

	public double getCoefficient(int i, int j) {
		try {
			return Double.parseDouble(coefficients[i].split(" ")[j]);
		}catch(Exception IndexOutOfBounds) {
			System.err.println("Index out of bounds!");
			return 0.0;
		}	
	}

	public int getNumberOfEquations() {
		return constants.length;
	}

	public int getNumberOfVariables() {
		return variables.length;
	}
	//EDIT: Static method to convert double arrays to single arrays method
	public static String[] convertToOneD(double[][] matrix) {
		
		String[] arr = new String[matrix.length];
		for(int k = 0; k < matrix.length; k++) {
			double[] row = matrix[k];
			String currentRow = "";
			for(double i: row) {
				currentRow += i +" ";
			}
			arr[k] = currentRow;
		}
		return arr;
	}
	//EDIT: Static method to Convert from one dimention to two dimenional array
	public static double[][] convertToTwoD(String[] oneDim) {
		double[][] twoDim = new double[oneDim[0].length()][oneDim.length];
		for(int i =0; i < oneDim.length; i++) {
			String[] current = oneDim[i].split(" ");
			for(int j = 0; j < current.length; j++) {
				twoDim[i][j] = Double.parseDouble(current[j]);
			}
		}
		return twoDim;
	}
/*
 * TODO: Change the follow methods to work with single string arrays
 * swapRows(int, int): AugmentedArray
 * swapColumns(int, int): AugmentedArray
 * multiplyToArray(double, int): Augmented Array
 * multiplyAndAddToArray(double, int): Augmented Array
 * toString(): String
*/
	public AugmentedArray swapRows(int rowOne, int rowTwo) {
		AugmentedArray temp = new AugmentedArray(this);
		try {
			rowOne--;
			rowTwo--;
			//TODO: Make sure this works before implementing generally
			String tempString = temp.coefficients[rowOne];
			temp.coefficients[rowOne] = temp.coefficients[rowTwo];
			temp.coefficients[rowTwo] = tempString;
			double tempConst = temp.constants[rowOne];
			temp.constants[rowOne] = temp.constants[rowTwo];
			temp.constants[rowTwo] = tempConst;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;
	}

	public AugmentedArray swapColumns(int columnOne, int columnTwo) {
		AugmentedArray temp = new AugmentedArray(this);
		try {
			columnOne--;
			columnTwo--;

			for (int i = 0; i < constants.length; i++) {
				String[] row = temp.coefficients[i].split(" ");
				String val = row[columnOne];
				row[columnOne] = row[columnTwo];
				row[columnTwo] = val;
				temp.coefficients[i] = String.join(" ", row);
				
			}
			String var = temp.variables[columnOne];
			temp.variables[columnOne] = temp.variables[columnTwo];
			temp.variables[columnTwo] = var;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;

	}

	public AugmentedArray multiplyToArray(double multiplier, int rowIndex) {
		rowIndex--;
		AugmentedArray temp = new AugmentedArray(this);

		try {
			if (multiplier == 0) {
				System.err.println("Cannot multiply by 0!");
				return this;
			}
			String[] row = temp.coefficients[rowIndex].split(" ");
			for (int i = 0; i < row.length; i++) {
				row[i]= String.valueOf(Double.parseDouble(row[i]) * multiplier)+"";
			}
			
			temp.coefficients[rowIndex] = String.join(" ", row);
			temp.constants[rowIndex] *= multiplier;
			return temp;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;
	}

	public AugmentedArray multiplyAndAddToArray(double multiplier, int srcindex, int dstindex) {
		dstindex--;
		srcindex--;
		AugmentedArray temp = new AugmentedArray(this);

		try {
			if (multiplier == 0) {
				System.err.println("Cannot multiply by 0!");
				return this;
			}
			for (int i = 0; i < this.getNumberOfVariables(); i++) {
				String[] row = temp.coefficients[dstindex].split(" ");
				row[i] = String.valueOf(temp.getCoefficient(dstindex, i) + multiplier * temp.getCoefficient(srcindex, i));
				temp.coefficients[dstindex] = String.join(" ", row);
			}
			temp.constants[dstindex] += multiplier * temp.constants[srcindex];
			return temp;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;
	}

	// public AugmentedArray
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < variables.length; i++) {
			builder.append(String.format("%-10s", variables[i]));
		}
		builder.append("# \n");

		for (int i = 0; i < coefficients.length; i++) {
			String[] row = coefficients[i].split(" ");
			for(int j = 0; j < row.length; j++)
				builder.append(String.format("%-10.4s", row[j]));
			builder.append(String.format("%-10.4g", constants[i]));
			builder.append("\n");
		}
		return builder.toString();
	}
}
