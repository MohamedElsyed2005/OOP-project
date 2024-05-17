/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package devicestore;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author 20100
 */
public class CardController implements Initializable {

    @FXML
    private AnchorPane Card_form;
    @FXML
    private Button Prod_AddBtn;
    @FXML
    private Label Prod_Price;
    @FXML
    private Spinner<Integer> Prod_Spinner;
    @FXML
    private ImageView Prod_imageView;
    @FXML
    private Label Prod_name;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    private Image image;

    private String prodID;
    private String type;
    private String prod_image;

    private LaptopData LapData;

    private SpinnerValueFactory<Integer> spin;

    public void setData(LaptopData LapData) {
        this.LapData = LapData;

        prodID = LapData.getLap_id();
        Prod_name.setText(LapData.getLap_Name());
        prod_image = LapData.getImage();
        type = LapData.getType();
        Prod_Price.setText("$" + String.valueOf(LapData.getPrice()));
        String path = "File:" + LapData.getImage();
        image = new Image(path, 215, 103, false, true); //Error
        Prod_imageView.setImage(image);
        price = LapData.getPrice();

    }

    private int qty;

    public void setQuantity() {

        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        Prod_Spinner.setValueFactory(spin);

    }

    private double Totalprice;
    private double price;

    public void addBtn() {
        ManagementCL custI = new ManagementCL();
        custI.customerID();

        qty = Prod_Spinner.getValue();

        connect = database.connectDB();

        if (qty == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("Something Wrong :3 ");
            alert.showAndWait();
        } else {

            prod_image = prod_image.replace("\\", "\\\\");

            String insertData = "INSERT INTO customer "
                    + "( Customer_id, Lap_id, Lap_Name, Type, Price, quantity, em_username, image)"
                    + "VALUES(?,?,?,?,?,?,?,?)";

            try {

                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, String.valueOf(users.custID));
                prepare.setString(2, prodID);
                prepare.setString(3, Prod_name.getText());
                prepare.setString(4, type);

                Totalprice = (qty * price);

                prepare.setString(5, String.valueOf(Totalprice));
                prepare.setString(6, String.valueOf(qty));
                prepare.setString(7, users.username);
                prepare.setString(8, prod_image);

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Successfuly Added !");
                alert.showAndWait();

                custI.menuGetTotal();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setQuantity();
    }

}
