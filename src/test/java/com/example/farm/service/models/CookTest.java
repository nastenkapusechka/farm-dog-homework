package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class CookTest {

    private final BlockingDeque<Cook> workers = new LinkedBlockingDeque<>();
    private final ExecutorService service = Executors.newFixedThreadPool(1);

    @SneakyThrows
    @Test
    public void testWork() {
        Cook cook = new Cook();
        Dog dog = DogFactory.getDog("Nancy");

        assertTrue(dog.isHungry());

        cook.setHungryDog(dog);
        cook.setWorkers(workers);

        Future<Boolean> task = service.submit(cook);
        Boolean b = task.get();

        assertFalse(dog.isHungry());
        assertTrue(b);
    }

}