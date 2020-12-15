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
public class Cook extends ServiceStaff {

    @Setter
    private Dog hungryDog;
    @Setter
    private BlockingDeque<Cook> workers;

    public Cook() {
        super("Cook");
    }

    @Override
    @SneakyThrows
    public Boolean call() {
        log.info(Ansi.BLUE.code + "{} starts to feed pretty {}..." + Ansi.RESET.code, this, hungryDog.getName());
        TimeUnit.SECONDS.sleep(5);
        hungryDog.setHungry(false);
        log.info(Ansi.BLUE.code + "{} now is well-fed! It has ate {} food!)" + Ansi.RESET.code, hungryDog.getName(),
                hungryDog.getStatus().getAmountOfFeed());
        workers.putLast(this);

        return true;
    }
}
