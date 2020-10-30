package com.demo.SpringMongoCRUD.service;

import com.demo.SpringMongoCRUD.exception.MovieCollectionException;
import com.demo.SpringMongoCRUD.model.Movie;
import com.demo.SpringMongoCRUD.repository.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    public void createMovie(Movie movie) throws ConstraintViolationException,MovieCollectionException
    {

        //If the movie is valid as per not null constraint we have to next check if the movie with the same name/id already exists
        Optional<Movie> movieNameOptional=movieRepo.findByTitle(movie.getTitle());
        if(movieNameOptional.isPresent())
        {
            System.out.println(movieNameOptional.get());
            throw new MovieCollectionException(MovieCollectionException.TitleAlreadyExists());
        }
        else
        {
            movieRepo.save(movie);
        }

    }

    public List<Movie> getAllMovies()
    {
        List<Movie> movies=movieRepo.findAll();
        if(movies.size()>0)
        {
            return movies;
        }
        else
        {
            return new ArrayList<Movie>();
        }
    }

    public void deleteMovieById(String id) throws MovieCollectionException
    {
        Optional<Movie> movieOptional=movieRepo.findById(id);
        if(!movieOptional.isPresent())
        {
            throw new MovieCollectionException(MovieCollectionException.NotFoundException(id));
        }
        else
        {
            movieRepo.deleteById(id);
        }
    }

    public void updateMovie(String id,Movie newMovie) throws ConstraintViolationException,MovieCollectionException
    {
        Optional<Movie> movieWithId=movieRepo.findById(id);
        Optional<Movie> movieWithSameTitle=movieRepo.findByTitle(newMovie.getTitle());
        if(movieWithId.isPresent())
        {
            if(movieWithSameTitle.isPresent() && !movieWithSameTitle.get().getId().equals(id))
            {

                throw new MovieCollectionException(MovieCollectionException.TitleAlreadyExists());
            }
            Movie movieToUpdate=movieWithId.get();
            BeanUtils.copyProperties(newMovie,movieToUpdate);

            // To make sure that newMovie doesn't get added as a new document
            movieToUpdate.setId(id);
            movieRepo.save(movieToUpdate);
        }
        else
        {
            throw new MovieCollectionException(MovieCollectionException.NotFoundException(id));
        }
    }


}
