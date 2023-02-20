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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
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
        assertThat(USER_MEAL1).isEqualTo(meal);
    }

    @Test
    public void getNotFoundMeal() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotFoundUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(ID, 1));
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
    public void deleteNotFoundUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(ID, 1));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        Assert.assertEquals(6, all.size());
        assertMatch(all, USER_MEAL3, USER_MEAL2, USER_MEAL1, USER_MEAL6, USER_MEAL5, USER_MEAL4);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(101, USER_ID), getUpdated());
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