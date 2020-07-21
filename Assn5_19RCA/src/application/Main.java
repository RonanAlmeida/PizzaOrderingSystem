package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/*
 *Name: Ronan Almeida
 * Net ID: 19RCA
 * Date: April 2nd, 2020
 *
 * CISC124 Assignment 5: Pizza Order System
 *
 * The purpose of this assignment is to utilize the Pizza classes and create an ordering system 
 * GUI based entirely in JavaFX. 
 * 
 * The main class is the base of the GUI, loading the fxml and css files 
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox)FXMLLoader.load(Main.class.getResource("PizzaSystem.fxml"));
			Scene scene = new Scene(root, 350, 460);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Order System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}// end start
	
	public static void main(String[] args) {
		launch(args);
	} // end launch 
}