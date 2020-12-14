package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Trainer extends ServiceStaff {

    private final List<Dog> dogs;

    public Trainer(List<Dog> dogs) {
        super("Trainer");
        this.dogs = dogs;
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} starts train naughty dogs!", this);
        long counter = dogs.stream()
                .filter(d -> !d.isAccustomed())
                .peek(d -> d.setAccustomed(true))
                .count();
        TimeUnit.SECONDS.sleep(counter);
        log.info("{}: now every dog is trained!)", this);

        return true;
    }
}
