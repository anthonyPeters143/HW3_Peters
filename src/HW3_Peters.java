import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
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

public class HW3_Peters extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //
        String MAIN_TITLE           = "POST Register",
                EOD_TOTAL_TITLE     = "Total sale for the day is: $",

                ADD_BUTTON_TITLE    = "Add",
                DONE_BUTTON_TITLE   = "Done",

                ITEM_NAME_TITLE     = "Item Name :",
                ITEM_PRICE_TITLE    = "Item Price :",
                ITEM_QUANTITY_TITLE = "Quantity :",
                ITEM_TOTAL_TITLE    = "Item total :",

                ITEM_NAME_FIELD_DEFAULT = "NA",
                ITEM_PRICE_FIELD_DEFAULT = "0.00",
                ITEM_TOTAL_FIELD_DEFAULT = "0.00",

                RECEIPT_TEXTFIELD       = "",

                SUBTOTAL_TITLE          = "Sale Subtotal :",
                SUBTOTAL_TAX_TITLE      = "Sale Tax Subtotal (6%) :",
                TENDER_TITLE            = "Tendered Amount :",
                CHANGE_TITLE            = "Change :",

                SUBTOTAL_FIELD_DEFAULT = "0.00",
                SUBTOTAL_TAX_FIELD_DEFAULT = "0.00",
                CHANGE_FIELD_DEFAULT = "0.00",

                CHECKOUT_BUTTON_TITLE   = "Checkout",

                DATA_ALT_TITLE          = "Data alteration",

                ADD_ITEM_BUTTON_TITLE   = "Add Item",
                DELETE_ITEM_BUTTON_TITLE= "Delete Item",
                MOD_ITEM_BUTTON_TITLE   = "Modify Item",
                QUIT_BUTTON_TITLE       = "Quit";


        double eodTotalPrice = 0;

        // Set up FXML and define objects in it
        FXMLLoader fxmlPOSTLoader = new FXMLLoader(HW3_Peters.class.getResource("POST.fxml"));

        // dataAlt scene

        // Create dataAlt title node
        Label dataAltLabel = new Label(DATA_ALT_TITLE);

        // Create dataAlt button nodes
        Button addItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        Button deleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        Button modItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
        HBox dataAltButtonHB = new HBox(addItemButton,deleteItemButton,modItemButton);

        // Create done button node
        Button quitButton = new Button(QUIT_BUTTON_TITLE);

        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,quitButton);

        // Create scene
        Scene dataAltScene = new Scene(dataAltVB);

        // sale scene

        // Create top add border pane

        // Create ComboBox node
        // Need to redo for Item objects
        ComboBox<Item> itemIDComBox = new ComboBox<>();

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

        // Create receipt Label node
        Label receiptLabel = new Label(RECEIPT_TEXTFIELD);

        // Create subtotal title VBox and nodes
        Label subtotalLabel = new Label(SUBTOTAL_TITLE);
        Label subtotalTaxLabel = new Label(SUBTOTAL_TAX_TITLE);
        Label tenderLabel = new Label(TENDER_TITLE);
        Label changeLabel = new Label(CHANGE_TITLE);
        VBox subtotalTitleVB = new VBox(subtotalLabel,subtotalTaxLabel,tenderLabel,changeLabel);

        // Create subtotal field VBox and nodes
        Label subtotalFieldLabel = new Label(SUBTOTAL_FIELD_DEFAULT);
        Label subtotalTaxFieldLabel = new Label(SUBTOTAL_TAX_FIELD_DEFAULT);
        TextField tenderTF = new TextField();
        Label changeFieldLabel = new Label(CHANGE_FIELD_DEFAULT);
        VBox subtotalFieldVB = new VBox(subtotalFieldLabel,subtotalTaxFieldLabel,tenderTF,changeFieldLabel);

        // Create checkout button node
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);

        // Create subtotal VBox
        VBox checkoutVB = new VBox(receiptLabel,subtotalTitleVB,subtotalFieldVB,checkoutButton);

        // Create bottom button Hbox
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        doneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));

        // Create sale border pane
        BorderPane saleBP = new BorderPane();
        saleBP.setTop(saleTopBP);
        saleBP.setCenter(checkoutVB);
        saleBP.setBottom(doneButton);

        // Create then set SaleScene
        Scene saleScene = new Scene(saleBP);

        // Main scene
        // Create main screen scene nodes
        Label mainTitleLabel = new Label("Welcome to Peter's store!!!");

        Button newSaleButton = new Button("New Sale");
        newSaleButton.setOnAction(e -> primaryStage.setScene(saleScene));

        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + eodTotalPrice);

        // Create main border pane and set nodes to positions. Title to top, button to center, label to bottom
        BorderPane mainBP = new BorderPane();
        mainBP.setTop(mainTitleLabel);
        mainBP.setCenter(newSaleButton);
        mainBP.setBottom(eodTotalLabel);

        // Create then set mainScene
        Scene mainScene = new Scene(mainBP);

        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}