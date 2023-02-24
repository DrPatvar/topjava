package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(ID, USER_ID);
        assertMatch(userMeal1, meal);
    }

    @Test
    public void getNotFoundMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getForUserWithNoMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(ID, GUEST_ID));
    }

    @Test
    public void delete() {
        service.delete(ID, USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(ID, USER_ID));
    }

    @Test
    public void deleteNotFoundMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteForUserWithNoMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(ID, GUEST_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, userMeal3, userMeal2, userMeal1, userMeal6, userMeal5, userMeal4);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(userMeal2.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateForUserWithNoMeal() {
        Meal updated = getUpdated();
        Assert.assertThrows(NotFoundException.class, () -> service.update(updated, GUEST_ID));
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> mealFilter = service.getBetweenInclusive(LocalDate.of(2023, 2, 16), LocalDate.of(2023, 2, 16), USER_ID);
        assertMatch(mealFilter, userMeal4, userMeal5, userMeal6);
    }

    @Test
    public void create() {
        Meal meal = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = meal.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(meal, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}