
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HW3_Peters extends Application {
    // Item array list
    static ArrayList<Item> itemArrayList;

    // Sale object
    static Sale sale;

    // Item observable list
    // NEW OBSERVABLE LIST OBJECTS FOR DIFFERENT SCENES
    static ObservableList<Item> itemSaleObservableList,itemDeleteObservableList,itemModifyObservableList;

    // Item ComboBox
    static ComboBox<Item> itemSaleComboBox,itemDeleteComboBox,itemModifyComboBox;

    // Scenes
    Scene addItemScene,deleteItemScene,modifyItemScene,dataAltScene,saleScene,mainScene;

    // Keys
    static String   FILE_NAME_KEY   = "item.txt",
                    MAIN_STYLE      = "MainStyle.css";

    // Listener update fields
    static String itemAltCodeInput,itemAltNameInput,itemAltPriceInput,itemSaleCodeInput,itemSaleNameInput,
            itemSalePriceInput,itemSaleQuantityInput,subtotalInput,subtotalTaxInput,tenderInput,
            RECEIPT_TEXT = "";

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

                ITEM_NAME_FIELD_DEFAULT = "NA",
                ITEM_PRICE_FIELD_DEFAULT = "0.00",
                ITEM_TOTAL_FIELD_DEFAULT = "0.00",

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

        String WELCOME_MESSAGE = "\nWelcome to Peter's cash register system!\n";


        double eodTotalPrice = 0;

        // Initializing itemComBox from file input
        Initializing();

        // NEED TO RECREATE FOR EACH DATA_ALT SECTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // Create shared dataAlt title/field VBox node
        Label itemAltCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemAltCodeField = new TextField();
        itemAltCodeField.textProperty().addListener((observable, oldValue, newValue) -> itemAltCodeInput = newValue);
        HBox itemAltCodeHB = new HBox(itemAltCodeTitle,itemAltCodeField);

        Label itemAltNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemAltNameField = new TextField();
        itemAltNameField.textProperty().addListener((observable, oldValue, newValue) -> itemAltNameInput = newValue);
        HBox itemAltNameHB = new HBox(itemAltNameTitle,itemAltNameField);

        Label itemAltPriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemAltPriceField = new TextField();
        itemAltPriceField.textProperty().addListener((observable, oldValue, newValue) -> itemAltPriceInput = newValue);
        HBox itemAltPriceHB = new HBox(itemAltPriceTitle,itemAltPriceField);

        // Create item TF VBox
        VBox itemAltTitleFieldVB = new VBox(itemAltCodeHB,itemAltNameHB,itemAltPriceHB);

        // Add scene
        // Create add item scene title
        Label addItemAltSceneTitle = new Label(ADD_ITEM_TITLE);

        // Create Add/Done buttons and button HBox
        Button addItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        addItemButton.setOnAction(e -> { checkAddItem(itemAltCodeInput,itemAltNameInput,itemAltPriceInput); });
        Button addDoneButton = new Button(DONE_BUTTON_TITLE);
        addDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox addButtonHB = new HBox(addItemButton,addDoneButton);

        // Create add item node using add scene title, shared dataAlt VBox, and add button HBox
        VBox addItemVB = new VBox(addItemAltSceneTitle,itemAltTitleFieldVB,addButtonHB);

        // Style nodes

        // Create scene
        addItemScene = new Scene(addItemVB);


        // Delete scene
        // Create delete item scene title
        Label deleteItemAltSceneTitle = new Label(DELETE_ITEM_TITLE);

        // Create update event for delete field nodes
        itemDeleteComboBox.setOnAction(e -> {
            itemAltCodeField.setText(itemDeleteComboBox.getValue().getItemCode());
            itemAltNameField.setText(itemDeleteComboBox.getValue().getItemName());
            itemAltPriceField.setText(itemDeleteComboBox.getValue().getItemPriceString());
        });

        // Create Delete/Done buttons and button HBox
        Button deleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        deleteItemButton.setOnAction(e -> checkDeleteItem(itemAltCodeInput));
        Button deleteDoneButton = new Button(DONE_BUTTON_TITLE);
        deleteDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox deleteButtonHB = new HBox(deleteItemButton,deleteDoneButton);

        // Create delete item node using delete scene title, deleteComboBox, shared dataAlt VBox, and delete button HBox
        VBox deleteItemVB = new VBox(deleteItemAltSceneTitle,itemDeleteComboBox,itemAltTitleFieldVB,deleteButtonHB);

        // Style nodes

        // Create scene
        deleteItemScene = new Scene(deleteItemVB);


        // Modify scene
        // Create modify item scene title
        Label modifyItemAltSceneTitle = new Label(MODIFY_ITEM_TITLE);

        // Select code from itemModifyComboBox

        // Create update event for modify field nodes
        itemModifyComboBox.setOnAction(e -> {
            itemAltCodeField.setText(itemModifyComboBox.getValue().getItemCode());
            itemAltNameField.setText(itemModifyComboBox.getValue().getItemName());
            itemAltPriceField.setText(itemModifyComboBox.getValue().getItemPriceString());
        });

        // Create Modify/Done buttons and button HBox
        Button modifyItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
	    modifyItemButton.setOnAction(e -> checkModifyItem(itemAltCodeInput,itemAltNameInput,itemAltPriceInput));
        Button modifyDoneButton = new Button(DONE_BUTTON_TITLE);
        modifyDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox modifyButtonHB = new HBox(modifyItemButton,modifyDoneButton);

        // Create modify item node using modify scene title, modifyComboBox, shared dataAlt VBox, and modify button HBox
        VBox modifyItemVB = new VBox(modifyItemAltSceneTitle,itemModifyComboBox,itemAltTitleFieldVB,modifyButtonHB);

        // Style nodes

        // Create scene
        modifyItemScene = new Scene(modifyItemVB);

        // dataAlt scene
        // Create dataAlt title node
        Label dataAltLabel = new Label(DATA_ALT_TITLE);

        // Create dataAlt button nodes
        Button dataAltAddItemButton = new Button(ADD_ITEM_BUTTON_TITLE);
        dataAltAddItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltCodeField.setText(TYPE_HERE_DEFAULT);
            itemAltNameField.setText(TYPE_HERE_DEFAULT);
            itemAltPriceField.setText(TYPE_HERE_DEFAULT);
            primaryStage.setScene(addItemScene);
        });
        Button dataAltDeleteItemButton = new Button(DELETE_ITEM_BUTTON_TITLE);
        dataAltDeleteItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltCodeField.setText("");
            itemAltNameField.setText("");
            itemAltPriceField.setText("");
            primaryStage.setScene(deleteItemScene);
        });
        Button dataAltModItemButton = new Button(MOD_ITEM_BUTTON_TITLE);
        dataAltModItemButton.setOnAction(e -> {
            // Resetting shared input fields in scene
            itemAltCodeField.setText("");
            itemAltNameField.setText("");
            itemAltPriceField.setText("");
            primaryStage.setScene(modifyItemScene);
        });
        HBox dataAltButtonHB = new HBox(dataAltAddItemButton,dataAltDeleteItemButton,dataAltModItemButton);

        // Create Done/Quit button node
	    Button saleAltButton = new Button(SALE_BUTTON_TITLE);
	    saleAltButton.setOnAction(e -> primaryStage.setScene(saleScene));
        Button quitAltButton = new Button(QUIT_BUTTON_TITLE);
	    quitAltButton.setOnAction(e -> System.exit(0));
	    HBox dataAltDoneQuitButtonHB = new HBox(saleAltButton,quitAltButton);

        // Create dataAlt node
        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,dataAltDoneQuitButtonHB);

        // Style nodes

        // Create scene
        dataAltScene = new Scene(dataAltVB);


        // sale scene

        // Select item from itemSaleComboBox

        // Create item title/field HBox nodes
        Label itemSaleCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemSaleCodeField = new TextField();
        itemSaleCodeField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleCodeInput = newValue);
        HBox itemSaleCodeHB = new HBox(itemSaleCodeTitle,itemSaleCodeField);

        Label itemSaleNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemSaleNameField = new TextField();
        itemSaleNameField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleNameInput = newValue);
        HBox itemSaleNameHB = new HBox(itemSaleNameTitle,itemSaleNameField);

        Label itemSalePriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemSalePriceField = new TextField();
        itemSalePriceField.textProperty().addListener((observable, oldValue, newValue) -> itemSalePriceInput = newValue);
        HBox itemSalePriceHB = new HBox(itemSalePriceTitle,itemSalePriceField);

        Label itemSaleQuantityTitle = new Label(ITEM_QUANTITY_TITLE);
        TextField itemSaleQuantityField = new TextField();
        itemSaleQuantityField.textProperty().addListener((observable, oldValue, newValue) -> itemSaleQuantityInput = newValue);
        HBox itemSaleQuantityHB = new HBox(itemSaleQuantityTitle,itemSaleQuantityField);

        // Create item TF VBox
        VBox itemSaleTitleFieldVB = new VBox(itemSaleCodeHB,itemSaleNameHB,itemSalePriceHB,itemSaleQuantityHB);

        // Create update event for sale field nodes
        itemSaleComboBox.setOnAction(e -> {
            itemSaleCodeField.setText(itemSaleComboBox.getValue().getItemCode());
            itemSaleNameField.setText(itemSaleComboBox.getValue().getItemName());
            itemSalePriceField.setText(itemSaleComboBox.getValue().getItemPriceString());
        });

        // Create add button to top of sale
        Button addButton = new Button(ADD_BUTTON_TITLE);
//        addButton.setOnAction(event -> {
//            if () {
//
//            }
//            else {
//
//            }
//        });

        // Create sale node
        BorderPane saleTopBP = new BorderPane();
        saleTopBP.setTop(itemSaleComboBox);
        saleTopBP.setCenter(itemSaleTitleFieldVB);
        saleTopBP.setBottom(addButton);

        // Create middle checkout Vbox
        // Create receipt Label node
        TextField receiptTextField = new TextField(RECEIPT_TEXT);

        // Add listener to text to update when RECEIPT_TEXT is changed

        // Create subtotal title/field HBox nodes
        Label subtotalTitle = new Label(SUBTOTAL_TITLE);
        TextField subtotalField = new TextField(SUBTOTAL_FIELD_DEFAULT);
        subtotalField.textProperty().addListener((observable, oldValue, newValue) -> subtotalInput = newValue);
        HBox subtotalHB = new HBox(subtotalTitle,subtotalField);

        Label subtotalTaxTitle = new Label(SUBTOTAL_TAX_TITLE);
        TextField subtotalTaxField = new TextField(SUBTOTAL_TAX_FIELD_DEFAULT);
        subtotalTaxField.textProperty().addListener((observable, oldValue, newValue) -> subtotalTaxInput = newValue);
        HBox subtotalTaxHB = new HBox(subtotalTaxTitle,subtotalTaxField);

        Label tenderTitle = new Label(TENDER_TITLE);
        TextField tenderTF = new TextField();
        tenderTF.textProperty().addListener((observable, oldValue, newValue) -> tenderInput = newValue);
        HBox tenderHB = new HBox(tenderTitle,tenderTF);

        Label changeTitle = new Label(CHANGE_TITLE);
        TextField changeField = new TextField(CHANGE_FIELD_DEFAULT);
	    HBox changeHB = new HBox(changeTitle,changeField);

        // Create checkout button node
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);

        // Create subtotal VBox
	    VBox subtotalVB = new VBox(subtotalHB,subtotalTaxHB,tenderHB,checkoutButton,changeHB);

        // Create checkout VBox
        VBox checkoutVB = new VBox(receiptTextField,subtotalVB);

        // Create bottom button HBox
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
	    VBox mainVB = new VBox(mainTitleLabel,newSaleButton,eodTotalLabel);

        // Style nodes
	    mainVB.setSpacing(25);

        // Create then set mainScene
        mainScene = new Scene(mainVB);

        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void updateItemList() {
        // Update itemObservableList and itemComboBoxes
    }

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

        // Create starting recpit text


        // CHECK IF NEED TO SET UP LISTENER EVENT FOR UPDATING
    }

    private static boolean addSaleItem(String codeInput, String priceInput, String quantityInput) {
        String
                FILENAME_MESSAGE                    = "\nInput file : ",
                FILE_INPUT_ERROR_MESSAGE            = "!!! Invalid input",
                BEGINNING_SALE_MESSAGE              = "\nBeginning a new sale? (Y/N) ",
                SALE_INPUT_ERROR_MESSAGE            = "!!! Invalid input\nShould be (Y/N)",
                CODE_INPUT_INCORRECT_MESSAGE        = "!!! Invalid product code\nShould be A[###] or B[###], 0000 = item list, -1 = quit\n",
                QUANTITY_INPUT_INCORRECT_MESSAGE    = "!!! Invalid quantity\nShould be [1-100]",
                BREAK_LINE                          = "--------------------",
                ENTER_CODE_MESSAGE                  = "\nEnter product code : ",
                ITEM_NAME_MESSAGE                   = "         item name : ",
                ENTER_QUANTITY_MESSAGE              = "\n\tEnter quantity : ",
                ITEM_TOTAL_MESSAGE                  = "        item total : $",
                RECEIPT_LINE                        = "\n----------------------------\n",
                RECEIPT_TOP                         = "Items list:\n",
                TENDERED_AMOUNT_RECEIPT             = "\nTendered amount\t\t\t $ ",
                TENDER_AMOUNT_WRONG                 = "\nAmount entered is invalid",
                TENDER_AMOUNT_TOO_SMALL             = "\nAmount entered is too small",
                CHANGE_AMOUNT                       = "Change\t\t\t\t\t $",
                EOD_MESSAGE                         = "\nThe total sale for the day is  $",

                UPDATE_PROMPT_MESSAGE               = "\nDo you want to update the items data? (A/D/M/Q): ",
                UPDATE_ERROR_MESSAGE                = "!!! Invalid input\nShould be (A/D/M/Q)",
                UPDATE_CODE_PROMPT                  = "item code: ",
                UPDATE_NAME_PROMPT                  = "item name: ",
                UPDATE_PRICE_PROMPT                 = "item price: ",

                UPDATE_ITEM_ALREADY_ADDED           = "!!! item already created",
                UPDATE_ITEM_NOT_FOUND               = "!!! item not found",


                UPDATE_CODE_ERROR_MESSAGE           = "!!! Invalid input\nShould be A[###] or B[###]\n",
                UPDATE_NAME_ERROR_MESSAGE           = "!!! Invalid input\nName shouldn't be only digits\n",

                UPDATE_NAME_OLD_MESSAGE             = "!!! Invalid input\nName already used\n",

                UPDATE_PRICE_ERROR_MESSAGE          = "!!! Invalid input\nShould be greater than 0\n",

                UPDATE_ADD_SUCCESSFUL               = "Item add successful!\n",
                UPDATE_DELETE_SUCCESSFUL            = "Item delete successful!\n",
                UPDATE_MODIFY_SUCCESSFUL            = "Item modify successful!\n",

                THANK_YOU                           = "Thanks for using POST system. Goodbye.";

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
                    // Code input valid

                    // Item found
                    // Output Item name message + item name from itemArray
                    System.out.print(ITEM_NAME_MESSAGE + inputItem.getItemName());

                    // Calc price
                    BigDecimal itemPriceBD = BigDecimal.valueOf(inputItem.getItemPrice());
                    BigDecimal itemQuantityBD = BigDecimal.valueOf(quantityInputInt);

                    // Add Item price to Sale object
                    sale.addSaleItem(inputItem,quantityInputInt,(itemPriceBD.multiply(itemQuantityBD).doubleValue()));

                    // Add text to
//                    RECEIPT_TEXT = RECEIPT_TEXT.concat()
                }
            }
            } catch (Exception e){
                // Input is invalid
                return false;
            }


            // REMOVE
            return true;
    }

	// Return true if valid, false if invalid
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

	// Return true if valid, false if invalid
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

	// Return true if valid, false if invalid
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
