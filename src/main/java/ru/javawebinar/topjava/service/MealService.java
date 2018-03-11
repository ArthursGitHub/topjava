package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealService {
  Meal create(int userId, Meal meal);
  void delete(int userId, int id);
  Meal get(int userId, int id);
  Collection<Meal> getAll(int userId);
  Collection<Meal> getFiltered(int userId, LocalDateTime start, LocalDateTime end);
  void update(int userId, Meal meal);
}