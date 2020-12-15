package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import com.example.farm.util.Ansi;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

//в китае есть проессия - обниматель панд. У нас будет обниматель собак)) все любят обнимашки
@Slf4j
public class DogHugger extends ServiceStaff {

    @Setter
    private Dog sadDog;

    @Setter
    private BlockingDeque<DogHugger> huggers;

    public DogHugger() {
        super("Dog hugger");
    }

    @SneakyThrows
    @Override
    public Boolean call() {
        log.info(Ansi.MAGENTA.code + "{} start to hug pretty {}..)" + Ansi.RESET.code, this, sadDog.getName());
        sadDog.setHappy(true);
        TimeUnit.SECONDS.sleep(6);
        log.info(Ansi.MAGENTA.code + "{}: {} is happy now!))" + Ansi.RESET.code, this, sadDog.getName());
        huggers.putLast(this);

        return true;
    }
}
