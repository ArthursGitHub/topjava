package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repo = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        final Integer[] mealId = {0};

        MealsUtil.MEALS.forEach(meal -> {
                    int userId = mealId[0] / 6;
                    save(userId, meal);
                    mealId[0]++;
                }
        );
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userRepo = repo.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (userRepo == null) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userRepo.computeIfAbsent(meal.getId(), i -> meal);
            return meal;
        }
        // treat case: update, but absent in storage
        userRepo.computeIfPresent(meal.getId(), (i, meal1) -> meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> userRepo = repo.get(userId);
        if (userRepo == null) {
            return false;
        }
        return (userRepo.remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> userRepo = repo.get(userId);
        if (userRepo == null) {
            return null;
        }
        return userRepo.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> userRepo = repo.get(userId);
        if (userRepo == null) {
            return null;
        }
        return userRepo.values();
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> meals = getAll(userId);
        return meals.stream()
                .filter(meal -> {
                    LocalDateTime dateTime = meal.getDateTime();
                    return DateTimeUtil.isBetween(dateTime.toLocalDate(), startDate, endDate);
                })
                .filter(meal -> {
                    LocalDateTime dateTime = meal.getDateTime();
                    return DateTimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime);
                })
                .collect(Collectors.toList());
    }
}
