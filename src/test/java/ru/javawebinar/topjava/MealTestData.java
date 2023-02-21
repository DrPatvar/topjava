package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int ID_MEAL1 = 100;
    public static final int ID_MEAL2 = 101;
    public static final int ID_MEAL3 = 102;
    public static final int ID_MEAL4 = 103;
    public static final int ID_MEAL5 = 104;
    public static final int ID_MEAL6 = 105;
    public static final int NOT_FOUND = 10;

    public static final Meal USER_MEAL1 = new Meal(ID_MEAL1, LocalDateTime.of(2023, 02, 17, 8, 30, 05), "BreakFast", 500);
    public static final Meal USER_MEAL2 = new Meal(ID_MEAL2, LocalDateTime.of(2023, 02, 17, 12, 25, 05), "Lunch", 500);
    public static final Meal USER_MEAL3 = new Meal(ID_MEAL3, LocalDateTime.of(2023, 02, 17, 18, 30, 05), "Dinner", 700);
    public static final Meal USER_MEAL4 = new Meal(ID_MEAL4, LocalDateTime.of(2023, 02, 16, 8, 15, 17), "BreakFast", 350);
    public static final Meal USER_MEAL5 = new Meal(ID_MEAL5, LocalDateTime.of(2023, 02, 16, 12, 00, 05), "Lunch", 1350);
    public static final Meal USER_MEAL6 = new Meal(ID_MEAL6, LocalDateTime.of(2023, 02, 16, 19, 11, 11), "Dinner", 750);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, 02, 18, 12, 25, 55), "Lunch", 1200);
    }

    public static Meal getUpdated() {
        Meal update = new Meal(USER_MEAL2);
        update.setDateTime(LocalDateTime.of(2023, 02, 20, 8, 25, 15));
        update.setDescription("Breakfast");
        update.setCalories(350);
        return update;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
