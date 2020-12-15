package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;
import com.example.farm.factory.DogFactory;
import com.example.farm.util.AgeStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PoliceStrategyTest {

    private final List<Booth> booths = new ArrayList<>();
    private final ExecutorService service = Executors.newCachedThreadPool();

    @BeforeEach
    public void set() {
        IntStream.range(0, 5)
                .forEach(x -> booths.add(new Booth(DogFactory.getDog("Dog"))));

        booths.stream()
                .peek(b -> assertTrue(b.getDog().isHungry()))
                .peek(b -> b.getDog().setHungry(false))
                .forEach(b -> b.getDog().setStatus(AgeStatus.ADULT));
    }

    @AfterEach
    public void reset() {
        booths.clear();
    }

    @Test
    public void doWork() {
        Workforce workforce = new Workforce();
        workforce
                .veterinarians(1)
                .cookers(1)
                .cleaners(1)
                .cynologists(2)
                .dogHuggers(1)
                .trainers(1);

        PoliceStrategy strategy = new PoliceStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertTrue(b.getDog().isHungry()));
    }

    @Test
    public void doNotWork() {
        Workforce workforce = new Workforce();
        workforce
                .veterinarians(1)
                .cookers(1)
                .cleaners(1)
                .dogHuggers(1)
                .trainers(1);

        PoliceStrategy strategy = new PoliceStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertFalse(b.getDog().isHungry()));
    }

}