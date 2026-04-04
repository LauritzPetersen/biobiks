package com.lauritz.biobiks.Service;


import com.lauritz.biobiks.Model.Snack;
import com.lauritz.biobiks.Model.intface.SnackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackService {

    private final SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository){
        this.snackRepository = snackRepository;
    }

    public List<Snack> getAllSnacks(){
        return snackRepository.findAll();
    }

    public Optional<Snack> getSnackById(int id){
        return snackRepository.findById(id);
    }

}
