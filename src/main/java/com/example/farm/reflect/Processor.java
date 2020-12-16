package com.example.farm.reflect;

import com.example.farm.entities.Dog;
import com.example.farm.entities.Farm;
import com.example.farm.entities.Workforce;
import com.example.farm.factory.DogFactory;
import com.example.farm.reflect.annotations.Dogs;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.*;

public final class Processor {

    private static final List<Dog> dogs = new ArrayList<>();
    private static Workforce workforce;

    @SneakyThrows
    public static void process(Class<?> clazz) {
        init(clazz);
        Farm farm = new Farm(workforce, dogs);
        farm.liveOneDay();
    }

    @SneakyThrows
    private static void init(@NonNull Class<?> clazz) {

        Object instance = clazz.getDeclaredConstructor().newInstance();

        Method config = clazz.getDeclaredMethod("configure", Workforce.class);
        config.setAccessible(true);

        workforce = new Workforce();
        config.invoke(instance, workforce);

        Field processedField = null;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Dogs.class)) {
                processedField = field;
                processedField.setAccessible(true);
                break;
            }
        }
        checkArgument(processedField != null, "Field is not exist or not " +
                "annotated by @Dogs annotation!");
        checkArgument(processedField.get(instance) instanceof String[],
                "Field should be String[]! (maybe it is null?)");

        String[] names = (String[]) processedField.get(instance);

        checkArgument(names.length != 0, processedField.getName() + " is empty!");

        Arrays.stream(names).forEach(name -> checkArgument(!name.trim().equals(""),
                "One of the names is empty!!"));

        for (String name : names) {
            dogs.add(DogFactory.getDog(name));
        }
    }
}
