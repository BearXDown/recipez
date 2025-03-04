package views;

import views.view_models.RecipeViewModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.GlobalValues;

//extends StackPane to use z-indexing of elements
public class RecipeView extends StackPane{

    private Label lblRecipeName, lblUserMessage;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, separatorNameInput, vboxLabelContainer, separatorNameLabel;
    private boolean recipeNameToggle;
    
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();

    public RecipeView(){       
        createNameRecipeView();
        bindViewModel(); 
    }
 
    private void createNameRecipeView(){
        this.recipeNameToggle = true;

        this.lblUserMessage = new Label("Click to rename your recipe!");
        this.lblRecipeName = new Label("");       
        this.tfRecipeName = new TextField("");
        this.btnSaveRecipeName = new Button("Save");

        this.lblUserMessage.setFont(GlobalValues.LARGE_FONT);
        this.lblRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.tfRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.btnSaveRecipeName.setFont(GlobalValues.MEDIUM_FONT);
        
        this.setStyle(GlobalValues.COLOR_PRIMARY);

        this.lblRecipeName.setStyle("-fx-padding: 12 8 8 8");
        this.tfRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipeName.setAlignment(Pos.CENTER);

        this.lblUserMessage.setOnMouseClicked(this::swapLayer);
        this.lblRecipeName.setOnMouseClicked(this::swapLayer);
        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::saveRecipeName);
        
        this.hboxRecipeNameInput = new HBox(); 
        this.hboxRecipeNameLabel = new HBox(); 

        this.vboxInputContainer = new VBox();
        this.vboxLabelContainer = new VBox();      
        this.separatorNameInput = new VBox();
        this.separatorNameLabel = new VBox();

        /*********************************User input screen*********************************/
    
        //TODO: This may be easier to do with a GridPane        
        //Hbox container for textfield for user input   
        this.hboxRecipeNameInput.setSpacing(10);
        this.hboxRecipeNameInput.setAlignment(Pos.CENTER);
        this.hboxRecipeNameInput.setStyle(GlobalValues.COLOR_PRIMARY);        
        this.hboxRecipeNameInput.getChildren().addAll(this.tfRecipeName);        
        this.hboxRecipeNameInput.setPrefWidth(900);
        HBox.setHgrow(this.tfRecipeName, Priority.ALWAYS);

        this.separatorNameInput.getChildren().addAll(this.hboxRecipeNameInput, this.btnSaveRecipeName);
        this.separatorNameInput.setAlignment(Pos.TOP_CENTER);
        this.separatorNameInput.setPrefHeight(150);
        this.separatorNameInput.setStyle(GlobalValues.COLOR_PRIMARY);
        
        //Vbox to hold Hbox to user input textfield
        this.vboxInputContainer.setPrefHeight(GlobalValues.VIEW_HEIGHTH);
        this.vboxInputContainer.setAlignment(Pos.CENTER);
        this.vboxInputContainer.setStyle(GlobalValues.COLOR_PRIMARY);
        this.vboxInputContainer.getChildren().addAll(separatorNameInput);

        /*********************************Input display screen*********************************/

        //Hbox container that displays user input via label             
        this.hboxRecipeNameLabel.setSpacing(10);
        this.hboxRecipeNameLabel.setAlignment(Pos.CENTER);
        this.hboxRecipeNameLabel.setStyle(GlobalValues.COLOR_PRIMARY);
        this.hboxRecipeNameLabel.getChildren().addAll(this.lblRecipeName);        
        this.hboxRecipeNameLabel.setPrefWidth(900);

        //Vbox to vertically align lblUserMessage and lblRecipeName 
        this.separatorNameLabel.getChildren().addAll(this.hboxRecipeNameLabel, this.lblUserMessage);
        this.separatorNameLabel.setAlignment(Pos.TOP_CENTER);
        this.separatorNameLabel.setPrefHeight(150);        
        this.separatorNameLabel.setStyle(GlobalValues.COLOR_PRIMARY);
        
        //Vbox to hold Hbox user input label     
        this.vboxLabelContainer.setPrefHeight(GlobalValues.VIEW_HEIGHTH);
        this.vboxLabelContainer.setAlignment(Pos.CENTER);
        this.vboxLabelContainer.setStyle(GlobalValues.COLOR_PRIMARY);
        this.vboxLabelContainer.getChildren().addAll(separatorNameLabel);

        //initial display      
        //Stacking containers, rather than only adding the one you are viewing/editing, allows for a smoother 
        //transition effect when calling the fadeIn() and fadeOut() methods 
        this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
    }

    //SwapLayer being called by a MouseEvent 
    private void swapLayer(MouseEvent event){
        swap();
    }

    //SwapLayer being called by a KeyEvent 
    private void swapLayer(KeyEvent event){
        swap();
    }

    //SwapLayer being called by a ActionEvent
    private void swapLayer(ActionEvent event){
        swap();
    }

    //only works for two items, rework for 3 and above to use in application function traversal
    private void swap(){

        /**/
        //swap logic that checks for the top most element
        // if (this.getChildren().getLast() == this.vboxInputContainer){
        //     System.out.println("You are on the input view, now swapping to label view.");           
        //     this.getChildren().clear();
        //     this.getChildren().addAll(this.vboxInputContainer, this.vboxLabelContainer);
        //     return;
        // }
        // System.out.println("You are on the label view, now swapping to input view.");   
        // this.getChildren().clear();;
        // this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
        /**/

        /**/
        //swap logic that checks against a boolean (addtional boolean attribute required), limited to two elements, may be perfect for usage here.
        //TODO: BROKEN, opacity doesnt remove element so you cant click through an invisible element. rework
        if(this.recipeNameToggle){
            fadeOut(this.vboxInputContainer);
            this.btnSaveRecipeName.setDisable(true);
            this.tfRecipeName.setDisable(true);
            this.getChildren().clear();
            this.getChildren().addAll(this.vboxInputContainer, this.vboxLabelContainer);
            fadeIn(this.vboxLabelContainer);
            this.recipeNameToggle = !this.recipeNameToggle;            
        }else{
            fadeOut(this.vboxLabelContainer);
            this.btnSaveRecipeName.setDisable(false);
            this.tfRecipeName.setDisable(false);
            this.getChildren().clear();
            this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
            fadeIn(this.vboxInputContainer);
            this.recipeNameToggle = !this.recipeNameToggle;  
        }
        /**/
    }

    // These fadeIn / fadeOut use Node as it is the base class (superclass) for all components added to the JavaFX Scene Graph. 
    // The JavaFX Node class is abstract, so you will only add subclasses of the Node.
    // When I use it in the RecipeView, I pass in a VBox not a Node. 
    private void fadeIn(Node elementToTransition) {        
        FadeTransition ft = new FadeTransition(Duration.millis(1000), elementToTransition);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();        
    }

    private void fadeOut(Node elementToTransition) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), elementToTransition);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty());
        this.lblRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty()); 
    }    

    // processKeyPres(), and both saveRecipeName() methods needs to be looked at, maybe too obtuse? Streamline this? dont know how at the moment.
    private void processKeyPress(KeyEvent event){
       switch(event.getCode()){
            case ENTER:
                recipeViewModel.setName(tfRecipeName.getText());
                saveRecipeName(event);
                swapLayer(event);
                break;
            default:
       } 
    }
    private void saveRecipeName(ActionEvent event){ recipeViewModel.setName(tfRecipeName.getText()); recipeViewModel.save(); swapLayer(event); }
    private void saveRecipeName(KeyEvent event){ recipeViewModel.save(); }
}
