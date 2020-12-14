package com.example.farm.entities;

import com.example.farm.service.models.Trainer;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TrainingPlayground implements Callable<Boolean> {

    private final ConcurrentLinkedDeque<Dog> dogs;
    private final BlockingDeque<Trainer> trainers;
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final List<Future<Boolean>> tasks = new ArrayList<>();

    public TrainingPlayground(Workforce workforce, List<Dog> dogs) {
        this.dogs = dogs.stream()
                .filter(d -> !d.isAccustomed())
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
        this.trainers = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("Trainer"))
                .map(w -> (Trainer) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        trainers.forEach(t -> t.setTrainers(trainers));
    }

    @SneakyThrows
    @Override
    public Boolean call() {

        if (trainers.isEmpty()) return false;

        while (!dogs.isEmpty()) {
            Trainer trainer = trainers.takeFirst();
            Dog dog = dogs.pollFirst();
            trainer.setDog(dog);
            tasks.add(service.submit(trainer));
        }
        for (Future<Boolean> task : tasks) {
            task.get();
        }
        service.shutdown();
        return true;
    }
}
