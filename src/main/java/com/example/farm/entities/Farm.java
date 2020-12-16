package com.example.farm.entities;

import com.example.farm.statistics.Statistic;
import com.example.farm.strategy.WorkingMethods;
import com.example.farm.strategy.impl.*;
import com.example.farm.util.AgeStatus;
import com.example.farm.util.Ansi;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Data
@Slf4j
public class Farm {

    private TrainingPlayground trainingPlayground;
    private List<Booth> booths = new ArrayList<>();
    private Workforce workforce;
    private final ExecutorService service = Executors.newCachedThreadPool();
    private WorkingMethods workingMethods;
    private final Statistic statistic;

    public Farm(Workforce workforce, List<Dog> dogs) {
        this.workforce = workforce;
        this.trainingPlayground = new TrainingPlayground(workforce, dogs);
        this.workingMethods = new WorkingMethods(workforce, booths, service);
        statistic = new Statistic(dogs);

        dogs.forEach(d -> booths.add(new Booth(d)));

        System.out.println();
        dogs.forEach(d -> System.out.println(Ansi.BRIGHT_MAGENTA.code + d + Ansi.RESET.code));

    }

    @SneakyThrows
    public void liveOneDay() {

        System.out.println();

        workingMethods.setStrategy(new CookingStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        workingMethods.setStrategy(new VeterinarianStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        workingMethods.setStrategy(new CleanStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        workingMethods.setStrategy(new HugsStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        oldDogsGoingRelax();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        //i would like to create a similar "TrainingStrategy" for trainers,
        //but requirement was stronger than me!
        Future<Boolean> task = service.submit(trainingPlayground);
        task.get();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        workingMethods.setStrategy(new PoliceStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        workingMethods.setStrategy(new CookingStrategy());
        workingMethods.doWork();
        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        service.shutdown();

        dogsGoingSleep();
        TimeUnit.SECONDS.sleep(1);

        statistic.showStatistic();
    }

    protected void dogsGoingSleep() {
        booths.forEach(Booth::goingSleep);
    }

    protected void oldDogsGoingRelax() {
        booths.stream()
                .filter(b -> b.getDog().getStatus() == AgeStatus.OLD)
                .forEach(Booth::oldRelax);
    }

}
