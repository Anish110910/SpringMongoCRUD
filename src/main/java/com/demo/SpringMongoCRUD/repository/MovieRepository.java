package com.demo.SpringMongoCRUD.repository;

import com.demo.SpringMongoCRUD.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie,String> {}
