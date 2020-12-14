package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import com.example.farm.util.AgeStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//кинолог
//забирает собачек на работу
@Slf4j
public class Cynologist extends ServiceStaff {

    private final List<Dog> dogs;

    public Cynologist(List<Dog> dogs) {
        super("Cynologist");
        this.dogs = dogs;
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} takes dogs to work ...", this);
        dogs.stream()
                .filter(d -> d.getStatus() == AgeStatus.ADULT)
                .forEach(d -> d.setHungry(true));
        TimeUnit.SECONDS.sleep(5);
        log.info("{}: today was a nice day! Every dog has worked and they are so hungry!", this);

        return true;
    }
}
