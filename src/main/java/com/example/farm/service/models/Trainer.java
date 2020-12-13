package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Trainer extends ServiceStaff implements Runnable {

    private final List<Dog> naughtyDogs = new ArrayList<>();

    public Trainer() {
        super("Trainer");
    }

    @Override
    public void run() {

        log.info("{} starts train naughty dogs!", this);
        naughtyDogs.forEach(d -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info("{} now is good!", d.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("{}: now every dog is trained!)", this);
        naughtyDogs.clear();
    }

    public void addNaughtyDog(Dog dog) {
        naughtyDogs.add(dog);
    }
}
