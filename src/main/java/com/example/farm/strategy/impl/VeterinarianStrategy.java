package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Dog;
import com.example.farm.entities.Workforce;
import com.example.farm.strategy.Personal;
import com.example.farm.service.models.Veterinarian;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class VeterinarianStrategy implements Personal {

    @Override
    @SneakyThrows
    public void doWork(Workforce workforce, List<Booth> booths, ExecutorService service) {

        ConcurrentLinkedDeque<Dog> pets = booths.stream()
                .map(Booth::getDog)
                .filter(d -> !d.isHealthy())
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        BlockingDeque<Veterinarian> veterinarians = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("Veterinarian"))
                .map(w -> (Veterinarian) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        veterinarians.forEach(v -> v.setVeterinarians(veterinarians));

        List<Future<Boolean>> tasks = new ArrayList<>();

        if (veterinarians.isEmpty()) return;

        while (!pets.isEmpty()) {
            Veterinarian veterinarian = veterinarians.takeFirst();
            veterinarian.setDog(pets.pollFirst());
            tasks.add(service.submit(veterinarian));
        }
        waiting(tasks);
    }
}
