package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import com.example.farm.util.Ansi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//кинолог
//забирает собачек на работу
@Slf4j
public class Cynologist extends ServiceStaff {

    private final List<Dog> workers = new ArrayList<>();

    public Cynologist() {
        super("Cynologist");
    }

    @SneakyThrows
    @Override
    public Boolean call() {

        if (workers.isEmpty()) return false;

        log.info(Ansi.BRIGHT_YELLOW.code + "{} takes dogs to work ... He has {} teammates!" + Ansi.RESET.code, this, workers.size());
        workers.forEach(d -> d.setHungry(true));
        TimeUnit.SECONDS.sleep(20);
        log.info(Ansi.BRIGHT_YELLOW.code + "{}: today was a nice day! Every dog has worked and they are so hungry!" + Ansi.RESET.code, this);

        return true;
    }

    public void addTeammate(Dog dog) {
        workers.add(dog);
    }
}
