package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TrackScheduleBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;

import java.util.ArrayList;

public class TrackScheduleBoImpl implements TrackScheduleBo {
    TherapySessionSchedulingBo therapySessionSchedulingBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_SESSION_SCHEDULING_BO);

    @Override
    public ArrayList<TherapySessionSchedulingDto> checkTherapySessionSchedule(String id) throws Exception {
        return therapySessionSchedulingBo.checkByTherapistId(id);
    }
}
