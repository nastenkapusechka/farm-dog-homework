package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//кинолог
//забирает собачек на работу
@Slf4j
public class Cynologist extends ServiceStaff implements Runnable {

    private final List<Dog> dogsTeam = new ArrayList<>();

    public Cynologist() {
        super("Cynologist");
    }

    @SneakyThrows
    @Override
    public void run() {

        log.info("{} takes dogs to work ...", this);
        dogsTeam.forEach(d -> d.setHungry(true));
        TimeUnit.SECONDS.sleep(20);
        dogsTeam.clear();
        log.info("{}: today was a nice day! Every dog has worked and they are so hungry!", this);
    }

    public void dogsGoingWork(Dog ...dogs) {
        dogsTeam.addAll(Arrays.asList(dogs));
    }

}
