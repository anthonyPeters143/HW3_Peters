package com.example.hw3_peters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class POSTSystem extends Application {



    int NEED_TO_CHANGE_HEIGHT = 320, NEED_TO_CHANGE_WIDTH= 240;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //
        String MAIN_TITLE           = "POST Register",
                EOD_TOTAL_TITLE     = "Total sale for the day is: $",

                ADD_BUTTON_TITLE    = "Add",
                DONE_BUTTON_TITLE   = "Done",
                MOD_BUTTON_TITLE    = "Modify",

                ITEM_NAME_TITLE     = "Item Name :",
                ITEM_PRICE_TITLE    = "Item Price :",
                ITEM_QUANTITY_TITLE = "Quantity :",
                ITEM_TOTAL_TITLE    = "Item total :",

                ITEM_NAME_FIELD_DEFAULT = "NA",
                ITEM_PRICE_FIELD_DEFAULT = "0.00",
                ITEM_TOTAL_FIELD_DEFAULT = "0.00";


        double eodTotalPrice = 0;

        // Set up FXML and define objects in it
//        FXMLLoader fxmlPOSTLoader = new FXMLLoader(POSTSystem.class.getResource("POST.fxml"));

        // Main scene
        // Create main screen scene nodes
        Label mainTitleLabel = new Label("Welcome to Peter's store!!!");

        Button newSaleButton = new Button("New Sale");
        newSaleButton.setOnAction(e -> primaryStage.setScene());

        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + eodTotalPrice);

        // Create main border pane and set nodes to positions. Title to top, button to center, label to bottom
        BorderPane mainBP = new BorderPane();
        mainBP.setTop(mainTitleLabel);
        mainBP.setCenter(newSaleButton);
        mainBP.setBottom(eodTotalLabel);

        // Create then set mainScene
        Scene mainScene = new Scene(mainBP);

        // Sale scene
        // Create sale screen scene nodes
        // Create top border pane

        // Need to redo for Item objects
        ComboBox<String> itemIDComBox = new ComboBox<String>();

        // Create item title VBox and nodes
        Label itemNameLabel = new Label(ITEM_NAME_TITLE);
        Label itemPriceLabel = new Label(ITEM_PRICE_TITLE);
        Label itemQuantityLabel = new Label(ITEM_QUANTITY_TITLE);
        Label itemTotalLabel = new Label(ITEM_TOTAL_TITLE);
        VBox itemTitlesVB = new VBox(itemNameLabel,itemPriceLabel,itemQuantityLabel,itemTotalLabel);

        // Create item field VBox and nodes
        Label itemNameFieldLabel = new Label(ITEM_NAME_FIELD_DEFAULT);
        Label itemPriceFieldLabel = new Label(ITEM_PRICE_FIELD_DEFAULT);
        TextField itemQuantityFieldTF = new TextField();
        Label itemTotalFieldLabel = new Label(ITEM_TOTAL_FIELD_DEFAULT);
        VBox itemFieldsVB = new VBox(itemNameFieldLabel,itemPriceFieldLabel,itemQuantityFieldTF,itemTotalFieldLabel);

        // Create top center pane item HBox with item VBoxes
        HBox itemHB = new HBox(itemTitlesVB,itemFieldsVB);

        // Create add button to top of sale
        Button addButton = new Button(ADD_BUTTON_TITLE);

        BorderPane saleTopBP = new BorderPane();
        saleTopBP.setTop(itemIDComBox);
        saleTopBP.setCenter(itemHB);
        saleTopBP.setBottom(addButton);

        // Create middle checkout Vbox
        VBox checkoutVB = new VBox();

        // ADD CHECKOUT SECTION


        // Create bottom button Hbox
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        Button modButton = new Button(MOD_BUTTON_TITLE);
        HBox doneOrModHB = new HBox(doneButton,modButton);

        // Create sale border pane
        BorderPane saleBP = new BorderPane();
        saleBP.setTop(saleTopBP);
        saleBP.setCenter(checkoutVB);
        saleBP.setBottom(doneOrModHB);

        // Create then set SaleScene
        Scene saleScene = new Scene();



        // ADD MODIFY SCENE




        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}