package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealDAO;
import ru.javawebinar.topjava.util.DAOFactory;
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
  private MealDAO mealDAO;

  @Override
  public void init() throws ServletException {
    super.init();
    mealDAO = DAOFactory.getDAO();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    if (action == null) {
      action = "getmeal";
    }

    switch (action) {
      case "getmeal" : {
        List<Meal> mealList = mealDAO.getAll();
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
        Meal meal = mealDAO.get(mealId);
        req.setAttribute("meal", meal);
        getServletContext().getRequestDispatcher("/editmeal.jsp").forward(req, resp);
        break;
      }
      case "deletemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        mealDAO.remove(mealId);

        List<Meal> mealList = mealDAO.getAll();
        List<MealWithExceed> filteredWithExceeded = MealsUtil.getFilteredWithExceeded(mealList, START_TIME, END_TIME, CALORIES_PER_DAY);

        req.setAttribute("mealList", filteredWithExceeded);

        resp.sendRedirect("meals");
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

        mealDAO.add(formatDateTime, description, Integer.valueOf(calories));

        resp.sendRedirect("meals");
        break;
      }
      case "updatemeal" : {
        Integer mealId = Integer.valueOf(req.getParameter("mealId"));
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String dateTime = req.getParameter("dateTime");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        mealDAO.update(formatDateTime, description, Integer.valueOf(calories), mealId);

        resp.sendRedirect("meals");
        break;
      }
    }
  }
}
