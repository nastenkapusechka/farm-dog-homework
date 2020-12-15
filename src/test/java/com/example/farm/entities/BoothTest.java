package com.example.farm.entities;

import com.example.farm.factory.DogFactory;
import com.example.farm.util.AgeStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoothTest {

    private final Booth booth = new Booth(DogFactory.getDog("Nancy"));

    @Test
    public void testLogic() {
        booth.goingSleep();
        assertTrue(booth.isDirty());

        booth.getDog().setStatus(AgeStatus.OLD);
        booth.getDog().setHungry(false);

        booth.oldRelax();

        assertTrue(booth.getDog().isHungry());
    }

}