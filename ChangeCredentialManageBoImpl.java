package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.ChangeCredentialManageBo;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.UserBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.UserDto;

import java.sql.SQLException;

public class ChangeCredentialManageBoImpl implements ChangeCredentialManageBo {

    UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BoType.USER);
    @Override
    public UserDto checkUser(String text, String text1) {
        return userBo.checkUser(text, text1);
    }

    @Override
    public boolean changeCredential(UserDto userDto, String username, String password) throws SQLException {
        UserDto userDto1 = new UserDto();
        userDto1.setId(userDto.getId());
        userDto1.setName(username);
        userDto1.setPassword(password);
        userDto1.setRole(userDto.getRole());
        userDto1.setEmail(userDto.getEmail());

        return userBo.update(userDto1);
    }
}
