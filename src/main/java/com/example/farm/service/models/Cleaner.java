package com.example.farm.service.models;

import com.example.farm.entities.Booth;
import com.example.farm.service.ServiceStaff;
import com.example.farm.util.Ansi;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

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

        log.info(Ansi.GREEN.code + "{} start cleaning booth #{}..." + Ansi.RESET.code, this, dirtyBooth.getID());
        dirtyBooth.setDirty(false);
        TimeUnit.SECONDS.sleep(6);
        log.info(Ansi.GREEN.code + "{}: now everything shines!" + Ansi.RESET.code, this);
        cleaners.putLast(this);

        return true;
    }
}
