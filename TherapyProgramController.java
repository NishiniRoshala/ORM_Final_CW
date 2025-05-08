package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapyProgramTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
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

public class TherapyProgramController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<TherapyProgramDto,String> descriptionColumn;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TableColumn<TherapyProgramDto,String> durationColumn;

    @FXML
    private TextField durationField;

    @FXML
    private TableColumn<TherapyProgramDto, Double> feeColumn;

    @FXML
    private TextField feeField;

    @FXML
    private TableColumn<TherapyProgramDto,String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<TherapyProgramDto,String> programIdColumn;

    @FXML
    private TextField programIdField;

    @FXML
    private TableView<TherapyProgramTm> programTable;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);

    @FXML
    void handleDelete(ActionEvent event) throws SQLException {
        boolean isDelete = therapyProgramBo.delete(programIdField.getText());

        if(isDelete){
            new Alert(Alert.AlertType.INFORMATION, "Program deleted successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete program").showAndWait();
        }

    }


    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        String id = programIdField.getText();
        String name = nameField.getText();
        String duration = durationField.getText();
        String fee = feeField.getText();
        String description = descriptionField.getText();
        double newFee = Double.parseDouble(fee);

        if (id.isEmpty() || name.isEmpty() || duration.isEmpty() || fee.isEmpty() || description.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z\\s-]+$";
        String durationPattern = "^\\d+\\s+(weeks|months)$";
        String feePattern = "^\\d+(\\.\\d{1,2})?$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(durationPattern);
        boolean isValidFee = fee.matches(feePattern);
        boolean isValidDescription = description.matches(descriptionPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidDuration) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration").showAndWait();
            return;
        }

        if (!isValidFee) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee").showAndWait();
            return;
        }

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        boolean isSaved = therapyProgramBo.save(new TherapyProgramDto(
                id,
                name,
                duration,
                description,
                newFee
        ));

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Program Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program Save UnSuccessful").showAndWait();
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) throws SQLException {
        String id = programIdField.getText();
        String name = nameField.getText();
        String duration = durationField.getText();
        String fee = feeField.getText();
        String description = descriptionField.getText();
        double newFee = Double.parseDouble(fee);

        if (id.isEmpty() || name.isEmpty() || duration.isEmpty() || fee.isEmpty() || description.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z\\s-]+$";
        String durationPattern = "^\\d+\\s+(weeks|months)$";
        String feePattern = "^\\d+(\\.\\d{1,2})?$";
        String descriptionPattern = "^[A-Za-z0-9\\s.,!-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidDuration = duration.matches(durationPattern);
        boolean isValidFee = fee.matches(feePattern);
        boolean isValidDescription = description.matches(descriptionPattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").showAndWait();
            return;
        }

        if (!isValidDuration) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration").showAndWait();
            return;
        }

        if (!isValidFee) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee").showAndWait();
            return;
        }

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").showAndWait();
            return;
        }

        boolean isSaved = therapyProgramBo.update(new TherapyProgramDto(
                id,
                name,
                duration,
                description,
                newFee
        ));

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Program Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program Save UnSuccessful").showAndWait();
        }
    }

    void load() throws SQLException {
        ArrayList<TherapyProgramDto> therapistDtos = therapyProgramBo.getAll();

        ObservableList<TherapyProgramTm> programTms = FXCollections.observableArrayList();

        for (TherapyProgramDto therapyProgramDto : therapistDtos){
            TherapyProgramTm therapyProgramTm = new TherapyProgramTm();
            therapyProgramTm.setT_id(therapyProgramDto.getT_id());
            therapyProgramTm.setName(therapyProgramDto.getName());
            therapyProgramTm.setDuration(therapyProgramDto.getDuration());
            therapyProgramTm.setFee(therapyProgramDto.getFee());
            therapyProgramTm.setDescription(therapyProgramDto.getDescription());

            programTms.add(therapyProgramTm);
        }

        programTable.setItems(programTms);
    }

    void reset() throws SQLException {
        String id = therapyProgramBo.getNextId();
        programIdField.setText(id);

        load();

        nameField.clear();
        descriptionField.clear();
        durationField.clear();
        feeField.clear();

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
    }

    @FXML
    void tblOnClicked(MouseEvent event) {
        TherapyProgramTm therapistTm = (TherapyProgramTm) programTable.getSelectionModel().getSelectedItem();

        if (therapistTm != null) {
            programIdField.setText(therapistTm.getT_id());
            nameField.setText(therapistTm.getName());
            durationField.setText(therapistTm.getDuration());
            feeField.setText(String.valueOf(therapistTm.getFee()));
            descriptionField.setText(therapistTm.getDescription());
        }

        saveButton.setDisable(true);
        deleteButton.setDisable(false);
        updateButton.setDisable(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programIdColumn.setCellValueFactory(new PropertyValueFactory<>("T_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        try{
            reset();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void btnReset(ActionEvent actionEvent) throws SQLException {
        reset();
    }

    public void LinkTherapist(ActionEvent actionEvent) {

    }
}
