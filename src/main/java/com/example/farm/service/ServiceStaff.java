package com.example.farm.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ServiceStaff {

    AtomicInteger counter = new AtomicInteger(1);
    protected final int ID;
    protected final String SPECIALIZATION;

    public ServiceStaff(String SPECIALIZATION) {
        this.ID = counter.getAndIncrement();
        this.SPECIALIZATION = SPECIALIZATION;

        log.info("Added employee - {}", this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceStaff that = (ServiceStaff) o;
        return ID == that.ID &&
                SPECIALIZATION.equals(that.SPECIALIZATION);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, SPECIALIZATION);
    }
}