package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.TherapySessionSchedulingDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Patient;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Therapist;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapySessionScheduling;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class TherapySessionSchedulingBoImpl implements TherapySessionSchedulingBo {


    TherapySessionSchedulingDao therapySessionSchedulingDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPY_SESSION_SCHEDULING);
    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);

    @Override
    public ArrayList<TherapySessionSchedulingDto> checkByTherapistId(String id) {
        ArrayList<TherapySessionScheduling> therapySessionSchedulings = therapySessionSchedulingDao.checkByTherapistId(id);
        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = new ArrayList<>();

        for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings) {
            TherapySessionSchedulingDto therapySessionSchedulingDto= new TherapySessionSchedulingDto();
            therapySessionSchedulingDto.setId(therapySessionScheduling.getId());
            therapySessionSchedulingDto.setStartTime(therapySessionScheduling.getStartTime());
            therapySessionSchedulingDto.setEndTime(therapySessionScheduling.getEndTime());
            therapySessionSchedulingDto.setDate(therapySessionScheduling.getDate());
            therapySessionSchedulingDto.setStatus(therapySessionScheduling.getStatus());
            therapySessionSchedulingDto.setPatientId(therapySessionScheduling.getPatient().getP_id());
            therapySessionSchedulingDto.setTherapistId(therapySessionScheduling.getTherapist().getId());
            therapySessionSchedulingDto.setTherapyProgramId(therapySessionScheduling.getTherapyProgram().getT_id());
            therapySessionSchedulingDtos.add(therapySessionSchedulingDto);
        }
        return therapySessionSchedulingDtos;


    }

    @Override
    public ArrayList<PatientDto> findBYDate(Date date) {
        ArrayList<TherapySessionScheduling> therapySessionSchedulings = therapySessionSchedulingDao.findBYDate(date);
        ArrayList<Patient> patients = new ArrayList<>();

        for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings) {
            patients.add(therapySessionScheduling.getPatient());
        }

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
    public ArrayList<PatientDto> findBYStatus(String status) {
        ArrayList<TherapySessionScheduling> therapySessionSchedulings = therapySessionSchedulingDao.findBYStatus(status);
        ArrayList<Patient> patients = new ArrayList<>();

        for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings) {
            patients.add(therapySessionScheduling.getPatient());
        }

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
    public String getNextId() throws SQLException {
       String id = therapySessionSchedulingDao.getNextId();
      if (id != null) {
          String substring = id.substring(2);
          int i = Integer.parseInt(substring);
          int newId = i + 1;
          return String.format("TS%03d", newId);
      }
      return "TS001";
    }

    @Override
    public ArrayList<TherapySessionSchedulingDto> getAll() throws SQLException {
        ArrayList<TherapySessionScheduling> therapySessionSchedulings = therapySessionSchedulingDao.getAll();
        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = new ArrayList<>();

        for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings) {
           TherapySessionSchedulingDto therapySessionSchedulingDto= new TherapySessionSchedulingDto();
            therapySessionSchedulingDto.setId(therapySessionScheduling.getId());
            therapySessionSchedulingDto.setStartTime(therapySessionScheduling.getStartTime());
            therapySessionSchedulingDto.setEndTime(therapySessionScheduling.getEndTime());
            therapySessionSchedulingDto.setDate(therapySessionScheduling.getDate());
            therapySessionSchedulingDto.setStatus(therapySessionScheduling.getStatus());
            therapySessionSchedulingDto.setPatientId(therapySessionScheduling.getPatient().getP_id());
            therapySessionSchedulingDto.setTherapistId(therapySessionScheduling.getTherapist().getId());
            therapySessionSchedulingDto.setTherapyProgramId(therapySessionScheduling.getTherapyProgram().getT_id());

            therapySessionSchedulingDtos.add(therapySessionSchedulingDto);


        }
               return therapySessionSchedulingDtos;
    }

    @Override
    public boolean save(TherapySessionSchedulingDto therapySessionSchedulingDto) throws Exception {
        TherapistDto therapistDto = therapistBo.findById(therapySessionSchedulingDto.getTherapistId());
        TherapyProgramDto programDto = therapyProgramBo.findById(therapySessionSchedulingDto.getTherapyProgramId());
        PatientDto patientDto = patientBo.findById(therapySessionSchedulingDto.getPatientId());


        Therapist therapist = new Therapist();
        therapist.setId(therapySessionSchedulingDto.getTherapistId());
        therapist.setName(therapistDto.getName());
        therapist.setContact(therapistDto.getContact());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setPassword(therapistDto.getPassword());
        therapist.setStatus(therapistDto.getStatus());

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(programDto.getT_id());
        therapyProgram.setName(programDto.getName());
        therapyProgram.setDuration(programDto.getDuration());
        therapyProgram.setDescription(programDto.getDescription());
        therapyProgram.setFee(programDto.getFee());


        Patient patient = new Patient();
        patient.setP_id(patientDto.getP_id());
        patient.setName(patient.getName());
        patient.setAddress(patient.getAddress());
        patient.setContact(patient.getContact());
        patient.setEmail(patient.getEmail());
        patient.setHistory(patient.getHistory());

        TherapySessionScheduling therapySessionScheduling = new TherapySessionScheduling();
        therapySessionScheduling.setId(therapySessionSchedulingDto.getId());
        therapySessionScheduling.setStartTime(therapySessionSchedulingDto.getStartTime());
        therapySessionScheduling.setEndTime(therapySessionSchedulingDto.getEndTime());
        therapySessionScheduling.setDate(therapySessionSchedulingDto.getDate());
        therapySessionScheduling.setStatus(therapySessionSchedulingDto.getStatus());
        therapySessionScheduling.setTherapist(therapist);
        therapySessionScheduling.setTherapyProgram(therapyProgram);
        therapySessionScheduling.setPatient(patient);


        return therapySessionSchedulingDao.save(therapySessionScheduling);
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return therapySessionSchedulingDao.delete(Id);
    }

    @Override
    public boolean update(TherapySessionSchedulingDto therapySessionSchedulingDto) throws Exception {

        TherapistDto therapistDto = therapistBo.findById(therapySessionSchedulingDto.getTherapistId());
        TherapyProgramDto programDto = therapyProgramBo.findById(therapySessionSchedulingDto.getTherapyProgramId());
        PatientDto patientDto = patientBo.findById(therapySessionSchedulingDto.getPatientId());


        Therapist therapist = new Therapist();
        therapist.setId(therapySessionSchedulingDto.getTherapistId());
        therapist.setName(therapistDto.getName());
        therapist.setContact(therapistDto.getContact());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setPassword(therapistDto.getPassword());
        therapist.setStatus(therapistDto.getStatus());

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setT_id(programDto.getT_id());
        therapyProgram.setName(programDto.getName());
        therapyProgram.setDuration(programDto.getDuration());
        therapyProgram.setDescription(programDto.getDescription());
        therapyProgram.setFee(programDto.getFee());


        Patient patient = new Patient();
        patient.setP_id(patientDto.getP_id());
        patient.setName(patient.getName());
        patient.setAddress(patient.getAddress());
        patient.setContact(patient.getContact());
        patient.setEmail(patient.getEmail());
        patient.setHistory(patient.getHistory());

        TherapySessionScheduling therapySessionScheduling = new TherapySessionScheduling();
        therapySessionScheduling.setId(therapySessionSchedulingDto.getId());
        therapySessionScheduling.setStartTime(therapySessionSchedulingDto.getStartTime());
        therapySessionScheduling.setEndTime(therapySessionSchedulingDto.getEndTime());
        therapySessionScheduling.setDate(therapySessionSchedulingDto.getDate());
        therapySessionScheduling.setStatus(therapySessionSchedulingDto.getStatus());
        therapySessionScheduling.setTherapist(therapist);
        therapySessionScheduling.setTherapyProgram(therapyProgram);
        therapySessionScheduling.setPatient(patient);


        return therapySessionSchedulingDao.update(therapySessionScheduling);

    }

    @Override
    public ArrayList<TherapySessionSchedulingDto> getAllCounts(String therapistId, String therapyProgramId) {
        ArrayList<TherapySessionScheduling> therapySessionSchedulings = therapySessionSchedulingDao.getAllCounts(therapistId, therapyProgramId);

        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = new ArrayList<>();

        for (TherapySessionScheduling therapySessionScheduling : therapySessionSchedulings){
            TherapySessionSchedulingDto therapySessionSchedulingDto = new TherapySessionSchedulingDto();
            therapySessionSchedulingDto.setId(therapySessionScheduling.getId());
            therapySessionSchedulingDto.setStartTime(therapySessionScheduling.getStartTime());
            therapySessionSchedulingDto.setDate(therapySessionScheduling.getDate());
            therapySessionSchedulingDto.setStatus(therapySessionScheduling.getStatus());
            therapySessionSchedulingDto.setTherapyProgramId(therapySessionScheduling.getTherapyProgram().getT_id());
            therapySessionSchedulingDto.setId(therapySessionScheduling.getTherapist().getId());
            therapySessionSchedulingDto.setPatientId(therapySessionScheduling.getPatient().getP_id());

            therapySessionSchedulingDtos.add(therapySessionSchedulingDto);
        }

        return therapySessionSchedulingDtos;    }
}
