/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package devicestore;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 20100
 */
public class Controller implements Initializable {

    @FXML
    private Hyperlink si_forget;
    @FXML
    private Button si_loginbtn;
    @FXML
    private AnchorPane si_loginform;
    @FXML
    private PasswordField si_password;
    @FXML
    private TextField si_username;
    @FXML
    private Button side_createAccount;
    @FXML
    private AnchorPane side_form;
    @FXML
    private PasswordField su_password;
    @FXML
    private Button su_signUpbttn;
    @FXML
    private AnchorPane su_signUpform;
    @FXML
    private TextField su_username;
    @FXML
    private Button side_AlreadyHave;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    //when the login button clicked
    public void LoginBtn() {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("incorrect username | password");
            alert.showAndWait();
        } else {

            String SelectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(SelectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                // if Successfully login ,then proceed to another form
                if (result.next()) {

                    // to get the user name                 
                    users.username = si_username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("informtion Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("Management.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("Device Store");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    stage.setScene(scene);
                    stage.show();

                    si_loginbtn.getScene().getWindow().hide();

                } else {// if not , then get error massage
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("incorrect username | password");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // when the create account clicked
    public void regBtn() {
        // هيشوف لو هي فاضيه او لا الاول 
        // لو هي فاضيه هيطلعلوا المسدج دي 
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setHeaderText(null);
            alert.setContentText("please fill all fields");
            alert.showAndWait();
            //لو هي مش فاضيه بقي هيروح يحط القيم اليوزر دخلهاا 
        } else {
            String regData = "INSERT INTO employee (username,password)" + "VALUES(?,?)";
            connect = database.connectDB();
            try {
                //Check if the username has alreaded recorded or not 
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + su_username.getText() + "' ";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                //لو لقي الاسم موجود قبل كدا يطلع مسدج 
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                    //لو الباس اقل من اربعه هيطلع المسدج دي 
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, Enter password at least 8 length");
                    alert.showAndWait();
                    //لو بقي مفيش اي اخطاء من دول هيدخل ع الداتا بيبز يضيف 
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    //update data in database
                    prepare.executeUpdate();
                    // get massage (created account)
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly registered Account! ");
                    alert.showAndWait();
                    // return field empty 
                    su_username.setText("");
                    su_password.setText("");
                    // switch to login form
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_AlreadyHave.setVisible(false);
                        side_createAccount.setVisible(true);
                    });
                    slider.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // to switch form to create account \\
    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_createAccount) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(true);
                side_createAccount.setVisible(false);
            });
            slider.play();

        } else if (event.getSource() == side_AlreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_AlreadyHave.setVisible(false);
                side_createAccount.setVisible(true);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
