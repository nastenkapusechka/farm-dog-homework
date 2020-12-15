package com.example.farm.strategy.impl;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Dog;
import com.example.farm.entities.Workforce;
import com.example.farm.strategy.Personal;
import com.example.farm.service.models.Cynologist;
import com.example.farm.util.AgeStatus;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class PoliceStrategy implements Personal {

    @Override
    @SneakyThrows
    public void doWork(Workforce workforce, List<Booth> booths, ExecutorService service) {

        ConcurrentLinkedDeque<Dog> dogsWhoWorks = booths.stream()
                .map(Booth::getDog)
                .filter(d -> d.getStatus() == AgeStatus.ADULT)
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        BlockingDeque<Cynologist> cynologists = workforce.getWorkers()
                .stream()
                .filter(w -> w.getClass().getSimpleName().contains("Cynologist"))
                .map(w -> (Cynologist) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));

        if (cynologists.isEmpty()) return;

        while (!dogsWhoWorks.isEmpty()) {
            Cynologist cynologist = cynologists.takeFirst();
            cynologist.addTeammate(dogsWhoWorks.pollFirst());
            cynologists.putLast(cynologist);
        }

        List<Future<Boolean>> tasks = cynologists.stream()
                .map(service::submit)
                .collect(Collectors.toList());

        waiting(tasks);
    }
}
