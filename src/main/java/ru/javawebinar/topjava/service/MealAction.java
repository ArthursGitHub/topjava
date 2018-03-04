package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by arthur on 3/3/18.
 */
public interface MealAction {
  void addMeal(Meal meal);
  void removeMeal(int id);
  Meal getMeal(int id);
  List<Meal> getMeals();
}
