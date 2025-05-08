package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ViewAllTherapyProgramsBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.QueryDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.Patient;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ViewAllTherapyProgramsImpl implements ViewAllTherapyProgramsBo {

    QueryDao queryDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.QUERY);
    @Override
    public ArrayList<PatientDto> checkPatientInPrograms() {
     ArrayList<Patient> patient = queryDao.checkPatientInPrograms();
     ArrayList<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient1 : patient) {
            patientDtos.add(new PatientDto(
                    patient1.getP_id(),
                    patient1.getName(),
                    patient1.getAddress(),
                    patient1.getContact(),
                    patient1.getEmail(),
                    patient1.getHistory(),
                    patient1.getDate()));
        }
        return patientDtos;

    }
}
