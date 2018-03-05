package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealDao;
import ru.javawebinar.topjava.service.impl.MemoryMealDaoImpl;
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
  private MealDao mealDao;

  @Override
  public void init() throws ServletException {
    super.init();
    mealDao = new MemoryMealDaoImpl();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    if (action == null) {
      action = "getmeal";
    }

    switch (action) {
      case "getmeal" : {
        List<Meal> mealList = mealDao.getAll();
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
        Meal meal = mealDao.get(mealId);
        req.setAttribute("meal", meal);
        getServletContext().getRequestDispatcher("/editmeal.jsp").forward(req, resp);
        break;
      }
      case "deletemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        mealDao.remove(mealId);

        List<Meal> mealList = mealDao.getAll();
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealList, START_TIME, END_TIME, CALORIES_PER_DAY);

        req.setAttribute("mealList", filteredWithExceeded);

        resp.sendRedirect("meals");
        break;
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    final String DESCRIPTION = "description";
    final String CALORIES = "calories";
    final String DATE_TIME = "dateTime";

    req.setCharacterEncoding("UTF-8");
    String action = req.getParameter("action");

    switch (action) {
      case "addmeal": {
        String description = req.getParameter(DESCRIPTION);
        String calories = req.getParameter(CALORIES);
        String dateTime = req.getParameter(DATE_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        mealDao.add(formatDateTime, description, Integer.valueOf(calories));

        resp.sendRedirect("meals");
        break;
      }
      case "updatemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        String description = req.getParameter(DESCRIPTION);
        String calories = req.getParameter(CALORIES);
        String dateTime = req.getParameter(DATE_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        mealDao.update(formatDateTime, description, Integer.valueOf(calories), mealId);

        resp.sendRedirect("meals");
        break;
      }
    }
  }
}
