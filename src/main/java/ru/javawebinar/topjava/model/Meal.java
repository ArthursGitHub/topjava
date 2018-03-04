package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Meal {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private int id;
    private static AtomicInteger counter = new AtomicInteger();


    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id = counter.incrementAndGet();
    }

    public Meal(LocalDateTime dateTime, String description, int calories, int mealId) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id = mealId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
