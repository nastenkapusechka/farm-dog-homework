package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cook extends ServiceStaff {

    private final List<Dog> dogs;
    private double foodKg;

    public Cook(List<Dog> dogs) {
        super("Cook");
        this.dogs = dogs;
        this.foodKg = dogs.size() * 4;
    }

    @Override
    @SneakyThrows
    public Boolean call() {
        log.info("{} starts to feed dogs... There is {} kg of food", this, foodKg);
        foodKg -= dogs.stream()
                .peek(d -> d.setHungry(false))
                .map(d -> d.getStatus().getAmountOfFeed())
                .reduce(Double::sum).orElse(0.0);
        TimeUnit.SECONDS.sleep(5);
        log.info("{}: every dog is well-fed!) There is {} kg of food in the remainder", this, foodKg);

        return true;
    }
}
