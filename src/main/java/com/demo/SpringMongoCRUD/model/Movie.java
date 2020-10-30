package com.demo.SpringMongoCRUD.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="movie")
public class Movie {

    @Id
    private String id;

    @NotNull(message = "Movie title cannot be null")
    private String title;

    @NotNull(message = "Movie rating cannot be null")
    private float rating;

    @NotNull(message = "Movie genre cannot be null")
    private String genre;

}
