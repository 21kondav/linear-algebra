package augmentMatrix;


import java.util.ArrayList;
import java.util.HashMap;

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
	
	@Override
	//initializes the application
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		back.setOnAction(e -> stage.setScene(menuScene()));
		this.stage = stage;
		stage.setTitle("Augmenting Matrices");
		stage.setScene(menuScene());
		stage.show();

	}
	//build a menu scene where the user can choose the next step
	public Scene menuScene() {
		
		VBox menu = new VBox();
		for(Menu i: Menu.values()) {
			Button select = new Button(i.toString());
			select.addEventHandler(ActionEvent.ACTION, e -> selectedScene = i);
			select.addEventHandler(ActionEvent.ACTION, e -> command(initArr));
			menu.getChildren().add(select);
		}
		return new Scene(menu, 300, 300);
	}
	//Builds an scene so that the user can initialize a new matrix
	@Override
	public AugmentedArray createAugmentedArray() {
		// TODO Auto-generated method stub
		VBox startBuild = new VBox();
		Label numEqnsLabel = new Label("Enter the number of equations");
		Label numVarsLabel = new Label("Enter the number of variables");
		TextField numeqns = new TextField();
		TextField numvars = new TextField();
		Button submit = new Button("Submit");
		
		submit.addEventHandler(ActionEvent.ACTION, e -> initArr 
				= new AugmentedArray(Integer.parseInt(numeqns.getText()), Integer.parseInt(numvars.getText())));
		submit.addEventHandler(ActionEvent.ACTION, e ->stage.setScene(menuScene()));
		startBuild.getChildren().addAll(numEqnsLabel, numeqns, numVarsLabel, numvars, submit, back);
		stage.setScene(new Scene(startBuild, 300, 300));
		return initArr;
	}
	//Prints the matrix
	@Override
	public void printMatrix(AugmentedArray aug) {
		// TODO Auto-generated method stub
		TilePane printed = new TilePane();
		printed.getChildren().addAll(new Text(aug.toString()), back);
		Scene printScene = new Scene(printed);
		stage.setScene(printScene);
		return;
		
		
	}
	//Allows the usee enter the variables they are working with 
	@Override
	public AugmentedArray enterVariables(AugmentedArray aug) {
		ArrayList<TextField> field = new ArrayList<TextField>();
		initArr = new AugmentedArray(aug);
		// TODO Auto-generated method stub
		VBox addVarsContainer = new VBox();
		int numvars = aug.getNumberOfVariables();
		Label addVars = new Label("Enter the variable");
		addVarsContainer.getChildren().add(addVars);
		for(int i = 0; i < numvars; i++) {
			TextField fieldAtI = new TextField();
			field.add(fieldAtI);
			addVarsContainer.getChildren().add(fieldAtI);
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(int i = 0; i < field.size(); i++) {
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

	@Override
	public AugmentedArray enterCoefficients(AugmentedArray aug) {
		// TODO Auto-generated method stub
		initArr = new AugmentedArray(aug);
		HashMap<int[], TextField> loc = new HashMap<int[], TextField>();
		GridPane grid = new GridPane();
		Label coeffLabel = new Label("Enter coefficients"); //needs added above grid
		grid.add(coeffLabel, 0, 0);
		int numvar = aug.getNumberOfVariables();
		int numeqn = aug.getNumberOfEquations();
		for(int i = 1; i < numeqn; i++) {
			for(int j = 1; j < numvar; j++) {
				TextField entry = new TextField();
				grid.add(entry, i, j);
				loc.put(new int[]{i, j}, entry);
			}
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				for(int[] pair: loc.keySet()) {
					
					double val = Double.parseDouble(loc.get(pair).getText());
					initArr.initializeCoefficients(val, pair[0], pair[1]);

				}
				stage.setScene(menuScene());
			}
		});
		grid.add(submit, 0, numeqn+1);
		grid.add(back, 0, numeqn+2);
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
		Label addVars = new Label("Enter the variable");
		addEqnsContainer.getChildren().add(addVars);
		for(int i = 0; i < numeqns; i++) {
			TextField fieldAtI = new TextField();
			field.add(fieldAtI);
			addEqnsContainer.getChildren().add(fieldAtI);
		}
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for(int i = 0; i < field.size(); i++) {
					String constants = field.get(i).getText();
					initArr.initializeVariable(constants, i);
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
		return null;
	}

	@Override
	public AugmentedArray inputSwapColumns(AugmentedArray aug) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AugmentedArray inputMultiplyRow(AugmentedArray aug) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AugmentedArray inputMultiplyandAdd(AugmentedArray aug) {
		// TODO Auto-generated method stub
		return null;
	}
	//allows us to print any message on the gui (as a pop window)
	@Override
	public void printMessage(String message) {
		// TODO Auto-generated method stub
		TilePane pop = new TilePane();
		pop.getChildren().add(new Text(message));
		Stage popup = new Stage();
		popup.setScene(new Scene(pop, 200, 50));
		popup.show();
	}
	//Selects the scene
	@Override
	public Menu select() {
		// TODO Auto-generated method stub
		return selectedScene;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	//launches the app
	public static void main(String[] args) {
		launch(args);
	}
}
