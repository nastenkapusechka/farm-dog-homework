package com.example.farm.entities;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
@Slf4j
public class Aviary {

    @ToString.Exclude
    private static final AtomicInteger counter = new AtomicInteger(1);
    @ToString.Exclude
    @Getter
    private final List<Dog> dogsWhoLiveHere;
    @Getter
    private final int ID;

    public Aviary(List<Dog> dogsWhoLiveHere) {
        this.dogsWhoLiveHere = dogsWhoLiveHere;
        this.ID = counter.getAndIncrement();
        log.info("Created {}", this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aviary aviary = (Aviary) o;
        return ID == aviary.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
