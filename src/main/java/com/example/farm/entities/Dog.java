package com.example.farm.entities;

import com.example.farm.util.AgeStatus;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
@Slf4j
@Data
public class Dog {

    @ToString.Exclude
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final long ID;
    private String name;
    private int age;
    private boolean isHungry;
    private boolean isHealthy;
    private boolean isHappy;
    private boolean isAccustomed;
    private AgeStatus status;

    public Dog(String name) {

        Random random = new Random();

        this.ID = COUNTER.getAndIncrement();
        this.name = name;
        this.age = random.nextInt(12);
        this.isHungry = true;
        this.isHappy = Math.random() <= 0.5;
        this.isAccustomed = Math.random() <= 0.5;
        this.isHealthy = Math.random() <= 0.3;

        this.status = age <= 2 ? AgeStatus.PUPPY : age <= 8 ? AgeStatus.ADULT : AgeStatus.OLD;

        log.info("Created object {}", this);
    }

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
