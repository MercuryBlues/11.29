package com.rq.service;

import com.rq.bean.QueryVo;
import com.rq.bean.User;
import com.rq.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    UserMapper userMapper;
    @Override
    public User Login(User user) {
        return userMapper.Login(user);
    }

    @Override
    public List<User> getUserlist(User user) throws Exception {
        return userMapper.getUserlist(user);
    }

    @Override
    public int RegisterUser(User user) throws Exception {
        return userMapper.RegisterUser(user);
    }


    @Override
    public User getUserByid(Integer id) throws Exception {
        return userMapper.getUserByid(id);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return userMapper.updateUser(user);
    }

    @Override
    public boolean modifyPs(User user) throws Exception {
        return userMapper.modifyPs(user);
    }

    @Override
    public int deleteUserByid(User user) throws Exception {
        return userMapper.deleteUserByid(user);
    }

    @Override
    public int checkName(String name) {
        return userMapper.checkName(name);
    }

    @Override
    public List<User> search(User user) {
        return userMapper.search(user);
    }

    @Override
    public List<User> layuiList() {
        return userMapper.layuiList();
    }

    @Override
    public List<User> selectpage(HashMap map) {
        return userMapper.selectpage(map);
    }

    @Override
    public int userCount(User user ) {
        return userMapper.userCount(user);
    }

    @Override
    public int deleteUser(QueryVo vo) {
        return userMapper.deleteUser(vo);
    }

    @Override
    public int addUser(User user) {
        userMapper.addUser(user);
        return  user.getId();
    }


}