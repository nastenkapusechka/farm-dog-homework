package com.example.farm.service.models;

import com.example.farm.entities.Aviary;
import com.example.farm.service.ServiceStaff;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.*;

@Slf4j
public class Cleaner extends ServiceStaff {

    private final List<Aviary> cleanTarget;

    public Cleaner(List<Aviary> cleanTarget) {
        super("Cleaner");
        this.cleanTarget = cleanTarget;
    }

    @SneakyThrows
    @Override
    public Boolean call() {

//        long n = cleanTarget.stream()
//                .filter(a -> a.getDogsWhoLiveHere().size() == 0)
//                .count();
//
//        checkState(n == cleanTarget.size(), "Aviaries are not empty!");

        log.info("{} start cleaning...", this);
        TimeUnit.SECONDS.sleep(cleanTarget.size() * 5);
        log.info("{}: now everything shines!", this);

        return true;
    }
}
