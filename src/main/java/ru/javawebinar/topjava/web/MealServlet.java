package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealAction;
import ru.javawebinar.topjava.util.MealFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by arthur on 3/2/18.
 */

public class MealServlet extends HttpServlet {
  private static final Logger log = getLogger(MealServlet.class);
  private final MealAction mealProcessor = MealFactory.getMeal();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    if (action == null) {
      action = "getmeal";
    }

    switch (action) {
      case "getmeal" : {
        List<Meal> mealList = mealProcessor.getMeals();
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealList, START_TIME, END_TIME, CALORIES_PER_DAY);

        req.setAttribute("mealList", filteredWithExceeded);

        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
        break;
      }
      case "addmeal": {
        getServletContext().getRequestDispatcher("/addmeal.jsp").forward(req, resp);
        break;
      }
      case "editmeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        Meal meal = mealProcessor.getMeal(mealId);
        req.setAttribute("meal", meal);
        getServletContext().getRequestDispatcher("/editmeal.jsp").forward(req, resp);
        break;
      }
      case "deletemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        mealProcessor.removeMeal(mealId);

        List<Meal> mealList = mealProcessor.getMeals();
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealList, START_TIME, END_TIME, CALORIES_PER_DAY);

        req.setAttribute("mealList", filteredWithExceeded);

        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
        break;
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String action = req.getParameter("action");

    switch (action) {
      case "addmeal": {
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String dateTime = req.getParameter("dateTime");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        mealProcessor.addMeal(new Meal(formatDateTime, description, Integer.valueOf(calories)));

        resp.sendRedirect("meals");
        break;
      }
      case "editmeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String dateTime = req.getParameter("dateTime");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        mealProcessor.addMeal(new Meal(formatDateTime, description, Integer.valueOf(calories), mealId));

        resp.sendRedirect("meals");
        break;
      }
      case "deletemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        mealProcessor.removeMeal(mealId);
        break;
      }
    }
  }
}
