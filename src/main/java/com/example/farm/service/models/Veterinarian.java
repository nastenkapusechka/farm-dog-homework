package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Veterinarian extends ServiceStaff {

    @Setter
    private Dog dog;

    @Setter
    private BlockingDeque<Veterinarian> veterinarians;

    public Veterinarian() {
        super("Veterinarian");
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info("{} start to cure about {}!", this, dog.getName());
        dog.setHealthy(true);
        TimeUnit.SECONDS.sleep(3);
        log.info("{}: {} is healthy now!)", this, dog.getName());
        veterinarians.putLast(this);

        return true;
    }
}
