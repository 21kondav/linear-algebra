package augmentMatrix;

public class AugmentedArray {

	private double[][] coefficients = null;
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
		coefficients = new double[numeqn][numvar];

		variables = new String[numvar];
		constants = new double[numeqn];
	}

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
		coefficients[row][col] = value;
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
		if (i < 0 || i >= getNumberOfEquations() || j < 0 || j >= getNumberOfVariables()) {
			System.err.println("Index out of bounds!");
			return 0.0;
		}
		return coefficients[i][j];
	}

	public int getNumberOfEquations() {
		return constants.length;
	}

	public int getNumberOfVariables() {
		return variables.length;
	}

	public AugmentedArray swapRows(int row1, int row2) {
		AugmentedArray temp = new AugmentedArray(this);
		try {
			row1--;
			row2--;
			if (row1 < 0 || row2 < 0 || row1 >= coefficients.length || row2 >= coefficients.length) {
				throw new IllegalArgumentException("Row cannot be less than zero");
			}
			for (int i = 0; i < variables.length; i++) {
				double var = temp.coefficients[row1][i];
				temp.coefficients[row1][i] = temp.coefficients[row2][i];
				temp.coefficients[row2][i] = var;
			}

			double var = temp.constants[row1];
			temp.constants[row1] = temp.constants[row2];
			temp.constants[row2] = var;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;
	}

	public AugmentedArray swapColumns(int column1, int column2) {
		AugmentedArray temp = new AugmentedArray(this);
		try {
			column1--;
			column2--;
			if (column1 < 0 || column2 < 0 || column1 >= variables.length || column2 >= variables.length) {
				System.err.println("Column index out of bounds!");
				return this;
			}
			for (int i = 0; i < constants.length; i++) {
				double var = temp.coefficients[i][column1];
				temp.coefficients[i][column1] = temp.coefficients[i][column2];
				temp.coefficients[i][column2] = var;
			}
			String var = temp.variables[column1];
			temp.variables[column1] = temp.variables[column2];
			temp.variables[column2] = var;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Index out of bounds! Retry...");
		}
		return temp;

	}

	public AugmentedArray multiplyToArray(double multiplier, int rowindex) {
		rowindex--;
		AugmentedArray temp = new AugmentedArray(this);

		try {
			if (rowindex < 0 || rowindex >= getNumberOfEquations()) {
				System.err.println("Row index out of bounds!");
				return this;
			}

			if (multiplier == 0) {
				System.err.println("Cannot multiply by 0!");
				return this;
			}
			for (int i = 0; i < coefficients[rowindex].length; i++) {
				temp.coefficients[rowindex][i] *= multiplier;
			}
			temp.constants[rowindex] *= multiplier;
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
			if (srcindex < 0 || dstindex < 0 || srcindex >= getNumberOfEquations()
					|| srcindex >= getNumberOfEquations()) {
				System.err.println("Row index out of bounds!");
				return this;
			}
			if (multiplier == 0) {
				System.err.println("Cannot multiply by 0!");
				return this;
			}
			for (int i = 0; i < coefficients[srcindex].length; i++) {
				temp.coefficients[dstindex][i] += multiplier * temp.coefficients[srcindex][i];
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
			for (int j = 0; j < coefficients[i].length; j++) {
				builder.append(String.format("%-10.4g", coefficients[i][j]));
			}
			builder.append(String.format("%-10.4g", constants[i]));
			builder.append("\n");
		}
		return builder.toString();
	}
}
