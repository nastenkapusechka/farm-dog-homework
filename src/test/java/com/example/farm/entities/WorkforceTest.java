package com.example.farm.entities;

import com.example.farm.service.ServiceStaff;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class WorkforceTest {

    private Workforce workforce;

    @Test
    public void testCount() {
        workforce = new Workforce();
        workforce
                .cleaners(5)
                .cookers(5)
                .cynologists(5)
                .veterinarians(5)
                .dogHuggers(5)
                .trainers(5);
        assertThat(workforce.getWorkers().size(), is(30));
        Map<String, Long> map = workforce.getWorkers()
                .stream()
                .collect(Collectors.groupingBy(ServiceStaff::getSPECIALIZATION, Collectors.counting()));

        assertThat(map, hasEntry("Cleaner", 5L));
        assertThat(map, hasEntry("Cook", 5L));
        assertThat(map, hasEntry("Cynologist", 5L));
        assertThat(map, hasEntry("Dog hugger", 5L));
        assertThat(map, hasEntry("Trainer", 5L));
        assertThat(map, hasEntry("Veterinarian", 5L));
    }

    @Test
    public void testNothing() {
        workforce = new Workforce();
        workforce
                .trainers(0)
                .dogHuggers(0)
                .veterinarians(0)
                .cynologists(0)
                .cookers(0)
                .cleaners(0);

        assertThat(workforce.getWorkers(), hasSize(0));
    }

}