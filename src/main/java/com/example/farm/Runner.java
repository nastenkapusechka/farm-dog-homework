package com.example.farm;

import com.example.farm.entities.Dog;
import com.example.farm.factory.DogFactory;

public class Runner {
    public static void main(String[] args) {
        Dog nancy = DogFactory.getDog("Nancy");
        Dog betty = DogFactory.getDog("Betty");
        Dog rex = DogFactory.getDog("Rex");
        Dog tom = DogFactory.getDog("Tom");

        System.out.println(nancy);
        System.out.println(betty);
        System.out.println(rex);
        System.out.println(tom);

        Farm farm = new Farm(nancy, betty, rex, tom);
        farm.liveOneDay();

        System.out.println(nancy);
        System.out.println(betty);
        System.out.println(rex);
        System.out.println(tom);
    }
}
