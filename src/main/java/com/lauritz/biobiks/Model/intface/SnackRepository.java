package com.lauritz.biobiks.Model.intface;

import com.lauritz.biobiks.Model.Snack;

import java.util.List;
import java.util.Optional;

public interface SnackRepository {
    List<Snack> findAll();
    Optional<Snack> findById(int id);
}
