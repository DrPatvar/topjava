package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.dao.MealDao.meals;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealDao mealDao = new MealDao();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String localDateTime = request.getParameter("datetime");
        Meal meal;
        if (idStr == null || idStr.length() == 0) {
            meal = new Meal(TimeUtil.dateParse(localDateTime), description, Integer.parseInt(calories));
            mealDao.save(meal);
        } else {
            meal = new Meal(Integer.parseInt(idStr), TimeUtil.dateParse(localDateTime), description, Integer.parseInt(calories));
            mealDao.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealTos = MealsUtil.filteredByStreams(meals.values(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
        String idStr = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        if (idStr != null || idStr.length() != 0) {
            Integer id = Integer.parseInt(idStr);
            Meal meal;
            switch (action) {
                case "delete":
                    mealDao.delete(id);
                    response.sendRedirect("meals");
                    return;
                case "update":
                    meal = meals.get(id);
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("editMeals.jsp").forward(request, response);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }
        request.setAttribute("meal", mealTos);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
