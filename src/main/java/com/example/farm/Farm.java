package com.example.farm;

import com.example.farm.entities.Aviary;
import com.example.farm.entities.Dog;
import com.example.farm.service.ServiceStaff;
import com.example.farm.factory.EmployeeFactory;
import com.example.farm.util.AgeStatus;
import com.example.farm.util.Employee;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Farm {

    private final ExecutorService executorService;
    private FutureTask<Boolean> task;

    private final List<Aviary> aviaries;
    private final ServiceStaff cleaner;
    private final ServiceStaff cook;
    private final ServiceStaff cynologist;
    private final ServiceStaff dogHugger;
    private final ServiceStaff trainer;
    private final ServiceStaff veterinarian;

    public Farm(Dog... dogs) {

        List<Dog> dogs1 = new ArrayList<>(Arrays.asList(dogs));
        this.executorService = Executors.newCachedThreadPool();

        this.aviaries = Stream.of(
                new Aviary(dogs1.subList(0, dogs.length / 2)),
                new Aviary(dogs1.subList(dogs.length / 2, dogs.length - 1))
        ).collect(Collectors.toList());

        EmployeeFactory employeeFactory = new EmployeeFactory(dogs1, aviaries);

        this.cleaner = employeeFactory.getEmployee(Employee.CLEANER);
        this.cook = employeeFactory.getEmployee(Employee.COOK);
        this.cynologist = employeeFactory.getEmployee(Employee.CYNOLOGIST);
        this.dogHugger = employeeFactory.getEmployee(Employee.DOG_HUGGER);
        this.trainer = employeeFactory.getEmployee(Employee.TRAINER);
        this.veterinarian = employeeFactory.getEmployee(Employee.VETERINARIAN);
    }

    @SneakyThrows
    public void liveOneDay() {

        List<Dog> dogs = new ArrayList<>();

//        for (Aviary aviary : aviaries) {
//            dogs.addAll(aviary.getDogsWhoLiveHere());
//            aviary.getDogsWhoLiveHere().clear();
//        }

        eating();
        therapy();
        cleaning();
        hugging();
        //дрессировка,работа, отдых
        FutureTask<Boolean> training = new FutureTask<>(trainer);
        FutureTask<Boolean> working = new FutureTask<>(cynologist);
        executorService.execute(training);
        executorService.execute(working);
        training.get();
        working.get();
//        dogs = dogs.stream()
//                .filter(d -> d.getStatus() == AgeStatus.OLD)
//                .collect(Collectors.toList());
//
//        aviaries.get(0).getDogsWhoLiveHere().addAll(dogs.subList(0, dogs.size() / 2));
//        aviaries.get(1).getDogsWhoLiveHere().addAll(dogs.subList(dogs.size() / 2, dogs.size() - 1));

        eating();
        executorService.shutdown();
    }

    @SneakyThrows
    protected void eating() {
        task = new FutureTask<>(cook);
        executorService.execute(task);
        task.get();
    }

    @SneakyThrows
    protected void therapy() {
        task = new FutureTask<>(veterinarian);
        executorService.execute(task);
        task.get();
    }

    @SneakyThrows
    protected void cleaning() {
        task = new FutureTask<>(cleaner);
        executorService.execute(task);
        task.get();
    }

    @SneakyThrows
    protected void hugging() {
        task = new FutureTask<>(dogHugger);
        executorService.execute(task);
        task.get();
    }

}
