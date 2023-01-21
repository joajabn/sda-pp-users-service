package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.db.HibernateUtils;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UsersDAO userDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userDAO.findAll().stream()
                .map(userMapper::map)
                .toList();
    }

    public UserDTO findByUsername(String username) throws NotFoundException {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username %s not found.".formatted(username));
        }
        return userMapper.map(user);

    }

    public void deleteByUsername(String username) {
        boolean isDeleted = userDAO.deleteByUsername(username);
        if (!isDeleted) {
            throw new NotFoundException("User with username %s not found.".formatted(username));
        }
    }

    public void create(User user){

        if(userDAO.existsByUsername(user.getUsername())){
            throw new UsernameConflictException("User with username %s already exists.".formatted(user.getUsername()));
        }
        userDAO.create(user);
    }


}
