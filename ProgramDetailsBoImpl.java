package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ProgramDetailsBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.ProgramDetailsDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.ProgramDetailsDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Patient;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.ProgramDetails;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.ProgramDetailsIds;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;


import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramDetailsBoImpl implements ProgramDetailsBo {

    ProgramDetailsDao programDetailsDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.PROGRAM_DETAILS);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);


    @Override
    public ArrayList<ProgramDetailsDto> getAll() throws SQLException {
        ArrayList<ProgramDetails> programDetails = programDetailsDao.getAll();

        ArrayList<ProgramDetailsDto> programDetailsDtos = new ArrayList<>();

        for (ProgramDetails programDetail : programDetails) {
            ProgramDetailsDto programDetailsDto = new ProgramDetailsDto();
            programDetailsDto.setPatient(programDetail.getPatient().getP_id());
            programDetailsDto.setTherapyProgram(programDetail.getTherapyProgram().getT_id());
            programDetailsDto.setTherapyProgramName(programDetail.getTherapyProgramName());

            programDetailsDtos.add(programDetailsDto);
        }
                      return programDetailsDtos;

        }

    @Override
    public boolean save(String patient_id, String program_id) throws SQLException {
        ProgramDetailsIds programDetailsIds = new ProgramDetailsIds(patient_id, patient_id);

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(program_id);
        PatientDto patientDto = patientBo.findById(patient_id);

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(therapyProgramDto.getT_id());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        Patient patient = new Patient();
        patient.setP_id(patientDto.getP_id());
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setContact(patientDto.getContact());
        patient.setEmail(patientDto.getEmail());
        patient.setHistory(patientDto.getHistory());
        patient.setDate(patientDto.getDate());

        ProgramDetails programDetails = new ProgramDetails();
        programDetails.setIds(programDetailsIds);
        programDetails.setPatient(patient);
        programDetails.setTherapyProgram(therapyProgram);
        programDetails.setTherapyProgramName(therapyProgram.getName());


        return programDetailsDao.save(programDetails);

    }

    @Override
    public boolean delete(String patient_id, String program_id) throws SQLException {
        ProgramDetailsIds programDetailsIds = new ProgramDetailsIds(patient_id, patient_id);

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(program_id);
        PatientDto patientDto = patientBo.findById(patient_id);

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(therapyProgramDto.getT_id());
        therapyProgram.setName(therapyProgramDto.getName());
        therapyProgram.setDuration(therapyProgramDto.getDuration());
        therapyProgram.setDescription(therapyProgramDto.getDescription());
        therapyProgram.setFee(therapyProgramDto.getFee());

        Patient patient = new Patient();
        patient.setP_id(patientDto.getP_id());
        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setContact(patientDto.getContact());
        patient.setEmail(patientDto.getEmail());
        patient.setHistory(patientDto.getHistory());
        patient.setDate(patientDto.getDate());

        ProgramDetails programDetails = new ProgramDetails();
        programDetails.setIds(programDetailsIds);
        programDetails.setPatient(patient);
        programDetails.setTherapyProgram(therapyProgram);
        programDetails.setTherapyProgramName(therapyProgram.getName());





        return programDetailsDao.delete(programDetails);

    }
}
