package com.example.farm.service.models;

import com.example.farm.entities.Booth;
import com.example.farm.service.ServiceStaff;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.*;

@Slf4j
public class Cleaner extends ServiceStaff {

    @Setter
    private Booth dirtyBooth;

    @Setter
    private BlockingDeque<Cleaner> cleaners;

    public Cleaner() {
        super("Cleaner");
    }

    @SneakyThrows
    @Override
    public Boolean call() {

        log.info("{} start cleaning booth #{}...", this, dirtyBooth.getID());
        dirtyBooth.setDirty(false);
        TimeUnit.SECONDS.sleep(3);
        log.info("{}: now everything shines!", this);
        cleaners.putLast(this);

        return true;
    }
}
