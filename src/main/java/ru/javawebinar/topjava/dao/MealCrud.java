package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealCrud {
    Meal get(int id);
    Collection<Meal> getAll();
    void update(Meal meal);
    void delete(int id);
    void create(Meal meal);

}
