package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//в китае есть проессия - обниматель панд. У нас будет обниматель собак)) все любят обнимашки
@Slf4j
public class DogHugger extends ServiceStaff implements Runnable {

    private final List<Dog> dogsToHug = new ArrayList<>();

    public DogHugger() {
        super("Dog hugger");
    }

    @Override
    public void run() {

        log.info("{} start to hug pretty dogs..)", this);
        dogsToHug.forEach( dog -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                dog.setHappy(true);
                log.info("{} now is happy!", dog.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        dogsToHug.clear();
        log.info("{}: every dog are hugged!)", this);
    }

    public void addSadDog(Dog dog) {
        dogsToHug.add(dog);
    }
}
