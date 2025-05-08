package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.PatientTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static java.util.Date.*;

public class PatientManageController implements Initializable {

    @FXML
    private Button btnAssignProgram;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFilterPatient;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnTherapyPrograms;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnViewPatientPrograms;

    @FXML
    private TableColumn<PatientTm, String> colAddress;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colMedicalHistory;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colPatientId;

    @FXML
    private TableColumn<PatientTm, Date> colRegDate;

    @FXML
    private DatePicker dpRegisterDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private TableView<PatientTm> tblPatients;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtName;

    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    private ReceptionistDashBoardController receptionistDashBoardController = new ReceptionistDashBoardController();


    @FXML
    void btnAssignProgramOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/AssignProgram.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Assign Program Form");

        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = lblPatientId.getText();
        boolean isDeleted = patientBo.delete(id);

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Patient deleted Successfully");
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete Patient");
        }
    }

    @FXML
    void btnFilterPatientOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/FilterPatient.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Assign Program Form");

        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    void reset() throws SQLException {
        String id = patientBo.getNextId();
        lblPatientId.setText(id);

       load();

        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtMedicalHistory.clear();
        dpRegisterDate.setValue(null);

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }


    void load() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();

        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos){
            PatientTm patientTm = new PatientTm();
            patientTm.setP_id(patientDto.getP_id());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setContact(patientDto.getContact());
            patientTm.setAddress(patientDto.getAddress());
            patientTm.setHistory(patientDto.getHistory());
            patientTm.setDate(patientDto.getDate());


            patientTms.add(patientTm);
        }

        tblPatients.setItems(patientTms);

    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String p_id = lblPatientId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String history = txtMedicalHistory.getText();
        Date date = java.sql.Date.valueOf(dpRegisterDate.getValue());


        if (p_id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()
                || address.isEmpty() || history.isEmpty() || date == null) {
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }
        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidDescription = history.matches(descriptionPattern);


        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").showAndWait();
            return;
        }
        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").showAndWait();
            return;
        }
        if (!isValidContact) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").showAndWait();
            return;
        }
        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description").showAndWait();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setP_id(p_id);
        patientDto.setName(name);
        patientDto.setEmail(email);
        patientDto.setContact(contact);
        patientDto.setAddress(address);
        patientDto.setHistory(history);
        patientDto.setDate((java.sql.Date) date);

        boolean isSaved = patientBo.save(patientDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Patient Saved Successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to Save Patient").showAndWait();
        }
    }
    @FXML
    void btnTherapyProgramsOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/ViewAllTherapyPrograms.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("View Patient Programs");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String p_id = lblPatientId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String history = txtMedicalHistory.getText();
        Date date = java.sql.Date.valueOf(dpRegisterDate.getValue());


        if (p_id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()
                || address.isEmpty() || history.isEmpty() || date == null) {
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }
        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidDescription = history.matches(descriptionPattern);


        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").showAndWait();
            return;
        }
        if (!isValidEmail) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").showAndWait();
            return;
        }
        if (!isValidContact) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").showAndWait();
            return;
        }
        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description").showAndWait();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setP_id(p_id);
        patientDto.setName(name);
        patientDto.setEmail(email);
        patientDto.setContact(contact);
        patientDto.setAddress(address);
        patientDto.setHistory(history);
        patientDto.setDate((java.sql.Date) date);

        boolean isSaved = patientBo.update(patientDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Patient Updated Successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Patient").showAndWait();
        }
    }

    @FXML
    void btnViewPatientProgramsOnAction(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/ViewPatientProgram.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("View Patient Programs");
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    void tblPatientsOnMouseClicked(MouseEvent event) {

        PatientTm patientTm = tblPatients.getSelectionModel().getSelectedItem();
        lblPatientId.setText(patientTm.getP_id());
        txtName.setText(patientTm.getName());
        txtEmail.setText(patientTm.getEmail());
        txtContact.setText(patientTm.getContact());
        txtAddress.setText(patientTm.getAddress());
        txtMedicalHistory.setText(patientTm.getHistory());
        dpRegisterDate.setValue(patientTm.getDate().toLocalDate());

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   colPatientId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
   colName.setCellValueFactory(new PropertyValueFactory<>("name"));
   colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
   colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
   colRegDate.setCellValueFactory(new PropertyValueFactory<>("date"));
   colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
   colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("history"));

        try {
            reset();
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPatientManageController(ReceptionistDashBoardController receptionistDashBoardController) {
      this.receptionistDashBoardController = receptionistDashBoardController;

        if (receptionistDashBoardController != null) {
            if (receptionistDashBoardController.user != null) {
                if (receptionistDashBoardController.user.getRole().equals("Receptionist")) {
                    btnDelete.setDisable(true);
                }
            }else {
                System.out.println("receptionistDashBoardController user is null");
            }
        }else {
            System.out.println("receptionistDashBoardController is null");
        }
    }

}

