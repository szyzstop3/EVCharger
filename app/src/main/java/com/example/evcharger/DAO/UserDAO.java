package com.example.evcharger.DAO;

import com.example.evcharger.vo.User;

public interface UserDAO {

    public boolean InsertUser(User user);
    public boolean LoginUser(User user);
    public boolean UserEX(User user);
    public String getUserinfo(User user);
}
