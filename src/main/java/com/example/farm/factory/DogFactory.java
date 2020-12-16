package com.example.farm.factory;

import com.example.farm.entities.Dog;
import com.example.farm.util.AgeStatus;

import java.util.Random;

//dog is a plain class, it contains only information, without logic,
//so, i made a factory which instantiate dogs:)
public final class DogFactory {

    public static Dog getDog(String name) {
        Random random = new Random();

        int age = random.nextInt(12);
        boolean isHealthy = Math.random() >= 0.3;
        boolean isHappy = Math.random() <= 0.5;
        AgeStatus status = age <= 2 ? AgeStatus.PUPPY : age <= 8 ? AgeStatus.ADULT : AgeStatus.OLD;
        boolean isAccustomed = status != AgeStatus.PUPPY;

        return Dog.builder()
                .name(name)
                .age(age)
                .isAccustomed(isAccustomed)
                .isHappy(isHappy)
                .isHealthy(isHealthy)
                .isHungry(true)
                .status(status)
                .build();
    }
}
