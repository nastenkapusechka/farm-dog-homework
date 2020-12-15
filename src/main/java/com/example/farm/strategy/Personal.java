package com.example.farm.strategy;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface Personal {

    void doWork(Workforce workforce, List<Booth> booths, ExecutorService service);

    @SneakyThrows
    default void waiting(List<Future<Boolean>> tasks) {
        for (Future<Boolean> task : tasks) {
            task.get();
        }
    }
}
