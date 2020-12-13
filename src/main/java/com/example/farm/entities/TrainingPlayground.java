package com.example.farm.entities;

import com.example.farm.service.models.Trainer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingPlayground {

    @Getter
    private final List<Trainer> trainers = new ArrayList<>();

    public TrainingPlayground(Trainer ... trainers) {
        this.trainers.addAll(Arrays.asList(trainers));
    }

}
