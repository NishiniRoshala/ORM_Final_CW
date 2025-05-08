package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.TherapyProgramDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.SessionStaticsDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Patient;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.ProgramDetails;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapySessionScheduling;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBoImpl implements TherapyProgramBo {

    TherapyProgramDao therapyProgramDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPY_PROGRAM);

    @Override
    public String getNextId() throws SQLException {
        String id = therapyProgramDao.getNextId();
        if (id != null) {
            String substring = id.substring(2);
            int i = Integer.parseInt(substring);
            int newId = i + 1;
            return String.format("TP%03d", newId);

        }
        return "TP001";
    }

    @Override
    public ArrayList<TherapyProgramDto> getAll() throws SQLException {
        ArrayList<TherapyProgram> therapyPrograms = therapyProgramDao.getAll();

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : therapyPrograms) {
            TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
            therapyProgramDto.setT_id(therapyProgram.getT_id());
            therapyProgramDto.setName(therapyProgram.getName());
            therapyProgramDto.setDescription(therapyProgram.getDescription());
            therapyProgramDto.setDuration(therapyProgram.getDuration());
            therapyProgramDto.setFee(therapyProgram.getFee());

            therapyProgramDtos.add(therapyProgramDto);
        }

        return therapyProgramDtos;

    }

    @Override
    public boolean save(TherapyProgramDto therapyProgramDto) throws SQLException {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(therapyProgramDto.getT_id());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        return therapyProgramDao.save(therapyProgram);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return therapyProgramDao.delete(Id);
    }

    @Override
    public boolean update(TherapyProgramDto therapyProgramDto) throws SQLException {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(therapyProgramDto.getT_id());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        return therapyProgramDao.update(therapyProgram);
    }

    @Override
    public TherapyProgramDto findByName(String programName) {
        TherapyProgram therapyProgram = therapyProgramDao.findByName(programName);

        TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
        therapyProgramDto.setT_id(therapyProgram.getT_id());
        therapyProgramDto.setName(therapyProgram.getName());
        therapyProgramDto.setDuration(therapyProgram.getDuration());
        therapyProgramDto.setDescription(therapyProgram.getDescription());
        therapyProgramDto.setFee(therapyProgram.getFee());

        return therapyProgramDto;
    }


    @Override
    public TherapyProgramDto findById(String therapyProgramId) {
        TherapyProgram therapyProgram = therapyProgramDao.findById(therapyProgramId);

        TherapyProgramDto therapyProgramDto = new TherapyProgramDto();

        therapyProgramDto.setT_id(therapyProgram.getT_id());
        therapyProgramDto.setName(therapyProgram.getName());
        therapyProgramDto.setDuration(therapyProgram.getDuration());
        therapyProgramDto.setDescription(therapyProgram.getDescription());
        therapyProgramDto.setFee(therapyProgram.getFee());

        return therapyProgramDto;
    }

    @Override
    public ArrayList<PatientDto> findPatientByProgramId(String proId) {
        TherapyProgram therapyProgram = therapyProgramDao.findById(proId);

        List<ProgramDetails> programDetails = therapyProgram.getProgramDetails();

        ArrayList<Patient> patients = new ArrayList<>();

        for (ProgramDetails programDetails1 : programDetails){
            patients.add(programDetails1.getPatient());
        }

        ArrayList<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : patients){
            patientDtos.add(new PatientDto(
                    patient.getP_id(),
                    patient.getName(),
                    patient.getAddress(),
                    patient.getContact(),
                    patient.getEmail(),
                    patient.getHistory(),
                    patient.getDate()
            ));
        }
        return patientDtos;
    }

    @Override
    public ArrayList<SessionStaticsDto> getAllDetails() throws SQLException {
        ArrayList<TherapyProgram> therapyPrograms = therapyProgramDao.getAll();

        ArrayList<SessionStaticsDto> sessionStaticsDtos = new ArrayList<>();

        int completedCounts = 0;
        int bookedCounts = 0;
        int rescheduleCounts = 0;
        int canceledCounts = 0;

        for (TherapyProgram therapyProgram : therapyPrograms){
            SessionStaticsDto sessionStatisticsDto = new SessionStaticsDto();
            sessionStatisticsDto.setId(therapyProgram.getT_id());
            sessionStatisticsDto.setName(therapyProgram.getName());

            List<TherapySessionScheduling> therapySessionSchedulings = therapyProgram.getTherapySessionScheduling();

            for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings){
                if (therapySessionScheduling.getStatus().equals("Completed")){
                    completedCounts++;
                } else if (therapySessionScheduling.getStatus().equals("Booked")){
                    bookedCounts++;
                } else if (therapySessionScheduling.getStatus().equals("Rescheduled")){
                    rescheduleCounts++;
                } else if (therapySessionScheduling.getStatus().equals("Cancelled")){
                    canceledCounts++;
                }
            }

            sessionStatisticsDto.setCompletedSessionCount(completedCounts);
            sessionStatisticsDto.setBookedSessionCount(bookedCounts);
            sessionStatisticsDto.setRescheduleSessionCount(rescheduleCounts);
            sessionStatisticsDto.setCanceledSessionCount(canceledCounts);

            completedCounts = 0;
            bookedCounts = 0;
            rescheduleCounts = 0;
            canceledCounts = 0;


            sessionStaticsDtos.add(sessionStatisticsDto);
        }

        return sessionStaticsDtos;
    }


}
