package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class VeterinarianTest {

    private final BlockingDeque<Veterinarian> veterinarians = new LinkedBlockingDeque<>();

    @SneakyThrows
    @Test
    public void testWork() {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setVeterinarians(veterinarians);

        Dog dog = DogFactory.getDog("Betty");
        dog.setHealthy(false);
        veterinarian.setDog(dog);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Boolean> task = service.submit(veterinarian);
        Boolean res = task.get();

        assertTrue(dog.isHealthy());
        assertTrue(res);

    }

}