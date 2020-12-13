package com.example.farm.service.models;

import com.example.farm.entities.Aviary;
import com.example.farm.service.ServiceStaff;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cleaner extends ServiceStaff implements Runnable {

    private final List<Aviary> cleanTarget = new ArrayList<>();

    public Cleaner() {
        super("Cleaner");
    }

    @Override
    public void run() {
        log.info("{} start cleaning...", this);

        cleanTarget.forEach(x -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info("Aviary #{} cleaned", x.getID());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cleanTarget.clear();
        log.info("{}: now everything shines!", this);
    }


    public void addObjToClean(Aviary ... aviaries) {
        cleanTarget.addAll(Arrays.asList(aviaries));
    }
}
