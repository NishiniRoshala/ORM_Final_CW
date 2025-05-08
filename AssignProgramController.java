package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ProgramDetailsBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.ProgramDetailsDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.ProgramDetailsTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.ProgramDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AssignProgramController implements Initializable {

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<String> cmbPatientName;

    @FXML
    private ComboBox<String> cmbProgramName;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colPatientId;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colProgramId;

    @FXML
    private TableColumn<ProgramDetailsTm, String> colProgramName;

    @FXML
    private Label lbPatientId;

    @FXML
    private Label lbProgramId;

    @FXML
    private TableView<ProgramDetailsTm> tblAssignments;

    ProgramDetailsBo programDetailsBo = BoFactory.getInstance().getBo(BoFactory.BoType.PROGRAM_DETAILS);
    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);


    @FXML
    void btnAssignOnAction(ActionEvent event) throws Exception {
        String patientId = lbPatientId.getText();
        String therapyProgramId = lbProgramId.getText();

        boolean isAssigned = programDetailsBo.save(patientId,therapyProgramId);

        if (isAssigned){
            new Alert(Alert.AlertType.INFORMATION, "Patient ASSIGNED  Successfully");
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to ASSIGNED Patient");
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
         String patientId = lbPatientId.getText();
         String therapyProgramId = lbProgramId.getText();

         boolean isDeleted = programDetailsBo.delete(patientId,therapyProgramId);

         if (isDeleted){
             new Alert(Alert.AlertType.INFORMATION, "Patient deleted Successfully");
             reset();
         }else {
             new Alert(Alert.AlertType.ERROR, "Failed to delete Patient");
         }
    }

    @FXML
    void cmbPatientNameOnAction(ActionEvent event) throws SQLException {
        String PatientName = cmbPatientName.getSelectionModel().getSelectedItem();


        if (PatientName == null || PatientName.isEmpty()) {
            return;
        }
        PatientDto patientDto = patientBo.findByName(PatientName);

        if (patientDto == null) {
            return;
        }
        lbPatientId.setText(patientDto.getP_id());

    }

    @FXML
    void cmbProgramNameOnAction(ActionEvent event) throws SQLException {
    String teherapyProgramname = cmbProgramName.getSelectionModel().getSelectedItem();

    if ( teherapyProgramname == null ||  teherapyProgramname.isEmpty()){
        return;
    }
        TherapyProgramDto therapyProgramDto = therapyProgramBo.findByName( teherapyProgramname);

        if (therapyProgramDto != null){
            lbProgramId.setText(therapyProgramDto.getT_id());
        }
    }

    @FXML
    void tblAssignmentsOnMouseClicked(MouseEvent event) {
    ProgramDetailsTm programDetailsTm = tblAssignments.getSelectionModel().getSelectedItem();

    if (programDetailsTm != null){

        lbPatientId.setText(programDetailsTm.getPatient());
        lbProgramId.setText(programDetailsTm.getTherapyProgram());

    }
    btnAssign.setDisable(true);
    btnDelete.setDisable(false);
    }

    void reset() throws Exception {
            loadTableData();

            lbPatientId.setText("");
            lbProgramId.setText("");
            cmbPatientName.setValue("");
            cmbProgramName.setValue("");

            btnAssign.setDisable(false);
            btnDelete.setDisable(true);
    }

    void loadTableData() throws Exception {

        ArrayList<ProgramDetailsDto> therapyProgramDtos = programDetailsBo.getAll();

        ObservableList<ProgramDetailsTm> programDetailsTms = FXCollections.observableArrayList();

        for (ProgramDetailsDto therapyProgramDto : therapyProgramDtos) {
            ProgramDetailsTm programDetailsTm = new ProgramDetailsTm();
            programDetailsTm.setPatient(therapyProgramDto.getPatient());
            programDetailsTm.setTherapyProgram(therapyProgramDto.getTherapyProgram());
            programDetailsTm.setTherapyProgramName(therapyProgramDto.getTherapyProgramName());
            programDetailsTms.add(programDetailsTm);
        }
        tblAssignments.setItems(programDetailsTms);



    }

    void LoadPatientId() throws SQLException {
        ArrayList<PatientDto> patientDtos = patientBo.getAll();

        ArrayList<String> patientIds = new ArrayList<>();

        for (PatientDto patientDto : patientDtos) {
            patientIds.add(patientDto.getName());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(patientIds);
        cmbPatientName.setItems(therapists);
    }

    void LoadProgramId() throws SQLException {
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();

        ArrayList<String> therapyProgramIds = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos) {
            therapyProgramIds.add(therapyProgramDto.getName());
        }

        ObservableList<String> therapists = FXCollections.observableArrayList(therapyProgramIds);
        cmbProgramName.setItems(therapists);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient"));
            colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgram"));
            colProgramName.setCellValueFactory(new PropertyValueFactory<>("therapyProgramName"));

            try{
            reset();
            LoadPatientId();
            LoadProgramId();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
