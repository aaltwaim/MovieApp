package com.task.movieApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.task.movieApp.model.User;


public interface UserDao extends CrudRepository<User, Integer>{
	public User findByEmailAddress(String emailAddress);

}
