package com.rq.mapper;

import com.rq.bean.Book;
import com.rq.bean.QueryVo;
import com.rq.bean.User;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    public User Login(User user);

    public List<User> getUserlist(User user) throws Exception;

    public int RegisterUser(User user) throws Exception;

    public List<User> selectByWhere(String name) throws Exception;

    public User getUserByid(Integer id) throws Exception;

    public int updateUser(User user) throws Exception;

    public boolean modifyPs(User user) throws Exception;

    public int deleteUserByid(User user) throws Exception;

    public boolean checkPassword(User user) throws Exception;

    public  User updatehead(User user);

    public  int checkName(String name);

    public List<User> search(User user);

    public List<User> layuiList();

    public int userCount(User user);
    public List<User> selectpage(HashMap map);

    public int deleteUser(QueryVo vo);

    public int addUser(User user);
}
