package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {
    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    @Override
    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    @Override
    public void delete(int userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public Meal get(int userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.getAll(userId);
        return (meals == null) ? Collections.emptyList() : meals;
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> filteredMeals = repository.getFiltered(userId, startDate, endDate, startTime, endTime);
        return (filteredMeals == null) ? Collections.emptyList() : filteredMeals;
    }
}