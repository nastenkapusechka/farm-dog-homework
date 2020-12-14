package com.example.farm.factory;

import com.example.farm.entities.Aviary;
import com.example.farm.entities.Dog;
import com.example.farm.service.*;
import com.example.farm.service.models.*;
import com.example.farm.util.Employee;

import java.util.List;

public final class EmployeeFactory {

    private final List<Dog> dogs;
    private final List<Aviary> aviaries;

    public EmployeeFactory(List<Dog> dogs, List<Aviary> aviaries) {
        this.dogs = dogs;
        this.aviaries = aviaries;
    }

    public ServiceStaff getEmployee(Employee employeeSphere) {

        switch (employeeSphere) {
            case CLEANER:
                return new Cleaner(aviaries);
            case TRAINER:
                return new Trainer(dogs);
            case CYNOLOGIST:
                return new Cynologist(dogs);
            case DOG_HUGGER:
                return new DogHugger(dogs);
            case VETERINARIAN:
                return new Veterinarian(dogs);
            case COOK:
                return new Cook(dogs);
            default:
                return null;
        }
    }
}
