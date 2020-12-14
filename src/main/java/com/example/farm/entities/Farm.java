package com.example.farm.entities;

import com.example.farm.service.models.*;
import com.example.farm.util.AgeStatus;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Data
@Slf4j
public class Farm {

    private TrainingPlayground trainingPlayground;
    private List<Booth> booths = new ArrayList<>();
    private Workforce workforce;
    private final ExecutorService service = Executors.newCachedThreadPool();

    public Farm(Workforce workforce, List<Dog> dogs) {
        this.workforce = workforce;
        this.trainingPlayground = new TrainingPlayground(workforce, dogs);

        dogs.forEach(d -> booths.add(new Booth(d)));

        dogs.forEach(System.out::println);
    }

    @SneakyThrows
    public void liveOneDay() {

        feedThePets();
        veterinarianTreatment();
        cleanBooths();
        hugs();
        booths.stream()
                .filter(b -> b.getDog().getStatus() == AgeStatus.OLD)
                .forEach(Booth::oldRelax);
        goWork();
        Future<Boolean> task = service.submit(trainingPlayground);
        task.get();
        feedThePets();
        System.out.println("DOne!!)))!))");
        service.shutdown();
        booths.forEach(Booth::goingSleep);
    }

    @SneakyThrows
    protected void feedThePets() {

        ConcurrentLinkedDeque<Dog> hungryPets = booths.stream()
                .map(Booth::getDog).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        BlockingDeque<Cook> cookers = workforce.getWorkers().stream()
                .filter(w -> w.getClass().getSimpleName().contains("Cook"))
                .map(w -> (Cook) w)
                .collect(Collectors.toCollection(LinkedBlockingDeque::new));
        cookers.forEach(c -> c.setWorkers(cookers));

        List<Future<Boolean>> tasks = new ArrayList<>();

        if (cookers.isEmpty()) return;

        while (!hungryPets.isEmpty()) {
            Cook cook = cookers.takeFirst();
            cook.setHungryDog(hungryPets.pollFirst());
            tasks.add(service.submit(cook));
        }
        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }

    @SneakyThrows
    protected void veterinarianTreatment() {

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

        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }

    @SneakyThrows
    protected void hugs() {

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

        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }

    @SneakyThrows
    protected void goWork() {
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

        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }

    @SneakyThrows
    protected void cleanBooths() {

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

        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }

}
