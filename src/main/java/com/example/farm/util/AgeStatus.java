package com.example.farm.util;

import lombok.Getter;

public enum AgeStatus {
    PUPPY(1.5),
    ADULT(2.0),
    OLD(1.8);

    @Getter
    private final double amountOfFeed; //one meal

    AgeStatus(double amountOfFeed) {
        this.amountOfFeed = amountOfFeed;
    }
}
