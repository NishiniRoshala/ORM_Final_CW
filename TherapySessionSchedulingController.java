package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.PatientTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapistTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapyProgramTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapySessionSchedulingTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.dialect.function.array.ArrayAggFunction;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapySessionSchedulingController implements Initializable {

    @FXML
    private Button btnBook;

    @FXML
    private Button btnReschedule;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbSessionStatus;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, Time> colEndTime;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colId;

    @FXML
    private TableColumn<PatientTm, String> colPatientId;

    @FXML
    private TableColumn<TherapyProgramTm, String> colProgramId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, Date> colSessionDate;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colSessionStatus;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, Time> colStartTime;

    @FXML
    private TableColumn<TherapistTm, String> colTherapistId;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private Label lblSessionId;

    @FXML
    private TableView<TherapySessionSchedulingTm> tblSessions;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);
    TherapySessionSchedulingBo therapySessionSchedulingBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_SESSION_SCHEDULING);

    @FXML
    void btnBookOnAction(ActionEvent event) throws Exception {
        String sessionId = lblSessionId.getText();
        String patientId = cmbPatientId.getValue();
        String therapistId = cmbTherapistId.getValue();
        String therapyProgramId = cmbProgramId.getValue();
        String status = cmbSessionStatus.getValue();
        Date sessionDate = Date.valueOf(dpSessionDate.getValue());
       String startTime = txtStartTime.getText();
       String endTime = txtEndTime.getText();

        if (sessionId.isEmpty() ||sessionDate == null || startTime == null || endTime == null || dpSessionDate.getValue() == null || status.isEmpty() || therapyProgramId.isEmpty() || therapistId.isEmpty() || patientId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }
        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        boolean isValidStartTime = startTime.toString().matches(timePattern);
        boolean isValidEndTime = endTime.toString().matches(timePattern);

        if (!isValidStartTime) {
            new Alert(Alert.AlertType.ERROR, "Invalid start time").showAndWait();
            return;
        }

        if (!isValidEndTime) {
            new Alert(Alert.AlertType.ERROR, "Invalid end time").showAndWait();
            return;
        }

        Time newStartTime = Time.valueOf(txtStartTime.getText());
        Time newEndTime = Time.valueOf(txtEndTime.getText());

        TherapySessionSchedulingDto therapySessionSchedulingDto = new TherapySessionSchedulingDto();

        therapySessionSchedulingDto.setId(sessionId);
        therapySessionSchedulingDto.setStartTime(newStartTime);
        therapySessionSchedulingDto.setEndTime(newEndTime);
        therapySessionSchedulingDto.setDate(sessionDate);
        therapySessionSchedulingDto.setStatus(status);
        therapySessionSchedulingDto.setTherapyProgramId(therapyProgramId);
        therapySessionSchedulingDto.setTherapistId(therapistId);
        therapySessionSchedulingDto.setPatientId(patientId);


        boolean idBooked = therapySessionSchedulingBo.save(therapySessionSchedulingDto);

        if (idBooked) {
            new Alert(Alert.AlertType.INFORMATION, "Session booked successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to booked session").showAndWait();
        }
    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) throws Exception {
        String sessionId = lblSessionId.getText();
        String patientId = cmbPatientId.getValue();
        String therapistId = cmbTherapistId.getValue();
        String therapyProgramId = cmbProgramId.getValue();
        String status = cmbSessionStatus.getValue();
        Date sessionDate = Date.valueOf(dpSessionDate.getValue());
        String startTime = txtStartTime.getText();
        String endTime = txtEndTime.getText();

        if (sessionId.isEmpty() ||sessionDate == null || startTime == null || endTime == null || dpSessionDate.getValue() == null || status.isEmpty() || therapyProgramId.isEmpty() || therapistId.isEmpty() || patientId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").showAndWait();
            return;
        }
        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        boolean isValidStartTime = startTime.toString().matches(timePattern);
        boolean isValidEndTime = endTime.toString().matches(timePattern);

        if (!isValidStartTime) {
            new Alert(Alert.AlertType.ERROR, "Invalid start time").showAndWait();
            return;
        }

        if (!isValidEndTime) {
            new Alert(Alert.AlertType.ERROR, "Invalid end time").showAndWait();
            return;
        }
        Time newStartTime = Time.valueOf(txtStartTime.getText());
        Time newEndTime = Time.valueOf(txtEndTime.getText());

        TherapySessionSchedulingDto therapySessionSchedulingDto = new TherapySessionSchedulingDto();

        therapySessionSchedulingDto.setId(sessionId);
        therapySessionSchedulingDto.setStartTime(newStartTime);
        therapySessionSchedulingDto.setEndTime(newEndTime);
        therapySessionSchedulingDto.setDate(sessionDate);
        therapySessionSchedulingDto.setStatus(status);
        therapySessionSchedulingDto.setTherapyProgramId(therapyProgramId);
        therapySessionSchedulingDto.setTherapistId(therapistId);
        therapySessionSchedulingDto.setPatientId(patientId);


        boolean idBooked = therapySessionSchedulingBo.update(therapySessionSchedulingDto);

        if (idBooked) {
            new Alert(Alert.AlertType.INFORMATION, "Session rescheduled successfully").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to reschedule session").showAndWait();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
                 reset();
    }

    @FXML
    void tblSessionsOnMouseClicked(MouseEvent event) {

        TherapySessionSchedulingTm therapySessionSchedulingTm = tblSessions.getSelectionModel().getSelectedItem();
        if (therapySessionSchedulingTm != null) {
            lblSessionId.setText(therapySessionSchedulingTm.getId());
            dpSessionDate.setValue(therapySessionSchedulingTm.getDate().toLocalDate());
            txtStartTime.setText(therapySessionSchedulingTm.getStartTime().toString());
            txtEndTime.setText(therapySessionSchedulingTm.getEndTime().toString());
            cmbPatientId.setValue(therapySessionSchedulingTm.getPatientId());
            cmbTherapistId.setValue(therapySessionSchedulingTm.getTherapistId());
            cmbProgramId.setValue(therapySessionSchedulingTm.getTherapyProgramId());
            cmbSessionStatus.setValue(therapySessionSchedulingTm.getStatus());

        }
        btnBook.setDisable(false);
        btnReschedule.setDisable(false);
    }

    void ProgramIds() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();
        ObservableList<String> programIds = FXCollections.observableArrayList();
        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            programIds.add(therapyProgramDto.getT_id());
        }
        cmbProgramId.setItems(programIds);
    }

    void LoadPatientId() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();
        ObservableList<String> patientIds = FXCollections.observableArrayList();
        for (PatientDto patientDto : patientDtos) {
            patientIds.add(patientDto.getP_id());
        }
        cmbPatientId.setItems(patientIds);
    }

    void TherapistIds() throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();
        ObservableList<String> therapistIds = FXCollections.observableArrayList();
        for (TherapistDto therapistDto : therapistDtos) {
            therapistIds.add(therapistDto.getId());
        }
        cmbTherapistId.setItems(therapistIds);
    }

    void load() throws SQLException {
        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = therapySessionSchedulingBo.getAll();

        ObservableList<TherapySessionSchedulingTm> therapySessionSchedulingTms = FXCollections.observableArrayList();

        for (TherapySessionSchedulingDto therapySessionSchedulingDto : therapySessionSchedulingDtos) {
            TherapySessionSchedulingTm therapySessionSchedulingTm = new TherapySessionSchedulingTm();
            therapySessionSchedulingTm.setId(therapySessionSchedulingDto.getId());
            therapySessionSchedulingTm.setStartTime(therapySessionSchedulingDto.getStartTime());
            therapySessionSchedulingTm.setEndTime(therapySessionSchedulingDto.getEndTime());
            therapySessionSchedulingTm.setDate(therapySessionSchedulingDto.getDate());
            therapySessionSchedulingTm.setStatus(therapySessionSchedulingDto.getStatus());
            therapySessionSchedulingTm.setPatientId(therapySessionSchedulingDto.getPatientId());
            therapySessionSchedulingTm.setTherapistId(therapySessionSchedulingDto.getTherapistId());
            therapySessionSchedulingTm.setTherapyProgramId(therapySessionSchedulingDto.getTherapyProgramId());


            therapySessionSchedulingTms.add(therapySessionSchedulingTm);
        }

        tblSessions.setItems(therapySessionSchedulingTms);
    }

    void reset() throws SQLException {
        String id = therapySessionSchedulingBo.getNextId();
        lblSessionId.setText(id);

        load();

       cmbPatientId.setValue("");
       cmbTherapistId.setValue("");
       cmbProgramId.setValue("");
       dpSessionDate.setValue(null);
       cmbSessionStatus.setValue("");
        txtStartTime.setText("");
        txtEndTime.setText("");


       btnBook.setDisable(false);
       btnReschedule.setDisable(false);
    }

    void setCmbSessionStatus(){
        ObservableList<String> sessionStatus = FXCollections.observableArrayList();
        sessionStatus.add("Booked");
        sessionStatus.add("Rescheduled");
        sessionStatus.add("Cancelled");
        sessionStatus.add("Completed");
        cmbSessionStatus.setItems(sessionStatus);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       colId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
       colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
       colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));
       colSessionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
       colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
       colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
       colSessionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    try{
        load();
        reset();
        ProgramIds();
        LoadPatientId();
        TherapistIds();
        setCmbSessionStatus();
    }catch (SQLException e){
        throw new RuntimeException(e);
          }
    }
}
