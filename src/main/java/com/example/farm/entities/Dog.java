package com.example.farm.entities;

import com.example.farm.util.AgeStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
@Slf4j
@Data
@Builder
public class Dog {

    @ToString.Exclude
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final long ID = COUNTER.getAndIncrement();
    private String name;
    private int age;
    private boolean isHungry;
    private boolean isHealthy;
    private boolean isHappy;
    private boolean isAccustomed;
    private AgeStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return ID == dog.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
