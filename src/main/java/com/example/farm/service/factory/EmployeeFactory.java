package com.example.farm.service.factory;

import com.example.farm.service.*;
import com.example.farm.service.models.*;
import com.example.farm.util.Employee;

public final class EmployeeFactory {

    public static ServiceStaff getEmployee(Employee employeeSphere) {

        switch (employeeSphere) {
            case CLEANER:
                return new Cleaner();
            case TRAINER:
                return new Trainer();
            case CYNOLOGIST:
                return new Cynologist();
            case DOG_HUGGER:
                return new DogHugger();
            case VETERINARIAN:
                return new Veterinarian();
            default:
                return null;
        }
    }
}
