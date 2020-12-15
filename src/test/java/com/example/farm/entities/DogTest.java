package com.example.farm.entities;

import com.example.farm.factory.DogFactory;
import com.example.farm.util.AgeStatus;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DogTest {

    @RepeatedTest(10)
    public void testInstance() {
        Dog dog = DogFactory.getDog("Nancy");
        if (dog.getAge() <= 2) {
            assertThat(dog.getStatus(), is(AgeStatus.PUPPY));
            assertFalse(dog.isAccustomed());
        } else if (dog.getAge() <= 8) {
            assertThat(dog.getStatus(), is(AgeStatus.ADULT));
            assertTrue(dog.isAccustomed());
        } else {
            assertThat(dog.getStatus(), is(AgeStatus.OLD));
            assertTrue(dog.isAccustomed());
        }

    }

    @Test
    public void testEquals() {
        Dog dog1 = DogFactory.getDog("Nancy");
        Dog dog2 = DogFactory.getDog("Nancy");

        assertThat(dog1, is(not(equalTo(dog2))));
    }

}