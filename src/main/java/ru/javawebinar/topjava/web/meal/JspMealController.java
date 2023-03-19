package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String showAll(Model model) {
        log.info("mealAll");
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String startDate,
                        @RequestParam String endDate,
                        @RequestParam String startTime,
                        @RequestParam String endTime,
                        Model model) {
        log.info("filter meal date");
        model.addAttribute("meals", getBetween(DateTimeUtil.parseLocalDate(startDate), DateTimeUtil.parseLocalTime(startTime),
                DateTimeUtil.parseLocalDate(endDate), DateTimeUtil.parseLocalTime(endTime)));
        return "meals";
    }

    @GetMapping("/create")
    public String newMeal(Model model){
        log.info("create meal");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)  );
        return "mealForm";
    }

    @PostMapping
    public String createOrUpdate(@RequestParam(required = false) Integer id,
                                 @RequestParam String dateTime,
                                 @RequestParam String description,
                                 @RequestParam int calories) {
        log.info("create meal");
        LocalDateTime mealDateTime = LocalDateTime.parse(dateTime);
        Meal meal = new Meal(id, mealDateTime, description, calories);
        if (id != null) {
            update(meal, id);
        } else {
            create(meal);
        }
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String edit (@RequestParam int id,
                        Model model){
        log.info("edit meal");
        model.addAttribute(get(id));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String remove(@RequestParam int id) {
        log.info("delete");
        delete(id);
        return "redirect:/meals";
    }
}
