package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.Util.orElse;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
  private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

  @Autowired
  protected MealService service;


  protected Meal get(int id) {
    int userId = AuthorizedUser.id();
    log.info("get meal {} for user {}", id, userId);
    return service.get(id, userId);
  }

  protected void delete(int id) {
    int userId = AuthorizedUser.id();
    log.info("delete meal {} for user {}", id, userId);
    service.delete(id, userId);
  }

  protected void update(Meal meal, int id) {
    int userId = AuthorizedUser.id();
    assureIdConsistent(meal, id);
    log.info("update {} for user {}", meal, userId);
    service.update(meal, userId);
  }

  protected Meal create(Meal meal) {
    int userId = AuthorizedUser.id();
    checkNew(meal);
    log.info("create {} for user {}", meal, userId);
    return service.create(meal, userId);
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
