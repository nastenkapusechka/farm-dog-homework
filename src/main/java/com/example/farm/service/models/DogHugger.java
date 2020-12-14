package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//в китае есть проессия - обниматель панд. У нас будет обниматель собак)) все любят обнимашки
@Slf4j
public class DogHugger extends ServiceStaff {

    private final List<Dog> dogs;

    public DogHugger(List<Dog> dogs) {
        super("Dog hugger");
        this.dogs = dogs;
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} start to hug pretty dogs..)", this);
        long count = dogs.stream()
                .filter(d -> !d.isHappy())
                .peek(d -> d.setHappy(true))
                .count();
        TimeUnit.SECONDS.sleep(count);
        log.info("{}: every dog are hugged!)", this);

        return true;
    }
}
