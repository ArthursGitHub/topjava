package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealAction;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by arthur on 3/3/18.
 */
public class MemoryMeal implements MealAction {

  private static ConcurrentHashMap<Integer, Meal> mealMap;

  static {
    List<Meal> mealList = Stream.of(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    )
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    mealMap = new ConcurrentHashMap<>();

    for (Meal meal : mealList) {
      mealMap.put(meal.getId(), meal);
    }
  }

  @Override
  public void addMeal(Meal meal) {
    mealMap.put(meal.getId(), meal);
  }

  @Override
  public void removeMeal(int id) {
    mealMap.remove(id);
  }

  @Override
  public List<Meal> getMeals() {
    return new ArrayList(mealMap.values());
  }

  @Override
  public Meal getMeal(int id) {
    Meal meal = mealMap.get(id);
    return meal;
  }
}
