package com.sda.exception;

import com.sda.db.HibernateUtils;
import com.sda.dto.UserDTO;
import com.sda.model.User;
import com.sda.dao.UsersDAO;
import org.hibernate.Session;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }


    }

