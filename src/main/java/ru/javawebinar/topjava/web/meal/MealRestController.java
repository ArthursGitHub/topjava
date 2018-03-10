package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public class MealRestController {
    @Autowired
    private MealService service;

    public Collection<Meal> getAll(int userId) {
        return service.getAll(userId);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public Meal create(int userId, Meal meal) {
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int userId, int id) {
        service.delete(userId, id);
    }

    public void update(int userId, Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(userId, meal);
    }

/*    public Meal getByMail(String email) {
        return service.getByEmail(email);
    }*/
}