package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            if (userId == meal.getUserId()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (userId == repository.get(id).getUserId()) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId & meal.getId() == id)
                .findFirst().get();
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> mealList;
        mealList = repository.values()
                .stream()
                .filter(m -> m.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return mealList;
    }
}

