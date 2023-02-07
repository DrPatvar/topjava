package ru.javawebinar.topjava.mealCrud;

import ru.javawebinar.topjava.model.Meal;

public interface MealCrud {
    void update(Meal meal);
    void delete(int id);
    void save(Meal meal);

}
