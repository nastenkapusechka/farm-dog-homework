package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CynologistTest {

    @SneakyThrows
    @Test
    public void imagineTheyAllWorkable() {
        List<Dog> dogs = Stream.of(
                DogFactory.getDog("Nancy"),
                DogFactory.getDog("Rex"),
                DogFactory.getDog("Betty")
        ).collect(Collectors.toList());

        dogs.forEach(d-> assertTrue(d.isHungry()));
        dogs.forEach(d -> d.setHungry(false));

        Cynologist cynologist = new Cynologist();
        dogs.forEach(cynologist::addTeammate);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Boolean> task = service.submit(cynologist);
        Boolean res = task.get();

        dogs.forEach(d -> assertTrue(d.isHungry()));
        assertTrue(res);
    }

}