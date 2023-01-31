package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> list = new ArrayList<>();
        UserMealWithExcess userMealWithExcess;
        HashMap<LocalDate, Integer> mapMeals = new HashMap<>();
        for (UserMeal meal : meals
        ) {
            mapMeals.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals
        ) {
            userMealWithExcess = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), count(mapMeals, meal) > caloriesPerDay);
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(userMealWithExcess);
            }
        }
        return list;
    }


    public static Integer count(HashMap<LocalDate, Integer> map, UserMeal meal) {
        for (Map.Entry<LocalDate, Integer> m : map.entrySet()
        ) {
            if (meal.getDateTime().toLocalDate().equals(m.getKey())) {
                return m.getValue();
            }
        }
        return 0;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
     meals.stream().collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories)));
        return null;
    }
}
