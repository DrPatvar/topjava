package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int ID = 100003;
    public static final int NOT_FOUND = 10;

    public static Meal userMeal1 = new Meal(ID, LocalDateTime.of(2023, 2, 17, 8, 30), "Breakfast", 500);
    public static Meal userMeal2 = new Meal(ID + 1, LocalDateTime.of(2023, 2, 17, 12, 25), "Lunch", 500);
    public static Meal userMeal3 = new Meal(ID + 2, LocalDateTime.of(2023, 2, 17, 18, 30), "Dinner", 700);
    public static Meal userMeal4 = new Meal(ID + 3, LocalDateTime.of(2023, 2, 16, 8, 15), "Breakfast", 350);
    public static Meal userMeal5 = new Meal(ID + 4, LocalDateTime.of(2023, 2, 16, 12, 1), "Lunch", 1350);
    public static Meal userMeal6 = new Meal(ID + 5, LocalDateTime.of(2023, 2, 16, 19, 11), "Dinner", 750);
    public static Meal adminMeal = new Meal(ID + 6, LocalDateTime.of(2023, 2, 22, 8, 15), "Breakfast", 450);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, 2, 18, 12, 25, 55), "Lunch", 1200);
    }

    public static Meal getUpdated() {
        Meal update = new Meal(userMeal2);
        update.setDateTime(LocalDateTime.of(2023, 2, 20, 8, 25, 15));
        update.setDescription("Breakfast");
        update.setCalories(350);
        return update;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsAll(expected);
    }
}
