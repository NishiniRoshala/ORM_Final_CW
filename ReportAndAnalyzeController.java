package com.assignment.orm.service.orm_final_course_work_health_care.Controllers;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ReportAndAnalizeBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.SessionStaticsDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TM.SessionStaticsTm;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportAndAnalyzeController  implements Initializable {
    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);
    ReportAndAnalizeBo reportAndAnalizeBo = BoFactory.getInstance().getBo(BoFactory.BoType.REPORT_AND_ANALYZE);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);



    @FXML
    private Button btnhistory;

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    @FXML
    private BarChart<String , Number> barChartPerformance;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbProgramName;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<SessionStaticsTm, Integer> colBookingCount;

    @FXML
    private TableColumn<SessionStaticsTm, Integer> colCancelledCount;

    @FXML
    private TableColumn<SessionStaticsTm, Integer> colCompletedCount;

    @FXML
    private TableColumn<SessionStaticsTm, String> colProgramId;

    @FXML
    private TableColumn<SessionStaticsTm, String> colProgramName;

    @FXML
    private TableColumn<SessionStaticsTm, Integer> colRescheduleCount;

    @FXML
    private Label lblTherapistName;

    @FXML
    private TableView<SessionStaticsTm> tblSessionStats;

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {

        String therapistId = cmbTherapistId.getValue();
        String sessionName = cmbProgramName.getValue();

        if (therapistId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select a therapist").showAndWait();
            return;
        }

        if (sessionName.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select a session").showAndWait();
            return;
        }

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findByName(sessionName);

        int[] arr = reportAndAnalizeBo.getAllCounts(therapistId , therapyProgramDto.getT_id());

        if (arr[0] == 0 && arr[1] == 0 && arr[2] == 0 && arr[3] == 0){
            new Alert(Alert.AlertType.ERROR, "No data");
            return;
        }

        loadChart(arr);


    }

    private void loadChart(int[] arr) {
        NumberAxis yAxis = (NumberAxis) barChartPerformance.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(20);
        yAxis.setTickUnit(5);

        XYChart.Series series = new XYChart.Series<>();

        barChartPerformance.setAnimated(false);

        series.getData().add(new XYChart.Data<>("Rescheduled Sessions", arr[0]));
        series.getData().add(new XYChart.Data<>("Cancelled Sessions", arr[1]));
        series.getData().add(new XYChart.Data<>("Completed Sessions", arr[2]));
        series.getData().add(new XYChart.Data<>("Booked Sessions", arr[3]));

        barChartPerformance.getData().clear();
        barChartPerformance.getData().addAll(series);

        barChartPerformance.requestLayout();

    }

    private void loadTable() throws Exception {
        ArrayList<SessionStaticsDto> sessionStatisticsDtos = reportAndAnalizeBo.getAllDetails();

        ObservableList<SessionStaticsTm> sessionStatisticsTms = FXCollections.observableArrayList();

        for (SessionStaticsDto sessionStatisticsDto : sessionStatisticsDtos) {
            SessionStaticsTm sessionStatisticsTm = new SessionStaticsTm();
            sessionStatisticsTm.setId(sessionStatisticsDto.getId());
            sessionStatisticsTm.setName(sessionStatisticsDto.getName());
            sessionStatisticsTm.setCompletedSessionCount(sessionStatisticsDto.getCompletedSessionCount());
            sessionStatisticsTm.setBookedSessionCount(sessionStatisticsDto.getBookedSessionCount());
            sessionStatisticsTm.setRescheduleSessionCount(sessionStatisticsDto.getRescheduleSessionCount());
            sessionStatisticsTm.setCanceledSessionCount(sessionStatisticsDto.getCanceledSessionCount());

            sessionStatisticsTms.add(sessionStatisticsTm);
        }

        tblSessionStats.setItems(sessionStatisticsTms);

    }

    private void loadTherapistIds() throws SQLException {
        ArrayList<TherapistDto> therapists = therapistBo.getAll();

        ArrayList<String> therapistIds = new ArrayList<>();

        for ( TherapistDto therapistDto : therapists) {
            therapistIds.add(therapistDto.getId());
        }

        ObservableList<String> therapistsIds = FXCollections.observableArrayList(therapistIds);
        cmbTherapistId.setItems(therapistsIds);
    }

    @FXML
    void cmbTherapistIdOnAction(ActionEvent event) throws Exception {
        barChartPerformance.getData().clear();

        String id = cmbTherapistId.getValue();

        if (id == null){
            return;
        }

        TherapistDto therapistDto = therapistBo.findById(id);
        lblTherapistName.setText(therapistDto.getName());

        ArrayList<TherapyProgramDto> therapyProgramDtoList = reportAndAnalizeBo.findById(id);

        if (therapyProgramDtoList == null){
            return;
        }

        ArrayList<String> names = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtoList) {
            names.add(therapyProgramDto.getName());
        }

        ObservableList<String> programNames = FXCollections.observableArrayList(names);
        cmbProgramName.setItems(programNames);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompletedCount.setCellValueFactory(new PropertyValueFactory<>("completedSessionCount"));
        colBookingCount.setCellValueFactory(new PropertyValueFactory<>("bookedSessionCount"));
        colRescheduleCount.setCellValueFactory(new PropertyValueFactory<>("rescheduleSessionCount"));
        colCancelledCount.setCellValueFactory(new PropertyValueFactory<>("canceledSessionCount"));

        try {
            loadTherapistIds();
            loadTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void historyonAction(ActionEvent actionEvent) throws IOException {
        Parent load =  FXMLLoader.load(getClass().getResource("/VIew/ViewPatientHistory.fxml"));
        Scene scene = new Scene(load);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Assign Program Form");

        stage.setResizable(false);
        stage.show();
    }
}

