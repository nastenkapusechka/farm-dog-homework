package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Veterinarian extends ServiceStaff {

    private final List<Dog> dogs;

    public Veterinarian(List<Dog> dogs) {
        super("Veterinarian");
        this.dogs = dogs;
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} start to cure about dogs!", this);
        long l = dogs.stream()
                .filter(d -> !d.isHealthy())
                .peek(d -> d.setHealthy(true))
                .count();
        TimeUnit.SECONDS.sleep(l);
        log.info("{}: every pretty dog is healthy now!)", this);

        return true;
    }
}
