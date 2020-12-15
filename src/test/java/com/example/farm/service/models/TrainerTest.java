package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {

    private final BlockingDeque<Trainer> trainers = new LinkedBlockingDeque<>();

    @SneakyThrows
    @Test
    public void testWork() {
        Trainer trainer = new Trainer();
        trainer.setTrainers(trainers);

        Dog dog = DogFactory.getDog("Rex");
        dog.setAccustomed(false);
        trainer.setDog(dog);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Boolean> task = service.submit(trainer);
        Boolean res = task.get();

        assertTrue(dog.isAccustomed());
        assertTrue(res);

    }

}