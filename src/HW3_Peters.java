//package HW3_Peters;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HW3_Peters extends Application {
    // Item array list
    static ArrayList<Item> itemArrayList;

    /// Item observable list
    static ObservableList<Item> itemObservableList;

    // Item ComboBox
    static ComboBox<Item> itemComBox;

    // Scenes
    Scene addItemScene,deleteItemScene,modifyItemScene,dataAltScene,saleScene,mainScene;

    // Keys
    static String   FILE_NAME_KEY   = "item.txt",
                    MAIN_STYLE      = "MainStyle.css";

    @Override
    public void start(Stage primaryStage) throws IOException {

        String  TYPE_HERE_DEFAULT   = "Type here...",

                MAIN_TITLE          = "POST Register",
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
                QUIT_BUTTON_TITLE       = "Quit",

                ADD_ITEM_TITLE    = "Add Item",
                DELETE_ITEM_TITLE = "Delete Item",
                MODIFY_ITEM_TITLE = "Modify Item";
        ;

        double eodTotalPrice = 0;

        // Initializing itemComBox from file input
        Initializing();

        // Create itemComBox node
        TilePane itemSaleComBoxTilePane = new TilePane(itemComBox);

        TilePane itemAltComBoxTilePane = new TilePane(itemComBox);

        // Add scene
        // Create add item scene title
        Label addItemAltSceneTitle = new Label(ADD_ITEM_TITLE);

        // Create item title/field HBox nodes
        Label itemAltCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemAltCodeField = new TextField();
        HBox itemAltCodeHB = new HBox(itemAltCodeTitle,itemAltCodeField);

        Label itemAltNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemAltNameField = new TextField();
        HBox itemAltNameHB = new HBox(itemAltNameTitle,itemAltNameField);

        Label itemAltPriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemAltPriceField = new TextField();
        HBox itemAltPriceHB = new HBox(itemAltPriceTitle,itemAltPriceField);

        // Create item TF VBox
        VBox itemAltTitleFieldVB = new VBox(itemAltCodeHB,itemAltNameHB,itemAltPriceHB);

        // Create Add/Done buttons and button HBox
        Button addItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        // ADD ITEM HANDLER
        Button addDoneButton = new Button(DONE_BUTTON_TITLE);
        addDoneButton.setOnAction(event -> primaryStage.setScene(dataAltScene));
        HBox addButtonHB = new HBox(addItemButton,addDoneButton);

        // Create add item node
        VBox addItemVB = new VBox(addItemAltSceneTitle,itemAltTitleFieldVB,addButtonHB);

        // Style nodes

        // Create scene
        addItemScene = new Scene(addItemVB);


        // Delete scene
        // Create delete item scene title
        Label deleteItemAltSceneTitle = new Label(DELETE_ITEM_TITLE);

        // Select code from itemComBoxTilePane

        // Preview item from comboBox in title/field HBox nodes
        itemAltCodeTitle = new Label(ITEM_NAME_TITLE);
        itemAltCodeField = new TextField();
        itemAltCodeHB = new HBox(itemAltCodeTitle,itemAltCodeField);

        itemAltNameTitle = new Label(ITEM_PRICE_TITLE);
        itemAltNameField = new TextField();
        itemAltNameHB = new HBox(itemAltNameTitle,itemAltNameField);

        itemAltPriceTitle = new Label(ITEM_TOTAL_TITLE);
        itemAltPriceField = new TextField();
        itemAltPriceHB = new HBox(itemAltPriceTitle,itemAltPriceField);

        // Create item TF VBox
        itemAltTitleFieldVB = new VBox(itemAltCodeHB,itemAltNameHB,itemAltPriceHB);

        // Create Delete/Done buttons and button HBox
        Button deleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        // DELETE ITEM HANDLER
        Button deleteDoneButton = new Button(DONE_BUTTON_TITLE);
        deleteDoneButton.setOnAction(event -> primaryStage.setScene(dataAltScene));
        HBox deleteButtonHB = new HBox(deleteItemButton,deleteDoneButton);

        // Create delete item node
        VBox deleteItemVB = new VBox(deleteItemAltSceneTitle,itemAltComBoxTilePane,itemAltTitleFieldVB,deleteButtonHB);

        // Style nodes

        // Create scene
        deleteItemScene = new Scene(deleteItemVB);


        // Modify scene
        // Create modify item scene title
        Label modifyItemAltSceneTitle = new Label(MODIFY_ITEM_TITLE);

        // Select code from itemComBoxTilePane

        // Preview/modify item fields
        itemAltCodeTitle = new Label(ITEM_NAME_TITLE);
        itemAltCodeField = new TextField();
        itemAltCodeHB = new HBox(itemAltCodeTitle,itemAltCodeField);

        itemAltNameTitle = new Label(ITEM_PRICE_TITLE);
        itemAltNameField = new TextField();
        itemAltNameHB = new HBox(itemAltNameTitle,itemAltNameField);

        itemAltPriceTitle = new Label(ITEM_TOTAL_TITLE);
        itemAltPriceField = new TextField();
        itemAltPriceHB = new HBox(itemAltPriceTitle,itemAltPriceField);

        // Create item TF VBox
        itemAltTitleFieldVB = new VBox(itemAltCodeHB,itemAltNameHB,itemAltPriceHB);

        // Create Modify/Done buttons and button HBox
        Button modifyItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
        // MODIFY ITEM HANDLER
        Button modifyDoneButton = new Button(DONE_BUTTON_TITLE);
        modifyDoneButton.setOnAction(event -> primaryStage.setScene(dataAltScene));
        HBox modifyButtonHB = new HBox(modifyItemButton,modifyDoneButton);

        // Create modify item node
        VBox modifyItemVB = new VBox(modifyItemAltSceneTitle,itemAltComBoxTilePane,itemAltTitleFieldVB,modifyButtonHB);

        // Style nodes

        // Create scene
        modifyItemScene = new Scene(modifyItemVB);

        // dataAlt scene
        // Create dataAlt title node
        Label dataAltLabel = new Label(DATA_ALT_TITLE);

        // Create dataAlt button nodes
        Button dataAltAddItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        dataAltAddItemButton.setOnAction(e -> primaryStage.setScene(addItemScene));
        Button dataAltDeleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        dataAltDeleteItemButton.setOnAction(event -> primaryStage.setScene(deleteItemScene));
        Button dataAltModItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
        dataAltModItemButton.setOnAction(event -> primaryStage.setScene(modifyItemScene));
        HBox dataAltButtonHB = new HBox(dataAltAddItemButton,dataAltDeleteItemButton,dataAltModItemButton);

        // Create done button node
        Button quitButton = new Button(QUIT_BUTTON_TITLE);

        // Create dataAlt node
        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,quitButton);

        // Style nodes

        // Create scene
        dataAltScene = new Scene(dataAltVB);


        // sale scene

        // Select item from itemComBoxTilePane

        // Create item title HBox nodes
        Label itemSaleCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemSaleCodeField = new TextField();
        HBox itemSaleCodeHB = new HBox(itemSaleCodeTitle,itemSaleCodeField);

        Label itemSaleNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemSaleNameField = new TextField();
        HBox itemSaleNameHB = new HBox(itemSaleNameTitle,itemSaleNameField);

        Label itemSalePriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemSalePriceField = new TextField();
        HBox itemSalePriceHB = new HBox(itemSalePriceTitle,itemSalePriceField);

        Label itemSaleQuantityTitle = new Label(ITEM_QUANTITY_TITLE);
        TextField itemSaleQuantityField = new TextField();
        HBox itemSaleQuantityHB = new HBox(itemSaleQuantityTitle,itemSaleQuantityField);

        // Create item TF VBox
        VBox itemSaleTitleFieldVB = new VBox(itemSaleCodeHB,itemSaleNameHB,itemSalePriceHB,itemSaleQuantityHB);

        // Create update event for sale field nodes
        itemComBox.setOnAction(event -> {
            itemSaleCodeField.setText(itemComBox.getValue().getItemCode());
            itemSaleNameField.setText(itemComBox.getValue().getItemName());
            itemSalePriceField.setText(itemComBox.getValue().getItemPriceString());
        });

        // Create add button to top of sale
        Button addButton = new Button(ADD_BUTTON_TITLE);
        // ADD HANDLER

        // Create sale node
        BorderPane saleTopBP = new BorderPane();
        saleTopBP.setTop(itemSaleComBoxTilePane);
        saleTopBP.setCenter(itemSaleTitleFieldVB);
        saleTopBP.setBottom(addButton);

        // Create middle checkout Vbox
        // Create receipt Label node
        TextField receiptTextField = new TextField(RECEIPT_TEXTFIELD);

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

        // Create subtotal HBox
        HBox subtotalHB = new HBox(subtotalTitleVB,subtotalFieldVB);

        // Create checkout button node
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);

        // Create checkoutVBox
        VBox checkoutVB = new VBox(receiptTextField,subtotalHB,checkoutButton);

        // Create bottom button Hbox
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        doneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));

        // Create sale border pane
        BorderPane saleBP = new BorderPane();
        saleBP.setTop(saleTopBP);
        saleBP.setCenter(checkoutVB);
        saleBP.setBottom(doneButton);

        // Style nodes

        // Create then set SaleScene
        saleScene = new Scene(saleBP);


        // Main scene
        // Create main screen scene nodes
        Label mainTitleLabel = new Label("Welcome to Peter's store!!!");

        Button newSaleButton = new Button("New Sale");
        newSaleButton.setOnAction(e -> primaryStage.setScene(saleScene));

        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + eodTotalPrice);

        // Create main node
        BorderPane mainBP = new BorderPane();
        mainBP.setTop(mainTitleLabel);
        mainBP.setCenter(newSaleButton);
        mainBP.setBottom(eodTotalLabel);

        // Style nodes

        // Create then set mainScene
        mainScene = new Scene(mainBP);


        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void updateItemList() {
        // Update itemObservableList and itemComBox
    }

    private static void Initializing() {
        {
            itemArrayList = new ArrayList<>();
            File fileRef;
            String fileLine;
            String[] splitFileImport;
            Scanner fileScanner;

            // input from item.txt
            try {
                // Create file
                fileRef = new File(FILE_NAME_KEY);

                // Set up scanner
                fileScanner = new Scanner(fileRef);

                // Scan in input and split by new line commas
                fileLine = fileScanner.nextLine();
                splitFileImport = fileLine.split(Pattern.quote(","));

                // For loop to index and input file data into itemArray
                // Input code, name, price into Items objects in the array, advance index by 1, split index by 4
                for (int index = 0, splitIndex = 0; index < 10; index+=1, splitIndex+=3) {


                    // Create by index in array and imported from split input array
                    itemArrayList.add(new Item(splitFileImport[splitIndex],
                            String.valueOf(splitFileImport[splitIndex+1]),
                            Double.parseDouble(splitFileImport[splitIndex+2])));
                }

                // Close scanner
                fileScanner.close();

            } catch (Exception exception) {
                // File input failed
                exception.printStackTrace();
            }
        }
        // initialize itemObservableList
        itemObservableList = FXCollections.observableList(itemArrayList);

        // initialize itemComBox
        itemComBox = new ComboBox<>(itemObservableList);
    }
}