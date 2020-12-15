package com.example.farm.statistics;

import com.example.farm.entities.Dog;
import com.example.farm.util.AgeStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Statistic {

    private final List<Dog> dogsStatistic;
    private double healthyDogs;
    private double sadDogs;
    private double feedEaten;
    private Long puppies;
    private Long adult;
    private Long old;

    public Statistic(List<Dog> dogsStatistic) {
        this.dogsStatistic = dogsStatistic;
        collectStatistic();
    }

    private void collectStatistic() {

        Map<AgeStatus, Long> ages = dogsStatistic.stream()
                .collect(Collectors.groupingBy(Dog::getStatus, Collectors.counting()));

        puppies = ages.get(AgeStatus.PUPPY);
        adult = ages.get(AgeStatus.ADULT);
        old = ages.get(AgeStatus.OLD);

        feedEaten = dogsStatistic.stream()
                .map(d -> d.getStatus().getAmountOfFeed())
                .reduce(Double::sum).orElse(0.0);

        healthyDogs = dogsStatistic.stream()
                .filter(Dog::isHealthy)
                .count();
        healthyDogs = 100.0 / dogsStatistic.size() * healthyDogs;

        sadDogs = dogsStatistic.stream()
                .filter(d -> !d.isHappy())
                .count();
        sadDogs = 100.0 / dogsStatistic.size() * sadDogs;
    }

    public void showStatistic() {

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("            STATISTICS");
        System.out.printf("%.2f %% of dogs were healthy\n", healthyDogs);
        System.out.printf("%.2f %% of dogs were sad\n", sadDogs);
        System.out.printf("%.2f amount of feed were eaten\n", feedEaten);
        System.out.printf("Count of puppies: %d\n", puppies);
        System.out.printf("Count of adult dogs: %d\n", adult);
        System.out.printf("Count of old dogs: %d\n", old);
        System.out.println("--------------------------------------------------");
    }
}
