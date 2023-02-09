package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao = new MealDao();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String localDateTime = request.getParameter("datetime");
        Meal meal;
        if (idStr == null || idStr.length() == 0) {
            meal = new Meal(TimeUtil.parseDateTime(localDateTime), description, Integer.parseInt(calories));
            mealDao.create(meal);
        } else {
            meal = new Meal(Integer.parseInt(idStr), TimeUtil.parseDateTime(localDateTime), description, Integer.parseInt(calories));
            mealDao.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
            /*if (!idStr.isEmpty() || idStr!=null){
                Integer id = Integer.parseInt(idStr);
            }*/
            Meal meal;
            switch (action) {
                case "delete":
                    String idStr = request.getParameter("id");
                    Integer id= Integer.parseInt(idStr);
                    log.debug("delete meal");
                    mealDao.delete(id);
                    response.sendRedirect("meals");
                    return;
                case "add":
                    log.debug("add new meal");
                    request.getRequestDispatcher("editMeals.jsp").forward(request, response);
                    break;
                case "update":
                    String idStr1 = request.getParameter("id");
                    Integer id1 = Integer.parseInt(idStr1);
                    log.debug("update meal");
                    meal = mealDao.get(id1);
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("editMeals.jsp").forward(request, response);
                    break;
                default:
                    log.debug("no action redirect meals");
                    response.sendRedirect("meals");
            }
        request.setAttribute("meal", mealTos);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
