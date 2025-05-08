package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.TherapistDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapistDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Therapist;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBoImpl implements TherapistBo {

    TherapistDao therapistDao  = DaoFactory.getInstance().getDao(DaoFactory.DaoType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    @Override
    public String getNextId() throws SQLException {
        String id = therapistDao.getNextId();
        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newId = i + 1;
            return String.format("T%03d", newId);
        }
        return "T001";
    }

    @Override
    public ArrayList<TherapistDto> getAll() throws SQLException {
        ArrayList<Therapist> therapists = therapistDao.getAll();

        ArrayList<TherapistDto> therapistDtos = new ArrayList<>();

        for (Therapist therapist : therapists) {
            therapistDtos.add(new TherapistDto(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getEmail(),
                    therapist.getPassword(),
                    therapist.getStatus(),
                    therapist.getContact()
            ));
        }
        return therapistDtos    ;
    }

    @Override
    public boolean save(TherapistDto therapistDto, List<String> Name) throws SQLException {
        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setContact(therapistDto.getContact());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setPassword(therapistDto.getPassword());
        therapist.setStatus(therapistDto.getStatus());

        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBo.getAll();
        List<TherapyProgram> therapyPrograms = new ArrayList<>();////

        for (TherapyProgramDto therapyProgramDto1 : therapyProgramDtos) {
            for (String s : Name) {
                if (s.equals(therapyProgramDto1.getName())) {
                    TherapyProgram therapyProgram = new TherapyProgram();
                    therapyProgram.setT_id(therapyProgramDto1.getT_id());
                    therapyProgram.setName(therapyProgramDto1.getName());
                    therapyProgram.setDuration(therapyProgramDto1.getDuration());
                    therapyProgram.setDescription(therapyProgramDto1.getDescription());
                    therapyProgram.setFee(therapyProgramDto1.getFee());


                    therapyPrograms.add(therapyProgram);

                }
            }

        }
        therapist.setTherapyPrograms(therapyPrograms);
        return therapistDao.save(therapist);

    }


    @Override
    public boolean delete(String Id) throws SQLException {
        return therapistDao.delete(Id);
    }

    @Override
    public boolean update(TherapistDto therapistDto, List<String> list) throws SQLException {
        Therapist therapist = new Therapist();
        therapist.setId(therapistDto.getId());
        therapist.setName(therapistDto.getName());
        therapist.setContact(therapistDto.getContact());
        therapist.setEmail(therapistDto.getEmail());
        therapist.setPassword(therapistDto.getPassword());
        therapist.setStatus(therapistDto.getStatus());

        return therapistDao.update(therapist);
    }


    @Override
    public TherapistDto findById(String id) throws Exception {
        Therapist therapist = therapistDao.findById(id);

        return new TherapistDto(
                therapist.getId(),
                therapist.getName(),
                therapist.getContact(),
                therapist.getPassword(),
                therapist.getEmail(),
                therapist.getStatus()
        );

    }

    @Override
    public TherapistDto findByName(String email) throws Exception {
        Therapist therapist = therapistDao.findByName(email);

        return new TherapistDto(
                therapist.getId(),
                therapist.getName(),
                therapist.getContact(),
                therapist.getPassword(),
                therapist.getEmail(),
                therapist.getStatus()
        );
    }

    @Override
    public List<TherapyProgram> getProgramsByTherapistId(String id) throws Exception {
        Therapist therapist = therapistDao.findById(id);

        List<TherapyProgram> therapyPrograms = therapist.getTherapyPrograms();

        return therapyPrograms;
    }
}
