package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Comparator<MealWithExceed> MEAL_COMPARATOR = (meal1, meal2) -> {
      LocalDateTime dateTime1 = meal1.getDateTime();
      LocalDateTime dateTime2 = meal2.getDateTime();
      return -dateTime1.compareTo(dateTime2);
    };

    private final MealService service;

  @Autowired
  public MealRestController(MealService service) {
    this.service = service;
  }

  public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.getId();
        List<MealWithExceed> withExceeded = MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        withExceeded.sort(MEAL_COMPARATOR);
        return withExceeded;
    }

  public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
      int userId = AuthorizedUser.getId();

      if (startTime == null) {
        startTime = LocalTime.MIN;
      }
      if (endTime == null) {
        startTime = LocalTime.MAX;
      }

      Collection<Meal> filteredMeals = service.getFiltered(userId, startDate, endDate, startTime, endTime);
      List<MealWithExceed> withExceeded = MealsUtil.getWithExceeded(filteredMeals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
      withExceeded.sort(MEAL_COMPARATOR);
      return withExceeded;
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.getId();
        return service.get(userId, id);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.getId();
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.getId();
        service.delete(userId, id);
    }

    public void update(Meal meal) {
        int userId = AuthorizedUser.getId();
        service.update(userId, meal);
    }
}
