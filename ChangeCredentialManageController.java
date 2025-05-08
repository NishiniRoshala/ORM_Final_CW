package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ChangeCredentialManageBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ChangeCredentialManageController {

    ChangeCredentialManageBo changeCredentialManageBo = BoFactory.getInstance().getBo(BoFactory.BoType.CHANGE_CREDENTIALS);

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField txtCurrentPassword;

    @FXML
    private TextField txtCurrentUsername;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtNewUsername;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
            reset();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String username = txtNewUsername.getText();
        String password = txtNewPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        if (!isValidUsername) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").showAndWait();
            return;
        }

        if (!isValidPassword) {
            new Alert(Alert.AlertType.ERROR, "Invalid password").showAndWait();
            return;
        }

        UserDto userDto = changeCredentialManageBo.checkUser(txtCurrentUsername.getText(), txtCurrentPassword.getText());

        if (userDto == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid current username or password").showAndWait();
            return;
        }

        boolean isChanged = changeCredentialManageBo.changeCredential(userDto, username, password);

        if (isChanged) {
            new Alert(Alert.AlertType.INFORMATION, "Credentials changed successfully").showAndWait();txtCurrentUsername.clear();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to change credentials").showAndWait();
        }
    }

    private void reset(){
        txtCurrentUsername.clear();
        txtCurrentPassword.clear();
        txtNewUsername.clear();
        txtNewPassword.clear();
    }

}
