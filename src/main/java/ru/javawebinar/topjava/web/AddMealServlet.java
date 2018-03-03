package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealAction;
import ru.javawebinar.topjava.util.MealFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by arthur on 3/3/18.
 */
public class AddMealServlet extends HttpServlet {
  private static final Logger log = getLogger(AddMealServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/addmeal.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");
    MealAction mealProcessor = MealFactory.getMeal();

    switch (action) {
      case "addmeal": {
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String dateTime = req.getParameter("dateTime");

        LocalDateTime now = LocalDateTime.now();

        mealProcessor.addMeal(new Meal(now, description, Integer.valueOf(calories)));

        resp.sendRedirect("meals");
        break;
      }
      case "editmeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
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
