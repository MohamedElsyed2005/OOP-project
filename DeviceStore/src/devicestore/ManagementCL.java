/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package devicestore;

import devicestore.LaptopData;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author 20100
 */
public class ManagementCL implements Initializable {

    @FXML
    private Button customer_btn;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button inventory_AddBtn;
    @FXML
    private Button inventory_ClearBtn;
    @FXML
    private Button inventory_DeleteBtn;
    @FXML
    private Button inventory_UppdateBtn;
    @FXML
    private Button inventory_btn;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_CPU;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Display;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_GPU;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Lap_Name;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Pirce;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Ram;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Storage;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_Type;
    @FXML
    private TableColumn<LaptopData, String> inventory_col_id;
    @FXML
    private ImageView inventory_imageView;
    @FXML
    private Button inventory_importBtn;
    @FXML
    private AnchorPane invetory_rorm;
    @FXML
    private AnchorPane dashBoard_form;
    @FXML
    private TableView<LaptopData> invetory_tableView;
    @FXML
    private Button logout_btn;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button menu_btn;
    @FXML
    private Label Home_username;
    @FXML
    private TextField invontory_CPU;
    @FXML
    private TextField invontory_Display;
    @FXML
    private TextField invontory_GPU;
    @FXML
    private TextField invontory_ID;
    @FXML
    private TextField invontory_Lap_Name;
    @FXML
    private TextField invontory_Price;
    @FXML
    private ComboBox<?> invontory_Type;
    @FXML
    private ComboBox<?> invontory_Ram;
    @FXML
    private ComboBox<?> invontory_Storage;
    @FXML
    private TextField menu_Amount;
    @FXML
    private Label menu_Change;
    @FXML
    private Button menu_PayBtn;
    @FXML
    private Label menu_Total;
    @FXML
    private TableColumn<LaptopData, String> menu_col_Lap_Name;
    @FXML
    private TableColumn<LaptopData, String> menu_col_Quantity;
    @FXML
    private TableColumn<LaptopData, String> menu_col_price;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private Button menu_receiptBtn;
    @FXML
    private Button menu_removeBtn;
    @FXML
    private GridPane menu_gridePane;
    @FXML
    private ScrollPane menu_ScrollPane;
    @FXML
    private TableView<LaptopData> menu_TableView;
    @FXML
    private TableColumn<CustomerData, String> Customer_col_Cashier;
    @FXML
    private TableColumn<CustomerData, String> Customer_col_CustomerID;
    @FXML
    private TableColumn<CustomerData, String> Customer_col_total;
    @FXML
    private AnchorPane Customer_form;
    @FXML
    private TableView<CustomerData> Customer_tableView;
    @FXML
    private Label dashboard_Totalinc;
    @FXML
    private Label dashboard_nomCus;
    @FXML
    private Label dashboard_shopcard;
    @FXML
    private Button searchbtn;
    @FXML
    private TextField searchlabel;

    // get connection with database
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    // get Window Alert(ERROR,INFORMATION,CONFOMTION)
    private Alert alert;

    // to store all items and show again in card(items page)
    public ObservableList<LaptopData> CardListdata = FXCollections.observableArrayList();

    // know how many customer buy from store and print it in first page (dashboard) 
    public void dashboardDisplayNC() {

        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_nomCus.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // know how much money(from store and print it in first page (dashboard))
    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connectDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_Totalinc.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // how many devices that we can sell (and print the number in the first page (dashboard))
    public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";

        connect = database.connectDB();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_shopcard.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Make Add button method (Action)
    public void inventoryAdd() {

        if (invontory_CPU.getText().isEmpty()
                || invontory_Display.getText().isEmpty()
                || invontory_GPU.getText().isEmpty()
                || invontory_ID.getText().isEmpty()
                || invontory_Lap_Name.getText().isEmpty()
                || invontory_Price.getText().isEmpty()
                || invontory_Type.getSelectionModel().getSelectedItem() == null
                || invontory_Ram.getSelectionModel().getSelectedItem() == null
                || invontory_Storage.getSelectionModel().getSelectedItem() == null
                || users.Path == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            String checkLapId = "SELECT Lap_id FROM laptop WHERE Lap_id ='"
                    + invontory_ID.getText() + "'";

            connect = database.connectDB();

            try {

                statement = connect.createStatement();
                statement.executeQuery(checkLapId);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText(invontory_ID.getText() + " is already taken");
                    alert.showAndWait();
                } else {

                    String insertData = "INSERT INTO laptop"
                            + "(Lap_id, Type, Lap_Name, Display, CPU, GPU, Ram, Storage, Price, image)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, invontory_ID.getText());
                    prepare.setString(2, (String) invontory_Type.getSelectionModel().getSelectedItem());
                    prepare.setString(3, invontory_Lap_Name.getText());
                    prepare.setString(4, invontory_Display.getText());
                    prepare.setString(5, invontory_CPU.getText());
                    prepare.setString(6, invontory_GPU.getText());
                    prepare.setString(7, (String) invontory_Ram.getSelectionModel().getSelectedItem());
                    prepare.setString(8, (String) invontory_Storage.getSelectionModel().getSelectedItem());
                    prepare.setString(9, invontory_Price.getText());

                    String path = users.Path;
                    path = path.replace("\\", "\\\\");
                    prepare.setString(10, path);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryCleanBtn();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //Make Update Button Method (Action)
    public void inventoryUpdate() {

        if (invontory_CPU.getText().isEmpty()
                || invontory_Display.getText().isEmpty()
                || invontory_GPU.getText().isEmpty()
                || invontory_ID.getText().isEmpty()
                || invontory_Lap_Name.getText().isEmpty()
                || invontory_Price.getText().isEmpty()
                || invontory_Type.getSelectionModel().getSelectedItem() == null
                || invontory_Ram.getSelectionModel().getSelectedItem() == null
                || invontory_Storage.getSelectionModel().getSelectedItem() == null
                || users.Path == null || users.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = users.Path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE laptop SET Lap_id = '" + invontory_ID.getText()
                    + "',Type = '" + invontory_Type.getSelectionModel().getSelectedItem()
                    + "',Lap_Name ='" + invontory_Lap_Name.getText()
                    + "',Display = '" + invontory_Display.getText()
                    + "',CPU = '" + invontory_CPU.getText()
                    + "',GPU = '" + invontory_GPU.getText()
                    + "',Ram = '" + invontory_Ram.getSelectionModel().getSelectedItem()
                    + "',Storage = '" + invontory_Storage.getSelectionModel().getSelectedItem()
                    + "',image = '" + path
                    + "',Price = '" + invontory_Price.getText()
                    + "'WHERE id = '" + users.id + "' ";

            connect = database.connectDB();

            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you wanna update?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryCleanBtn();

                } else {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Make Delete button Method (Action)
    public void inventoryDeleteDate() {

        if (users.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want delete Laptop" + invontory_ID.getText());
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String Delete = "DELETE FROM laptop WHERE id = " + users.id;

                try {

                    prepare = connect.prepareStatement(Delete);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryCleanBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();

            }
        }

    }

    // Make the items Selected by mouse (mouse clicked)
    public void inventorySelectData() {

        LaptopData lapData = invetory_tableView.getSelectionModel().getSelectedItem();
        int num = invetory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        invontory_ID.setText(lapData.getLap_id());
        invontory_Lap_Name.setText(lapData.getLap_Name());
        invontory_Display.setText(String.valueOf(lapData.getDisplay()));
        invontory_Price.setText(String.valueOf(lapData.getPrice()));
        invontory_GPU.setText(String.valueOf(lapData.getGPU()));
        invontory_CPU.setText(String.valueOf(lapData.getCPU()));

        users.Path = lapData.getImage();

        String path = "File:" + lapData.getImage();
        users.id = lapData.getId();

        image = new Image(path, 157, 159, false, true);
        inventory_imageView.setImage(image);
    }

    // make clear button Method(Action)
    public void inventoryCleanBtn() {

        invontory_ID.setText("");
        invontory_Lap_Name.setText("");
        invontory_Display.setText("");
        invontory_CPU.setText("");
        invontory_GPU.setText("");
        invontory_Price.setText("");
        invontory_Ram.getSelectionModel().clearSelection();
        invontory_Storage.getSelectionModel().clearSelection();
        invontory_Type.getSelectionModel().clearSelection();
        users.Path = "";
        users.id = 0;
        inventory_imageView.setImage(null);

    }

    // make import button Method (Action)
    private Image image;

    public void inventoryImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            users.Path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 157, 159, false, true);
            inventory_imageView.setImage(image);

        }

    }

    // collect all data in table
    private ObservableList<LaptopData> LapData;

    public ObservableList<LaptopData> TableLapList() {

        ObservableList<LaptopData> LapData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM laptop";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            LaptopData lapdata;

            while (result.next()) {
                //Constructor LaptopData
                lapdata = new LaptopData(result.getInt("id"),
                        result.getString("Lap_id"),
                        result.getString("Type"),
                        result.getString("Lap_Name"),
                        result.getString("Display"),
                        result.getString("CPU"),
                        result.getString("GPU"),
                        result.getString("Ram"),
                        result.getString("Storage"),
                        result.getDouble("Price"),
                        result.getString("image"));

                LapData.add(lapdata);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return LapData;
    }

    // show data in table
    private ObservableList<LaptopData> inventoryLapData;

    public void inventoryShowData() {

        inventoryLapData = TableLapList();
        inventory_col_id.setCellValueFactory(new PropertyValueFactory<>("Lap_id"));
        inventory_col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        inventory_col_Lap_Name.setCellValueFactory(new PropertyValueFactory<>("Lap_Name"));
        inventory_col_Display.setCellValueFactory(new PropertyValueFactory<>("Display"));
        inventory_col_CPU.setCellValueFactory(new PropertyValueFactory<>("CPU"));
        inventory_col_GPU.setCellValueFactory(new PropertyValueFactory<>("GPU"));
        inventory_col_Ram.setCellValueFactory(new PropertyValueFactory<>("Ram"));
        inventory_col_Storage.setCellValueFactory(new PropertyValueFactory<>("Storage"));
        inventory_col_Pirce.setCellValueFactory(new PropertyValueFactory<>("Price"));

        invetory_tableView.setItems(inventoryLapData);

    }

    //comboBox Type of lap
    private String[] type = {"Lenovo", "Dell", "Apple", "MSI"};

    public void invertoryTypeList() {

        ArrayList<String> TypeL = new ArrayList<>();

        for (String data : type) {
            TypeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(TypeL);
        invontory_Type.setItems(listData);
    }

    //comboBox Ram
    private String[] Ram = {"8GB", "16GB", "32GB"};

    public void invertoryRamList() {

        ArrayList<String> RamL = new ArrayList<>();

        for (String data : Ram) {
            RamL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(RamL);
        invontory_Ram.setItems(listData);
    }

    //ComboBox Storage
    private String[] Storage = {"256GB", "512GB", "1TB", "2TB", "4TB"};

    public void invertoryStorageList() {

        ArrayList<String> StorageL = new ArrayList<>();

        for (String data : Storage) {
            StorageL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(StorageL);
        invontory_Storage.setItems(listData);
    }

    // menu get all data and store it
    public ObservableList<LaptopData> menuGetData() {

        String sql = "SELECT * FROM laptop";

        ObservableList<LaptopData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            LaptopData lapData;

            while (result.next()) {

                lapData = new LaptopData(result.getInt("id"),
                        result.getString("Lap_id"),
                        result.getString("Lap_Name"),
                        result.getString("Type"),
                        result.getDouble("Price"),
                        result.getString("image"));
                listData.add(lapData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    //make method to get the same name like your input from data base 
    private ObservableList<LaptopData> lap;

    public ObservableList<LaptopData> getSearchLaptop(String name) {

        ObservableList<LaptopData> lap = FXCollections.observableArrayList();

        String search = "SELECT * FROM laptop WHERE Lap_Name LIKE '%" + name + "%'";

        connect = database.connectDB();
        try {

            prepare = connect.prepareStatement(search);
            result = prepare.executeQuery();

            LaptopData lapdata;

            while (result.next()) {
                lapdata = new LaptopData(result.getInt("id"),
                        result.getString("Lap_id"),
                        result.getString("Type"),
                        result.getString("Lap_Name"),
                        result.getString("Display"),
                        result.getString("CPU"),
                        result.getString("GPU"),
                        result.getString("Ram"),
                        result.getString("Storage"),
                        result.getDouble("Price"),
                        result.getString("image"));

                lap.add(lapdata);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lap;
    }

    //make search button method (Action) 
    private ObservableList<LaptopData> searchLap;

    public void searchBtn() {

        searchLap = getSearchLaptop(searchlabel.getText());

        inventory_col_id.setCellValueFactory(new PropertyValueFactory<>("Lap_id"));
        inventory_col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        inventory_col_Lap_Name.setCellValueFactory(new PropertyValueFactory<>("Lap_Name"));
        inventory_col_Display.setCellValueFactory(new PropertyValueFactory<>("Display"));
        inventory_col_CPU.setCellValueFactory(new PropertyValueFactory<>("CPU"));
        inventory_col_GPU.setCellValueFactory(new PropertyValueFactory<>("GPU"));
        inventory_col_Ram.setCellValueFactory(new PropertyValueFactory<>("Ram"));
        inventory_col_Storage.setCellValueFactory(new PropertyValueFactory<>("Storage"));
        inventory_col_Pirce.setCellValueFactory(new PropertyValueFactory<>("Price"));

        invetory_tableView.setItems(searchLap);

    }

    // display card data in gride and you can add more than one
    public void menuDispalyCard() {

        CardListdata.clear();
        CardListdata.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridePane.getChildren().clear();
        menu_gridePane.getRowConstraints().clear();
        menu_gridePane.getColumnConstraints().clear();

        for (int q = 0; q < CardListdata.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("CardProduct.fxml"));
                AnchorPane pane = load.load();
                CardController cardCl = load.getController();
                cardCl.setData(CardListdata.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridePane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // get the order from data base 
    public ObservableList<LaptopData> menuGetOrder() {
        customerID();
        ObservableList<LaptopData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE Customer_id = " + custID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            LaptopData lapData;

            while (result.next()) {
                lapData = new LaptopData(result.getInt("id"),
                        result.getString("Lap_id"),
                        result.getString("Lap_Name"),
                        result.getInt("quantity"),
                        result.getString("Type"),
                        result.getDouble("Price"),
                        result.getString("image"));

                listData.add(lapData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    // show order data that you get it from data base and print it 
    private ObservableList<LaptopData> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_Lap_Name.setCellValueFactory(new PropertyValueFactory<>("Lap_Name"));
        menu_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        menu_TableView.setItems(menuOrderListData);

    }

    //Make the items Selected by mouse (mouse clicked)
    
    private int getid;

    public void menuSelectOrder() {
        LaptopData lap = menu_TableView.getSelectionModel().getSelectedItem();
        int num = menu_TableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        getid = lap.getId();

    }

    // know the total price from your order(Data base)
    private double Totalprice;

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(Price) FROM customer WHERE Customer_id = " + custID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                Totalprice = result.getDouble("SUM(Price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //print the total price
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_Total.setText("$" + Totalprice);
    }

    // know is the customer`s money is enough to pay or not 
    private double amount;
    double change;

    public void menuAmount() {
        menuGetTotal();

        if (menu_Amount.getText().isEmpty() || Totalprice == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("invalid");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_Amount.getText());

            if (amount < Totalprice) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("you don`t have enough money!!!");
                alert.showAndWait();
                menu_Amount.setText("");
            } else {
                change = (amount - Totalprice);
                menu_Change.setText("$" + change);
            }
        }

    }

    //make pay button method (Action)
    public void payBtn() {

        if (Totalprice == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("please choose your order first!!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (Customer_id, total, em_username)"
                    + "VALUES(?,?,?)";

            connect = database.connectDB();

            try {

                if (amount == 0) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("ARE YOU SURE !!!");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(users.custID));
                        prepare.setString(2, String.valueOf(Totalprice));
                        prepare.setString(3, users.username);
                        prepare.executeUpdate();
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error Massage");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful");
                        alert.showAndWait();

                        menuShowOrderData();

                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Error Massage");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //make remove button method (Action)
    public void menuRomoveBtn() {
        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("please Select the order you want to remove");
            alert.showAndWait();
        } else {
            
            String deleteData = "DELETE FROM customer WHERE id = " + getid;

            connect = database.connectDB();

            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // make receipt button method (Action) and print the jaspar report
    public void receiptBtn() {
        if (Totalprice == 0 || menu_Amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("please order fisrt!!");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getRecepit", (custID - 1));
            try {

                JasperDesign jD = JRXmlLoader.load("C:\\Users\\20100\\Documents\\NetBeansProjects\\DeviceStore\\src\\devicestore\\Store.jrxml");
                JasperReport jR = JasperCompileManager.compileReport(jD);
                JasperPrint jP = JasperFillManager.fillReport(jR, map, connect);

                JasperViewer.viewReport(jP, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // method to restart all page after the customer confirm the pay process
    public void menuRestart() {

        Totalprice = 0;
        amount = 0;
        change = 0;

        menu_Total.setText("$0.0");
        menu_Amount.setText("");
        menu_Change.setText("$0.0");

    }

    //
    private int custID;

    public void customerID() {

        String sql = "SELECT MAX(Customer_id) FROM customer";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                custID = result.getInt("MAX(Customer_id)");
            }

            String checkCID = "SELECT MAX(Customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(Customer_id)");
            }

            if (custID == 0) {
                custID += 1;
            } else if (custID == checkID) {
                custID += 1;
            }

            users.custID = custID;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<CustomerData> customersDataList() {
        ObservableList<CustomerData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM receipt";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CustomerData cData;

            while (result.next()) {
                cData = new CustomerData(result.getInt("id"),
                        result.getInt("Customer_id"),
                        result.getDouble("total"),
                        result.getString("em_username"));

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // show all customer 
    private ObservableList<CustomerData> customersListData;

    public void customersShowData() {

        customersListData = customersDataList();

        Customer_col_CustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        Customer_col_Cashier.setCellValueFactory(new PropertyValueFactory<>("em_username"));
        Customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));

        Customer_tableView.setItems(customersListData);

    }

    // when you click on any button 
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {

            dashBoard_form.setVisible(true);
            invetory_rorm.setVisible(false);
            menu_form.setVisible(false);
            Customer_form.setVisible(false);

            dashboardDisplayNC();
            dashboardTotalI();
            dashboardNSP();

        } else if (event.getSource() == inventory_btn) {

            dashBoard_form.setVisible(false);
            invetory_rorm.setVisible(true);
            menu_form.setVisible(false);
            Customer_form.setVisible(false);

            invertoryTypeList();
            invertoryRamList();
            invertoryStorageList();
            inventoryShowData();

        } else if (event.getSource() == menu_btn) {

            dashBoard_form.setVisible(false);
            invetory_rorm.setVisible(false);
            menu_form.setVisible(true);
            Customer_form.setVisible(false);

            menuDispalyCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == customer_btn) {

            dashBoard_form.setVisible(false);
            invetory_rorm.setVisible(false);
            menu_form.setVisible(false);
            Customer_form.setVisible(true);

            customersShowData();
        }

    }

    // Event log out 
    public void logout() {

        try {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Device store");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = users.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        Home_username.setText("Welcome, " + user);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
        dashboardDisplayNC();
        dashboardTotalI();
        dashboardNSP();

        invertoryTypeList();
        invertoryRamList();
        invertoryStorageList();
        inventoryShowData();

        menuDispalyCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();

        customersShowData();
    }
}
