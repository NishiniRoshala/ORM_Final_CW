package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapistTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.TherapyProgramTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Therapist;
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
import java.util.List;
import java.util.ResourceBundle;



import static org.slf4j.LoggerFactoryFriend.reset;

public class TherapistManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnTrackSchedule;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapistDto, String> colContact;

    @FXML
    private TableColumn<TherapistDto, String> colEmail;

    @FXML
    private TableColumn<TherapistDto, String> colPassword;

    @FXML
    private TableColumn<TherapistDto, String> colStatus;

    @FXML
    private TableColumn<TherapistDto, String> colTherapistId;

    @FXML
    private TableColumn<TherapistDto, String> colTherapistName;

    @FXML
    private Button delete;

    @FXML
    private Label labTherapistId;

    @FXML
    private ListView<String> programList;

    @FXML
    private Button resets;

    @FXML
    private Button save;

    @FXML
    private TableView<TherapistTm> tbTherapists;

    @FXML
    private Button trshedule;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button update;

    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);

    @FXML
    void btnDelete(ActionEvent event) throws SQLException {
        boolean isDelete = therapistBo.delete(labTherapistId.getText());

        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to Therapist user").showAndWait();
        }

    }

    @FXML
    void btnSave(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String password = txtPassword.getText();
        String status = txtStatus.getText();

        List<String> list = new ArrayList<>();

        for (String s : programList.getSelectionModel().getSelectedItems()) {
            list.add(s);
        }

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);

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

        boolean isSave = therapistBo.save(new TherapistDto(id,name,email,password,status,contact),list);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Therapist Save UnSuccessful").showAndWait();
        }
    }

    @FXML
    void btnTrack(ActionEvent event) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/TrackShedule.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Schedule Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnreset(ActionEvent event) throws SQLException {
            reset();

    }

    void reset () throws SQLException {
        String id = therapistBo.getNextId();
        labTherapistId.setText(id);

        loadTable();

        txtName.clear();
        txtContact.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtStatus.clear();

        save.setDisable(false);
        delete.setDisable(true);
        update.setDisable(true);

    }

    @FXML
    void btnupdate(ActionEvent event) throws SQLException {
        String id = labTherapistId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String password = txtPassword.getText();
        String status = txtStatus.getText();

        List<String> list = new ArrayList<>();

        for (String s : programList.getSelectionModel().getSelectedItems()) {
            list.add(s);
        }

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidContact = contact.matches(contactPattern);

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

        boolean isSave = therapistBo.update(new TherapistDto(id,name,email,password,status,contact),list);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Therapist Save Successful").showAndWait();
            reset();
        }else {
            new Alert(Alert.AlertType.ERROR, "Therapist Save UnSuccessful").showAndWait();
        }
    }

    @FXML
    void tbTherapistOnMouseClicked(MouseEvent event) {
        TherapistTm therapistTm = tbTherapists.getSelectionModel().getSelectedItem();

        if (therapistTm != null){
            labTherapistId.setText(therapistTm.getId());
            txtName.setText(therapistTm.getName());
            txtEmail.setText(therapistTm.getEmail());
            txtContact.setText(therapistTm.getContact());
            txtPassword.setText(therapistTm.getPassword());
            txtStatus.setText(therapistTm.getStatus());
        }

        save.setDisable(true);
        delete.setDisable(false);
        update.setDisable(false);

    }

    void loadName() throws SQLException {
        ArrayList<TherapyProgramDto> therapistDtos = therapyProgramBo.getAll();

        ArrayList<String> therapistNames = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapistDtos) {
            therapistNames.add(therapyProgramDto.getName());
        }

        programList.setItems(FXCollections.observableArrayList(therapistNames));

    }

    void  loadTable () throws SQLException {
        ArrayList<TherapistDto> therapistDtos = therapistBo.getAll();

        ObservableList<TherapistTm> therapistTms = FXCollections.observableArrayList();

        for (TherapistDto therapistDto : therapistDtos){
            TherapistTm therapistTm = new TherapistTm();
            therapistTm.setId(therapistDto.getId());
            therapistTm.setName(therapistDto.getName());
            therapistTm.setEmail(therapistDto.getEmail());
            therapistTm.setContact(therapistDto.getContact());
            therapistTm.setPassword(therapistDto.getPassword());
            therapistTm.setStatus(therapistDto.getStatus());

            therapistTms.add(therapistTm);
        }

        tbTherapists.setItems(therapistTms);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        programList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            reset();
            loadName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
