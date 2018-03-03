package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by arthur on 3/2/18.
 */

public class MealServlet extends HttpServlet {
  private static final Logger log = getLogger(MealServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    List<Meal> mealList = MealsUtil.getMeals();
    List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealList, START_TIME, END_TIME, CALORIES_PER_DAY);

    req.setAttribute("mealList", filteredWithExceeded);

    getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
  }
}
