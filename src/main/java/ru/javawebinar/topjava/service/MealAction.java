package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by arthur on 3/3/18.
 */
public interface MealAction {
  void addMeal(Meal meal);
  void removeMeal(int id);
  void updateMeal(int id, Meal meal);
  List<Meal> getMeal();
}
