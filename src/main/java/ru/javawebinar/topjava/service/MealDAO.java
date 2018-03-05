package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arthur on 3/3/18.
 */
public interface MealDAO {
  Meal add(LocalDateTime dateTime, String description, int calories);
  Meal update(LocalDateTime dateTime, String description, int calories, int mealId);
  void remove(int id);
  Meal get(int id);
  List<Meal> getAll();
}
