package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Dog;
import com.example.farm.entities.Workforce;
import com.example.farm.strategy.Personal;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CookingStrategy implements Personal {

    @SneakyThrows
    @Override
    public void doWork(Workforce workforce, List<Booth> booths, ExecutorService service) {

        ConcurrentLinkedDeque<Dog> hungryPets = booths.stream()
                .map(Booth::getDog).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        BlockingDeque<com.example.farm.service.models.Cook> cookers = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("Cook"))
                .map(w -> (com.example.farm.service.models.Cook) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        cookers.forEach(c -> c.setWorkers(cookers));

        List<Future<Boolean>> tasks = new ArrayList<>();

        if (cookers.isEmpty()) return;

        while (!hungryPets.isEmpty()) {
            com.example.farm.service.models.Cook cook = cookers.takeFirst();
            cook.setHungryDog(hungryPets.pollFirst());
            tasks.add(service.submit(cook));
        }
        waiting(tasks);
    }
}
