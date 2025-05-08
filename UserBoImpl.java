package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.BoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.EncryptandBycript;
import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.UserBo;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.CrudDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.Custom.UserDao;
import com.assignment.orm.service.orm_final_course_work_health_care.DAO.DaoFactory;
import com.assignment.orm.service.orm_final_course_work_health_care.DTO.UserDto;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {

    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoFactory.DaoType.USER);
    EncryptandBycript encryptandBycript = BoFactory.getInstance().getBo(BoFactory.BoType.ENCRYPT);


    @Override
    public UserDto checkUser(String Username, String password) {

        List<User> users = userDao.CheckUser();
        for (User user : users) {
            if (user.getName().equals(Username)) {
                if (encryptandBycript.VerifyUser(user.getEmail(), password)) {
                    return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole());
                }
            }
        }
        return null;
    }

    @Override
    public String getNextId() throws SQLException {
        String id = userDao.getNextId();
        if (id != null) {
            String substring = id.substring(1);
            int i = Integer.parseInt(substring);
            int newId = i + 1;
            return String.format("U%03d", newId);

        }
        return "U001";
    }

    @Override
    public ArrayList<UserDto> getAll() throws SQLException {
        ArrayList<User> users = userDao.getAll();

        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setRole(user.getRole());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public boolean save(UserDto userDto) throws SQLException {
        String encryptedPassword = encryptandBycript.EncryptPassword(userDto.getPassword());

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(userDto.getRole());

        return userDao.save(user);


    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return userDao.delete(Id);
    }

    @Override
    public boolean update(UserDto userDto) throws SQLException {
        String encryptedPassword = encryptandBycript.EncryptPassword(userDto.getPassword());

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(userDto.getRole());

        return userDao.update(user);
    }
}
