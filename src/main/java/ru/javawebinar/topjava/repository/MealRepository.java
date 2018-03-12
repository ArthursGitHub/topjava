package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(int userId, Meal meal);
    boolean delete(int userId, int id);
    Meal get(int userId, int id);
    Collection<Meal> getAll(int userId);
    Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
