package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;
import com.example.farm.strategy.Personal;
import com.example.farm.service.models.Cleaner;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CleanStrategy implements Personal {

    @Override
    @SneakyThrows
    public void doWork(Workforce workforce, List<Booth> booths, ExecutorService service) {
        ConcurrentLinkedDeque<Booth> dirtyBooths = new ConcurrentLinkedDeque<>(booths);

        BlockingDeque<Cleaner> cleaners = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("Cleaner"))
                .map(w -> (Cleaner) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        cleaners.forEach(c -> c.setCleaners(cleaners));

        if (cleaners.isEmpty()) return;

        List<Future<Boolean>> tasks = new ArrayList<>();

        while (!dirtyBooths.isEmpty()) {
            Cleaner cleaner = cleaners.takeFirst();
            cleaner.setDirtyBooth(dirtyBooths.pollFirst());
            tasks.add(service.submit(cleaner));
        }
        waiting(tasks);
    }
}
