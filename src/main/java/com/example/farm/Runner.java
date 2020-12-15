package com.example.farm;

import com.example.farm.entities.Workforce;
import com.example.farm.reflect.annotations.Dogs;
import com.example.farm.reflect.configs.WorkforceConfiguration;
import com.example.farm.reflect.Processor;

public class Runner implements WorkforceConfiguration {

    @Dogs
    private final String[] names = {"Nancy", "Betty", "Tom", "Rex", "Sobaka",
    "Aksel", "Bonya", "Veron", "Dobby", "Kasper", "Hatiko"};

    public static void main(String[] args) {
        Processor.process(Runner.class);
    }

    //здесь мона настроить сколько и какой персонал будет работать в питомнике
    @Override
    public void configure(Workforce workforce) {
        workforce
                .cleaners(2)
                .cookers(2)
                .cynologists(2)
                .dogHuggers(4)
                .trainers(3)
                .veterinarians(2);
    }
}
