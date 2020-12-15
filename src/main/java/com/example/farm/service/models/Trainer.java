package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import com.example.farm.util.Ansi;
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
        log.info(Ansi.WHITE.code + "{} training a dog {}..." + Ansi.RESET.code, this, dog.getName());

        dog.setAccustomed(true);
        dog.setHungry(true);

        TimeUnit.SECONDS.sleep(6);
        log.info(Ansi.WHITE.code + "{}: done!" + Ansi.RESET.code, this);

        trainers.putLast(this);
        return true;
    }

}
