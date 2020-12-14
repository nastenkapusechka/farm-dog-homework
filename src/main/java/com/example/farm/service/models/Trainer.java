package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Trainer extends ServiceStaff {

    @Setter
    private Dog dog;
    @Setter
    private BlockingDeque<Trainer> trainers;

    public Trainer() {
        super("Trainer");
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} training a dog {}...", this, dog.getName());

        dog.setAccustomed(true);
        dog.setHungry(true);

        TimeUnit.SECONDS.sleep(3);
        log.info("{}: done!", this);

        trainers.putLast(this);
        return true;
    }

}
