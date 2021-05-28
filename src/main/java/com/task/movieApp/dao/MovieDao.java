package com.task.movieApp.dao;

import org.springframework.data.repository.CrudRepository;

import com.task.movieApp.model.Movie;

public interface MovieDao extends CrudRepository<Movie, Integer> {
	public Movie findById(int id);

}
