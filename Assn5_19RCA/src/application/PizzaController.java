package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

/*
 * The PizzaController class adds functionality to the GUI elements/window 
 * This includes 6 interactive Choice boxes with sub choices, a text field to 
 * enter the numeric quantity and text area to display orders.
 */

public class PizzaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> cmbSize = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cmbVeg = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cmbCheese = new ChoiceBox<>();


    @FXML
    private ChoiceBox<String> cmbPin = new ChoiceBox<>();

    
    @FXML
    private ChoiceBox<String> cmbGreen = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> cmbHam = new ChoiceBox<>();


    @FXML
    private TextField txtQuan = new TextField();

    
    @FXML
    private Button btnAddOrder;

    @FXML
    private Text txtPizzaCost;

    @FXML
    private Text txtTotalCost;

    @FXML
    private TextArea txtArea;
    
    
    //Creating Observable list objects to populate the Choice box options
    private ObservableList<String> sizeChoices = FXCollections.observableArrayList("Small", "Medium", "Large");
    private ObservableList<String> vegChoices = FXCollections.observableArrayList("Yes", "No");
    private ObservableList<String> cheeseChoices = FXCollections.observableArrayList("Single", "Double", "Triple");
    private ObservableList<String> pinChoices = FXCollections.observableArrayList("None", "Single");
    private ObservableList<String> greenChoices = FXCollections.observableArrayList("None", "Single");
    private ObservableList<String> hamChoices = FXCollections.observableArrayList("None","Single");

   // Current Pizza item, String Output, cost of order and line item number
    private Pizza Pizza;
    private String outLineItem="";
    private float totalCost;
    private int lineCount=0;
    
    // Adds the users selected options to create the Pizza object 
    private void addPizza() {
    	try {
    		boolean veg;
    		if(cmbVeg.getValue()=="Yes") {
    			veg=true;
    		}
    		else {
    			veg=false;
    		}
			Pizza = new Pizza(cmbSize.getValue(),veg,cmbCheese.getValue(),cmbPin.getValue(),cmbGreen.getValue(),cmbHam.getValue());
		} catch (IllegalPizza e) {
			e.printStackTrace();
		}
    } // end addPizza
    
    // Method for checking if user has selected all GUI inputs.
    // Updates cost label pricing if user has selected all inputs
    private void checkIsBlank() {
    	if(cmbSize.getValue()!= null && txtQuan.getText()!= null&& cmbVeg.getValue() != null&& cmbCheese.getValue()!= null &&
    			cmbPin.getValue()!= null && cmbGreen.getValue()!= null && (cmbHam.getValue()!= null||cmbVeg.getValue() == "Yes")) {
    		btnAddOrder.setDisable(false);
        	txtQuan.setDisable(false);
    		addPizza();
    		txtPizzaCost.setText(String.format("$%.2f",Pizza.getCost()));
    		try {
    			txtTotalCost.setText(String.format("$%.2f",Integer.parseInt(txtQuan.getText().strip())*Pizza.getCost()));
    		}catch (NumberFormatException e) {
    			txtTotalCost.setText("$0.00");
    		}
    	}	
    	else {
    		btnAddOrder.setDisable(true);
        	txtQuan.setDisable(true);

    	}
    } // end checkIsBlank

    // Warning the user about incorrect Quantity input - another window popup
    private void alertQuantity() {
    	Alert quanInfo = new Alert(AlertType.INFORMATION);
    	quanInfo.setTitle("Incorrect Input");
    	quanInfo.setContentText("Quantity must be between 1 and 100");
    	quanInfo.setHeaderText("Incorrect Quantity Input");
    	quanInfo.showAndWait();
    } // end alertQuantity
    
    // Meth
    @FXML
    void initialize() {
    	//Populating choice boxes with initial options
    	cmbSize.setItems(sizeChoices);
    	cmbVeg.setItems(vegChoices);
    	cmbCheese.setItems(cheeseChoices);
    	cmbPin.setItems(pinChoices);
    	cmbGreen.setItems(greenChoices);
    	cmbHam.setItems(hamChoices);
    	btnAddOrder.setDisable(true);
    	txtQuan.setDisable(true);
    	txtArea.setEditable(false);
    	
    	// For all choice boxes, having the checkIsBlank method inside to check if all user options are selected
    	cmbSize.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		checkIsBlank();
    	});
    	// Setting restriction for ham, disabling ham choice box if veg option is selected 
    	cmbVeg.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		if(newVal.equals("Yes")) {
    			cmbHam.setValue("None");
    			cmbHam.setDisable(true);
    		}
    		else {
    			cmbHam.setDisable(false);

    		}
    		checkIsBlank();
    	});
    	cmbCheese.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		checkIsBlank();
    	});
    	cmbPin.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		checkIsBlank();

    	});
    	cmbGreen.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		checkIsBlank();

    	});
    	cmbHam.valueProperty().addListener((observableValue,oldVal,newVal)->
    	{
    		checkIsBlank();
    	});	
    	
    	// Input for Quantity text field. setting limitations for integers only between 1 and 100
    	txtQuan.textProperty().addListener((observableValue, oldText, newText) ->
    	{
    		if (newText != null && !newText.isEmpty() ) {
    			btnAddOrder.setDisable(false);
	    		int numQuan=0;
	    		try {
	    			numQuan = Integer.parseInt(txtQuan.getText());
		    		txtTotalCost.setText(String.format("$%.2f",  numQuan*Pizza.getCost()));
		    		checkIsBlank();
	    		} catch (NumberFormatException e) {
	    			((StringProperty)observableValue).setValue("");
	    		}
	    		if (numQuan > 100 || numQuan < 1) {
	    			alertQuantity();
	    			((StringProperty)observableValue).setValue("");
	    		}
    		}
    		else
    		{
    			btnAddOrder.setDisable(true);
    			txtTotalCost.setText("$0.00");
    			((StringProperty)observableValue).setValue("");
    		} 		
    	}); // end txtQuan input
    	
        // On click setOnAction event for add button, it displays the order in the text area along with summing the cost per line 
    	btnAddOrder.setOnAction(event ->
    	{
    		if(txtQuan.getText()!= null&& !txtQuan.getText().isEmpty()) {
    			lineCount++;
        		totalCost +=  Integer.parseInt(txtQuan.getText())*Pizza.getCost();
        		txtTotalCost.setText(String.format("$%.2f",+ Integer.parseInt(txtQuan.getText())*Pizza.getCost()));
        		outLineItem+= "#"+ lineCount+ ", "+  Pizza.toString() + " Quantity: "+txtQuan.getText() +  ", Order Cost: " 
        		+ String.format("$%.2f",+Integer.parseInt(txtQuan.getText())*Pizza.getCost()) +"\n" ;
        		txtArea.setText(outLineItem+ "Total Order Cost: " +String.format("$%.2f", +totalCost) + "\n");
    		}
    		else {
    			alertQuantity(); 			
    		}
    	}); // end btnAdd click-event
    } // end initialize
}
