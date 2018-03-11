package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.getId();

        List<MealWithExceed> withExceeded = MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        withExceeded.sort((o1, o2) -> {
                    LocalDateTime dateTime1 = o1.getDateTime();
                    LocalDateTime dateTime2 = o2.getDateTime();
                    return -dateTime1.compareTo(dateTime2);
                }
        );
        return withExceeded;
    }

    public List<MealWithExceed> getAll(LocalDateTime start, LocalDateTime end) {
      int userId = AuthorizedUser.getId();
      Collection<Meal> meals = service.getAll(userId);
      List<Meal> collect = meals.stream()
              .filter(meal -> {
                LocalDateTime dateTime = meal.getDateTime();
                return DateTimeUtil.isBetween(dateTime, start, end);
              })
              .collect(Collectors.toList());

      List<MealWithExceed> withExceeded = MealsUtil.getWithExceeded(collect, MealsUtil.DEFAULT_CALORIES_PER_DAY);
      withExceeded.sort((o1, o2) -> {
                LocalDateTime dateTime1 = o1.getDateTime();
                LocalDateTime dateTime2 = o2.getDateTime();
                return -dateTime1.compareTo(dateTime2);
              }
      );
      return withExceeded;
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.getId();
        return service.get(userId, id);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.getId();
        return service.create(userId, meal);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.getId();
        service.delete(userId, id);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.getId();
        assureIdConsistent(meal, id);
        service.update(userId, meal);
    }
}
