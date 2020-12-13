package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Veterinarian extends ServiceStaff implements Runnable {

    private final List<Dog> sickDogs = new ArrayList<>();

    public Veterinarian() {
        super("Veterinarian");
    }

    @Override
    public void run() {
        log.info("{} start to cure about dogs!", this);
        sickDogs.forEach(d -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                log.info("{} now is healthy!)", d.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sickDogs.clear();
        log.info("{}: every pretty dog is healthy now!)", this);
    }

    public void addSickDog(Dog dog) {
        sickDogs.add(dog);
    }
}
