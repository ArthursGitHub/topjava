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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
      List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
      System.out.println(filteredWithExceeded);
    }

  public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    // TODO return filtered list with correctly exceeded field

    Map<LocalDate, List<UserMeal>> mealListGroupedByDay = mealList
            .stream()
            .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()));

    List<UserMealWithExceed> userMealList = new ArrayList<>();

    mealListGroupedByDay
            .forEach((localDate, mealListPerDay) -> {

              Integer sumCaloriesPerDay = mealListPerDay
                      .stream()
                      .mapToInt(UserMeal::getCalories).sum();

              boolean exceed = sumCaloriesPerDay > caloriesPerDay;

              List<UserMealWithExceed> userMealListPerDay = mealListPerDay
                      .stream()
                      .filter(meal -> {
                        LocalTime localTime = meal.getDateTime().toLocalTime();
                        return TimeUtil.isBetween(localTime, startTime, endTime);
                      })
                      .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed))
                      .collect(Collectors.toList());

              userMealList.addAll(userMealListPerDay);  // как нужно переписать эту часть кода чтобы уйти от работы с коллекциями?
            });

    return userMealList;
  }

/*
  public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    Map<LocalDate, Integer> caloriesSumByDate = mealList
            .stream()
            .collect(
                    Collectors.groupingBy(p -> p.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories))
            );

    List<UserMealWithExceed> userMealList = mealList
            .stream()
            .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
            .map(meal -> {
              boolean exceed = caloriesSumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
              UserMealWithExceed userMealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
              return userMealWithExceed;
            })
            .collect(Collectors.toList());

    return userMealList;
  }
*/

}
