package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.PatientBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapyProgramBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.TherapySessionSchedulingBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ViewPatientHistoryBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.PatientDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapyProgramDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.TherapySessionSchedulingDto;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.ViewPatientHistoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewPatientHistoryBoImpl implements ViewPatientHistoryBo {

    PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);
    TherapySessionSchedulingBo therapySessionSchedulingBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_SESSION_SCHEDULING);
    TherapyProgramBo therapyProgramBo = BoFactory.getInstance().getBo(BoFactory.BoType.THERAPY_PROGRAM);
    @Override
    public ArrayList<String> getAllPatientIds() throws SQLException {
        ArrayList<PatientDto> patientsDtos = patientBo.getAll();

        ArrayList<String> patientIds = new ArrayList<>();

        for (PatientDto patientDto : patientsDtos) {
            patientIds.add(patientDto.getP_id());
        }

        return patientIds;
    }

    @Override
    public ArrayList<ViewPatientHistoryDto> loadPatientHistory(String id) throws SQLException {
        ArrayList<TherapySessionSchedulingDto> therapySessionSchedulingDtos = therapySessionSchedulingBo.checkByTherapistId(id);

        ArrayList<ViewPatientHistoryDto> viewPatientHistoryDtos1 = new ArrayList<>();

        for (TherapySessionSchedulingDto dto : therapySessionSchedulingDtos) {
            ViewPatientHistoryDto viewPatientHistoryDto = new ViewPatientHistoryDto();
            viewPatientHistoryDto.setProgramId(dto.getTherapyProgramId());
            viewPatientHistoryDto.setDate(dto.getDate());
            viewPatientHistoryDto.setTime(dto.getEndTime());
            viewPatientHistoryDto.setStatus(dto.getStatus());
            viewPatientHistoryDto.setSessionId(dto.getId());

            TherapyProgramDto therapyProgramDto = therapyProgramBo.findById(dto.getTherapyProgramId());
            viewPatientHistoryDto.setProgramName(therapyProgramDto.getName());

            viewPatientHistoryDtos1.add(viewPatientHistoryDto);

        }

        return viewPatientHistoryDtos1;

    }
}
