package com.example.farm;

import com.example.farm.entities.Workforce;
import com.example.farm.reflect.annotations.Dogs;
import com.example.farm.reflect.configs.WorkforceConfiguration;
import com.example.farm.reflect.Processor;

public class Runner implements WorkforceConfiguration {

    @Dogs
    private final String[] names = {"Nancy", "Betty", "Tom", "Rex"};

    public static void main(String[] args) {
        Processor.process(Runner.class);
    }

    @Override
    public void configure(Workforce workforce) {
        workforce
                .cleaners(1)
                .cookers(1)
                .cynologists(1)
                .dogHuggers(1)
                .trainers(1)
                .veterinarians(1);
    }
}
