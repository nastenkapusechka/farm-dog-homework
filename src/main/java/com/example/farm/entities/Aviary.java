package com.example.farm.entities;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
@Slf4j
public class Aviary {

    @ToString.Exclude
    private static final AtomicInteger counter = new AtomicInteger(1);
    @ToString.Exclude
    private final List<Dog> dogs = new ArrayList<>();
    @Getter
    private final int ID;

    {
        this.ID = counter.getAndIncrement();
        log.info("Created {}", this);
    }

    public void addDogsToAviary(Dog ...dogsEnter) {
        dogs.addAll(Arrays.asList(dogsEnter));
    }

    public List<Dog> getDogsFromAviary() {
        List<Dog> temp = new ArrayList<>(dogs);
        dogs.removeAll(temp);
        return temp;
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
