package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.PatientDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Patient;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.ProgramDetails;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;
import org.springframework.security.core.parameters.P;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBoImpl implements PatientBo {

    PatientDao patientDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PATIENT);

    @Override
    public String getNextId() throws SQLException {

        String id = patientDao.getNextId();
        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex  = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public ArrayList<PatientDto> getAll() throws SQLException {
        ArrayList<Patient> patients = patientDao.getAll();
        ArrayList<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : patients) {
            patientDtos.add(new PatientDto(
                    patient.getP_id(),
                    patient.getName(),
                    patient.getAddress(),
                    patient.getContact(),
                    patient.getEmail(),
                    patient.getHistory(),
                    patient.getDate()));
        }
        return patientDtos;
    }

    @Override
    public boolean save(PatientDto patientDto) throws SQLException {

        Patient patient = new Patient();

        patient.setP_id(patientDto.getP_id());
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setContact(patientDto.getContact());
        patient.setEmail(patientDto.getEmail());
        patient.setHistory(patientDto.getHistory());
        patient.setDate(patientDto.getDate());

        return patientDao.save(patient);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return patientDao.delete(Id);
    }

    @Override
    public boolean update(PatientDto patientDto) throws SQLException {
        Patient patient = new Patient();

        patient.setP_id(patientDto.getP_id());
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setContact(patientDto.getContact());
        patient.setEmail(patientDto.getEmail());
        patient.setHistory(patientDto.getHistory());
        patient.setDate(patientDto.getDate());

        return patientDao.update(patient);
    }

    @Override
    public PatientDto findById(String p_id) throws SQLException {
        Patient patient = patientDao.findById(p_id);
        return new PatientDto(
                patient.getP_id(),
                patient.getName(),
                patient.getAddress(),
                patient.getContact(),
                patient.getEmail(),
                patient.getHistory(),
                patient.getDate());
    }

    @Override
    public PatientDto findByName(String name) throws SQLException {
        Patient patient = patientDao.findByName(name);
        return new PatientDto(
                patient.getP_id(),
                patient.getName(),
                patient.getAddress(),
                patient.getContact(),
                patient.getEmail(),
                patient.getHistory(),
                patient.getDate());

    }

    @Override
    public ArrayList<TherapyProgramDto> getProgramsByPatientId(String p_id) throws SQLException {
        Patient patient = patientDao.findById(p_id);

        List<ProgramDetails> therapyPrograms = patient.getProgramDetails();


        ArrayList<TherapyProgram> programs = new ArrayList<>();

        for (ProgramDetails programDetails : therapyPrograms) {
            programs.add(programDetails.getTherapyProgram());
        }

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : programs) {
            therapyProgramDtos.add(new TherapyProgramDto(
                    therapyProgram.getT_id(),
                    therapyProgram.getName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getDescription(),
                    therapyProgram.getFee()
            ));
        }

        return therapyProgramDtos;
    }
    }

