package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.UserBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.UserDto;
import com.assignment.orm.service.orm_final_course_work_health_care.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BoType.USER);
    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane mainAnchorpane;

    @FXML
    private CheckBox showPasswordBox;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtSecondPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if (showPasswordBox.isSelected()) {
            new Alert(Alert.AlertType.ERROR, "please uncheck shw the PasswordBox").showAndWait();
            return;
        }
        if (txtPassword.getText().isEmpty() || txtUsername.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter username and  password").showAndWait();
            return;

        }else{
            try {
                UserDto userDto = userBo.checkUser(txtUsername.getText(), txtPassword.getText());
                if (userDto == null) {
                    new Alert(Alert.AlertType.ERROR, "please enter username and  password").showAndWait();
                    txtUsername.clear();
                    txtPassword.clear();
                    txtSecondPassword.clear();
                    return;
                }
                Stage currentStage = (Stage) mainAnchorpane.getScene().getWindow();
                currentStage.close();


                if(userDto.getRole().equals("Admin")) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VIew/AdminDashBoard.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Admin DashBoard");
                stage.setScene(new Scene(root));
                stage.show();

                }else if(userDto.getRole().equals("Receptionist")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VIew/ReceptionistDashBoard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Receptionist DashBoard");
                    stage.setScene(new Scene(root));
                    stage.show();

                }

            }catch (IOException e) {
//               new Alert(Alert.AlertType.ERROR, "error").showAndWait();
                e.printStackTrace();
            }
        }
    }
    @FXML
    void showPasswordBoxOnAction(ActionEvent event) {
         if (showPasswordBox.isSelected()) {
             txtSecondPassword.setText(txtPassword.getText());
             txtSecondPassword.setVisible(true);
             txtPassword.setVisible(false);
         }else {
             txtPassword.setText(txtSecondPassword.getText());
             txtPassword.setVisible(true);
             txtSecondPassword.setVisible(false);
         }
    }

}
