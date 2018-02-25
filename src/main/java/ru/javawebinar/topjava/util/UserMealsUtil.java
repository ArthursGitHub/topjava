package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 2000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 100),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
      List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14, 0), 1300);
//        .toLocalDate();
//        .toLocalTime();
    }

  public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    // TODO return filtered list with correctly exceeded field

    List<UserMeal> filteredMealList = mealList
            .stream()
            .filter(p -> {
              LocalTime localTime = p.getDateTime().toLocalTime();
              return (localTime.compareTo(startTime) > 0 && endTime.compareTo(localTime) > 0);
            })
            .collect(Collectors.toList());

    Map<LocalDate, List<UserMeal>> filteredMealListGroupedByDay = filteredMealList
            .stream()
            .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate()));

    List<UserMealWithExceed> userMealList = new ArrayList<>();

    filteredMealListGroupedByDay
            .forEach((localDate, mealListPerDay) -> {

              Integer sumCaloriesPerDay = mealListPerDay
                      .stream()
                      .collect(Collectors.summingInt(p -> p.getCalories()));

              boolean exceed = sumCaloriesPerDay > caloriesPerDay;

              List<UserMealWithExceed> userMealListPerDay = mealListPerDay
                      .stream()
                      .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), exceed))
                      .collect(Collectors.toList());

              userMealList.addAll(userMealListPerDay);
            });

    return userMealList;
  }
}
