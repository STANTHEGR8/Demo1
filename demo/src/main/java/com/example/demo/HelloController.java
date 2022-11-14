package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label msgLoginInvalid;
    @FXML
    private Label msgLoginInvalid2;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField pwPassword;

    @FXML
    protected void setBtnExit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to exit the application!");
        alert.setContentText("Are you sure you want to exit the application?");

        if(alert.showAndWait().get() == ButtonType.OK){
            close();
        }
    }
    public void close(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void setBtnLogin(ActionEvent event){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDblink();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE `User Name`= '" + tfUserName.getText() + "' AND Password = '" + pwPassword.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WELCOME!!!");
                    alert.setHeaderText("Username and password correct!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please try again.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}