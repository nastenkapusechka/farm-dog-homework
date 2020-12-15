package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Dog;
import com.example.farm.entities.Workforce;
import com.example.farm.strategy.Personal;
import com.example.farm.service.models.DogHugger;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class HugsStrategy implements Personal {
    @Override
    @SneakyThrows
    public void doWork(Workforce workforce, List<Booth> booths, ExecutorService service) {

        ConcurrentLinkedDeque<Dog> sadDogs = booths.stream()
                .map(Booth::getDog)
                .filter(d -> !d.isHappy())
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        BlockingDeque<DogHugger> huggers = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("DogHugger"))
                .map(w -> (DogHugger) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        huggers.forEach(h -> h.setHuggers(huggers));

        List<Future<Boolean>> tasks = new ArrayList<>();

        if (huggers.isEmpty()) return;

        while (!sadDogs.isEmpty()) {
            DogHugger hugger = huggers.takeFirst();
            hugger.setSadDog(sadDogs.pollFirst());
            tasks.add(service.submit(hugger));
        }
        waiting(tasks);
    }
}
