package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ViewPatientProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewPatientProgramBoImpl implements ViewPatientProgramBo {
    PatientBo  patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    @Override
    public ArrayList<TherapyProgramDto> getProgramsByPatientId(String patientId) throws SQLException {
        return patientBo.getProgramsByPatientId(patientId);
    }
}
