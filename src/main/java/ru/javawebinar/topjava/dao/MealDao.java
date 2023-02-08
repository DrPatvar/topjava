package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.mealCrud.MealCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Counter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class MealDao implements MealCrud {

    public static Map<Integer, Meal> meals;

    static {
        meals = new HashMap<>();
        meals.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public MealDao() {
    }

    @Override
    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    @Override
    public void save(Meal meal) {
        int id = Counter.COUNT.getAndIncrement();
        meal.setId(id);
        meals.put(id, meal);
    }

    public void delete(int id) {
    meals.remove(id);
    }

}
