package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistDashBoardController {

    @FXML
    private Button btnCredentialManage;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPatientManage;

    @FXML
    private Button btnPaymentInvoiceManage;

    @FXML
    private Button btnReportingAnalytics;

    @FXML
    private Button btnTherapySessionScheduling;

    @FXML
    private Label lblAvailableTherapists;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblDashboardTitle;

    @FXML
    private Label lblPendingPayments;

    @FXML
    private Label lblReceptionistName;

    @FXML
    private Label lblTodaysAppointments;

    @FXML
    private Label lblTodaysNewPatients;

    @FXML
    private Label lblTotalPatients;

    @FXML
    private Label lblTotalSessions;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane subAnchorPaneOne;

    User user = new User();

    @FXML
    void btnCredentialManageOnAction(ActionEvent event) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/VIew/ChangeCredential.fxml"));
        subAnchorPaneOne.getChildren().add(parent);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) mainAnchorPane.getScene().getWindow();
        currentStage.close();
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/Login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnPatientManageOnAction(ActionEvent event) throws IOException {

        user.setRole("Receptionist");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VIew/PatientManage.fxml"));
        Parent root = fxmlLoader.load();



        PatientManageController patientManageController = fxmlLoader .getController();
        patientManageController.setPatientManageController(this);

        subAnchorPaneOne.getChildren().clear();
        subAnchorPaneOne.getChildren().add(root);

    }

    @FXML
    void btnPaymentInvoiceManageOnAction(ActionEvent event) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/VIew/Payment.fxml"));
        subAnchorPaneOne.getChildren().add(parent);

    }

    @FXML
    void btnReportingAnalyticsOnAction(ActionEvent event) throws IOException {
        subAnchorPaneOne.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/VIew/ReportAndAnalize.fxml"));
        subAnchorPaneOne.getChildren().add(parent);

    }

    @FXML
    void btnTherapySessionSchedulingOnAction(ActionEvent event) throws IOException {

        subAnchorPaneOne.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/VIew/TherapySessionScheduling.fxml"));
        subAnchorPaneOne.getChildren().add(parent);

    }

}
