package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.UserBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.UserTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.UserDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    public Button resetButton;
    @FXML
    private Button deleteButton;


    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TextField emailField;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableColumn<UserTm, String> roleColumn;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<UserTm, String> userIdColumn;

    @FXML
    private TextField userIdField;

    @FXML
    private TableView<UserTm> userTable;

    @FXML
    private TableColumn<UserTm, String> usernameColumn;

    @FXML
    private TextField usernameField;

    UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BoType.USER);

    @FXML
    void handleDelete(ActionEvent event) throws SQLException {
        boolean isDeleted = userBo.delete(userIdField.getText());
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "User deleted Successfully");
            reset();

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete User");
        }


    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        String id = userIdField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();

        if (id.isEmpty() || name.isEmpty() ||  password.isEmpty() || email.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Fill All The Fields").show();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidUsername = name.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidUsername) {
            new Alert(Alert.AlertType.ERROR, "Invalid username format").show();
            return;
        }

        if (!isValidPassword) {
            new Alert(Alert.AlertType.ERROR, "Invalid password format").show();
            return;
        }

        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format").show();
            return;
        }

        boolean isSaved = userBo.save(new UserDto(id, name, email, password, role));

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully");
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Save User");
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) throws SQLException {
        String id = userIdField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();

        if (id.isEmpty() || name.isEmpty()|| password.isEmpty() || email.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Fill All The Fields").show();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidUsername = name.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidUsername) {
            new Alert(Alert.AlertType.ERROR, "Invalid username format").show();
            return;
        }

        if (!isValidPassword) {
            new Alert(Alert.AlertType.ERROR, "Invalid password format").show();
            return;
        }

        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format").show();
            return;
        }

        boolean isUpdated = userBo.update(new UserDto(id, name, email, password, role));

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully");
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Save User");
        }

    }

    void loadData() {
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Receptionist");
        roleComboBox.setItems(roles);
    }

    void loadTableData() throws SQLException {
        ArrayList<UserDto> userDtos = userBo.getAll();
        ObservableList<UserTm> userTms = FXCollections.observableArrayList();
        for (UserDto userDto : userDtos) {
            UserTm userTm = new UserTm();
            userTm.setId(userDto.getId());
            userTm.setName(userDto.getName());
            userTm.setEmail(userDto.getEmail());
            userTm.setRole(userDto.getRole());

            userTms.add(userTm);
        }
        userTable.setItems(userTms);
    }

    void reset() throws SQLException {
        String id = userBo.getNextId();
        userIdField.setText(id);
        loadTableData();
        passwordField.clear();
        emailField.clear();
        nameField.clear();
        roleComboBox.setValue("");

        saveButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadData();
        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void tblOnclicked(MouseEvent mouseEvent) {
        UserTm userTm = userTable.getSelectionModel().getSelectedItem();

        if (userTm != null) {
            userIdField.setText(userTm.getId());
            nameField.setText(userTm.getName());
            emailField.setText(userTm.getEmail());
            roleComboBox.setValue(userTm.getRole());
        }
        saveButton.setDisable(true);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    public void handleReset(ActionEvent actionEvent) throws SQLException {
        reset();
    }
}
