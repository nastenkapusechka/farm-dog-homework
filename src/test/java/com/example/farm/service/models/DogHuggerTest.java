package com.example.farm.service.models;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DogHuggerTest {

    private final BlockingDeque<DogHugger> huggers = new LinkedBlockingDeque<>();

    @SneakyThrows
    @Test
    public void testWork() {
        DogHugger hugger = new DogHugger();
        Dog sadDog = DogFactory.getDog("Rex");
        sadDog.setHappy(false);

        hugger.setSadDog(sadDog);
        hugger.setHuggers(huggers);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Boolean> task = service.submit(hugger);
        Boolean res = task.get();

        assertThat(sadDog.isHappy(), is(true));
        assertTrue(res);
    }

}