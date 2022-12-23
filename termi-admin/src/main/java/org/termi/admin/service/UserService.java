package org.termi.admin.service;


import org.termi.common.entity.User;

public interface UserService {

    User checkUser(String username, String password);
}