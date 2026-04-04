package com.lauritz.biobiks.Model.intface;

import com.lauritz.biobiks.Model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(int id);
}
