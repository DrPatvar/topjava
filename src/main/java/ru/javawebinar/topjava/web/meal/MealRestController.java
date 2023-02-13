package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;
    private int authUserId = SecurityUtil.authUserId();

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll( int CaloriesPerDay) {
        log.info("getAll");
        return service.getAll(authUserId, CaloriesPerDay);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId, id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(authUserId, meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId, id);
    }

    public void update(Meal meal) {
        log.info("update {}", meal);
        assureIdConsistent(meal, meal.getId());
        service.update(authUserId, meal);
    }

}