package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    MealService mealService;

    @GetMapping("")
    public String showAll(Model model) {
        log.info("mealAll");
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String startDate,
                        @RequestParam String endDate,
                        @RequestParam String startTime,
                        @RequestParam String endTime,
                        Model model){
        log.info("filter meal date");
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(DateTimeUtil.parseLocalDate(startDate),
                DateTimeUtil.parseLocalDate(endDate), SecurityUtil.authUserId());
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                DateTimeUtil.parseLocalTime(startTime),DateTimeUtil.parseLocalTime(endTime)));
        return "meals";
    }

    @GetMapping("/create")
    public String newMeal(Model model){
        log.info("create meal");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)  );
        return "mealForm";
    }

    @PostMapping("/save")
    public String createAndUpdate(@RequestParam(required = false) Integer id,
                                  @RequestParam String dateTime,
                                  @RequestParam String description,
                                  @RequestParam int calories){
        log.info("create meal");
        LocalDateTime mealDateTime = DateTimeUtil.dateTimeParser(dateTime);
        Meal meal = new Meal(id, mealDateTime, description, calories);
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String edit (@RequestParam int id,
                        Model model){
        log.info("edit meal");
        model.addAttribute(mealService.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id){
        log.info("delete" );
        mealService.delete (id,SecurityUtil.authUserId() );
        return "redirect:/meals";
    }
}
