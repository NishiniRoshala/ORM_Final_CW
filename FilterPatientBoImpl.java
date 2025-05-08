package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.FilterPatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;

import java.sql.Date;
import java.util.ArrayList;

public class FilterPatientBoImpl implements FilterPatientBo {
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    TherapySessionSchedulingBo therapySessionSchedulingBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_SESSION_SCHEDULING_BO);
    @Override
    public ArrayList<PatientDto> findPatientByProgramId(String ProId) throws Exception {
        return therapyProgramBo.findPatientByProgramId(ProId);
    }

    @Override
    public ArrayList<PatientDto> findPatientByDate(Date date) throws Exception {
        return therapySessionSchedulingBo.findBYDate(date);
    }

    @Override
    public ArrayList<PatientDto> findPatientByStatus(String status) throws Exception {
        return therapySessionSchedulingBo.findBYStatus(status);
    }
}
