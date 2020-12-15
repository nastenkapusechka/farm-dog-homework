package com.example.farm.entities;

import com.example.farm.util.Ansi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

//у каждой собачки будет своя будка))
@Slf4j
@ToString
public class Booth {

    @ToString.Exclude
    private final static AtomicInteger counter = new AtomicInteger(1);

    @Getter
    private final Dog dog;
    @Getter
    private final int ID = counter.getAndIncrement();
    @Setter @Getter
    private boolean isDirty;

    public Booth(Dog dog) {
        this.dog = dog;
        this.dog.setBoothId(ID);
        this.isDirty = true;
    }

    public void oldRelax() {
        log.info(Ansi.CYAN.code + "{} goes relax.." + Ansi.RESET.code, dog.getName());
        dog.setHungry(true);
    }

    public void goingSleep() {
        log.info(Ansi.BRIGHT_GREEN.code + "{}: wof! wof! (good night)" + Ansi.RESET.code, dog.getName());
        this.isDirty = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booth booth = (Booth) o;
        return ID == booth.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
