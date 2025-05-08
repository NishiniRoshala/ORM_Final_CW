package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TrackScheduleBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.TherapistDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapistTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapySessionSchedulingTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TrackScheduleController implements Initializable {

    @FXML
    private Button btnClearSelection;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colPatientId;
    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colProgramId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colSessionDate;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, String> colSessionStatus;

    @FXML
    private TableColumn<TherapySessionSchedulingTm, Time> colSessionTime;

    @FXML
    private Label lblTherapistName;

    @FXML
    private TableView<TherapySessionSchedulingTm> tblTherapistSessions;

    TrackScheduleBo trackScheduleBo = BoFactory.getInstance().getBo(BoFactory.BoType.TRACK_SCHEDULE);
    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);

    @FXML
    void btnClearSelectionOnAction(ActionEvent event) {
        cmbTherapistId.setValue("");
        lblTherapistName.setText("");
        tblTherapistSessions.setItems(null);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws Exception {

        String therapistId = cmbTherapistId.getValue();

        if (therapistId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a therapist").showAndWait();
            return;
        }

        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = trackScheduleBo.checkTherapySessionSchedule(therapistId);
        if (therapySessionSchedulingDtos.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No session found").showAndWait();
            return;
        }
        ObservableList<TherapySessionSchedulingTm> therapistTms = FXCollections.observableArrayList();

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

            therapistTms.add(therapySessionSchedulingTm);
        }

        tblTherapistSessions.setItems(therapistTms);
    }

    @FXML
    void cmbTherapistIdOnAction(ActionEvent event) throws Exception {
        String therapistId = cmbTherapistId.getValue();

        if (therapistId == null || therapistId.isEmpty()) {
            return;
        }

        TherapistDto therapistDto = therapistBo.findById(therapistId);
        lblTherapistName.setText(therapistDto.getName());

    }


    void load() throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();
        ArrayList<String> therapistIds = new ArrayList<>();
        for (TherapistDto therapistDto : therapistDtos) {
            therapistIds.add(therapistDto.getId());
        }
        ObservableList<String> therapist = FXCollections.observableArrayList(therapistIds);
        cmbTherapistId.setItems(therapist);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));
        colSessionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSessionTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
