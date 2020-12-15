package com.example.farm.entities;

import com.example.farm.factory.DogFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class FarmTest {

    private final Workforce workforce = mock(Workforce.class);

    @Test
    public void testBooths() {
        List<Dog> dogs = Stream.of(
                DogFactory.getDog("Nancy"),
                DogFactory.getDog("Betty")
        ).collect(Collectors.toList());

        dogs.forEach(d -> assertEquals(d.getBoothId(), 0));

        Farm farm = new Farm(workforce, dogs);

        dogs.forEach(d -> assertThat(d.getBoothId(), is(not(0))));
    }
}
