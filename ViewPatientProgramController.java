package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ViewPatientProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
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

public class ViewPatientProgramController implements Initializable {

    @FXML
    private Button btnClose;

    @FXML
    private Button btnViewDetails;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private TableColumn<TherapyProgramTm, String> colProgramId;

    @FXML
    private TableColumn<TherapyProgramTm, String> colProgramName;

    @FXML
    private Label lblPatientName;

    @FXML
    private TableView<TherapyProgramTm> tblPatientPrograms;

    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    ViewPatientProgramBo viewPatientProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.VIEW_PATIENT_PROGRAMS);


    @FXML
    void btnCloseOnAction(ActionEvent event) {
        cmbPatientId.setValue(null);
        lblPatientName.setText(null);
        tblPatientPrograms.setItems(null);
    }

    @FXML
    void btnViewDetailsOnAction(ActionEvent event) throws SQLException {
        String patientId = cmbPatientId.getValue();

        if (patientId == null || patientId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a patient").showAndWait();
            return;
        }

        ArrayList<TherapyProgramDto> therapyProgramDtos = viewPatientProgramBo.getProgramsByPatientId(patientId);

        ObservableList<TherapyProgramTm> therapyProgramTms = FXCollections.observableArrayList();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            TherapyProgramTm therapyProgramTm = new TherapyProgramTm();
            therapyProgramTm.setT_id(therapyProgramDto.getT_id());
            therapyProgramTm.setName(therapyProgramDto.getName());

            therapyProgramTms.add(therapyProgramTm);
        }

        tblPatientPrograms.setItems(therapyProgramTms);
    }

    @FXML
    void cmbPatientIdOnAction(ActionEvent event) throws SQLException {

        String patientId = cmbPatientId.getValue();

        if (patientId == null) {
            return;
        }
        PatientDto patientDto = patientBo.findById(patientId);
        lblPatientName.setText(patientDto.getName());

    }
    @FXML
    void tblPatientProgramsOnMouseClicked(MouseEvent event) {

    }


    void load() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();
        ArrayList<String> therapyProgramIds = new ArrayList<>();

        for (PatientDto patientDto : patientDtos) {
            therapyProgramIds.add(patientDto.getP_id());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(therapyProgramIds);
        cmbPatientId.setItems(therapists);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("t_id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
