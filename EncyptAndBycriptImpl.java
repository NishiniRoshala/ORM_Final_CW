package com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.Impl;

import com.assignment.orm.service.orm_final_course_work_health_care.BO.Custom.EncryptandBycript;
import com.assignment.orm.service.orm_final_course_work_health_care.Cunfig.FactoryConfiguration;
import com.assignment.orm.service.orm_final_course_work_health_care.Entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hibernate.sql.ast.Clause.WHERE;

public class EncyptAndBycriptImpl implements EncryptandBycript {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public EncyptAndBycriptImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public boolean VerifyUser  (String email, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = query.uniqueResult();

        boolean isMatch = false;
        if (user != null) {
            isMatch = bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        return isMatch;
    }
    @Override
    public String EncryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
