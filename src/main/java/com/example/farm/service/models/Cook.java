package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
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
        log.info("{} starts to feed pretty {}...", this, hungryDog);
        TimeUnit.SECONDS.sleep(3);
        hungryDog.setHungry(false);
        log.info("{} now is well-fed! It has ate {} food!)", hungryDog.getName(),
                hungryDog.getStatus().getAmountOfFeed());
        workers.putLast(this);

        return true;
    }
}
