package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ReportAndAnalizeBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapistBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.SessionStaticsDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class ReportAndAnalizeBoImpl implements ReportAndAnalizeBo {

    TherapistBo therapistBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPIST);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    TherapySessionSchedulingBo therapySessionSchedulingBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_SESSION_SCHEDULING_BO);


    @Override
    public ArrayList<TherapyProgramDto> findById(String id) throws Exception {
        List<TherapyProgram> therapyPrograms = therapistBo.getProgramsByTherapistId(id);

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : therapyPrograms){
            TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
            therapyProgramDto.setT_id(therapyProgram.getT_id());
            therapyProgramDto.setName(therapyProgram.getName());
            therapyProgramDto.setDuration(therapyProgram.getDuration());
            therapyProgramDto.setDescription(therapyProgram.getDescription());
            therapyProgramDto.setFee(therapyProgram.getFee());


            therapyProgramDtos.add(therapyProgramDto);
        }

        return therapyProgramDtos;
    }

    @Override
    public int[] getAllCounts(String therapistId, String therapyProgramId) {
        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = therapySessionSchedulingBo.getAllCounts(therapistId, therapyProgramId);

        int rescheduled = 0;
        int cancelled = 0;
        int completed = 0;
        int booked = 0;

        for (TherapySessionSchedulingDto therapySessionDto : therapySessionSchedulingDtos){
            if (therapySessionDto.getStatus().equals("Booked")){
                booked++;
            } else if (therapySessionDto.getStatus().equals("Rescheduled")){
                rescheduled++;
            } else if (therapySessionDto.getStatus().equals("Completed")){
                completed++;
            } else if (therapySessionDto.getStatus().equals("Cancelled")){
                cancelled++;
            }
        }

        int[] counts = new int[4];
        counts[0] = rescheduled;
        counts[1] = cancelled;
        counts[2] =completed;
        counts[3] = booked;

        return counts;

    }

    @Override
    public ArrayList<SessionStaticsDto> getAllDetails() throws Exception {
        return therapyProgramBo.getAllDetails();
    }
}
