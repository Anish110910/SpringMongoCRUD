package com.demo.SpringMongoCRUD.controller;


import com.demo.SpringMongoCRUD.model.Movie;
import com.demo.SpringMongoCRUD.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    //POST      /movie
    @RequestMapping(method= RequestMethod.POST,value="/movie")
    public ResponseEntity<String> createMovie(@RequestBody Movie movie)
    {
        try{
            movieRepo.save(movie);
            return new ResponseEntity("Successfully added movie "+movie.getTitle(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET       /movie
    @RequestMapping(method=RequestMethod.GET,value="/movie")
    public ResponseEntity getAllMovies()
    {
        List<Movie> movies=movieRepo.findAll();
        if(movies.size()>0)
        {
            return new ResponseEntity(movies,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity("No movies found",HttpStatus.NOT_FOUND);
        }
    }

    //DELETE        /movie/{id}
    @RequestMapping(method=RequestMethod.DELETE,value="/movie/{id}")
    public ResponseEntity deleteMovieById(@PathVariable("id") String id)
    {
        try{
            movieRepo.deleteById(id);
            return new ResponseEntity("Successfully deleted movie with id "+id,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //PUT       /movie/{id}
    @RequestMapping(method=RequestMethod.PUT,value="/movie/{id}")
    public ResponseEntity updateById(@PathVariable("id") String id,@RequestBody Movie newMovie)
    {
        Optional<Movie> movieOptional =movieRepo.findById(id);
        if(movieOptional.isPresent())
        {
            Movie movieToSave=movieOptional.get();
            movieToSave.setTitle(newMovie.getTitle());
            movieToSave.setRating(newMovie.getRating());
            movieToSave.setGenre(newMovie.getGenre());
            movieRepo.save(movieToSave);
            return new ResponseEntity("Updated Movie with id "+id,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity("No Movie with id "+id+" found",HttpStatus.NOT_FOUND);
        }
    }



}
