package com.recipez.views;

import com.recipez.util.GlobalValues;
import com.recipez.views.view_models.IngredientViewModel;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//This is a single Ingredient View, multiple of these will be stacked into VBox inside of CreateRecipeView.java
public class IngredientView extends HBox{
    
    // 6 labels will be swapped out for six other editable elements
    private Label lblIngredientName, lblQuantity, lblVolume, lblUnitsOfVolume, lblWeight, lblUnitsOfWeight;

    // editable elements to be used when in editable state
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxUnitsOfWeight, cboxWeight;
    private TextField tfIngredientNameInput;

    // button to toggle editable state;
    private Button toggleEditButton;

    private final IngredientViewModel ingredientViewModel = new IngredientViewModel();  
    
    public IngredientView(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight) {        
        //Initialize elements for the Ingredient View
        createIngredientView(ingredientName, quantity, volume, unitOfVolume, weight, unitOfWeight);
        createIngredientViewEditable(ingredientName, quantity, volume, unitOfVolume, weight, unitOfWeight);        
        //default view is ingredient without edit
        populateIngredientView();
        bindViewModel();               
    }

    public void bindViewModel(){
        this.lblIngredientName.textProperty().bindBidirectional(ingredientViewModel.ingredientNameStringProperty());       
        this.tfIngredientNameInput.textProperty().bindBidirectional(ingredientViewModel.ingredientNameStringProperty());
    }

    // Initializes elements for the ingredient View
    public void createIngredientView(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight){
        // System.out.println("IngredientView.creatIngredientView() ingredient name: "+ ingredientName);
        this.lblIngredientName = new Label("");
        this.lblQuantity = new Label(quantity);
        this.lblVolume = new Label(volume);
        this.lblUnitsOfVolume = new Label(unitOfVolume);
        this.lblWeight = new Label(weight);
        this.lblUnitsOfWeight = new Label(unitOfWeight);
        this.toggleEditButton = new Button("+");

        // Ingredient name needs to be set in the viewModel before the binding works.
        this.ingredientViewModel.setIngredientName(ingredientName);
       
        this.lblIngredientName.setMinWidth(200);
       // this.lblQuantity.setMinWidth(50);
        this.lblVolume.setMinWidth(50);
        this.lblUnitsOfVolume.setMinWidth(50);
        this.toggleEditButton.setFont(GlobalValues.SMALL_FONT);

    } 

    // Initializes elements for the ingredient View Editable
    public void createIngredientViewEditable(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight) {
        this.tfIngredientNameInput = new TextField("");
        this.cboxQuantity = new ChoiceBox<>();
        this.cboxVolume = new ChoiceBox<>();
        this.cboxUnitsOfVolume = new ChoiceBox<>();
        this.cboxWeight = new ChoiceBox<>();
        this.cboxUnitsOfWeight = new ChoiceBox<>();
        this.toggleEditButton = new Button("+");

        // If this is the view that is shown first this may need to be set first too.
        // this.ingredientViewModel.setIngredientName(ingredientName);
        
        this.tfIngredientNameInput.setMinWidth(200);
        this.cboxVolume.setMinWidth(50);
        this.cboxUnitsOfVolume.setMinWidth(50);
        this.toggleEditButton.setFont(GlobalValues.SMALL_FONT);

    }

    public void populateIngredientView(){     
        // System.out.println("IngredientView.populateIngredientView() ingredient name: "+ this.lblIngredientName.getText());  
        this.getChildren().clear();
        this.getChildren().addAll(this.lblIngredientName, this.lblVolume, this.lblUnitsOfVolume, this.toggleEditButton);
        // System.out.println("(2) IngredientView.populateIngredientView() ingredient name: "+ this.lblIngredientName.getText()+"\n"); 
        this.toggleEditButton.setOnAction(e -> toggleEditableView(false));
    }

    public void populateIngredientsViewEditable(){
        // System.out.println("IngredientView.populateIngredientEditable() ingredient name: "+ this.tfIngredientNameInput.getText()); 
        this.getChildren().clear();
        this.getChildren().addAll(this.tfIngredientNameInput, this.cboxVolume, this.cboxUnitsOfVolume, this.toggleEditButton);
        // System.out.println("(2)IngredientView.populateIngredientEditable() ingredient name: "+ this.tfIngredientNameInput.getText()+"\n"); 
        this.toggleEditButton.setOnAction(e -> toggleEditableView(true));
    }

    //bad way to toggle view of editable state. True Editable, False Viewable 
    public void toggleEditableView(boolean toggle){
        // if true show viewable
        if(toggle){
            populateIngredientView();
        // else false show editable
        }else{
            populateIngredientsViewEditable();
        }
    }

    public String getIngredientName(){
        return ingredientViewModel.getIngredientName();
    }

}
