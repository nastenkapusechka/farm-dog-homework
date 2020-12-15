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

class VeterinarianStrategyTest {

    private final List<Booth> booths = new ArrayList<>();
    private final ExecutorService service = Executors.newCachedThreadPool();

    @BeforeEach
    public void set() {
        IntStream.range(0, 5)
                .forEach(x -> booths.add(new Booth(DogFactory.getDog("Dog"))));

        booths.forEach(b -> b.getDog().setHealthy(false));
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

        VeterinarianStrategy strategy = new VeterinarianStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertTrue(b.getDog().isHealthy()));
    }

    @Test
    public void doNotWork() {
        Workforce workforce = new Workforce();
        workforce
                .cookers(1)
                .cleaners(1)
                .cynologists(2)
                .dogHuggers(1)
                .trainers(1);

        VeterinarianStrategy strategy = new VeterinarianStrategy();
        strategy.doWork(workforce, booths, service);

        booths.forEach(b -> assertFalse(b.getDog().isHealthy()));
    }

}