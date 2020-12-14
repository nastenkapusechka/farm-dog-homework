package com.example.farm.entities;

import com.example.farm.service.ServiceStaff;
import com.example.farm.service.models.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Workforce {

    @Getter
    private final List<ServiceStaff> workers;

    public Workforce() {
        this.workers = new ArrayList<>();
    }

    public Workforce cookers(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new Cook()));
        return this;
    }

    public Workforce cleaners(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new Cleaner()));
        return this;
    }

    public Workforce cynologists(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new Cynologist()));
        return this;
    }

    public Workforce dogHuggers(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new DogHugger()));
        return this;
    }

    public Workforce trainers(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new Trainer()));
        return this;
    }

    public Workforce veterinarians(int countOfEmployees) {
        IntStream.range(0, countOfEmployees)
                .forEach(x -> this.workers.add(new Veterinarian()));
        return this;
    }
}
