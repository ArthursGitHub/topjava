package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by arthur on 3/3/18.
 */
public interface MealDao {
  Meal add(Meal meal);
  Meal update(Meal meal);
  void remove(int id);
  Meal get(int id);
  List<Meal> getAll();
}
