package com.example.farm.strategy;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;
import com.example.farm.factory.DogFactory;
import com.example.farm.strategy.impl.*;
import com.example.farm.util.AgeStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class WorkingMethodsTest {

    private static final List<Booth> booths = new ArrayList<>();
    private final ExecutorService service = Executors.newCachedThreadPool();

    @BeforeAll
    static void set() {
        IntStream.range(0, 5)
                .forEach(x -> booths.add(new Booth(DogFactory.getDog("Dog"))));

    }

    @Test
    public void cleanUp() {
        Workforce workforce = new Workforce();
        workforce
                .veterinarians(2)
                .cookers(2)
                .cleaners(2)
                .cynologists(2)
                .dogHuggers(2)
                .trainers(2);

        WorkingMethods method = new WorkingMethods(workforce, booths, service);

        method.setStrategy(new CleanStrategy());
        method.doWork();

        booths.forEach(b -> assertFalse(b.isDirty()));

        method.setStrategy(new CookingStrategy());
        method.doWork();

        booths.forEach(b -> assertFalse(b.getDog().isHungry()));

        method.setStrategy(new PoliceStrategy());
        method.doWork();

        booths.stream()
                .filter(b -> b.getDog().getStatus() == AgeStatus.ADULT)
                .forEach(b -> assertTrue(b.getDog().isHungry()));

        method.setStrategy(new VeterinarianStrategy());
        method.doWork();

        method.setStrategy(new HugsStrategy());
        method.doWork();

        booths.stream()
                .peek(b -> assertTrue(b.getDog().isHappy()))
                .forEach(b -> assertTrue(b.getDog().isHealthy()));

    }

}