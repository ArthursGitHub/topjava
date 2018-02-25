package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        List<UserMeal> filteredMealList = mealList
                .stream()
                .filter(p -> {
                    LocalTime localTime = p.getDateTime().toLocalTime();
                    return (localTime.compareTo(startTime) > 0 && endTime.compareTo(localTime) > 0);
                })
                .collect(Collectors.toList());

        Integer caloriesPerMeal = filteredMealList
                .stream()
                .collect(Collectors.summingInt(p -> p.getCalories()));

        boolean exceed = caloriesPerMeal > caloriesPerDay;

        List<UserMealWithExceed> filteredMealExceedList = filteredMealList
                .stream()
                .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), exceed))
                .collect(Collectors.toList());

        return filteredMealExceedList;
    }
}
