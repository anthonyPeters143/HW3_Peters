// Homework 3: Cash Register Program
// Course: CIS357
// Due date: 7/20/22
// Name: Anthony Peters
// Instructor: Il-Hyung Cho
// Program description: HW3_Peters is a driver program that runs a GUI program meant to be used to drive a POS system
// for a store. Includes Sale, SaleItemTracker, and Item classes that are used to hold data and operate with the GUI
// program.

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Anthony Peters
 *
 * Driver class to create a GUI to drive Sale class using Items. GUI will allow the user to choose from a comboBox of
 * all items then enter an amount for the quanitiy then added the Item and amount to the Sale object and recipt
 * Textfield. While adding items to sale, price are added to the subtotal fields. Will prompt for tender and then calc.
 * and return change amount. After/Before a sale can use the dataAlt section to choose between adding, deleting, and
 * modifing the item list.
 */
public class HW3_Peters extends Application {
    /**
     * ArrayList to store Item objects
     */
    private static ArrayList<Item> itemArrayList;

    /**
     * Sale object
     */
    private static Sale sale;

    /**
     * ObservableLists of Items
     */
    private static ObservableList<Item> itemSaleObservableList,itemDeleteObservableList,itemModifyObservableList;

    /**
     * Item ComboBox
     */
    private static ComboBox<Item> itemSaleComboBox,itemDeleteComboBox,itemModifyComboBox;

    /**
     * Scenes
     */
    private Scene addItemScene,deleteItemScene,modifyItemScene,dataAltScene,saleScene,mainScene;

    /**
     * Keys
     */
    private static String FILE_NAME_KEY   = "item.txt";

    /**
     * Listener update fields
     */
    private static String itemAltAddCodeInput,itemAltAddNameInput,itemAltAddPriceInput,
            itemAltModifyCodeInput,itemAltModifyNameInput,itemAltModifyPriceInput,
            itemSaleCodeInput,itemSaleNameInput,
            itemSalePriceInput,itemSaleQuantityInput,subtotalInput,subtotalTaxInput,tenderInput,
            RECEIPT_TEXT = "";
    private double eodTotalPrice = 0;

    /**
     * Formatting vars
     */
    private final Font titleFont = new Font("Lucida Sans Unicode",30),
        bodyFont = new Font("Lucida Sans Unicode",15),
        buttonFont = new Font("Lucida Sans Unicode",15),
        receiptFont = new Font("Lucida Sans Unicode",10);
    private final int sceneSpace = 13;

    /**
     * Format for currency
     */
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,###.00");

    @Override
    public void start(Stage primaryStage) throws IOException {

        String  TYPE_HERE_DEFAULT   = "Type here...",

                MAIN_TITLE          = "POST Register",
                EOD_TOTAL_TITLE     = "Total sale for the day is: $",

                ADD_BUTTON_TITLE    = "Add",
                DONE_BUTTON_TITLE   = "Done",
		        SALE_BUTTON_TITLE   = "Sale",

                ITEM_NAME_TITLE     = "Item Name :",
                ITEM_PRICE_TITLE    = "Item Price :",
                ITEM_QUANTITY_TITLE = "Set Quantity :",
                ITEM_TOTAL_TITLE    = "Item total :",

                SUBTOTAL_TITLE          = "Sale Subtotal :",
                SUBTOTAL_TAX_TITLE      = "Sale Tax Subtotal (6%) :",
                TENDER_TITLE            = "Tendered Amount :",
                CHANGE_TITLE            = "Change :",

                SUBTOTAL_FIELD_DEFAULT = "0.00",
                SUBTOTAL_TAX_FIELD_DEFAULT = "0.00",
                CHANGE_FIELD_DEFAULT = "0.00",

                CHECKOUT_BUTTON_TITLE   = "Checkout",

                DATA_ALT_TITLE          = "Data alteration",
                ITEM_SALE_TITLE         = "Item Sale",

                ADD_ITEM_BUTTON_TITLE   = "Add Item",
                DELETE_ITEM_BUTTON_TITLE= "Delete Item",
                MOD_ITEM_BUTTON_TITLE   = "Modify Item",
                QUIT_BUTTON_TITLE       = "Quit",

                ADD_ITEM_TITLE    = "Add Item",
                DELETE_ITEM_TITLE = "Delete Item",
                MODIFY_ITEM_TITLE = "Modify Item";

        int RECEIPT_WIDTH = 120, RECEIPT_HEIGHT = 100;

        // Initializing itemComBox from file input
        Initializing();

        // Create dataAlt add title/field VBox node
        Label itemAltAddCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemAltAddCodeField = new TextField();
        itemAltAddCodeField.textProperty().addListener((observable, oldValue, newValue) -> itemAltAddCodeInput = newValue);
        HBox itemAltAddCodeHB = new HBox(itemAltAddCodeTitle,itemAltAddCodeField);
        itemAltAddCodeTitle.setFont(bodyFont);
        itemAltAddCodeField.setFont(bodyFont);
        itemAltAddCodeHB.setSpacing(sceneSpace);
        itemAltAddCodeHB.setAlignment(Pos.CENTER);

        Label itemAltAddNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemAltAddNameField = new TextField();
        itemAltAddNameField.textProperty().addListener((observable, oldValue, newValue) -> itemAltAddNameInput = newValue);
        HBox itemAltAddNameHB = new HBox(itemAltAddNameTitle,itemAltAddNameField);
        itemAltAddNameTitle.setFont(bodyFont);
        itemAltAddNameField.setFont(bodyFont);
        itemAltAddNameHB.setSpacing(sceneSpace);
        itemAltAddNameHB.setAlignment(Pos.CENTER);

        Label itemAltAddPriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemAltAddPriceField = new TextField();
        itemAltAddPriceField.textProperty().addListener((observable, oldValue, newValue) -> itemAltAddPriceInput = newValue);
        HBox itemAltAddPriceHB = new HBox(itemAltAddPriceTitle,itemAltAddPriceField);
        itemAltAddPriceTitle.setFont(bodyFont);
        itemAltAddPriceField.setFont(bodyFont);
        itemAltAddPriceHB.setSpacing(sceneSpace);
        itemAltAddPriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltAddTitleFieldVB = new VBox(itemAltAddCodeHB,itemAltAddNameHB,itemAltAddPriceHB);
        itemAltAddTitleFieldVB.setSpacing(sceneSpace);
        itemAltAddTitleFieldVB.setAlignment(Pos.CENTER);


        // Add scene
        // Create add item scene title
        Label addItemAltSceneTitle = new Label(ADD_ITEM_TITLE);
        addItemAltSceneTitle.setFont(titleFont);

        // Create Add/Done buttons and button HBox
        Button addItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        addItemButton.setOnAction(e -> { checkAddItem(itemAltAddCodeInput,itemAltAddNameInput,itemAltAddPriceInput); });
        Button addDoneButton = new Button(DONE_BUTTON_TITLE);
        addDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox addButtonHB = new HBox(addItemButton,addDoneButton);
        addItemButton.setFont(buttonFont);
        addDoneButton.setFont(buttonFont);
        addButtonHB.setSpacing(sceneSpace);
        addButtonHB.setAlignment(Pos.CENTER);

        // Create add item node using add scene title, shared dataAlt VBox, and add button HBox
        VBox addItemVB = new VBox(addItemAltSceneTitle,itemAltAddTitleFieldVB,addButtonHB);
        addItemVB.setSpacing(sceneSpace);
        addItemVB.setAlignment(Pos.CENTER);

        // Create scene
        addItemScene = new Scene(addItemVB);


        // Delete scene
        // Create delete item scene title
        Label deleteItemAltSceneTitle = new Label(DELETE_ITEM_TITLE);
        deleteItemAltSceneTitle.setFont(titleFont);

        // Create dataAlt delete title/field VBox node
        Label itemAltDeleteCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemAltDeleteCodeField = new TextField();
        HBox itemAltDeleteCodeHB = new HBox(itemAltDeleteCodeTitle,itemAltDeleteCodeField);
        itemAltDeleteCodeTitle.setFont(bodyFont);
        itemAltDeleteCodeField.setFont(bodyFont);
        itemAltDeleteCodeHB.setSpacing(sceneSpace);
        itemAltDeleteCodeHB.setAlignment(Pos.CENTER);

        Label itemAltDeleteNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemAltDeleteNameField = new TextField();
        HBox itemAltDeleteNameHB = new HBox(itemAltDeleteNameTitle,itemAltDeleteNameField);
        itemAltDeleteNameTitle.setFont(bodyFont);
        itemAltDeleteNameField.setFont(bodyFont);
        itemAltDeleteNameHB.setSpacing(sceneSpace);
        itemAltDeleteNameHB.setAlignment(Pos.CENTER);

        Label itemAltDeletePriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemAltDeletePriceField = new TextField();
        HBox itemAltDeletePriceHB = new HBox(itemAltDeletePriceTitle,itemAltDeletePriceField);
        itemAltDeletePriceTitle.setFont(bodyFont);
        itemAltDeletePriceField.setFont(bodyFont);
        itemAltDeletePriceHB.setSpacing(sceneSpace);
        itemAltDeletePriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltDeleteTitleFieldVB = new VBox(itemAltDeleteCodeHB,itemAltDeleteNameHB,itemAltDeletePriceHB);
        itemAltDeleteTitleFieldVB.setSpacing(sceneSpace);
        itemAltDeleteTitleFieldVB.setAlignment(Pos.CENTER);

        // Create update event for delete field nodes
        itemDeleteComboBox.setOnAction(e -> {
            itemAltDeleteCodeField.setText(itemDeleteComboBox.getValue().getItemCode());
            itemAltDeleteNameField.setText(itemDeleteComboBox.getValue().getItemName());
            itemAltDeletePriceField.setText(currencyFormat.format(itemDeleteComboBox.getValue().getItemPrice()));
        });
        itemDeleteComboBox.getEditor().setFont(bodyFont);

        // Create Delete/Done buttons and button HBox
        Button deleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        deleteItemButton.setOnAction(e -> checkDeleteItem(itemDeleteComboBox.getValue().getItemCode()));
        Button deleteDoneButton = new Button(DONE_BUTTON_TITLE);
        deleteDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox deleteButtonHB = new HBox(deleteItemButton,deleteDoneButton);
        deleteItemButton.setFont(buttonFont);
        deleteDoneButton.setFont(buttonFont);
        deleteButtonHB.setSpacing(sceneSpace);
        deleteButtonHB.setAlignment(Pos.CENTER);

        // Create delete item node using delete scene title, deleteComboBox, shared dataAlt VBox, and delete button HBox
        VBox deleteItemVB = new VBox(deleteItemAltSceneTitle,itemDeleteComboBox,itemAltDeleteTitleFieldVB,deleteButtonHB);
        deleteItemVB.setSpacing(sceneSpace);
        deleteItemVB.setAlignment(Pos.CENTER);

        // Create scene
        deleteItemScene = new Scene(deleteItemVB);

        // Modify Scene
        // Create dataAlt modify title/field VBox node
        Label itemAltModifyCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemAltModifyCodeField = new TextField();
        itemAltModifyCodeField.textProperty().addListener((observable, oldValue, newValue) -> itemAltModifyCodeInput = newValue);
        HBox itemAltModifyCodeHB = new HBox(itemAltModifyCodeTitle,itemAltModifyCodeField);
        itemAltModifyCodeTitle.setFont(bodyFont);
        itemAltModifyCodeField.setFont(bodyFont);
        itemAltModifyCodeHB.setSpacing(sceneSpace);
        itemAltModifyCodeHB.setAlignment(Pos.CENTER);

        Label itemAltModifyNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemAltModifyNameField = new TextField();
        itemAltModifyNameField.textProperty().addListener((observable, oldValue, newValue) -> itemAltModifyNameInput = newValue);
        HBox itemAltModifyNameHB = new HBox(itemAltModifyNameTitle,itemAltModifyNameField);
        itemAltModifyNameTitle.setFont(bodyFont);
        itemAltModifyNameField.setFont(bodyFont);
        itemAltModifyNameHB.setSpacing(sceneSpace);
        itemAltModifyNameHB.setAlignment(Pos.CENTER);

        Label itemAltModifyPriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemAltModifyPriceField = new TextField();
        itemAltModifyPriceField.textProperty().addListener((observable, oldValue, newValue) -> itemAltModifyPriceInput = newValue);
        HBox itemAltModifyPriceHB = new HBox(itemAltModifyPriceTitle,itemAltModifyPriceField);
        itemAltModifyPriceTitle.setFont(bodyFont);
        itemAltModifyPriceField.setFont(bodyFont);
        itemAltModifyPriceHB.setSpacing(sceneSpace);
        itemAltModifyPriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltModifyTitleFieldVB = new VBox(itemAltModifyCodeHB,itemAltModifyNameHB,itemAltModifyPriceHB);
        itemAltModifyTitleFieldVB.setSpacing(sceneSpace);
        itemAltModifyTitleFieldVB.setAlignment(Pos.CENTER);

        // Modify scene
        // Create modify item scene title
        Label modifyItemAltSceneTitle = new Label(MODIFY_ITEM_TITLE);
        modifyItemAltSceneTitle.setFont(titleFont);

        // Select code from itemModifyComboBox

        // Create update event for modify field nodes
        itemModifyComboBox.setOnAction(e -> {
            itemAltModifyCodeField.setText(itemModifyComboBox.getValue().getItemCode());
            itemAltModifyNameField.setText(itemModifyComboBox.getValue().getItemName());
            itemAltModifyPriceField.setText(currencyFormat.format(itemModifyComboBox.getValue().getItemPrice()));
        });
        itemModifyComboBox.getEditor().setFont(bodyFont);

        // Create Modify/Done buttons and button HBox
        Button modifyItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
	    modifyItemButton.setOnAction(e -> checkModifyItem(itemAltModifyCodeInput,itemAltModifyNameInput,itemAltModifyPriceInput));
        Button modifyDoneButton = new Button(DONE_BUTTON_TITLE);
        modifyDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox modifyButtonHB = new HBox(modifyItemButton,modifyDoneButton);
        modifyItemButton.setFont(buttonFont);
        modifyDoneButton.setFont(buttonFont);
        modifyButtonHB.setSpacing(sceneSpace);
        modifyButtonHB.setAlignment(Pos.CENTER);

        // Create modify item node using modify scene title, modifyComboBox, shared dataAlt VBox, and modify button HBox
        VBox modifyItemVB = new VBox(modifyItemAltSceneTitle,itemModifyComboBox,itemAltModifyTitleFieldVB,modifyButtonHB);
        modifyItemVB.setSpacing(sceneSpace);
        modifyItemVB.setAlignment(Pos.CENTER);

        // Create scene
        modifyItemScene = new Scene(modifyItemVB);

        // dataAlt scene
        // Create dataAlt title node
        Label dataAltLabel = new Label(DATA_ALT_TITLE);
        dataAltLabel.setFont(titleFont);

        // Create dataAlt button nodes
        Button dataAltAddItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        dataAltAddItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltAddCodeField.setText(TYPE_HERE_DEFAULT);
            itemAltAddNameField.setText(TYPE_HERE_DEFAULT);
            itemAltAddPriceField.setText(TYPE_HERE_DEFAULT);
            primaryStage.setScene(addItemScene);
        });
        Button dataAltDeleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        dataAltDeleteItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltDeleteCodeField.setText("");
            itemAltDeleteNameField.setText("");
            itemAltDeletePriceField.setText("");
            primaryStage.setScene(deleteItemScene);
        });
        Button dataAltModItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
        dataAltModItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltModifyCodeField.setText("");
            itemAltModifyNameField.setText("");
            itemAltModifyPriceField.setText("");
            primaryStage.setScene(modifyItemScene);
        });
        HBox dataAltButtonHB = new HBox(dataAltAddItemButton,dataAltDeleteItemButton,dataAltModItemButton);
        dataAltAddItemButton.setFont(buttonFont);
        dataAltDeleteItemButton.setFont(buttonFont);
        dataAltModItemButton.setFont(buttonFont);
        dataAltButtonHB.setSpacing(sceneSpace);
        dataAltButtonHB.setAlignment(Pos.CENTER);


        // Create Done/Quit button node
	    Button saleAltButton = new Button(SALE_BUTTON_TITLE);
	    saleAltButton.setOnAction(e -> primaryStage.setScene(saleScene));
        Button quitAltButton = new Button(QUIT_BUTTON_TITLE);
	    quitAltButton.setOnAction(e -> System.exit(0));
	    HBox dataAltDoneQuitButtonHB = new HBox(saleAltButton,quitAltButton);
        saleAltButton.setFont(buttonFont);
        quitAltButton.setFont(buttonFont);
        dataAltDoneQuitButtonHB.setSpacing(sceneSpace);
        dataAltDoneQuitButtonHB.setAlignment(Pos.CENTER);


        // Create dataAlt node
        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,dataAltDoneQuitButtonHB);
        dataAltVB.setSpacing(sceneSpace);
        dataAltVB.setAlignment(Pos.CENTER);

        // Create scene
        dataAltScene = new Scene(dataAltVB);


        // sale scene
        // Create sale title
        Label itemSaleTitle = new Label(ITEM_SALE_TITLE);
        itemSaleTitle.setFont(titleFont);

        // Select item from itemSaleComboBox

        // Create item title/field HBox nodes
        Label itemSaleCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemSaleCodeField = new TextField();
        itemSaleCodeField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleCodeInput = newValue);
        HBox itemSaleCodeHB = new HBox(itemSaleCodeTitle,itemSaleCodeField);
        itemSaleCodeTitle.setFont(bodyFont);
        itemSaleCodeField.setFont(bodyFont);
        itemSaleCodeHB.setSpacing(sceneSpace);
        itemSaleCodeHB.setAlignment(Pos.CENTER);

        Label itemSaleNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemSaleNameField = new TextField();
        itemSaleNameField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleNameInput = newValue);
        HBox itemSaleNameHB = new HBox(itemSaleNameTitle,itemSaleNameField);
        itemSaleNameTitle.setFont(bodyFont);
        itemSaleNameField.setFont(bodyFont);
        itemSaleNameHB.setSpacing(sceneSpace);
        itemSaleNameHB.setAlignment(Pos.CENTER);

        Label itemSalePriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemSalePriceField = new TextField();
        itemSalePriceField.textProperty().addListener((observable, oldValue, newValue) -> itemSalePriceInput = newValue);
        HBox itemSalePriceHB = new HBox(itemSalePriceTitle,itemSalePriceField);
        itemSalePriceTitle.setFont(bodyFont);
        itemSalePriceField.setFont(bodyFont);
        itemSalePriceHB.setSpacing(sceneSpace);
        itemSalePriceHB.setAlignment(Pos.CENTER);

        Label itemSaleQuantityTitle = new Label(ITEM_QUANTITY_TITLE);
        TextField itemSaleQuantityField = new TextField();
        itemSaleQuantityField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleQuantityInput = newValue);
        HBox itemSaleQuantityHB = new HBox(itemSaleQuantityTitle,itemSaleQuantityField);
        itemSaleQuantityTitle.setFont(bodyFont);
        itemSaleQuantityField.setFont(bodyFont);
        itemSaleQuantityHB.setSpacing(sceneSpace);
        itemSaleQuantityHB.setAlignment(Pos.CENTER);


        // Create item TF VBox
        VBox itemSaleTitleFieldVB = new VBox(itemSaleCodeHB,itemSaleNameHB,itemSalePriceHB,itemSaleQuantityHB);
        itemSaleTitleFieldVB.setSpacing(sceneSpace);
        itemSaleTitleFieldVB.setAlignment(Pos.CENTER);


        // Create update event for sale field nodes
        itemSaleComboBox.setOnAction(e -> {
            itemSaleCodeField.setText(itemSaleComboBox.getValue().getItemCode());
            itemSaleNameField.setText(itemSaleComboBox.getValue().getItemName());
            itemSalePriceField.setText(currencyFormat.format(itemSaleComboBox.getValue().getItemPrice()));
        });
        itemSaleComboBox.getEditor().setFont(bodyFont);

        // Create add button to top of sale
        Button addButton = new Button(ADD_BUTTON_TITLE);
        addButton.setOnAction(event -> addSaleItem(itemSaleCodeInput,itemSaleQuantityInput));
        addButton.setFont(buttonFont);

        // Create sale node
        VBox saleTopVB = new VBox(itemSaleTitle,itemSaleComboBox,itemSaleTitleFieldVB,addButton);
        saleTopVB.setSpacing(sceneSpace);
        saleTopVB.setAlignment(Pos.CENTER);

        // Create middle checkout Vbox
        // Create receipt Label node
        TextField receiptTextField = new TextField(RECEIPT_TEXT);
        receiptTextField.setMinSize(RECEIPT_WIDTH,RECEIPT_HEIGHT);
        receiptTextField.setFont(receiptFont);

        // Create subtotal title/field HBox nodes
        Label subtotalTitle = new Label(SUBTOTAL_TITLE);
        TextField subtotalField = new TextField(SUBTOTAL_FIELD_DEFAULT);
        subtotalField.textProperty().addListener((observable, oldValue, newValue) -> subtotalInput = newValue);
        HBox subtotalHB = new HBox(subtotalTitle,subtotalField);
        subtotalTitle.setFont(bodyFont);
        subtotalField.setFont(bodyFont);
        subtotalHB.setSpacing(sceneSpace);
        subtotalHB.setAlignment(Pos.CENTER);

        Label subtotalTaxTitle = new Label(SUBTOTAL_TAX_TITLE);
        TextField subtotalTaxField = new TextField(SUBTOTAL_TAX_FIELD_DEFAULT);
        subtotalTaxField.textProperty().addListener((observable, oldValue, newValue) -> subtotalTaxInput = newValue);
        HBox subtotalTaxHB = new HBox(subtotalTaxTitle,subtotalTaxField);
        subtotalTaxTitle.setFont(bodyFont);
        subtotalTaxField.setFont(bodyFont);
        subtotalTaxHB.setSpacing(sceneSpace);
        subtotalTaxHB.setAlignment(Pos.CENTER);

        Label tenderTitle = new Label(TENDER_TITLE);
        TextField tenderTF = new TextField();
        tenderTF.textProperty().addListener((observable, oldValue, newValue) -> tenderInput = newValue);
        HBox tenderHB = new HBox(tenderTitle,tenderTF);
        tenderTitle.setFont(bodyFont);
        tenderTF.setFont(bodyFont);
        tenderHB.setSpacing(sceneSpace);
        tenderHB.setAlignment(Pos.CENTER);

        Label changeTitle = new Label(CHANGE_TITLE);
        TextField changeField = new TextField(CHANGE_FIELD_DEFAULT);
	    HBox changeHB = new HBox(changeTitle,changeField);
        changeTitle.setFont(bodyFont);
        changeField.setFont(bodyFont);
        changeHB.setSpacing(sceneSpace);
        changeHB.setAlignment(Pos.CENTER);

        // Create checkout button node
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);
        checkoutButton.setFont(buttonFont);

        // Create subtotal VBox
	    VBox subtotalVB = new VBox(subtotalHB,subtotalTaxHB,tenderHB,checkoutButton,changeHB);
        subtotalVB.setSpacing(sceneSpace);
        subtotalVB.setAlignment(Pos.CENTER);

        // Create checkout VBox
        VBox checkoutVB = new VBox(receiptTextField,subtotalVB);
        checkoutVB.setSpacing(sceneSpace);
        checkoutVB.setAlignment(Pos.CENTER);

        // Create bottom button HBox
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        doneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        doneButton.setFont(buttonFont);

        // Create sale border pane
        VBox saleVB = new VBox(saleTopVB,checkoutVB,doneButton);
        saleVB.setSpacing(sceneSpace);
        saleVB.setAlignment(Pos.CENTER);

        // Create then set SaleScene
        saleScene = new Scene(saleVB);

        // Main scene
        // Create main screen scene nodes
        Label mainTitleLabel = new Label("Welcome to Peter's store!!!");
        mainTitleLabel.setFont(titleFont);

        Button newSaleButton = new Button("New Sale");
        newSaleButton.setOnAction(e -> primaryStage.setScene(saleScene));
        newSaleButton.setFont(buttonFont);

        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + currencyFormat.format(eodTotalPrice));
        eodTotalLabel.setFont(bodyFont);

        // Create main node
	    VBox mainVB = new VBox(mainTitleLabel,newSaleButton,eodTotalLabel);
        mainVB.setSpacing(sceneSpace);
        mainVB.setAlignment(Pos.CENTER);

        // Create then set mainScene
        mainScene = new Scene(mainVB);

        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Start GUI
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Initialize itemObservableList, ComboBoxes, Sale objects, arrayList and import items from fileRef to arrayList
     */
    private static void Initializing() {
        {
            File fileRef;
            String fileLine;
            String[] splitFileImport;
            Scanner fileScanner;

            // Initialize arrayList of Items
            itemArrayList = new ArrayList<>();

            // Initialize sale object
            sale = new Sale();

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
        itemSaleObservableList = FXCollections.observableList(itemArrayList);
        itemDeleteObservableList = FXCollections.observableList(itemArrayList);
        itemModifyObservableList = FXCollections.observableList(itemArrayList);

        // initialize item ComboBoxes
        itemSaleComboBox = new ComboBox<>(itemSaleObservableList);
        itemDeleteComboBox = new ComboBox<>(itemDeleteObservableList);
        itemModifyComboBox = new ComboBox<>(itemModifyObservableList);
    }

    /**
     * Handler method for adding item and quantity to Sale object. If inputs are valid then return true amd add item to
     * Sale object, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param quantityInput String, quantity of item
     */
    private static boolean addSaleItem(String codeInput, String quantityInput) {
        try {
            // Convert priceInput into an Integer
            int quantityInputInt = Integer.parseInt(quantityInput);

            if (codeInput.matches("[AB]\\d\\d\\d") && (quantityInputInt > 0 && quantityInputInt <= 100)) {
                // Find item
                Item inputItem = findItemFromCode(codeInput);

                if (inputItem == null) {
                    // Code input invalid
                    return false;

                } else {

                    // Calc price
                    BigDecimal itemPriceBD = BigDecimal.valueOf(inputItem.getItemPrice());
                    BigDecimal itemQuantityBD = BigDecimal.valueOf(quantityInputInt);

                    // Add Item price to Sale object
                    sale.addSaleItem(inputItem,quantityInputInt,(itemPriceBD.multiply(itemQuantityBD).doubleValue()));

                    // Add text to RECEIPT_TEXT to display in sale scene within receipt text field node
                }
            }
            } catch (Exception e){
                // Input is invalid
                return false;
            }


            // REMOVE
            return true;
    }

    /**
     * Handler method for adding item object to itemArrayList. If inputs are valid then return true and add item object
     * to itemArrayList, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
	private static boolean checkAddItem(String codeInput, String nameInput, String priceInput) {
		// Check if input is valid
		try {
			if ((codeInput.matches("[AB]\\d\\d\\d")) && (Double.parseDouble(priceInput) > 0)) {
				// input valid
				return true;

				// Add item to itemArrayList

			} else {
				// input invalid
				return false;
			}

		}
		catch (Exception e) {
			// input invalid
			return false;
		}
	}

    /**
     * Handler method for deleting item object to itemArrayList. If inputs are valid then return true and remove item
     * object from itemArrayList, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     */
	private static boolean checkDeleteItem(String codeInput) {
		// Check if input is valid
		try {
			if ((codeInput.matches("[AB]\\d\\d\\d"))) {
				// input valid
				return true;

				// Remove item from itemArrayList
				
			} else {
				// input invalid
				return false;
			}

		}
		catch (Exception e) {
			// input invalid
			return false;
		}
	}

    /**
     * Handler method for modifying item object to itemArrayList. If inputs are valid then return true and remove old
     * item object from itemArrayList and add new item object, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
	private static boolean checkModifyItem(String codeInput, String nameInput, String priceInput) {
        // Check if input is valid
        try {
            if ((codeInput.matches("[AB]\\d\\d\\d")) && (Double.parseDouble(priceInput) > 0)) {
                // input valid
                return true;

                // Delete old item from itemArrayList
                // Add new item to itemArrayList

            } else {
                // input invalid
                return false;
            }

        }
        catch (Exception e) {
            // input invalid
            return false;
        }

	}

    /**
     * Searches itemArrayList from Item with matching itemCode value, then returns Item object address. If not found
     * then will return null.
     *
     * @return Item, Item address
     * @param itemCode String, Item code to search against itemArrayList
     */
    private static Item findItemFromCode(String itemCode){
        for (Item item : itemArrayList) {
            if (Objects.equals(item.getItemCode(), itemCode)) {
                // Item found, return item object
                return item;
            }
        }
        // If not found return null
        return null;
    }

}
