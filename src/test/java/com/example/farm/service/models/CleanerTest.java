package com.example.farm.service.models;

import com.example.farm.entities.Booth;
import com.example.farm.factory.DogFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class CleanerTest {

    private final BlockingDeque<Cleaner> cleaners = new LinkedBlockingDeque<>();
    private final ExecutorService service = Executors.newFixedThreadPool(1);

    @SneakyThrows
    @Test
    public void testWork() {

        Cleaner cleaner = new Cleaner();
        Booth booth = new Booth(DogFactory.getDog("Alex"));
        cleaner.setDirtyBooth(booth);
        cleaner.setCleaners(cleaners);

        Future<Boolean> task = service.submit(cleaner);
        Boolean res = task.get();

        assertFalse(booth.isDirty());
        assertTrue(res);
    }

}