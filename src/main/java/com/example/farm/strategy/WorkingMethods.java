package com.example.farm.strategy;

import com.example.farm.entities.Booth;
import com.example.farm.entities.Workforce;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkingMethods {

    private Personal personal;
    private final Workforce workforce;
    private final List<Booth> booths;
    private final ExecutorService service;

    public WorkingMethods(Workforce workforce, List<Booth> booths, ExecutorService service) {
        this.workforce = workforce;
        this.booths = booths;
        this.service = service;
    }

    public void setStrategy(Personal method) {
        this.personal = method;
    }

    public void doWork() {
        this.personal.doWork(workforce, booths, service);
    }
}
