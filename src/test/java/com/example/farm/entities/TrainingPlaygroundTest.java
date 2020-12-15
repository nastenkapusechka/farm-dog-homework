package com.example.farm.entities;

import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TrainingPlaygroundTest {

    private static Workforce workforce;
    private static List<Dog> dogs;
    private final static ExecutorService service = Executors.newCachedThreadPool();

    @BeforeAll
    static void setWorkforceAndDogs() {
        dogs = Stream.of(
                DogFactory.getDog("Nancy"),
                DogFactory.getDog("Betty")
        ).collect(Collectors.toList());
    }

    @SneakyThrows
    @Order(0)
    @Test
    public void testTrainersWork() {

        workforce = new Workforce();
        workforce
                .dogHuggers(2)
                .trainers(2);
        dogs.forEach(d -> d.setAccustomed(false));

        TrainingPlayground playground = new TrainingPlayground(workforce, dogs);
        Future<Boolean> task = service.submit(playground);
        Boolean b = task.get();

        assertTrue(b);
        dogs.forEach(d -> assertThat(d.isAccustomed(), is(true)));
    }

    @SneakyThrows
    @Test
    @Order(1)
    public void testTrainersFalse() {
        workforce = new Workforce();
        workforce
                .dogHuggers(2)
                .veterinarians(2)
                .cynologists(2)
                .cookers(2)
                .cleaners(2);
        dogs.forEach(d -> d.setAccustomed(false));

        TrainingPlayground playground = new TrainingPlayground(workforce, dogs);

        Future<Boolean> task = service.submit(playground);
        Boolean res = task.get();

        assertFalse(res);

    }

}