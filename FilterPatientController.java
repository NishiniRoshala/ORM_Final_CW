package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.FilterPatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.PatientTm;
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
import java.sql.Date;
import java.util.ResourceBundle;




public class FilterPatientController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbSessionStatus;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colPatientId;

    @FXML
    private DatePicker dates;

    @FXML
    private TableView<PatientTm> tblPatients;

    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    FilterPatientBo filterPatientBo = BoFactory.getInstance().getBo(BoFactory.BoType.FILTER_PATIENT);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbProgramId.setValue(null);
        dates.setValue(null);
        cmbSessionStatus.setValue(null);
        tblPatients.setItems(null);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws Exception {
        String programId = cmbProgramId.getValue();
        String sessionStatus = cmbSessionStatus.getValue();

      ArrayList<PatientDto> patientDtos = new ArrayList<>();

        if (programId != null && sessionStatus == null & dates.getValue() == null) {
            patientDtos = filterPatientBo.findPatientByProgramId(programId);
        } else if (dates.getValue() != null && programId == null && sessionStatus == null) {
          Date date = Date.valueOf(dates.getValue());
            patientDtos = filterPatientBo.findPatientByDate(date);
        } else if (sessionStatus != null && programId == null && dates.getValue() == null) {
            patientDtos = filterPatientBo.findPatientByStatus(sessionStatus);

        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            cmbProgramId.setValue(null);
            dates.setValue(null);
            cmbSessionStatus.setValue(null);
            return;
        }

        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos) {
            PatientTm patientTm = new PatientTm();
            patientTm.setP_id(patientDto.getP_id());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setContact(patientDto.getContact());

            patientTms.add(patientTm);

        }

        tblPatients.setItems(patientTms);
    }

    @FXML
    void tblPatientsOnMouseClicked(MouseEvent event) {

    }

    void loadAll() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();
        ArrayList<String> therapyProgramIds = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            therapyProgramIds.add(therapyProgramDto.getT_id());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(therapyProgramIds);
        cmbProgramId.setItems(therapists);

    }

    void setSessionStatus() {
        ObservableList<String> sessionStatus = FXCollections.observableArrayList("Booked", "Rescheduled", "Cancelled", "Completed");
        cmbSessionStatus.setItems(sessionStatus);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));


        try {
            loadAll();
            setSessionStatus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
