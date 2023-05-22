package augmentMatrix;
//TODO: Apply CSS classes to improve the look of the GUI
//TODO: Implement new functionality to the augmentation program
//TODO: Check for optimizations
//TODO: Begin adding more linear algebra functionality
//TODO: Beginning commenting for easier understanding
//TODO: More testing for fringe cases and error handling
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application implements UI {
	private AugmentedArray initArr;
	private Menu selectedScene;
	private Stage stage;
	private final Button back = new Button("Back");
	private boolean arrayCreated = false;

	// launches the app
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	// initializes the application
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		back.setOnAction(e -> stage.setScene(menuScene()));
		this.stage = stage;
		stage.setTitle("Augmenting Matrices");
		stage.setScene(menuScene());
		stage.show();

	}

	// build a menu scene where the user can choose the next step
	public Scene menuScene() {

		VBox menu = new VBox();
		for (Menu i : Menu.values()) {
			Button select = new Button(i.toString());
			select.addEventHandler(ActionEvent.ACTION, e -> selectedScene = i);
			select.addEventHandler(ActionEvent.ACTION, e -> command(initArr));
			menu.getChildren().add(select);
		}
		return new Scene(menu, 300, 300);
	}

	// Builds an scene so that the user can initialize a new matrix
	@Override
	public AugmentedArray createAugmentedArray() {
		// TODO Auto-generated method stub

		if (arrayCreated) {
			printMessage("A new matrix has already been created. Please try again");
			// TODO: Build a wait timer in
			back.fire();
			return null;
		}
		VBox startBuild = new VBox();
		Label numEqnsLabel = new Label("Enter the number of equations");
		Label numVarsLabel = new Label("Enter the number of variables");
		TextField numeqns = new TextField();
		TextField numvars = new TextField();
		Button submit = new Button("Submit");

		submit.addEventHandler(ActionEvent.ACTION,
				e -> initArr = new AugmentedArray(Integer.parseInt(numeqns.getText()),
						Integer.parseInt(numvars.getText())));
		submit.addEventHandler(ActionEvent.ACTION, e -> stage.setScene(menuScene()));
		startBuild.getChildren().addAll(numEqnsLabel, numeqns, numVarsLabel, numvars, submit, back);
		stage.setScene(new Scene(startBuild, 300, 300));
		return initArr;
	}

	// Prints the matrix
	@Override
	public void printMatrix(AugmentedArray aug) {
		// TODO Auto-generated method stub
		TilePane printed = new TilePane();
		printed.getChildren().addAll(new Text(aug.toString()), back);
		Scene printScene = new Scene(printed);
		stage.setScene(printScene);
		return;

	}

	// Allows the usee enter the variables they are working with
	@Override
	public AugmentedArray enterVariables(AugmentedArray aug) {
		ArrayList<TextField> field = new ArrayList<TextField>();
		initArr = new AugmentedArray(aug);
		// TODO Auto-generated method stub
		VBox addVarsContainer = new VBox();
		int numvars = aug.getNumberOfVariables();
		Label addVars = new Label("Enter the variable");
		addVarsContainer.getChildren().add(addVars);
		for (int i = 0; i < numvars; i++) {
			TextField fieldAtI = new TextField();
			field.add(fieldAtI);
			addVarsContainer.getChildren().add(fieldAtI);
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (int i = 0; i < field.size(); i++) {
					String var = field.get(i).getText();
					initArr.initializeVariable(var, i);
				}
				stage.setScene(menuScene());
			}
		});
		addVarsContainer.getChildren().addAll(submit, back);
		stage.setScene(new Scene(addVarsContainer));
		return new AugmentedArray(initArr);
	}

	// FIXME:
	@Override
	public AugmentedArray enterCoefficients(AugmentedArray aug) {
		// TODO Auto-generated method stub
		initArr = new AugmentedArray(aug);
		HashMap<int[], TextField> loc = new HashMap<int[], TextField>();
		GridPane grid = new GridPane();
		Label coeffLabel = new Label("Enter coefficients"); // needs added above grid
		grid.add(coeffLabel, 0, 0);
		int numvar = aug.getNumberOfVariables();
		int numeqn = aug.getNumberOfEquations();
		for (int i = 1; i <= numeqn; i++) {
			for (int j = 0; j < numvar; j++) {
				TextField entry = new TextField();
				grid.add(entry, i, j);
				loc.put(new int[] { i - 1, j }, entry);
			}
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				for (int[] pair : loc.keySet()) {

					double val = Double.parseDouble(loc.get(pair).getText());
					initArr.initializeCoefficients(val, pair[1], pair[0]);

				}
				stage.setScene(menuScene());
			}
		});
		grid.add(submit, 0, numeqn + 1);
		grid.add(back, 0, numeqn + 2);
		stage.setScene(new Scene(grid));
		return new AugmentedArray(initArr);
	}

	@Override
	public AugmentedArray enterConstants(AugmentedArray aug) {
		// TODO Auto-generated method stub
		ArrayList<TextField> field = new ArrayList<TextField>();
		initArr = new AugmentedArray(aug);
		// TODO Auto-generated method stub
		VBox addEqnsContainer = new VBox();
		int numeqns = aug.getNumberOfEquations();
		Label addEqs = new Label("Enter the constants");
		addEqnsContainer.getChildren().add(addEqs);
		for (int i = 0; i < numeqns; i++) {
			TextField fieldAtI = new TextField();
			field.add(fieldAtI);
			addEqnsContainer.getChildren().add(fieldAtI);
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (int i = 0; i < field.size(); i++) {
					String constants = field.get(i).getText();
					initArr.initializeConstants(Double.parseDouble(constants), i);
				}
				stage.setScene(menuScene());
			}
		});
		addEqnsContainer.getChildren().addAll(submit, back);
		stage.setScene(new Scene(addEqnsContainer));
		return new AugmentedArray(initArr);
	}

	@Override
	public AugmentedArray inputSwapRows(AugmentedArray aug) {
		// TODO Auto-generated method stub
		VBox rowSwap = new VBox();
		Button submit = new Button("Submit");

		Label rowSwapLabel = new Label("Enter rows to swap");
		TextField rowOne = new TextField();
		TextField rowTwo = new TextField();

		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					int valueOne = Integer.parseInt(rowOne.getText());
					int valueTwo = Integer.parseInt(rowTwo.getText());
					if (valueOne < 1 || valueOne > initArr.getNumberOfEquations() || valueTwo < 1
							|| valueTwo > initArr.getNumberOfEquations()) {
						printMessage("Invalid input! Try again");
						return;
					}
					initArr = new AugmentedArray(aug.swapRows(valueOne, valueTwo));
					back.fire();
				} catch (InputMismatchException except) {
					printMessage("Invalid input! Try again");
				}
			}
		});
		rowSwap.getChildren().addAll(rowSwapLabel, rowOne, rowTwo, submit, back);
		stage.setScene(new Scene(rowSwap));
		return new AugmentedArray(initArr);
	}

	@Override
	public AugmentedArray inputSwapColumns(AugmentedArray aug) {
		// TODO Auto-generated method stub
		VBox colSwap = new VBox();
		Button submit = new Button("Submit");

		Label colSwapLabel = new Label("Enter column to swap");
		TextField colOne = new TextField();
		TextField colTwo = new TextField();

		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					int valueOne = Integer.parseInt(colOne.getText());
					int valueTwo = Integer.parseInt(colTwo.getText());
					if (valueOne < 1 || valueOne > initArr.getNumberOfVariables() || valueTwo < 1
							|| valueTwo > initArr.getNumberOfVariables()) {
						printMessage("Invalid input! Try again");
						return;
					}
					initArr = new AugmentedArray(aug.swapColumns(valueOne, valueTwo));
					back.fire();
				} catch (InputMismatchException except) {
					printMessage("Invalid input! Try again");
				}
			}
		});
		colSwap.getChildren().addAll(colSwapLabel, colOne, colTwo, submit, back);
		stage.setScene(new Scene(colSwap));
		return new AugmentedArray(initArr);
	}

	@Override
	public AugmentedArray inputMultiplyRow(AugmentedArray aug) {
		// TODO Auto-generated method stub
		GridPane multiplyRow = new GridPane();
		Label rowLabel = new Label("Enter row: ");
		TextField rowToMultiply = new TextField();
		Label multiplierLabel = new Label("Enter row multiplier: ");
		TextField multiplier = new TextField();
		Button submit = new Button("Submit");

		GridPane.setConstraints(rowLabel, 0, 0);
		GridPane.setConstraints(rowToMultiply, 1, 0);
		GridPane.setConstraints(multiplierLabel, 0, 1);
		GridPane.setConstraints(multiplier, 1, 1);
		GridPane.setConstraints(submit, 0, 2);
		GridPane.setConstraints(back, 0, 3);
		multiplyRow.getChildren().addAll(rowLabel, rowToMultiply, multiplierLabel, multiplier, submit, back);

		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					int valueOne = Integer.parseInt(rowToMultiply.getText());
					double valueTwo = Integer.parseInt(multiplier.getText());
					if (valueOne < 1 || valueOne > initArr.getNumberOfVariables()) {
						printMessage("Invalid input! Try again");
						return;
					}
					initArr = new AugmentedArray(aug.multiplyToArray(valueTwo, valueOne));
					back.fire();
				} catch (InputMismatchException except) {
					printMessage("Invalid input! Try again");
				}
			}
		});
		stage.setScene(new Scene(multiplyRow));
		return initArr;
	}

	@Override
	public AugmentedArray inputMultiplyandAdd(AugmentedArray aug) {
		// TODO Auto-generated method stub
		GridPane multiplyAdd = new GridPane();
		Label srcRowLabel = new Label("What row are you starting with: ");
		Label multiplierLabel = new Label("Enter a non-zero multiplier: ");
		Label endRowLabel = new Label("What row are you ending with: ");

		TextField srcRow = new TextField();
		TextField multiplier = new TextField();
		TextField endRow = new TextField();
		Button submit = new Button("Submit");

		GridPane.setConstraints(srcRowLabel, 0, 0);
		GridPane.setConstraints(srcRow, 1, 0);
		GridPane.setConstraints(multiplierLabel, 0, 1);
		GridPane.setConstraints(multiplier, 1, 1);
		GridPane.setConstraints(endRowLabel, 0, 2);
		GridPane.setConstraints(endRow, 1, 2);
		GridPane.setConstraints(submit, 0, 3);
		GridPane.setConstraints(back, 0, 4);

		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					int srow = Integer.parseInt(srcRow.getText());
					double multi = Double.parseDouble(multiplier.getText());
					int drow = Integer.parseInt(endRow.getText());
					if (srow < 1 || srow > aug.getNumberOfEquations() || drow < 1 || drow > aug.getNumberOfEquations()
							|| multi == 0) {
						System.err.println("Invalid input! Retry...");
						printMessage("Invalid input, try again!");
						return;
					}

					initArr = new AugmentedArray(aug.multiplyAndAddToArray(multi, srow, drow)); // chain calling
					back.fire();
				} catch (InputMismatchException except) {
					System.err.println("Input incorrect! Retry...");
					printMessage("Invalid input, try again!");
				}
			}
		});
		multiplyAdd.getChildren().addAll(srcRowLabel,multiplierLabel,endRowLabel,srcRow,multiplier,endRow,submit,back);stage.setScene(new Scene(multiplyAdd));
		return initArr;
	}

	// allows us to print any message on the gui (as a pop window)
	@Override
	public void printMessage(String message) {
		// TODO Auto-generated method stub
		TilePane pop = new TilePane();
		pop.getChildren().add(new Text(message));
		Stage popup = new Stage();
		popup.setScene(new Scene(pop, 200, 50));
		popup.show();
	}

	// Selects the scene
	@Override
	public Menu select() {
		// TODO Auto-generated method stub
		return selectedScene;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

}
