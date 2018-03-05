package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealDao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arthur on 3/3/18.
 */
public class MemoryMealDaoImpl implements MealDao {

  private final Map<Integer, Meal> mealMap;
  private final AtomicInteger counter;

  public MemoryMealDaoImpl() {
    mealMap = new ConcurrentHashMap<>();
    counter = new AtomicInteger();

    add(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    add(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    add(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    add(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    add(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    add(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
  }

  @Override
  public Meal add(LocalDateTime dateTime, String description, int calories) {
    Meal meal = newMealProducer(dateTime, description, calories);
    return mealMap.put(meal.getId(), meal);
  }

  @Override
  public Meal update(LocalDateTime dateTime, String description, int calories, int mealId) {
    Meal meal = new Meal(dateTime, description, calories, mealId);
    return mealMap.put(meal.getId(), meal);
  }

  @Override
  public void remove(int id) {
    mealMap.remove(id);
  }

  @Override
  public List<Meal> getAll() {
    return new ArrayList<>(mealMap.values());
  }

  @Override
  public Meal get(int id) {
    return mealMap.get(id);
  }

  private int getNewMealId() {
    return counter.incrementAndGet();
  }

  private Meal newMealProducer(LocalDateTime dateTime, String description, int calories) {
    return new Meal(dateTime, description, calories, getNewMealId());
  }
}
