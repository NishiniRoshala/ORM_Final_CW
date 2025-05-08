package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ViewAllTherapyProgramsBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.PatientTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewAllTherapyProgramsController implements Initializable {

    @FXML
    private TableColumn<PatientTm,String> colDate;

    @FXML
    private TableColumn<PatientTm,String> colEmail;

    @FXML
    private TableColumn<PatientTm,String> colId;

    @FXML
    private TableColumn<PatientTm,String> colName;

    @FXML
    private TableView<PatientTm> tblPatients;


    ViewAllTherapyProgramsBo viewAllTherapyProgramsBo = BoFactory.getInstance().getBo(BoFactory.BoType.VIEW_PATIENT_IN_ALL_PROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void load() {
        ArrayList<PatientDto> patientDtos = viewAllTherapyProgramsBo.checkPatientInPrograms();
        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : patientDtos) {
            PatientTm patientTm = new PatientTm();
            patientTm.setP_id(patientDto.getP_id());
            patientTm.setName(patientDto.getName());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setDate(patientDto.getDate());

            patientTms.add(patientTm);
        }

        tblPatients.setItems(patientTms);

    }
}
