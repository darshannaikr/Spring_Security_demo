package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.User;

public interface UserService {

	User signUp(User user) throws Exception;

	String userLogin(String username, String password) throws Exception;

	List<User> getAllUser() throws Exception;

}
