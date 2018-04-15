package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;

@RequestMapping(value = "/meals")
@Controller
public class JspMealController {

  @Autowired
  private MealService service;

  @GetMapping("/create")
  public String createMeal(HttpServletRequest request, Model model) {
    int userId = AuthorizedUser.id();
    final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
    model.addAttribute("meal", meal);
    return "mealForm";
  }

  @GetMapping("/delete")
  public String deleteMeal(HttpServletRequest request) {
    int userId = AuthorizedUser.id();
    int paramId = getId(request);
    System.out.println(paramId);
    service.delete(paramId, userId);
    return "redirect:/meals";
  }

  @GetMapping("/update")
  public String updateMeal(HttpServletRequest request, Model model) {
    int userId = AuthorizedUser.id();
    final Meal meal = service.get(getId(request), userId);
    model.addAttribute("meal", meal);
    return "mealForm";
  }

  @GetMapping(params = "all")
  public String allMeal(Model model) {
    return null;
  }

  @PostMapping()
  public String setMeal(HttpServletRequest request) {
    int userId = AuthorizedUser.id();
    Meal meal = new Meal(
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories"))
    );

    String id = request.getParameter("id");
    if(id.isEmpty()){
      service.create(meal, userId);// ???
    } else {
      meal.setId(Integer.parseInt(id));
      service.update(meal, userId);
    }
    return "redirect:/meals";
  }

  @PostMapping("/filter")
  public String filter(HttpServletRequest request, Model model) {
    LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
    LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
    LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
    LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

    model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
    return "meals";
  }

  private int getId(HttpServletRequest request) {
    String paramId = Objects.requireNonNull(request.getParameter("id"));
    return Integer.parseInt(paramId);
  }

  public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
    int userId = AuthorizedUser.id();

    List<Meal> mealsDateFiltered = service.getBetweenDates(
            orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

    return MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
            orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)
    );
  }
}
