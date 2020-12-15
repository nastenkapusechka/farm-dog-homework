package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;
import com.example.farm.factory.DogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CleanStrategyTest {

    private final List<Booth> booths = new ArrayList<>();
    private final ExecutorService service = Executors.newCachedThreadPool();

    @BeforeEach
    public void setDirtyBooths() {
        IntStream.range(1, 5)
                .forEach(x -> booths.add(new Booth(DogFactory.getDog("Dog"))));
        booths.forEach(b -> assertTrue(b.isDirty()));
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
                .cynologists(1)
                .dogHuggers(1)
                .trainers(1);

        CleanStrategy strategy = new CleanStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertFalse(b.isDirty()));
    }

    @Test
    public void doNotWork() {
        Workforce workforce = new Workforce();
        workforce
                .veterinarians(1)
                .cookers(1)
                .cynologists(1)
                .dogHuggers(1)
                .trainers(1);

        CleanStrategy strategy = new CleanStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertTrue(b.isDirty()));
    }
}